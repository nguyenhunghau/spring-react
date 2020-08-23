import React, { useState, useRef } from 'react'
import { useDrag, useDrop } from 'react-dnd'
import { ItemTypes } from './ItemTypes';

import styles from './builder.module.css'

const style = {
    border: '1px dashed gray',
    padding: '0.5rem 1rem',
    marginBottom: '.5rem',
    backgroundColor: 'white',
    cursor: 'move',
}
export const Control = ({ key, id, idLayout, index, pushControl, removeControl, moveControl, findLayout, findControl, control }) => {
    const [html, setHtml] = useState();
    const ref = useRef(null);
    const createControlHtml = () => {
        switch (control.type) {
            case ItemTypes.BUTTON:
                return <input type="button" value="button" />
            case ItemTypes.LINK:
                return <a href="#">link</a>
            case ItemTypes.CHECKBOX:
                return <input type="checbox" value="button" />
            case ItemTypes.TEXTBOX:
                return <input type="text" className={`form-control ${styles.textbox}`} />
            case ItemTypes.TEXTAREA:
                return <textarea className={'form-control'}></textarea>
            case ItemTypes.PHARAPH:
                return <textarea className={'form-control'}></textarea>
            default:
                return '';
        }
    }

    const changeText = () => {

    }

    // const originalIndex = findControl(id).index;
    const [{ isDragging }, drag] = useDrag({
        item: { type: ItemTypes.CONTROL, id, idLayout, index, control },
        collect: (monitor) => ({
            isDragging: monitor.isDragging(),
        }),
        begin: (monitor) => {
            // setWasDrop(false);
            // return {			
            //     index: index,
            //     control: control,
            //     id: id
            // };
        },
        end: (data, monitor) => {
            // const item = monitor.getItem();
            if (!monitor.getItem()) return;
            if (!monitor.didDrop()) return;
            const { id: droppedId, index: originalIndex } = monitor.getItem();
            const dropResult = monitor.getDropResult();
            if (dropResult && dropResult.idContainer !== monitor.getItem().idLayout) {
                removeControl(monitor.getItem().index);
                //moveControl(monitor.getItem().control, monitor.getItem().index, index);
            }
            // if (dropResult && dropResult.id !== droppedId && index != originalIndex) {
            //     removeControl(originalIndex);
            //     // removeControl(droppedId, index);
            // }
            // console.log({ id: droppedId, originalIndex });
            // const didDrop = monitor.didDrop()
            // if (!didDrop) {
            //     moveControl(droppedId, originalIndex)
            // }
        },
    })
    const [, drop] = useDrop({
        accept: ItemTypes.CONTROL,
        canDrop: () => false,
        hover(props, monitor) {
            if (!ref.current) {
                return
              }
            const dragIndex = monitor.getItem().index;
            const hoverIndex = index;
            const draggedId = props.id;
            if (dragIndex === index) {
                return;
            }

            const hoverBoundingRect = ref.current?.getBoundingClientRect()
            // Get vertical middle
            const hoverMiddleY =
                (hoverBoundingRect.bottom - hoverBoundingRect.top) / 2
            // Determine mouse position
            const clientOffset = monitor.getClientOffset()
            // Get pixels to the top
            const hoverClientY = clientOffset.y - hoverBoundingRect.top
            // Only perform the move when the mouse has crossed half of the items height
            // When dragging downwards, only move when the cursor is below 50%
            // When dragging upwards, only move when the cursor is above 50%
            // Dragging downwards
            if (dragIndex < hoverIndex && hoverClientY < hoverMiddleY) {
                return
            }
            // Dragging upwards
            if (dragIndex > hoverIndex && hoverClientY > hoverMiddleY) {
                return
            }
            // Time to actually perform the action
            // moveCard(dragIndex, hoverIndex)
            moveControl(monitor.getItem().control, monitor.getItem().index, index);
            // Note: we're mutating the monitor item here!
            // Generally it's better to avoid mutations,
            // but it's good here for the sake of performance
            // to avoid expensive index searches.
            props.index = hoverIndex
            //if the dragIndex is index of control belong to other container
            // Push vo list current

            // if (props.id === monitor.getItem().id) {
            //     console.log('da move');
            //     moveControl(monitor.getItem().control, monitor.getItem().index, index);
            //     // moveControl(monitor.getItem().control, index);
            //     // const { index: overIndex } = findControl(id)
            //     // moveControl(draggedId, overIndex);
            //     // monitor.getItem().index = overIndex;
            // }
        },//https://rafaelquintanilha.com/sortable-targets-with-react-dnd/
    })
    const opacity = isDragging ? 0 : 1
    drag(drop(ref));
    return (
        <div  ref={ref} style={{ ...style, opacity }}>
            {createControlHtml()}
        </div>
    )
}
