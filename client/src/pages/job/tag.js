import React, { useState, useEffect } from "react";
import Header from '../../components/header/header';
import MenuLeft from '../../components/menu/menu-left';
import Moment from 'react-moment';
import './style.css';
import BootstrapTable from 'react-bootstrap-table-next';
import filterFactory, { selectFilter, dateFilter, textFilter, numberFilter, multiSelectFilter } from 'react-bootstrap-table2-filter';
import paginationFactory from 'react-bootstrap-table2-paginator';
import 'bootstrap/dist/css/bootstrap.css';
import {URL_TAG} from '../../constants/path'
import { makeSalaryMin, makeSalaryMax} from '../../components/job-utils'

export default function Tag() {
    const [collapsemenu, setCollapsemenu] = useState((localStorage['colapseMenu'] === 'true') || false);
    const [tagList, setTagList] = useState([]);

    const changeMenu = () => {
        const newValue = !collapsemenu;
        localStorage['colapseMenu'] = newValue;
        setCollapsemenu(newValue);
    }

    useEffect(() => {
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
            .then(companyListJson => {
                let resultJobList = [];
                companyListJson.map(item => {
                    let jobEntity = {};
                    let avrSalaymin = 0, avgSalaryMax = 0;
                    if(item.jobList.length === 0) {
                        return;
                    }
                    item.jobList.map(job => {
                        avrSalaymin+= makeSalaryMin(job['description']) || 0;
                        avgSalaryMax+= makeSalaryMax(job['description']) || 0;
                    });
                    
                    jobEntity['tagName'] = item.tagName;
                    jobEntity['numJob'] = item.jobList.length;
                    jobEntity['numCompany'] = item.jobList.map(item => item.company).filter((v, i, a) => a.indexOf(v) === i).length;
                    jobEntity['salaryMin'] = Math.round(avrSalaymin / item.jobList.length);
                    jobEntity['salaryMax'] = Math.round(avgSalaryMax / item.jobList.length);
                    resultJobList.push(jobEntity);
                });

                setTagList(resultJobList);
            })
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
            text: 'All', value: tagList.length
        }]
    };

    const defaultSorted = [{
        dataField: 'salaryMax',
        order: 'desc'
    }];

    const columns = [
        {
            text: 'Tag',
            dataField: 'tagName',
            sort: true,
            filter: textFilter()
        },
        {
            text: 'Number Job',
            dataField: 'numJob',
            sort: true,
            formatter: (cell) => cell.toLocaleString(),
            filter: numberFilter()
        },
        {
            text: 'Number Company',
            dataField: 'numCompany',
            sort: true,
            formatter: (cell) => cell.toLocaleString(),
            filter: numberFilter()
        },
        {
            text: 'Salary min',
            dataField: 'salaryMin',
            sort: true,
            formatter: (cell) => cell.toLocaleString(),
            filter: numberFilter()
        },
        {
            text: 'Salary max',
            dataField: 'salaryMax',
            sort: true,
            formatter: (cell) => cell.toLocaleString(),
            filter: numberFilter()
        }
    ]

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
                                    <BootstrapTable keyField='id' data={tagList}
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