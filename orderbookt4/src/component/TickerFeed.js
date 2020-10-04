import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from './AppNavbar';

class TradeList extends Component {

  constructor(props) {
    super(props);
    this.state = { trades: [], isLoading: true };
  }

  componentDidMount() {
    this.setState({ isLoading: true, userId: this.props.userId });

    this.interval = setInterval(() => this.getTrades(), 1000);
  }

  componentWillUnmount() {
    clearInterval(this.interval);
  }

  getTrades() {
    fetch(`orderbook/trades/`)
      .then(response => response.json())
      .then(data => this.setState({ trades: data, isLoading: false }));
  }


  render() {
    const { trades, userId, isLoading } = this.state;

    if (isLoading) {
      return <p>Loading...</p>;
    }

    let tickerFeedList = trades.map(trade => {
      var date = new Date(trade.datetime);
      date = ('00' + date.getHours()).slice(-2) + ':' +
        ('00' + date.getMinutes()).slice(-2) + ':' +
        ('00' + date.getSeconds()).slice(-2);
      return <tr key={trade.id}>
        <td style={{ textAlign: "center", fontSize: "0.8rem" }}>{date}</td>
        <td style={{ whiteSpace: 'nowrap', fontSize: "0.8rem", textAlign: "right" }}>${trade.price.toFixed(3)}</td>
        <td style={{ textAlign: "center", fontSize: "0.8rem" }}>{trade.amount.toFixed(3)}</td>
      </tr>
    });
    tickerFeedList = tickerFeedList.slice(0, 15);

    return (
      <div style={{ backgroundColor: "#DFE7FA", width: "95%" }}>
        <Container fluid>
          <h5 style={{ textAlign: "center" }}>Trades</h5>
          <Table bordered hover striped size="sm">
            <thead>
              <tr>
                <th style={{ textAlign: "center", fontSize: "0.8rem" }} minWidth="30%">Time</th>
                <th style={{ textAlign: "center", fontSize: "0.8rem" }} minWidth="30%">Price</th>
                <th style={{ textAlign: "center", fontSize: "0.8rem" }} minWidth="40%">Amount</th>
              </tr>
            </thead>
            <tbody>
              {tickerFeedList}
            </tbody>
          </Table>
        </Container>
      </div>
    );
  }
}

export default TradeList;