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
export const Card = ({ id, idLayout, text, moveCard, moveControl, findLayout, findControl, wasDrop, setWasDrop }) => {
  const layout = findLayout(idLayout);
  // const currentIdLayout = layout.id;
  const originalIndex = findControl(layout, id).index;
  const [{ isDragging }, drag] = useDrag({
    item: { type: ItemTypes.CARD, id, idLayout, originalIndex },
    collect: (monitor) => ({
      isDragging: monitor.isDragging(),
    }),
    begin: (data) => {
    },
    end: (dropResult, monitor) => {
      const { id: droppedId, idLayout: idOfLayout, originalIndex } = monitor.getItem()
      console.log({ id: droppedId, idLayout: idOfLayout, originalIndex });
      const didDrop = monitor.didDrop()
      if (!wasDrop) {
        moveControl(idOfLayout, idLayout,droppedId, originalIndex)
      }
    },
  })
  const [, drop] = useDrop({
    accept: ItemTypes.CARD,
    canDrop: () => false,
    hover({ id: draggedId,  idLayout: idOldLayout}) {
      if (draggedId !== id) {
        const curentLayout = findLayout(idOldLayout);
        debugger;
        const { index: overIndex } = findControl(curentLayout, id)
        moveControl(idOldLayout, idLayout, draggedId, overIndex);
        setWasDrop(true);
        // moveCard(draggedId, overIndex)
      }
    },
  })
  const opacity = isDragging ? 0 : 1
  return (
    <div ref={(node) => drag(drop(node))} style={{ ...style, opacity }}>
      {text}
    </div>
  )
}
