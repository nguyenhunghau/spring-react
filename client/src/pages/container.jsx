import React,  { useState, useEffect } from 'react';
import update from 'immutability-helper'
import { useDrop } from 'react-dnd'
import { ControlNew } from './control-new'
// import update from 'immutability-helper'
import { ItemTypes } from './ItemTypes'
const Container = (props) => {

    const [controlList, setControlList] = useState(props.controlList.children);
    const [isRender, setIsRender] = useState(0);
    const [wasDrop, setWasDrop] = useState(true);

    const [collapsemenu, setCollapsemenu] = useState();

    const pushControl = (control) => {
        controlList.push(control);
        setControlList(controlList);
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
        // setIsRender(isRender + 1);
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

    const [, drop] = useDrop({ accept: ItemTypes.CONTROL })

    return (
        <div ref={drop} class="col-md-3">
            {controlList.map(control => (
                <ControlNew
                    key={control.id}
                    idLayout={`${props.controlList.id}`}
                    id={`${control.id}`}
                    text={control.text}
                    findControl={findControl}
                    moveControl={moveControl}
                    findLayout={findLayout}
                    wasDrop={wasDrop}
                    pushControl={pushControl}
                    removeControl={removeControl}
                    setWasDrop={setWasDrop}
                />
            ))}
        </div>
    )
}
export default Container;