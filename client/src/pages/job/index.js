import React, { useState, useEffect } from "react";
import Header from '../../components/header/header';
import MenuLeft from '../../components/menu/menu-left';
import Moment from 'react-moment';
import BootstrapTable from 'react-bootstrap-table-next';
import filterFactory, { selectFilter, dateFilter, textFilter, numberFilter, multiSelectFilter } from 'react-bootstrap-table2-filter';
import paginationFactory from 'react-bootstrap-table2-paginator';
import {URL_TAG, URL_JOB} from '../../constants/path'
import {makeSalaryMin, makeSalaryMax} from '../../components/job-utils'

export default function Job() {

    const [collapsemenu, setCollapsemenu] = useState((localStorage['colapseMenu'] === 'true') || false);
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
        wrap(document.querySelector(".card-pagination"), 'react-bootstrap-table-pagination');
    }, []);

    const wrap = (node, tag) => {
        node.parentNode.insertBefore(document.getElementsByClassName(tag)[0], node);
        node.previousElementSibling.appendChild(node);
    }

    const getListTag = () => {
        fetch(URL_TAG)
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
        fetch(URL_JOB)
            .then(resp => resp.json())
            .then(resp => {
                let data = resp;
                data.map(item => {
                    item['salaryMin'] = makeSalaryMin(item['description']);
                    item['salaryMin'] = makeSalaryMin(item['description']);
                    item['salaryMax'] = makeSalaryMax(item['description']);

                    const company = JSON.parse(item['company']);
                    item['companyName'] = company.Name
                    item['country'] = company.country;
                    item['members'] = company.size;
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
            dataField: 'companyName',
            sort: true,
            filter: textFilter()
        },
        {
            text: 'Country',
            dataField: 'country',
            sort: true,
            filter: textFilter()
        },
        {
            text: 'Number members',
            dataField: 'members',
            sort: true,
            filter: numberFilter()
        },
        {
            text: 'Date Post',
            dataField: 'datePost',
            sort: true,
            style: {
                maxWidth: '80px'
            },
            formatter: data => <Moment format="YYYY/MM/DD">{data}</Moment>,
            filter: dateFilter()
        },
        {
            text: 'Date Expixed',
            dataField: 'dateExpired',
            sort: true,
            style: {
                maxWidth: '80px'
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
            text: 'Requirement',
            dataField: 'requirement',
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
                {/*  Main content  */}
                <section class="content">
                    <div class="row">
                        <div class="col-12">
                            <div class="card">
                                <div class="card-pagination">
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
