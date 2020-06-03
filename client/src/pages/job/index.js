import React, { useState, useEffect } from "react";
import Header from '../../components/header/header';
import MenuLeft from '../../components/menu/menu-left';
import DataTable, { createTheme, Button } from 'react-data-table-component';
// import dataJob from '../../list.json';
import Moment from 'react-moment';
import './style.css';

export default function Job() {

    const [collapsemenu, setCollapsemenu] = useState(false);
    const [dataJob, setDataJob] = useState([]);

    const changeMenu = () => {
        setCollapsemenu(!collapsemenu);
    }

    useEffect(() => {
        fetch('http://localhost:8088/list')
            .then(resp => resp.json())
            .then(resp => {
                setDataJob(resp);
            })
    }, []);

    createTheme('solarized', {
        text: {
            //   primary: '#268bd2',
            //   secondary: '#2aa198',
        },
        background: {
            //   default: '#002b36',
            primary: '#268bd2',
            secondary: '#2aa198',
        },
        context: {
            background: '#cb4b16',
            text: '#FFFFFF',
        },
        divider: {
            default: 'rgba(0,0,0,.05);',
        },
        action: {
            button: 'rgba(0,0,0,.54)',
            hover: 'rgba(0,0,0,.08)',
            disabled: 'rgba(0,0,0,.12)',
        },
    });

    const columns = [
        {
            name: 'Title',
            selector: 'title',
            sortable: true,
            width: '120px',
            wrap: true
        },
        {
            name: 'Company',
            selector: 'company',
            sortable: true,
            width: '100px',
            wrap: true
        },
        {
            name: 'Date Post',
            selector: 'datePost',
            sortable: true,
            width: '120px',
            cell: row => <Moment format="YYYY/MM/DD">{row.datePost}</Moment>
        },
        {
            name: 'Date Expixed',
            selector: 'dateExpired',
            sortable: true,
            width: '120px',
            cell: row => (row.dateExpired ? <Moment format="YYYY/MM/DD">{row.dateExpired}</Moment> : '')
        },
        {
            name: 'Salary',
            sortable: true,
            wrap: true,
            width: '120px',
            cell: row => makeSalary(row.description)
        },
        {
            name: 'Description',
            sortable: true,
            wrap: true,
            cell: row => makeDescription(row.description)
        },
        {
            name: 'Link',
            selector: 'link',
            sortable: true,
            width: '120px',
            wrap: true,
            cell: row => <a target="_blank" href={row.link} style={{ width: "110px" }}>{row.link}</a>
        },
        {
            name: 'Tags',
            selector: 'tagIds',
            sortable: true,
            width: '120px',
            wrap: true
        },
        {
            name: 'Address',
            selector: 'address',
            sortable: true,
            width: '120px',
            wrap: true
        },
    ];

    const makeSalary = (description) => {
        let myObject = JSON.parse(description);
        if ('jobSalary' in myObject) {
            const salaryArray = [parseInt(myObject.jobSalary), parseInt(myObject.salaryMin), parseInt(myObject.salaryMax)];
            salaryArray.sort(function (a, b) {
                return a - b;
            });
            return salaryArray[0] + '-' + salaryArray[2];
        }
        return myObject.salary;
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
                                    <DataTable
                                        columns={columns}
                                        data={dataJob}
                                        // conditionalRowStyles={conditionalRowStyles}
                                        // selectableRows
                                        pagination="true"
                                        theme="solarized"
                                    />
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