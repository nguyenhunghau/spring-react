import React, { useEffect, useState } from 'react';
import { Component } from "react";
import {
    BrowserRouter as Router,
    Switch,
    Route,
    Link
} from "react-router-dom";

import '../css/style.css';
import NavItem from './nav-item';
import adminLTELogo from '../img/AdminLTELogo.png';
import userLogo from '../img/user2-160x160.jpg';
import { URL_MENU } from '../../constants/path'

import { useSelector, useDispatch } from 'react-redux';
import { ItemTypes } from '../../pages/ItemTypes';


const MenuLeft = () => {

    const [navData, setNavData] = useState([]);
    const account = useSelector(state => state);
    const dispatch = useDispatch();

    useEffect(() => {
        fetch(URL_MENU)
            .then(resp => resp.json())
            .then(resp => {
                setNavData(resp);
            })
    }, []);

    const expandChild = (ev) => {
        console.log(ev);
    }

    const addControl = (type) => {
        dispatch({type: 'ADD', itemType: type});
        console.log(account);
    }

    return (
        <aside class="main-sidebar sidebar-dark-primary elevation-4">
            <a href="index3.html" class="brand-link">
                <img src={adminLTELogo} alt="AdminLTE Logo" class="brand-image img-circle elevation-3" />
                <span class="brand-text font-weight-light">AdminLTE 3</span>
            </a>

            <div class="sidebar">
                <div class="user-panel mt-3 pb-3 mb-3 d-flex">
                    <div class="image">
                        <img src={userLogo} class="img-circle elevation-2" alt="User Image" />
                    </div>
                    <div class="info">
                        <a href="#" class="d-block">Alexander Pierce</a>
                    </div>
                </div>

                <nav class="mt-2">
                    <ul class="nav nav-pills nav-sidebar flex-column" data-widget="treeview" role="menu" data-accordion="false">
                        <li class="nav-item has-treeview menu-open">
                            <a href="#" class="nav-link">
                                <i class="nav-icon fas fa-edit"></i>
                                <p>
                                    Forms
                <i class="fas fa-angle-left right"></i>
                                </p>
                            </a>
                            <ul class="nav nav-treeview">
                                <li class="nav-item">
                                    <a class="nav-link" onClick={(e) => addControl(ItemTypes.TEXTBOX)}>
                                    <i class="far fa-circle nav-icon"></i>
                                        <p>Texbox</p>
                                    </a>
                                </li>
                                <li class="nav-item">
                                    <a href="#" class="nav-link" onClick={(e) => addControl(ItemTypes.PHARAPH)}>
                                        <i class="far fa-circle nav-icon"></i>
                                        <p>Text Paraph</p>
                                    </a>
                                </li>
                                <li class="nav-item">
                                    <a href="#" class="nav-link" onClick={(e) => addControl(ItemTypes.BUTTON)}>
                                        <i class="far fa-circle nav-icon"></i>
                                        <p>Button</p>
                                    </a>
                                </li>
                                <li class="nav-item">
                                    <a href="#" class="nav-link" onClick={(e) => addControl(ItemTypes.TEXTAREA)}>
                                        <i class="far fa-circle nav-icon"></i>
                                        <p>Text Area</p>
                                    </a>
                                </li>
                            </ul>
                        </li>
                    </ul>
                </nav>
            </div>
        </aside>
    )
}
export default MenuLeft;