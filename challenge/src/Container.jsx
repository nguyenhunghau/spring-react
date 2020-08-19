import React, { useState, useEffect } from 'react'
import { useDrop } from 'react-dnd'
import { Card } from './Card'
import update from 'immutability-helper'
import { ItemTypes } from './ItemTypes'
const style = {
  width: 400,
}
const ITEMS = [
  {
    id: 1,
    text: 'Write a cool JS library',
  },
  {
    id: 2,
    text: 'Make it generic enough',
  },
  {
    id: 3,
    text: 'Write README',
  },
  {
    id: 4,
    text: 'Create some examples',
  },
  {
    id: 5,
    text: 'Spam in Twitter and IRC to promote it',
  },
  {
    id: 6,
    text: '???',
  },
  {
    id: 7,
    text: 'PROFIT',
  },
]

const ITEM_NEW = [
  {
    type: 'layout', id: 'l0', index: 0,
    children: [{
      id: 1,
      text: 'Write a cool JS library',
    },
    {
      id: 2,
      text: 'PROFIT',
    }]
  },
  {
    type: 'layout', id: 'l1', index: 1,
    children: [
      {
        id: 3,
        text: 'Make it generic enough',
      },
      {
        id: 4,
        text: 'Write README',
      }
    ]
  }
]
export const Container = () => {
  const [cards, setCards] = useState(ITEM_NEW);
  const [isRender, setIsRender] = useState(0);
  const [wasDrop, setWasDrop] = useState(true);

  // useEffect(() => {
  //   console.log('change control');
  // }, [isRender]);

  const moveCard = (id, atIndex) => {
    const { card, index } = findCard(id)
    setCards(
      update(cards, {
        $splice: [
          [index, 1],
          [atIndex, 0, card],
        ],
      }),
    )
  }

  const moveControl = (idOldLayout, idNewLayout, idControl, atIndex) => {
    const oldLayout = cards.filter((c) => `${c.id}` === idOldLayout)[0];
    const newLayout = cards.filter((c) => `${c.id}` === idNewLayout)[0];
    const { control, index } = findControl(oldLayout, idControl);
    if(!oldLayout || !newLayout) {
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
    setCards(
      cards
    )
    setIsRender(isRender + 1);
  }

  const findCard = (id) => {
    const card = cards.filter((c) => `${c.id}` === id)[0]
    return {
      card,
      index: cards.indexOf(card),
    }
  }

  const findLayout = (id) => {
    return cards.filter((c) => `${c.id}` === id)[0]
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

  const [, drop] = useDrop({ accept: ItemTypes.CARD })
  return (
    <>
      <div ref={drop} style={style}>

        {cards.map((card) => (
          <div class="col-md-3" style={{ 'width': '50%', 'float': 'left' }}>
            {card.children.map(control => (
              <Card
                key={control.id}
                idLayout={`${card.id}`}
                id={`${control.id}`}
                text={control.text}
                moveCard={moveCard}
                findControl={findControl}
                moveControl={moveControl}
                findLayout={findLayout}
                wasDrop={wasDrop}
                setWasDrop = {setWasDrop}
              />
            ))}
          </div>
        ))}

        {/* {cards.map((card) => (
          <Card
            key={card.id}
            id1={`${card.id}`}
            id={`${card.id}`}
            text={card.text}
            moveCard={moveCard}
            findCard={findCard}
            moveControl={moveControl}
            findLayout={findLayout}
          />
        ))} */}
      </div>
    </>
  )
}
