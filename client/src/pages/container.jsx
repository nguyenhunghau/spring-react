import React, { useState, useEffect } from 'react';
import update from 'immutability-helper';
import { useDrop } from 'react-dnd';
import { Control } from './control';
import { ItemTypes } from './ItemTypes';
import {useSelector, useDispatch} from 'react-redux';

const Container = (props) => {

    // const [controlList, setControlList] = useState(() => props.controlList.children);
    // const [controlList, setControlList] = useState([])
    const [collapsemenu, setCollapsemenu] = useState();
    const dispatch = useDispatch();

    const getContainer = (state, idContainer) => {
        return state.data.filter((c) => `${c.id}` === idContainer)[0];
    }
    
    const containerData = useSelector(item => getContainer(item.builder, props.controlList.id));
    // const [controlLists, setControlList] = useState(controlList => (controlList? controlList.children: ))
    
    const pushControl = (control) => {
        // const newControlList = update(controlList, {
        //     $push: [control]
        // });
        // setControlList(
        //     newControlList
        // )
        dispatch({type: 'PUSH', idContainer: props.controlList.id, control: control})
    }

    const removeControl = (index) => {
        dispatch({type: 'REMOVE', idContainer: props.controlList.id, index: index});
        // const newControlList = update(controlList, {
        //     $splice: [
        //         [index, 1],
        //     ],
        // });
        // setControlList(
        //     newControlList
        // )
    }

    const moveControl = (control, oldIndex, newIndex) => {
        dispatch({type: 'MOVE', idContainer: props.controlList.id, control: control, oldIndex: oldIndex, newIndex: newIndex})
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
            } else {
                //moveControl(sourceObj.control, );
            }
            //remove sourceObj in old layout

            return {
                idContainer: props.controlList.id
            };
        }
    })

    return (
        <div ref={drop} class="col-md-4">
            {containerData.children && containerData.children.map((control, i) => (
                <Control
                    key={control.id}
                    idLayout={`${props.controlList.id}`}
                    id={`${control.id}`}
                    index={i}
                    moveControl={moveControl}
                    pushControl={pushControl}
                    removeControl={removeControl}
                    control={control}
                />
            ))}
        </div>
    )
}
export default Container;