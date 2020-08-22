import React from 'react';
import logo from './logo.svg';
import './App.css';

import Widgets from './pages/widgets/index';
import Validation from './pages/form/validation';
import Table from './pages/form/table';
import Job from './pages/job/index';
import Company from './pages/job/company'
import Builder from './pages/builder'
import PageNotFound from './pages/error/page-not-found'
import RootReducer from './redux/reducer/root-reducer';
import {createStore } from 'redux';
import { Provider } from 'react-redux';
import 'bootstrap/dist/css/bootstrap.min.css';

import { BrowserRouter as Router, Route, Link, Switch } from "react-router-dom";
import { DndProvider } from 'react-dnd'
import { HTML5Backend } from 'react-dnd-html5-backend'

class App extends React.Component {

  render() {
    const store = createStore(RootReducer);
    return (
      <Provider store={store}>
        <DndProvider backend={HTML5Backend}>
          <div className="App">
            <Router>
              <Switch>
                <Route exact path="/" component={Builder} />
                <Route exact path="/index" component={Job} />
                <Route exact path="/widgets" component={Widgets} />
                <Route exact path="/validation" component={Validation} />
                <Route exact path="/table" component={Table} />
                <Route exact path="/company" component={Company} />
                <Route path="*" component={PageNotFound} />
              </Switch>
            </Router>
          </div>
        </DndProvider>
      </Provider>
    );
  }
}

export default App;
