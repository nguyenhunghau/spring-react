import React, { useState, useEffect } from "react";
import Header from '../../components/header/header';
import MenuLeft from '../../components/menu/menu-left';
import DataTable, { createTheme, Button } from 'react-data-table-component';
import dataJob from '../../list.json';
import '../job/style.css';
import Moment from 'react-moment';
import BootstrapTable from 'react-bootstrap-table-next';
import filterFactory, { selectFilter, dateFilter, textFilter, numberFilter, multiSelectFilter } from 'react-bootstrap-table2-filter';
import paginationFactory from 'react-bootstrap-table2-paginator';
import 'bootstrap/dist/css/bootstrap.css';


export default function Table() {

    const [collapsemenu, setCollapsemenu] = useState(localStorage['colapseMenu'] || false);
    const [tagList, setTagList] = useState({});
    const [dataJob, setDataJob] = useState([]);

    const changeMenu = () => {
        const newValue = !collapsemenu;
        localStorage['colapseMenu'] = newValue;
        setCollapsemenu(newValue);
    }

    useEffect(() => {
        getListJob();
        getListTag();
    }, []);

    const getListTag = () => {
        fetch('http://localhost:8088/getListTag')
            .then(resp => resp.json())
            .then(resp => {
                let optionObject = {};
                resp.map(item => {
                    optionObject[item.name] = item.name;
                })
                setTagList(optionObject);
            })
    }

    const getListJob = () => {
        fetch('http://localhost:8088/list')
            .then(resp => resp.json())
            .then(resp => {
                let data = resp;
                data.map(item => {
                    item['salaryMin'] = makeSalaryMin(item['description']);
                    item['salaryMax'] = makeSalaryMax(item['description']);
                });
                setDataJob(data);
            })
    }

    const defaultSorted = [{
        dataField: 'datePost',
        order: 'desc'
      }];

    const columns = [
        {
            text: 'Title',
            dataField: 'title',
            sort: true,
            filter: textFilter()
        },
        {
            text: 'Company',
            dataField: 'company',
            sort: true,
            filter: textFilter()
        },
        {
            text: 'Date Post',
            dataField: 'datePost',
            sort: true,
            style: {
                maxWidth: '120px'
            },
            formatter: data => <Moment format="YYYY/MM/DD">{data}</Moment>,
            filter: dateFilter()
        },
        {
            text: 'Date Expixed',
            dataField: 'dateExpired',
            sort: true,
            style: {
                maxWidth: '100px'
            },
            formatter: data => (data ? <Moment format="YYYY/MM/DD">{data}</Moment> : ''),
            filter: dateFilter()
        },
        {
            text: 'Salary Min',
            dataField: 'salaryMin',
            sort: true,
            style: {
                minWidth: '100px'
            },
            filter: numberFilter(),
        },
        {
            text: 'Salary Max',
            dataField: 'salaryMax',
            sort: true,
            style: {
                minWidth: '100px'
            },
            filter: numberFilter()
        },
        {
            text: 'Description',
            dataField: 'description',
            sort: true,
            wrap: true,
            formatter: data => makeDescription(data),
            filter: textFilter()
        },
        {
            text: 'Link',
            dataField: 'link',
            sort: true,
            classes: 'link-job',
            formatter: data => <a target="_blank" href={data} style={{ width: "110px" }}>{data}</a>,
            filter: textFilter()
        },
        {
            text: 'Tags',
            dataField: 'tagIds',
            sort: true,
            style: {
                width: '120px'
            },
            wrap: true,
            filter: multiSelectFilter({
                options: tagList
            })
        },
        {
            text: 'Address',
            dataField: 'address',
            sort: true,
            style: {
                width: '120px'
            },
            wrap: true,
            filter: textFilter()
        },
    ];

    const makeSalaryMin = (description) => {
        let myObject = JSON.parse(description);
        if ('salaryMin' in myObject) {
            return parseInt(myObject.salaryMin);
        }
        let salary = myObject.salary.replace(/\$|,|\.|\+USD|usd|m vnd/g, '').trim();
        var salaryArray = salary.split(' ');
        if (salary.startsWith('From') && !isNaN(salaryArray[salaryArray.length - 1])) {
            return parseInt(salaryArray[salaryArray.length - 1]);
        } else if (!isNaN(salaryArray[0])) {
            return parseInt(salaryArray[0]);
        }
        return 0;
    }

    const makeSalaryMax = (description) => {
        let myObject = JSON.parse(description);
        if ('salaryMax' in myObject) {
            return Math.max(parseInt(myObject.salaryMax), parseInt(myObject.jobSalary));
        }
        let salary = myObject.salary.replace(/\$|,|\.|\+|USD|usd|m vnd/g, '').trim();
        var salaryArray = salary.split(' ');
        if (salary.startsWith('From')) {
            return -1;
        }
        if (salary.startsWith('Up') || !isNaN(salaryArray[salaryArray.length - 1])) {
            return parseInt(salaryArray[salaryArray.length - 1]);
        }
        return -1;
    }


    const makeDescription = (description) => {
        let result = '';
        let myObject = JSON.parse(description);
        if ('description' in myObject) {
            return myObject.benefits + myObject.description
        }
        return myObject.benefitValue;
    }

    const customTotal = (from, to, size) => (
        <span className="react-bootstrap-table-pagination-total">
            Showing { from} to { to} of { size} Results
        </span>
    );

    const options = {
        paginationSize: 4,
        pageStartIndex: 0,
        // sizePerPage: 50,
        hidePageListOnlyOnePage: true,
        showTotal: true,
        paginationTotalRenderer: customTotal,
        sizePerPageList: [{
            text: '30', value: 30
          }, {
            text: '50', value: 50
          }, {
            text: '100', value: 100
          }, {
            text: 'All', value: dataJob.length
          }] 
    };

    return (
        <div className={collapsemenu ? 'sidebar-mini layout-fixed sidebar-collapse' : 'wrapper'} >
            <Header changeMenu={changeMenu} />
            <MenuLeft />
            <div class="content-wrapper">
                {/*  Content Header (Page header)  */}
                <section class="content-header">
                    <div class="container-fluid">
                        <div class="row mb-2">
                            <div class="col-sm-6">
                                <h1>DataTables</h1>
                            </div>
                            <div class="col-sm-6">
                                <ol class="breadcrumb float-sm-right">
                                    <li class="breadcrumb-item"><a href="#">Home</a></li>
                                    <li class="breadcrumb-item active">DataTables</li>
                                </ol>
                            </div>
                        </div>
                    </div>{/*  /.container-fluid  */}
                </section>

                {/*  Main content  */}
                <section class="content">
                    <div class="row">
                        <div class="col-12">
                            <div class="card">
                                <div class="card-header">
                                    <h3 class="card-title">DataTable with minimal features & hover style</h3>
                                </div>
                                {/*  /.card-header  */}
                                <div class="card-body">
                                    <BootstrapTable keyField='id' data={dataJob}
                                        columns={columns} defaultSorted={defaultSorted} filter={filterFactory()}
                                        pagination={paginationFactory(options)} />
                                </div>
                            </div>
                        </div>
                        {/*  /.col  */}
                    </div>
                    {/*  /.row  */}
                </section>
                {/*  /.content  */}
            </div>
        </div >
    )
}