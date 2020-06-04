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

export default function Table() {

    const [collapsemenu, setCollapsemenu] = useState(false);
    const [selectOptions, setSelectOptions] = useState({});

    const changeMenu = () => {
        setCollapsemenu(!collapsemenu);
    }

    const getTag = () => {
        fetch('http://localhost:8088/getTag')
            .then(resp => resp.json())
            .then(resp => {
                let optionObject = {};
                resp.map(item => {
                    optionObject[item.name] = item.name;
                })
                setSelectOptions(optionObject);
            })
    }

    const selectOption = {
        0: 'good',
        1: 'Bad',
        2: 'unknown'
    };

    const columnss = [{
        dataField: 'id',
        text: 'Product ID'
    }, {
        dataField: 'title',
        text: 'Product Name'
    }, {
        dataField: 'company',
        text: 'Product Quailty',
        formatter: cell => selectOptions[cell],
        filter: selectFilter({
            options: selectOptions
        })
    }];

    const columns = [
        {
            text: 'Title',
            dataField: 'title',
            sort: true,
            width: '120px',
            wrap: true,
            filter: textFilter()
        },
        {
            text: 'Company',
            dataField: 'company',
            sort: true,
            width: '100px',
            wrap: true,
            filter: textFilter()
        },
        {
            text: 'Date Post',
            dataField: 'datePost',
            sort: true,
            width: '90px',
            formatter: data => <Moment format="YYYY/MM/DD">{data}</Moment>,
            filter: dateFilter()
        },
        {
            text: 'Date Expixed',
            dataField: 'dateExpired',
            sort: true,
            width: '120px',
            formatter: data => (data ? <Moment format="YYYY/MM/DD">{data}</Moment> : ''),
            filter: dateFilter()
        },
        {
            text: 'Salary Min',
            dataField: 'description',
            sort: true,
            wrap: true,
            formatter: data => makeSalaryMin(data),
            filter: numberFilter()
        },
        {
            text: 'Salary Max',
            dataField: 'description',
            sort: true,
            wrap: true,
            formatter: data => makeSalaryMax(data),
            filter: numberFilter()
        },
        {
            text: 'Description',
            dataField: 'description',
            sort: true,
            wrap: true,
            // formatter: data => makeDescription(data),
            filter: textFilter()
        },
        {
            text: 'Link',
            dataField: 'link',
            sort: true,
            width: '120px',
            wrap: true,
            formatter: data => <a target="_blank" href={data} style={{ width: "110px" }}>{data}</a>,
            filter: textFilter()
        },
        {
            text: 'Tags',
            dataField: 'tagIds',
            sort: true,
            width: '120px',
            wrap: true,
            filter: multiSelectFilter({
                options: selectOptions
            })
        },
        {
            text: 'Address',
            dataField: 'address',
            sort: true,
            width: '120px',
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
        if(salary.startsWith('From') && !isNaN(salaryArray[salaryArray.length - 1])) {
            return parseInt(salaryArray[salaryArray.length - 1]);
        } else if(!isNaN(salaryArray[0])) {
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
        if(salary.startsWith('From')) {
            return -1;
        }
        if(salary.startsWith('Up') || !isNaN(salaryArray[salaryArray.length - 1])) {
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

    const conditionalRowStyles = [
        {
            when: row => row.calories < 300,
            style: {
                backgroundColor: 'green',
                color: 'white',
                '&:hover': {
                    cursor: 'pointer',
                },
            },
        },
    ];

    const defaultSorted = [{
        dataField: 'time',
        order: 'desc'
    }];

    const options = {
        // pageStartIndex: 0,
        sizePerPage: 50,
        hideSizePerPage: true,
        hidePageListOnlyOnePage: true
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
                                    <BootstrapTable bootstrap4 keyField='id' data={dataJob} 
                                    columns={columns} defaultSorted={defaultSorted} filter={filterFactory()} 
                                    pagination={ paginationFactory(options) } />
                                    {/* <DataTable
                                        columns={columns}
                                        data={dataJob}
                                        // conditionalRowStyles={conditionalRowStyles}
                                        // selectableRows
                                        pagination="true"
                                        theme="solarized"
                                    /> */}
                                    <div class="card">
                                        <div class="card-header">
                                            <h3 class="card-title">DataTable with default features</h3>
                                        </div>
                                        {/*  /.card-header  */}
                                        <div class="card-body">
                                            <table id="example1" class="table table-bordered table-striped">
                                                <thead>
                                                    <tr>
                                                        <th>Rendering engine</th>
                                                        <th>Browser</th>
                                                        <th>Platform(s)</th>
                                                        <th>Engine version</th>
                                                        <th>CSS grade</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <tr>
                                                        <td>Trident</td>
                                                        <td>Internet
                                                        Explorer 4.0
                    </td>
                                                        <td>Win 95+</td>
                                                        <td> 4</td>
                                                        <td>X</td>
                                                    </tr>
                                                    <tr>
                                                        <td>Trident</td>
                                                        <td>Internet
                                                        Explorer 5.0
                    </td>
                                                        <td>Win 95+</td>
                                                        <td>5</td>
                                                        <td>C</td>
                                                    </tr>
                                                    <tr>
                                                        <td>Trident</td>
                                                        <td>Internet
                                                        Explorer 5.5
                    </td>
                                                        <td>Win 95+</td>
                                                        <td>5.5</td>
                                                        <td>A</td>
                                                    </tr>
                                                    <tr>
                                                        <td>Trident</td>
                                                        <td>Internet
                                                        Explorer 6
                    </td>
                                                        <td>Win 98+</td>
                                                        <td>6</td>
                                                        <td>A</td>
                                                    </tr>
                                                    <tr>
                                                        <td>Trident</td>
                                                        <td>Internet Explorer 7</td>
                                                        <td>Win XP SP2+</td>
                                                        <td>7</td>
                                                        <td>A</td>
                                                    </tr>
                                                    <tr>
                                                        <td>Trident</td>
                                                        <td>AOL browser (AOL desktop)</td>
                                                        <td>Win XP</td>
                                                        <td>6</td>
                                                        <td>A</td>
                                                    </tr>
                                                    <tr>
                                                        <td>Gecko</td>
                                                        <td>Firefox 1.0</td>
                                                        <td>Win 98+ / OSX.2+</td>
                                                        <td>1.7</td>
                                                        <td>A</td>
                                                    </tr>
                                                    <tr>
                                                        <td>Gecko</td>
                                                        <td>Firefox 1.5</td>
                                                        <td>Win 98+ / OSX.2+</td>
                                                        <td>1.8</td>
                                                        <td>A</td>
                                                    </tr>
                                                    <tr>
                                                        <td>Gecko</td>
                                                        <td>Firefox 2.0</td>
                                                        <td>Win 98+ / OSX.2+</td>
                                                        <td>1.8</td>
                                                        <td>A</td>
                                                    </tr>
                                                    <tr>
                                                        <td>Gecko</td>
                                                        <td>Firefox 3.0</td>
                                                        <td>Win 2k+ / OSX.3+</td>
                                                        <td>1.9</td>
                                                        <td>A</td>
                                                    </tr>
                                                    <tr>
                                                        <td>Gecko</td>
                                                        <td>Camino 1.0</td>
                                                        <td>OSX.2+</td>
                                                        <td>1.8</td>
                                                        <td>A</td>
                                                    </tr>
                                                    <tr>
                                                        <td>Gecko</td>
                                                        <td>Camino 1.5</td>
                                                        <td>OSX.3+</td>
                                                        <td>1.8</td>
                                                        <td>A</td>
                                                    </tr>
                                                    <tr>
                                                        <td>Gecko</td>
                                                        <td>Netscape 7.2</td>
                                                        <td>Win 95+ / Mac OS 8.6-9.2</td>
                                                        <td>1.7</td>
                                                        <td>A</td>
                                                    </tr>
                                                    <tr>
                                                        <td>Gecko</td>
                                                        <td>Netscape Browser 8</td>
                                                        <td>Win 98SE+</td>
                                                        <td>1.7</td>
                                                        <td>A</td>
                                                    </tr>
                                                    <tr>
                                                        <td>Gecko</td>
                                                        <td>Netscape Navigator 9</td>
                                                        <td>Win 98+ / OSX.2+</td>
                                                        <td>1.8</td>
                                                        <td>A</td>
                                                    </tr>
                                                    <tr>
                                                        <td>Gecko</td>
                                                        <td>Mozilla 1.0</td>
                                                        <td>Win 95+ / OSX.1+</td>
                                                        <td>1</td>
                                                        <td>A</td>
                                                    </tr>
                                                    <tr>
                                                        <td>Gecko</td>
                                                        <td>Mozilla 1.1</td>
                                                        <td>Win 95+ / OSX.1+</td>
                                                        <td>1.1</td>
                                                        <td>A</td>
                                                    </tr>
                                                    <tr>
                                                        <td>Gecko</td>
                                                        <td>Mozilla 1.2</td>
                                                        <td>Win 95+ / OSX.1+</td>
                                                        <td>1.2</td>
                                                        <td>A</td>
                                                    </tr>
                                                    <tr>
                                                        <td>Gecko</td>
                                                        <td>Mozilla 1.3</td>
                                                        <td>Win 95+ / OSX.1+</td>
                                                        <td>1.3</td>
                                                        <td>A</td>
                                                    </tr>
                                                    <tr>
                                                        <td>Gecko</td>
                                                        <td>Mozilla 1.4</td>
                                                        <td>Win 95+ / OSX.1+</td>
                                                        <td>1.4</td>
                                                        <td>A</td>
                                                    </tr>
                                                    <tr>
                                                        <td>Gecko</td>
                                                        <td>Mozilla 1.5</td>
                                                        <td>Win 95+ / OSX.1+</td>
                                                        <td>1.5</td>
                                                        <td>A</td>
                                                    </tr>
                                                    <tr>
                                                        <td>Gecko</td>
                                                        <td>Mozilla 1.6</td>
                                                        <td>Win 95+ / OSX.1+</td>
                                                        <td>1.6</td>
                                                        <td>A</td>
                                                    </tr>
                                                    <tr>
                                                        <td>Gecko</td>
                                                        <td>Mozilla 1.7</td>
                                                        <td>Win 98+ / OSX.1+</td>
                                                        <td>1.7</td>
                                                        <td>A</td>
                                                    </tr>
                                                    <tr>
                                                        <td>Gecko</td>
                                                        <td>Mozilla 1.8</td>
                                                        <td>Win 98+ / OSX.1+</td>
                                                        <td>1.8</td>
                                                        <td>A</td>
                                                    </tr>
                                                    <tr>
                                                        <td>Gecko</td>
                                                        <td>Seamonkey 1.1</td>
                                                        <td>Win 98+ / OSX.2+</td>
                                                        <td>1.8</td>
                                                        <td>A</td>
                                                    </tr>
                                                    <tr>
                                                        <td>Gecko</td>
                                                        <td>Epiphany 2.20</td>
                                                        <td>Gnome</td>
                                                        <td>1.8</td>
                                                        <td>A</td>
                                                    </tr>
                                                    <tr>
                                                        <td>Webkit</td>
                                                        <td>Safari 1.2</td>
                                                        <td>OSX.3</td>
                                                        <td>125.5</td>
                                                        <td>A</td>
                                                    </tr>
                                                    <tr>
                                                        <td>Webkit</td>
                                                        <td>Safari 1.3</td>
                                                        <td>OSX.3</td>
                                                        <td>312.8</td>
                                                        <td>A</td>
                                                    </tr>
                                                    <tr>
                                                        <td>Webkit</td>
                                                        <td>Safari 2.0</td>
                                                        <td>OSX.4+</td>
                                                        <td>419.3</td>
                                                        <td>A</td>
                                                    </tr>
                                                    <tr>
                                                        <td>Webkit</td>
                                                        <td>Safari 3.0</td>
                                                        <td>OSX.4+</td>
                                                        <td>522.1</td>
                                                        <td>A</td>
                                                    </tr>
                                                    <tr>
                                                        <td>Webkit</td>
                                                        <td>OmniWeb 5.5</td>
                                                        <td>OSX.4+</td>
                                                        <td>420</td>
                                                        <td>A</td>
                                                    </tr>
                                                    <tr>
                                                        <td>Webkit</td>
                                                        <td>iPod Touch / iPhone</td>
                                                        <td>iPod</td>
                                                        <td>420.1</td>
                                                        <td>A</td>
                                                    </tr>
                                                    <tr>
                                                        <td>Webkit</td>
                                                        <td>S60</td>
                                                        <td>S60</td>
                                                        <td>413</td>
                                                        <td>A</td>
                                                    </tr>
                                                    <tr>
                                                        <td>Presto</td>
                                                        <td>Opera 7.0</td>
                                                        <td>Win 95+ / OSX.1+</td>
                                                        <td>-</td>
                                                        <td>A</td>
                                                    </tr>
                                                    <tr>
                                                        <td>Presto</td>
                                                        <td>Opera 7.5</td>
                                                        <td>Win 95+ / OSX.2+</td>
                                                        <td>-</td>
                                                        <td>A</td>
                                                    </tr>
                                                    <tr>
                                                        <td>Presto</td>
                                                        <td>Opera 8.0</td>
                                                        <td>Win 95+ / OSX.2+</td>
                                                        <td>-</td>
                                                        <td>A</td>
                                                    </tr>
                                                    <tr>
                                                        <td>Presto</td>
                                                        <td>Opera 8.5</td>
                                                        <td>Win 95+ / OSX.2+</td>
                                                        <td>-</td>
                                                        <td>A</td>
                                                    </tr>
                                                    <tr>
                                                        <td>Presto</td>
                                                        <td>Opera 9.0</td>
                                                        <td>Win 95+ / OSX.3+</td>
                                                        <td>-</td>
                                                        <td>A</td>
                                                    </tr>
                                                    <tr>
                                                        <td>Presto</td>
                                                        <td>Opera 9.2</td>
                                                        <td>Win 88+ / OSX.3+</td>
                                                        <td>-</td>
                                                        <td>A</td>
                                                    </tr>
                                                    <tr>
                                                        <td>Presto</td>
                                                        <td>Opera 9.5</td>
                                                        <td>Win 88+ / OSX.3+</td>
                                                        <td>-</td>
                                                        <td>A</td>
                                                    </tr>
                                                    <tr>
                                                        <td>Presto</td>
                                                        <td>Opera for Wii</td>
                                                        <td>Wii</td>
                                                        <td>-</td>
                                                        <td>A</td>
                                                    </tr>
                                                    <tr>
                                                        <td>Presto</td>
                                                        <td>Nokia N800</td>
                                                        <td>N800</td>
                                                        <td>-</td>
                                                        <td>A</td>
                                                    </tr>
                                                    <tr>
                                                        <td>Presto</td>
                                                        <td>Nintendo DS browser</td>
                                                        <td>Nintendo DS</td>
                                                        <td>8.5</td>
                                                        <td>C/A<sup>1</sup></td>
                                                    </tr>
                                                    <tr>
                                                        <td>KHTML</td>
                                                        <td>Konqureror 3.1</td>
                                                        <td>KDE 3.1</td>
                                                        <td>3.1</td>
                                                        <td>C</td>
                                                    </tr>
                                                    <tr>
                                                        <td>KHTML</td>
                                                        <td>Konqureror 3.3</td>
                                                        <td>KDE 3.3</td>
                                                        <td>3.3</td>
                                                        <td>A</td>
                                                    </tr>
                                                    <tr>
                                                        <td>KHTML</td>
                                                        <td>Konqureror 3.5</td>
                                                        <td>KDE 3.5</td>
                                                        <td>3.5</td>
                                                        <td>A</td>
                                                    </tr>
                                                    <tr>
                                                        <td>Tasman</td>
                                                        <td>Internet Explorer 4.5</td>
                                                        <td>Mac OS 8-9</td>
                                                        <td>-</td>
                                                        <td>X</td>
                                                    </tr>
                                                    <tr>
                                                        <td>Tasman</td>
                                                        <td>Internet Explorer 5.1</td>
                                                        <td>Mac OS 7.6-9</td>
                                                        <td>1</td>
                                                        <td>C</td>
                                                    </tr>
                                                    <tr>
                                                        <td>Tasman</td>
                                                        <td>Internet Explorer 5.2</td>
                                                        <td>Mac OS 8-X</td>
                                                        <td>1</td>
                                                        <td>C</td>
                                                    </tr>
                                                    <tr>
                                                        <td>Misc</td>
                                                        <td>NetFront 3.1</td>
                                                        <td>Embedded devices</td>
                                                        <td>-</td>
                                                        <td>C</td>
                                                    </tr>
                                                    <tr>
                                                        <td>Misc</td>
                                                        <td>NetFront 3.4</td>
                                                        <td>Embedded devices</td>
                                                        <td>-</td>
                                                        <td>A</td>
                                                    </tr>
                                                    <tr>
                                                        <td>Misc</td>
                                                        <td>Dillo 0.8</td>
                                                        <td>Embedded devices</td>
                                                        <td>-</td>
                                                        <td>X</td>
                                                    </tr>
                                                    <tr>
                                                        <td>Misc</td>
                                                        <td>Links</td>
                                                        <td>Text only</td>
                                                        <td>-</td>
                                                        <td>X</td>
                                                    </tr>
                                                    <tr>
                                                        <td>Misc</td>
                                                        <td>Lynx</td>
                                                        <td>Text only</td>
                                                        <td>-</td>
                                                        <td>X</td>
                                                    </tr>
                                                    <tr>
                                                        <td>Misc</td>
                                                        <td>IE Mobile</td>
                                                        <td>Windows Mobile 6</td>
                                                        <td>-</td>
                                                        <td>C</td>
                                                    </tr>
                                                    <tr>
                                                        <td>Misc</td>
                                                        <td>PSP browser</td>
                                                        <td>PSP</td>
                                                        <td>-</td>
                                                        <td>C</td>
                                                    </tr>
                                                    <tr>
                                                        <td>Other browsers</td>
                                                        <td>All others</td>
                                                        <td>-</td>
                                                        <td>-</td>
                                                        <td>U</td>
                                                    </tr>
                                                </tbody>
                                                <tfoot>
                                                    <tr>
                                                        <th>Rendering engine</th>
                                                        <th>Browser</th>
                                                        <th>Platform(s)</th>
                                                        <th>Engine version</th>
                                                        <th>CSS grade</th>
                                                    </tr>
                                                </tfoot>
                                            </table>
                                        </div>
                                        {/*  /.card-body  */}
                                    </div>
                                    {/*  /.card  */}
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