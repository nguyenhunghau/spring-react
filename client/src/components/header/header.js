import React, { useState } from 'react';
import { useSelector, useDispatch } from 'react-redux';

const Header = (props) => {
    let fileReader;
    const [saveLink, setSaveLink] = useState();
    const data = useSelector(state => state.builder);
    const dispatch = useDispatch();
    const saveFile = () => {
        var myURL = window.URL || window.webkitURL //window.webkitURL works in Chrome and window.URL works in Firefox
        var csv = JSON.stringify(data);
        var blob = new Blob([csv], { type: 'text/json' });
        var tempLink = document.createElement('a');
        tempLink.href = myURL.createObjectURL(blob);
        tempLink.setAttribute('download', 'export.json');
        tempLink.click();
    }
    const handleFileRead = (e) => {
        const content = fileReader.result;
        dispatch({type: 'IMPORT', data: JSON.parse(content)});
      };
      
      const handleFileChosen = (file) => {
        fileReader = new FileReader();
        fileReader.onloadend = handleFileRead;
        fileReader.readAsText(file);
      };

    return (
        <nav class="main-header navbar navbar-expand navbar-white navbar-light">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link" onClick={props.changeMenu} data-widget="pushmenu" href="#" role="button"><i class="fas fa-bars"></i></a>
                </li>
                <li class="nav-item d-none d-sm-inline-block">
                    <a class="nav-link" onClick={() => saveFile()}>Save File</a>
                </li>
                <li class="nav-item d-none d-sm-inline-block">
                    <a href="#" class="nav-link">Import file</a>
                </li>
            </ul>

            <form class="form-inline ml-3">
                <div class="input-group input-group-sm">
                    <input
                        type='file'
                        id='file'
                        className='input-file'
                        accept='.json'
                        onChange={e => handleFileChosen(e.target.files[0])}
                    />
                    <div class="input-group-append">
                        <button class="btn btn-navbar" type="submit">
                            <i class="fas fa-search"></i>
                        </button>
                    </div>
                </div>
            </form>
        </nav>
    )
}
export default Header;