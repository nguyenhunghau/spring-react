import React from 'react';
import { Component } from "react";
import globalVal from '../../components/GlobalVal';
import DataTable, { Button } from 'react-data-table-component';
import memoize from 'memoize-one';

export default class MyComponent extends Component {

    constructor(props) {
        super(props);
        this.state = {
            data: []
        }
        // createTheme('solarized', {
        //     text: {
        //       primary: '#268bd2',
        //       secondary: '#2aa198',
        //     },
        //     background: {
        //       default: '#95b5d8',
        //     },
        //     context: {
        //       background: '#cb4b16',
        //       text: '#FFFFFF',
        //     },
        //     divider: {
        //       default: '#073642',
        //     },
        //     action: {
        //       button: 'rgba(0,0,0,.54)',
        //       hover: 'rgba(0,0,0,.08)',
        //       disabled: 'rgba(0,0,0,.12)',
        //     },
        //   });
    }

    componentDidMount() {
        fetch(globalVal.accountUrl)
            .then(res => res.json())
            .then((rows) => {
                this.setState({ data: rows })
            })
    }

    render() {

        const columns = memoize(clickHandler => [
            {
                name: 'Name',
                selector: 'name',
                sortable: true,
            },
            {
                name: 'Email',
                selector: 'email',
                sortable: true
            },
            {
                name: 'Created',
                selector: 'created',
                sortable: true
            },
            {
                name: 'is CS member',
                selector: 'isCsMember',
                sortable: true
            },
            {
                cell:(row) => <button onClick={clickHandler} id={row.name}>Action</button>,
                ignoreRowClick: true,
                allowOverflow: true,
                button: true,
            }
        ]);

        return (
            <DataTable
                columns={columns}
                data={this.state.data}
                selectableRows
            />
        )
    }
};