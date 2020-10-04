import React, { Component } from 'react';
import SearchTrades from './component/SearchTrades'
import OrderTradePage from './component/OrderTradePage';
import OrderEdit from './component/OrderEdit';
import ShareInfo from './component/ShareInfo';
import MainPage from './component/MainPage';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import socketIOClient from "socket.io-client";
import './App.css';

class App extends Component {

  state = {
    isLoading: true,
    userId: 3,
    response: false,
    endpoint: "http://127.0.0.1:4001"
  };

  async componentDidMount() {
    this.setState({ isLoading: false });

    const { endpoint } = this.state;
    const socket = socketIOClient(endpoint);
    socket.on("FromAPI", data => this.setState({ response: data }))
  }

  render() {

    return (

      <Router>
        <Switch>
          <Route path='/' exact={true} component={MainPage} />
          <Route path='/searchTrades' exact={true} component={SearchTrades} />
          {/* <Route path='/orders' exact={true} component={() => <OrderList userId={this.state.userId} />} /> */}
          <Route path='/orders' exact={true} component={OrderTradePage} />
          <Route path='/orders/:id' component={OrderEdit} />
          <Route path='/shareInfo' component={ShareInfo} />
        </Switch>
      </Router>

    );
  }
}

export default App;
