import React, { useState } from 'react'
import { useDrag, useDrop } from 'react-dnd'
import { ItemTypes } from './ItemTypes'

const style = {
    border: '1px dashed gray',
    padding: '0.5rem 1rem',
    marginBottom: '.5rem',
    backgroundColor: '#f1f9f4',
    cursor: 'move',
}
export const Control = ({ id, idLayout, index, pushControl, removeControl, moveControl, findLayout, findControl, control }) => {
    const [html, setHtml] = useState();

    const createControlHtml = () => {
        switch (control.type) {
            case ItemTypes.BUTTON:
                return <input type="button" value="button" />
            case ItemTypes.LINK:
                return <a href="#">link</a>
            case ItemTypes.CHECKBOX:
                return <input type="checbox" value="button" />
            case ItemTypes.TEXTBOX:
                return <input type="text" className={'form-control'}/>
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
            return {			
                index: index,
                control: control,
                id: id
            };
        },
        end: (data, monitor) => {
            // const item = monitor.getItem();
            if (!monitor.getItem()) return;
            const { id: droppedId, index: originalIndex } = monitor.getItem();
            const dropResult = monitor.getDropResult();
            if (dropResult && dropResult.id !== droppedId && index != originalIndex) {
                removeControl(originalIndex);
                // removeControl(droppedId, index);
            }
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
        hover(props, monitor, component) {
            // console.log(props);
            // console.log(monitor);
            // console.log(component); //Not exist
            const dragIndex = monitor.getItem().index;
            // const hoverIndex = props.index;
            const draggedId = props.id;
            if (dragIndex === index) {
                return;
            }
            //if the dragIndex is index of control belong to other container
            // Push vo list current

            if (props.id === monitor.getItem().id) {
                moveControl(monitor.getItem().id, index);
                // const { index: overIndex } = findControl(id)
                // moveControl(draggedId, overIndex);
                // monitor.getItem().index = overIndex;
            }

            // if (draggedId !== id) {
            //     const { index: overIndex } = findControl(id)
            //     moveControl(draggedId, overIndex);
            //     // setWasDrop(true);
            //     // moveCard(draggedId, overIndex)
            // }
        },//https://rafaelquintanilha.com/sortable-targets-with-react-dnd/
    })
    const opacity = isDragging ? 0 : 1
    return (
        <div ref={(node) => drag(drop(node))} style={{ ...style, opacity }}>
            {createControlHtml()}
        </div>
    )
}
