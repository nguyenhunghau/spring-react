import { Component } from "react";
import React from 'react'
// export default class TableModel extends Component {
//     render() {

//         const characters = [
//             {
//               name: 'Charlie',
//               job: 'Janitor',
//             },
//             {
//               name: 'Mac',
//               job: 'Bouncer',
//             },
//             {
//               name: 'Dee',
//               job: 'Aspring actress',
//             },
//             {
//               name: 'Dennis',
//               job: 'Bartender',
//             },
//           ]

//         return (
//             <div className="container">
//               <Table characterData={characters} />
//             </div>
//           )

//     }
// }

export default class Table extends Component {
    render() {
        const { characterData, removeCharacter } = this.props
        const TableBody = props => {
            const rows = props.characterData.map((row, index) => {
                return (
                    <tr key={index}>
                        <td>{row.name}</td>
                        <td>{row.job}</td>
                        <td>
                            <button onClick={() => props.removeCharacter(index)}>Delete</button>
                        </td>
                    </tr>
                )
            })
            return <tbody>{rows}</tbody>
        }

        return (
            <table class="">
                {/* <TableHeader /> */}
                <TableBody characterData={characterData} removeCharacter={removeCharacter} />
            </table>
        )
    }
}