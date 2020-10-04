import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import AppNavbar from './AppNavbar';

class OrderEdit extends Component {

  emptyItem = {
    id: '',
    user: '',
    isSell: '',
    price: '',
    amount: '',
    dateTime: '',
    isComplete: ''
  };

  constructor(props) {
    super(props);
    this.state = {
      item: this.emptyItem
    };
    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  async componentDidMount() {
    if (this.props.match.params.id !== 'new') {
      const order = await (await fetch(`/orderbook/orders/${this.props.match.params.id}`)).json();
      this.setState({item: order});
    }
  }

  handleChange(event) {
    const target = event.target;
    const value = target.value;
    const name = target.name;
    let item = {...this.state.item};
    item[name] = value;
    this.setState({item});
  }

  async handleSubmit(event) {
    event.preventDefault();
    const {item} = this.state;

    await fetch(`/orderbook/orders/${this.props.match.params.id}`, {
      method: (item.id) ? 'PUT' : 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(item),
    });
    this.props.history.push('/orders');
  }

  render() {
    const {item} = this.state;
    const title = <h2>{item.id ? 'Edit order' : 'Edit order'}</h2>;
    
    return <div>
      <AppNavbar/>
      <Container>
        {title}
        <Form onSubmit={this.handleSubmit}>
          <FormGroup>
            <Label for="price">Price</Label>
            <Input type="number" min={0.00001} step={0.00001} oninput="validity.valid||(value='');" name="price" id="price" value={item.price || ''}
                   onChange={this.handleChange} autoComplete="price"/>
          </FormGroup>
          <div className="row">
            <FormGroup className="col-md-4 mb-3">
              <Label for="amount">Amount</Label>
              <Input type="number" min={0.00001} step={0.00001} oninput="validity.valid||(value='');" name="amount" id="amount" value={item.amount || ''}
                     onChange={this.handleChange} autoComplete="amount"/>
            </FormGroup>
          </div>
          <FormGroup>
            <Button color="primary" type="submit">Save</Button>{' '}
            <Button color="secondary" tag={Link} to="/orders">Cancel</Button>
          </FormGroup>
        </Form>
      </Container>
    </div>
  }
}

export default withRouter(OrderEdit);