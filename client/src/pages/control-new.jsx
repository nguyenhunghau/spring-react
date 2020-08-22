import React from 'react'
import { useDrag, useDrop } from 'react-dnd'
import { ItemTypes } from './ItemTypes'

const style = {
    border: '1px dashed gray',
    padding: '0.5rem 1rem',
    marginBottom: '.5rem',
    backgroundColor: '#f1f9f4',
    cursor: 'move',
}
export const ControlNew = ({ id, idLayout, text, pushControl, removeControl, moveControl, findLayout, findControl, control }) => {
    const originalIndex = findControl(id).index;
    const [{ isDragging }, drag] = useDrag({
        item: { type: ItemTypes.CONTROL, id, idLayout, originalIndex, control },
        collect: (monitor) => ({
            isDragging: monitor.isDragging(),
        }),
        begin: (monitor) => {
            // setWasDrop(false);
        },
        end: ( data, monitor) => {
            // const item = monitor.getItem();
            if(!monitor.getItem())  return;
            const { id: droppedId, originalIndex } = monitor.getItem();
            const dropResult = monitor.getDropResult();
            if (dropResult && dropResult.id !== droppedId) {
                removeControl(originalIndex);
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
            const dragIndex = monitor.getItem().originalIndex;
		    // const hoverIndex = props.index;
            const draggedId = props.id;
            if (dragIndex === originalIndex) {
                return;
            }
            //if the dragIndex is index of control belong to other container
            // Push vo list current

            if ( props.id === monitor.getItem().id ) {
                const { index: overIndex } = findControl(id)
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
            {text}
            <input type="text" value="sdsf"></input>
        </div>
    )
}
