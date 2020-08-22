import React, { useState, useEffect } from 'react';
import update from 'immutability-helper'
import { useDrop } from 'react-dnd'
import { Control } from './control'
import { ItemTypes } from './ItemTypes'
const Container = (props) => {

    const [controlList, setControlList] = useState(() => props.controlList.children);

    const [collapsemenu, setCollapsemenu] = useState();

    const pushControl = (control) => {
        setControlList(controlList);
        const newControlList = update(controlList, {
            $push: [control]
        });
        setControlList(
            newControlList
        )
    }

    const removeControl = (index) => {
        const newControlList = update(controlList, {
            $splice: [
                [index, 1],
            ],
        });
        setControlList(
            newControlList
        )
    }

    const moveControl = (idControl, atIndex) => {
        const { control, index } = findControl(idControl);

        //Update the layout of cards by 
        // Remove control of old layout

        //Add new control at new layout
        const newControlList = update(controlList, {
            $splice: [
                [index, 1],
                [atIndex, 0, control],
            ],
        });
        setControlList(
            newControlList
        )
    }

    const findLayout = (id) => {
        return controlList.filter((c) => `${c.id}` === id)[0]
        // return {
        //   layout,
        //   index: cards.indexOf(layout),
        // }
    }

    const findControl = (id) => {
        const control = controlList.filter((c) => `${c.id}` === id)[0];
        return {
            control,
            index: controlList.indexOf(control),
        }
    }

    const [, drop] = useDrop({
        accept: ItemTypes.CONTROL,
        drop(data, monitor, component) {
            // console.log(props);
            // console.log(monitor.getItem());
            //console.log(component); //Not exist

            // const { idLayout } = props;
            const sourceObj = monitor.getItem();
            if (props.controlList.id !== sourceObj.idLayout) {
                pushControl(sourceObj.control);
            }
            else {
                // sourceObj.children.
            }
            //remove sourceObj in old layout

            // return {
            //     listId: id
            // };
        }
    })

    return (
        <div ref={drop} class="col-md-4">
            {controlList.map((control, i) => (
                <Control
                    key={control.id}
                    idLayout={`${props.controlList.id}`}
                    id={`${control.id}`}
                    index={i}
                    findControl={findControl}
                    moveControl={moveControl}
                    findLayout={findLayout}
                    pushControl={pushControl}
                    removeControl={removeControl}
                    control={control}
                />
            ))}
        </div>
    )
}
export default Container;