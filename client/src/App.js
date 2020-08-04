import React from 'react';
import logo from './logo.svg';
import './App.css';

import Home from './pages/home/index';
import Widgets from './pages/widgets/index';
import Validation from './pages/form/validation';
import Table from './pages/form/table';
import Job  from './pages/job/index';
import Company from './pages/job/company'
import PageNotFound from './pages/error/page-not-found'

import { BrowserRouter as Router, Route, Link, Switch } from "react-router-dom";

class App extends React.Component {

  render() {

    return (
      <div className="App">
          <Router>
            <Switch>
              <Route exact path="/" component={Job} />
              <Route exact path="/index" component={Job} />
              <Route exact path="/widgets" component={Widgets} />
              <Route exact path="/validation" component={Validation} />
              <Route exact path="/table" component={Table} />
              <Route exact path="/company" component={Company} />
              <Route path="*" component={PageNotFound} />
            </Switch>
          </Router>
        </div>
    );
  }
}

export default App;
