import React from 'react'
import { useDrag, useDrop } from 'react-dnd'
import { ItemTypes } from './ItemTypes'

const style = {
    border: '1px dashed gray',
    padding: '0.5rem 1rem',
    marginBottom: '.5rem',
    backgroundColor: 'white',
    cursor: 'move',
}
export const ControlNew = ({ id, idLayout, text, pushControl, removeControl, moveControl, findLayout, findControl, wasDrop, setWasDrop }) => {
    const originalIndex = findControl(id).index;
    const [{ isDragging }, drag] = useDrag({
        item: { type: ItemTypes.CONTROL, id, originalIndex },
        collect: (monitor) => ({
            isDragging: monitor.isDragging(),
        }),
        begin: (monitor) => {
            setWasDrop(false);
        },
        end: (dropResult, monitor) => {
            // const item = monitor.getItem();

            const { id: droppedId, originalIndex } = monitor.getItem();
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
            console.log(props);
            console.log(monitor);
            console.log(component);
            // if (draggedId !== id) {
            //     const { index: overIndex } = findControl(id)
            //     moveControl(draggedId, overIndex);
            //     // setWasDrop(true);
            //     // moveCard(draggedId, overIndex)
            // }
        },https://rafaelquintanilha.com/sortable-targets-with-react-dnd/
    })
    const opacity = isDragging ? 0 : 1
    return (
        <div ref={(node) => drag(drop(node))} style={{ ...style, opacity }}>
            {text}
        </div>
    )
}
