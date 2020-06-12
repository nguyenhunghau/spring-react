import React, { useState, useEffect } from "react";
import Header from '../../components/header/header';
import MenuLeft from '../../components/menu/menu-left';
import Moment from 'react-moment';
import './style.css';
import BootstrapTable from 'react-bootstrap-table-next';
import filterFactory, { selectFilter, dateFilter, textFilter, numberFilter, multiSelectFilter } from 'react-bootstrap-table2-filter';
import paginationFactory from 'react-bootstrap-table2-paginator';
import 'bootstrap/dist/css/bootstrap.css';
import {URL_COMPANY} from '../../constants/path'

export default function Company() {
    const [collapsemenu, setCollapsemenu] = useState((localStorage['colapseMenu'] === 'true') || false);
    const [companyList, setCompanyList] = useState({});

    const changeMenu = () => {
        const newValue = !collapsemenu;
        localStorage['colapseMenu'] = newValue;
        setCollapsemenu(newValue);
    }

    useEffect(() => {
        getListCompany();
        // wrap(document.querySelector(".card-pagination"), 'react-bootstrap-table-pagination');
    }, []);

    const getListCompany = () => {
        fetch(URL_COMPANY)
            .then(resp => resp.json())
            .then(resp => {
                setCompanyList(companyList);
            })
    }

    const defaultSorted = [{
        dataField: 'salaryMax',
        order: 'desc'
    }];

    const columns = [
        {
            text: 'Company',
            dataField: 'company',
            sort: true,
            filter: textFilter()
        },
        {
            text: 'Number Job',
            dataField: 'numJob',
            sort: true,
            filter: textFilter()
        },
        {
            text: 'Salary min',
            dataField: 'salaryMin',
            sort: true,
            filter: textFilter()
        },
        {
            text: 'Salary max',
            dataField: 'salaryMax',
            sort: true,
            filter: textFilter()
        },
        {
            text: 'Tag',
            dataField: 'tag',
            sort: true,
            filter: textFilter()
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
                                    <BootstrapTable keyField='id' data={companyList}
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