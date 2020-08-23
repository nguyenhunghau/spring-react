import update from 'immutability-helper';

const initialState = {
    count: 0,
    data: [
        {
            type: 'layout', id: 'c0', index: 0,
            children: [
            ]
        },
        {
            type: 'layout', id: 'c1', index: 1,
            children: [
            ]
        },
        {
            type: 'layout', id: 'c2', index: 1,
            children: [
            ]
        }
    ],
    history: []
};

function BuilderReducer(state = initialState, action) {
    console.log('reducer', state, action);
    switch (action.type) {
        case 'ADD':
            return addNew(state, action);
        case 'IMPORT':
            return { ...action.data };
        case 'GET_CONTAINER':
            return getContainer(state, action.idContainer);
        case 'PUSH':
            const currentContainer = getContainer(state, action.idContainer);
            currentContainer.children.push(action.control);
            return {
                count: state.count,
                data: state.data
            };
        case 'MOVE':
            return {
                ...state,
                data: moveControl(state, action)
            };
        case 'REMOVE':
                return {
                    count: state.count - 1,
                    data: removeControl(state, action)
                };
        default:
            return state;
    }
}

const addNew = (state, action) => {
    state.data[0].children.push({
        id: state.count + 1,
        type: action.itemType,
        text: '',
        link: ''
    });
    return {
        count: state.count + 1,
        data: state.data
    };
}

const getContainer = (state, idContainer) => {
    return state.data.filter((c) => `${c.id}` === idContainer)[0];
}

const moveControl = (state, action) => {
    // const newState = {...state};
    const currentContainer = {...getContainer(state, action.idContainer)};
    const newControlList = update(currentContainer.children, {
        $splice: [
            [action.oldIndex, 1],
            [action.newIndex, 0, action.control],
        ],
    });
    return state.data.map(item => 
        item.id == action.idContainer? {...item, children: newControlList}: item
    );
}

const removeControl = (state, action) => {
    const currentContainer = {...getContainer(state, action.idContainer)};
    const newControlList = update(currentContainer.children, {
        $splice: [
            [action.index, 1]
        ],
    });
    return state.data.map(item => 
        item.id == action.idContainer? {...item, children: newControlList}: item
    );
}
export default BuilderReducer;