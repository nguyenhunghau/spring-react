
import React, { useState, useEffect } from "react";
import {
    Link
} from "react-router-dom";

function NavItem(props) {
    var item = props.item;
    const [isExpand, setIsExpand] = useState(false);
    const [isActive, setIsActive] = useState(() => checkActiveLink());

    function checkActiveLink() {
        var pathName = window.location.pathname;
        var result = false;
        if (pathName === '/' + (item.link || '')) {
            result = true;
        }
        if (item.childMenu) {
            item.childMenu.map(child => {
                if (pathName === '/' + child.link) {
                    result = true;
                }
            });
        }
        return result;
    };

    return (
        <li className={isExpand ? 'nav-item has-treeview menu-open' : 'nav-item'}>
            <Link to={item.link || '#'} className={isActive ? 'nav-link active' : 'nav-link'} onClick={() => setIsExpand(!isExpand)} >
                <i class={item.icon}></i>
                <p>
                    {item.text}
                    {item.childMenu ? <i class="right fas fa-angle-left"></i> : ''}
                </p>
            </Link>
            <ul class="nav nav-treeview">
                {
                    item.childMenu ?
                        item.childMenu.map(child =>
                            <li class="nav-item" >
                                <Link to={child.link || '#'} className={('/' + child.link) === window.location.pathname? "nav-link active": "nav-link"}>
                                    <i class={child.icon}></i>
                                    <p>{child.text}</p>
                                </Link>
                            </li>
                        ) : ''
                }
            </ul>
        </li>
    )
}
export default NavItem;