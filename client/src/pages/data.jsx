import React from 'react';

const ControlList = [
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

export default ControlList;