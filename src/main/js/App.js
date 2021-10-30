import React, { Component } from 'react'
import { Route, BrowserRouter, Switch, Redirect } from 'react-router-dom'
import 'bootstrap/dist/css/bootstrap.min.css'
import "react-bootstrap";
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap/dist/css/bootstrap-grid.css';

import Home from './components/Layout/Home'

import history from './history'

class App extends Component {
  render() {
    console.log(localStorage.getItem("jwtToken"))
    
    return (
        <BrowserRouter history={history}>
          <div className="App">
            
            <Route exact path="/" component={Home} />
            
          </div>
        </BrowserRouter>
    )
  }
}

export default App

