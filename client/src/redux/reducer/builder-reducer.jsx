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
    ]
};

function BuilderReducer(state = initialState, action) {
    console.log('reducer', state, action);
    switch (action.type) {
        case 'ADD':
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
        case 'IMPORT':
            return {...action.data};
        case 'RESET':
            return {
                count: 0
            };
        default:
            return state;
    }
}
export default BuilderReducer;