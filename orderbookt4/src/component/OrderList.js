import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from './AppNavbar';
import { Link } from 'react-router-dom';
import TradeList from './TradeList';

class OrderList extends Component {

  constructor(props) {
    super(props);
    this.state = { orders: [], userId: 3, isLoading: true };
    this.remove = this.remove.bind(this);
  }

  componentDidMount() {
    //this.setState({ isLoading: true });
    this.getOrders();
    console.log("here");

  }
  getOrders() {
    fetch(`/orderbook/user/${this.state.userId}/completionorders?completed=false`)
      .then(response => response.json())
      .then(data => this.setState({ orders: data, isLoading: false }));
    this.setState({ isLoading: false });
  }

  async remove(id) {
    await fetch(`/orderbook/orders/${id}`, {
      method: 'DELETE',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      }
    }).then(() => {
      let updatedorders = [...this.state.orders].filter(i => i.id !== id);
      this.setState({ orders: updatedorders });
    });
  }

  render() {
    const { orders } = this.state;

    const ordersList = orders.map(order => {
      var date = new Date(order.dateTime);
      date = date.getFullYear() + '-' +
        ('00' + (date.getMonth() + 1)).slice(-2) + '-' +
        ('00' + date.getDate()).slice(-2) + ' ' +
        ('00' + date.getHours()).slice(-2) + ':' +
        ('00' + date.getMinutes()).slice(-2) + ':' +
        ('00' + date.getSeconds()).slice(-2);
      return <tr key={order.id}>
        <td style={{ whiteSpace: 'nowrap' }}>{date}</td>
        <td>{order.amount}</td>
        <td>${order.price}</td>
        <td>
          <ButtonGroup>
            <Button size="sm" color="primary" tag={Link} to={"/orders/" + order.id}>Edit</Button>
            <Button size="sm" color="danger" onClick={(e) => { if (window.confirm('Are you sure you wish to delete this item?')) this.remove(order.id) }}>Delete</Button>
          </ButtonGroup>
        </td>
      </tr>
    });

    return (
      <div>
        <Container fluid>
          <div class="row border mb-3 ml-1 mr-1" style={{ backgroundColor: "#D6DBE8" }}>
            <div class="col m-3">
              <h3 class="text-center">My Active Orders</h3>
            </div>
          </div>
          <div class="row mb-3 ml-1 mr-1" style={{
            backgroundColor: "#DFE7FA",
            overflowY: 'auto',
            height: "750px",
            display: 'block'
          }}>
            <div class="col-md-12 mx-auto">
              <Table
                className="mt-4"
                // style={{
                //   overflowY: 'auto',
                //   height: "750px",
                //   display: 'block'
                // }}
                striped
                borderless
                hover
                size="sm">
                <thead>
                  <tr>
                    <th width="35%">Date and Time</th>
                    <th width="20%">KTC</th>
                    <th width="25%">Price</th>
                    <th width="20%"></th>
                  </tr>
                </thead>
                <tbody>
                  {ordersList}
                </tbody>
              </Table>
            </div>
          </div>

        </Container>
      </div>
    );
  }
}

export default OrderList;