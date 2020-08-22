import React, { useState, useEffect } from "react";
import Header from '../components/header/header';
import MenuLeft from '../components/menu/menu-left';

import { useDrop } from 'react-dnd'
import { Control } from './control'
// import update from 'immutability-helper'
import { ItemTypes } from './ItemTypes'
import ControlListData from './data'
import Container from "./container";
import styles from './builder.module.css'
import '../components/css/style.css';
import {useSelector, useDispatch} from 'react-redux';

const Builder = (props) => {

    const [controlList, setControlList] = useState([]);
    const [isRender, setIsRender] = useState(0);
    const [wasDrop, setWasDrop] = useState(true);
    const buiderSelector = useSelector(state => state.builder);

    const [collapsemenu, setCollapsemenu] = useState();


    useEffect(() => {
        console.log(buiderSelector);
        setControlList(buiderSelector.data);
    }, [buiderSelector.count]);

    const moveControl = (idOldLayout, idNewLayout, idControl, atIndex) => {
        const oldLayout = controlList.filter((c) => `${c.id}` === idOldLayout)[0];
        const newLayout = controlList.filter((c) => `${c.id}` === idNewLayout)[0];
        const { control, index } = findControl(oldLayout, idControl);
        if (!oldLayout || !newLayout) {
            return;
        }
        oldLayout.children.splice(index, 1);
        newLayout.children.splice(atIndex, 0, control);
        //Update the layout of cards by 
        // Remove control of old layout

        //Add new control at new layout
        // oldLayout.children = update(oldLayout.children, {
        //   $splice: [
        //     [index, 1],
        //     [atIndex, 0, control],
        //   ],
        // });
        setControlList(
            controlList
        )
        setIsRender(isRender + 1);
    }

    const findLayout = (id) => {
        return controlList.filter((c) => `${c.id}` === id)[0]
        // return {
        //   layout,
        //   index: cards.indexOf(layout),
        // }
    }

    const findControl = (layout, id) => {
        const control = layout.children.filter((c) => `${c.id}` === id)[0];
        return {
            control,
            index: layout.children.indexOf(control),
        }
    }

    // const [, drop] = useDrop({ accept: ItemTypes.CONTROL })

    return (
        <div className={collapsemenu ? 'sidebar-mini layout-fixed sidebar-collapse' : 'wrapper'} >
            <Header changeMenu={props.changeMenu} />
            <MenuLeft />
            <div className={`content-wrapper container ${styles.container_builder}`}>
                <div>
                    <div className={`row ${styles.container_control}`}>
                        {controlList.map((controlLayout) => (
                            <Container controlList={controlLayout} />
                        ))}
                    </div>
                </div>
            </div>
        </div>
    )
}
export default Builder;