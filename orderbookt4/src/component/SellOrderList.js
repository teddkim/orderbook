import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from './AppNavbar.js';

class SellOrderList extends Component {

  getTotal(amount, price) {
    return amount * price;
  }

  constructor(props) {
    super(props);
    this.state = { orders: [], isLoading: true };
  }

  componentDidMount() {
    this.setState({ isLoading: true });

    this.interval = setInterval(() => this.getOrders(), 1000)
  }

  componentWillUnmount() {
    clearInterval(this.interval)
  }

  getOrders() {
    fetch('orderbook/orders/sells/')
      .then(response => response.json())
      .then(data => this.setState({ orders: data, isLoading: false }));
  }

  render() {
    const { orders, isLoading } = this.state;

    if (isLoading) {
      return <p>Loading...</p>;
    }

    let ordersList = orders.map(order => {
      return <tr key={order.id}>
        <td style={{ whiteSpace: 'nowrap', borderColor: "#C9A0A0" }}>${order.price}</td>
        <td style={{ borderColor: "#C9A0A0" }}>${this.getTotal(order.amount, order.price).toFixed(5)} </td>
        <td style={{ borderColor: "#C9A0A0" }}>{order.amount}</td>
        {/* <td>
            <ButtonGroup>
              <Button size="sm" color="primary" >Edit</Button>
              <Button size="sm" color="danger" onClick={() => this.remove(order.id)}>Delete</Button>
            </ButtonGroup>
          </td> */}
      </tr>
    });

    ordersList = ordersList.slice(0, 15)

    return (
      <div style={{ backgroundColor: 'rgba(252, 95, 95, 0.24)', paddingBottom: '3px', paddingTop: '2px' }}>
        {/* <AppNavbar /> */}
        <Container fluid>
          {/* <div className="float-right">
              <Button color="success">Add Group</Button>
            </div> */}
          <h5>Sells</h5>
          <Table className="mt-4" style={{ borderColor: "#C9A0A0" }} striped hover size="sm">
            <thead>
              <tr>
                <th width="20%" style={{ borderColor: "#C9A0A0" }}>Price</th>
                <th width="20%" style={{ borderColor: "#C9A0A0" }}>Total</th>
                <th width="10%" style={{ borderColor: "#C9A0A0" }}>Amount</th>
              </tr>
            </thead>
            <tbody>
              {ordersList}
            </tbody>
          </Table>
        </Container>
      </div>
    );
  }
}

export default SellOrderList;
