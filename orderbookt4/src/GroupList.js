import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from './component/AppNavbar';

class GroupList extends Component {

  constructor(props) {
    super(props);
    this.state = { users: [], isLoading: true };
    this.remove = this.remove.bind(this);
  }

  componentDidMount() {
    this.setState({ isLoading: true });

    fetch('orderbook/users')
      .then(response => response.json())
      .then(data => this.setState({ users: data, isLoading: false }));
  }

  async remove(id) {
    await fetch(`/orderbook/users/${id}`, {
      method: 'DELETE',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      }
    }).then(() => {
      let updatedUsers = [...this.state.users].filter(i => i.id !== id);
      this.setState({ users: updatedUsers });
    });
  }

  render() {
    const { users, isLoading } = this.state;

    if (isLoading) {
      return <p>Loading...</p>;
    }

    const usersList = users.map(user => {
      return <tr key={user.id}>
        <td style={{ whiteSpace: 'nowrap' }}>{user.username}</td>
        <td>{user.coin}</td>
        <td>${user.dollars}</td>
        <td>
          <ButtonGroup>
            <Button size="sm" color="primary" >Edit</Button>
            <Button size="sm" color="danger" onClick={() => this.remove(user.id)}>Delete</Button>
          </ButtonGroup>
        </td>
      </tr>
    });

    return (
      <div>
        <AppNavbar />
        <Container fluid>
          <div className="float-right">
            <Button color="success">Add Group</Button>
          </div>
          <h3>Trades</h3>
          10 Most Recent Trades
          <Table className="mt-4">
            <thead>
              <tr>
                <th width="20%">Name</th>
                <th width="20%">Coins</th>
                <th width="10%">Dollars</th>
              </tr>
            </thead>
            <tbody>
              {usersList}
            </tbody>
          </Table>
        </Container>
      </div>
    );
  }
}

export default GroupList;