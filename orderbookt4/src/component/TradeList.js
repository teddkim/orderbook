import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from './AppNavbar';

class TradeList extends Component {

  constructor(props) {
    super(props);
    this.state = { trades: [], userId: 3, dateNow: 0, isLoading: true };
  }

  componentDidMount() {
    this.setState({ isLoading: true, userId: this.props.userId });

    fetch(`orderbook/trade/${this.state.userId}/ten_trades`)
      .then(response => response.json())
      .then(data => this.setState({ trades: data, isLoading: false }));


  }

  render() {
    const { trades, userId, isLoading } = this.state;

    // if (isLoading) {
    //   return <p>Loading...</p>;
    // }

    const tradesList = trades.map(trade => {
      var date = new Date(trade.datetime);
      date = date.getFullYear() + '-' +
        ('00' + (date.getMonth() + 1)).slice(-2) + '-' +
        ('00' + date.getDate()).slice(-2) + ' ' +
        ('00' + date.getHours()).slice(-2) + ':' +
        ('00' + date.getMinutes()).slice(-2) + ':' +
        ('00' + date.getSeconds()).slice(-2);
      return <tr key={trade.id}>
        <td >{date}</td>
        <td style={{ whiteSpace: 'nowrap' }}>${trade.price.toFixed(2)}</td>
        <td>{trade.amount}</td>
        <td>${(trade.price * trade.amount).toFixed(2)}</td>
        {/* <td>
          <ButtonGroup>
            <Button size="sm" color="primary" >Edit</Button>
            <Button size="sm" color="danger" onClick={() => this.remove(user.id)}>Delete</Button>
          </ButtonGroup>
        </td> */}
      </tr>
    });

    return (
      <Container fluid>
        {/* <div className="float-right">
            <Button color="success">Add Group</Button>
          </div> */}
        <div class="row border mb-3 ml-1 mr-1" style={{ backgroundColor: "#D6DBE8" }}>
          <div class="col m-3">
            <h3 style={{ textAlign: "center" }}>My 10 Most Recent Trades</h3>
          </div>
        </div>

        <div class="row mb-3 ml-1 mr-1" style={{ backgroundColor: "#DFE7FA", height: "750px" }}>
          <div class="col-lg mx-auto">
            <Table
              className="mt-4 center"
              striped
              borderless
              hover>
              <thead style={{ textAlign: "center" }}>
                {/* <tr>
                    <th width="40%">Date and Time</th>
                    <th width="25%">Price</th>
                    <th width="20%">Amount</th>
                    <th width="30%">Total</th>
                  </tr> */}
                <tr>
                  <th width="30%">Date and Time</th>
                  <th width="20%">Price</th>
                  <th width="20%">Amount</th>
                  <th width="30%">Total</th>
                </tr>
              </thead>
              <tbody style={{ textAlign: "center" }}>
                {tradesList}
              </tbody>
            </Table>
          </div>
        </div>
      </Container>
    );
  }
}

export default TradeList;