import React, { useState, useEffect } from "react";
import Header from '../components/header/header';
import MenuLeft from '../components/menu/menu-left';

import { useDrop } from 'react-dnd'
import { Control } from './control'
// import update from 'immutability-helper'
import { ItemTypes } from './ItemTypes'
import Container from "./container";
import styles from './builder.module.css'
import { useSelector, useDispatch } from 'react-redux';

const Builder = (props) => {

    const [controlList, setControlList] = useState([]);
    const [isRender, setIsRender] = useState(0);
    const buiderSelector = useSelector(state => state.builder);
    const [collapsemenu, setCollapsemenu] = useState();
    let fileReader;
    const [saveLink, setSaveLink] = useState();
    const data = useSelector(state => state.builder);
    const dispatch = useDispatch();
    const saveFile = () => {
        var myURL = window.URL || window.webkitURL //window.webkitURL works in Chrome and window.URL works in Firefox
        var csv = JSON.stringify(data);
        var blob = new Blob([csv], { type: 'text/json' });
        var tempLink = document.createElement('a');
        tempLink.href = myURL.createObjectURL(blob);
        tempLink.setAttribute('download', 'export.json');
        tempLink.click();
    }
    const handleFileRead = (e) => {
        const content = fileReader.result;
        dispatch({ type: 'IMPORT', data: JSON.parse(content) });
    };

    const handleFileChosen = (file) => {
        fileReader = new FileReader();
        fileReader.onloadend = handleFileRead;
        fileReader.readAsText(file);
    };

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
            <div className={'content-wrapper'}>
                <div class="content-header">
                    <div class="container-fluid">
                        <div class="row mb-2">
                            <div class="col-sm-6">
                                <h1 class="m-0 text-dark">Dashboard</h1>
                            </div>
                            <div class="col-sm-6">
                                <ol class="breadcrumb float-sm-right">
                                    <li class="nav-item d-none d-sm-inline-block">
                                        <a class="nav-link" onClick={() => saveFile()}>Save File</a>
                                    </li>
                                    <li class="nav-item d-none d-sm-inline-block">
                                        <a href="#" class="nav-link">Import file</a>
                                    </li>
                                    <li>
                                    <input
                                    type='file'
                                    id='file'
                                    className='input-file'
                                    accept='.json'
                                    onChange={e => handleFileChosen(e.target.files[0])}
                                />
                                    </li>
                                </ol>
                            </div>
                        </div>
                    </div>
                </div>
                <section className={'content'}>
                    <div class="container-fluid">
                        <div className={'card'}>
                            <div className={`row col-md-12 ${styles.container_control}`}>
                                {controlList.map((controlLayout, i) => (
                                    <Container key={i} controlList={controlLayout} />
                                ))}
                            </div>
                        </div>
                    </div>
                </section>

            </div>
        </div>
    )
}
export default Builder;