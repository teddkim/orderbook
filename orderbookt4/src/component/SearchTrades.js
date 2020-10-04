import React, { Component } from 'react';
import AppNavbar from './AppNavbar'
import { Button, ButtonGroup, Container, Table } from 'reactstrap';

class SearchTrades extends Component {

  constructor(props) {
    super(props);

    let date = new Date();
    const dateString = date.getFullYear() + "-" + ('0' + (date.getMonth() + 1)).slice(-2) + "-" + ('0' + date.getDate()).slice(-2)

    this.state = {
      trades: [],
      isLoading: true,
      searchType: 'price',
      startPrice: 1000,
      endPrice: 2000,
      startAmount: 10,
      endAmount: 20,
      startDate: dateString,
      endDate: dateString
    }
  }

  componentDidMount() {
    this.setState({ isLoading: true });
    this.getTrades()
  }

  getTrades() {
    fetch('orderbook/trades')
      .then(response => response.json())
      .then(data => this.setState({ trades: data, isLoading: false }))
  }

  handleChange = (event) => {
    if (event) event.preventDefault();

    const name = event.target.name
    const value = event.target.value

    this.setState({
      [name]: value
    })

    console.log(`Set ${name} to ${value}`)
  }

  handleSubmit = (event) => {
    clearInterval(this.interval)
    if (event) event.preventDefault();
    this.setState({ isLoading: true })

    if (this.state.searchType === 'price') {
      console.log("Searching trades by price")
      fetch(`orderbook/trade/query/prices?start=${this.state.startPrice}&end=${this.state.endPrice}`)
        .then(response => response.json())
        .then(data => this.setState({ trades: data, isLoading: false }))
    } else if (this.state.searchType === 'amount') {
      console.log("Searching trades by amount")
      fetch(`orderbook/trade/query/amounts?start=${this.state.startAmount}&end=${this.state.endAmount}`)
        .then(response => response.json())
        .then(data => this.setState({ trades: data, isLoading: false }))
    } else {
      console.log("Searching trades by date")
      console.log(this.state.startDate)
      fetch(`orderbook/trade/query/dates?start=${this.state.startDate}T00:00:00&end=${this.state.endDate}T23:59:59`)
        .then(response => response.json())
        .then(data => this.setState({ trades: data, isLoading: false }))
    }

    this.setState({ isLoading: false })
  }

  render() {
    const { trades, isLoading } = this.state;

    if (isLoading) {
      return <p>Loading...</p>
    }

    const tradesList = trades.map(trade => {
      var date = new Date(trade.datetime);
      date = date.getFullYear() + '-' +
        ('00' + (date.getMonth() + 1)).slice(-2) + '-' +
        ('00' + date.getDate()).slice(-2) + ' ' +
        ('00' + date.getHours()).slice(-2) + ':' +
        ('00' + date.getMinutes()).slice(-2) + ':' +
        ('00' + date.getSeconds()).slice(-2);
      return <tr key={trade.id}>
        <td style={{ whiteSpace: 'nowrap' }}>{date}</td>
        <td style={{ whiteSpace: 'nowrap' }}>${trade.price}</td>
        <td style={{ whiteSpace: 'nowrap' }}>{trade.amount}</td>
      </tr>
    })

    return (
      <div>
        <AppNavbar />
        <Container fluid>
          <div class="row border mb-4 ml-2 mr-2" style={{ backgroundColor: "#D6DBE8" }}>
            <div class="col">
              <div style={{
                margin: '0',
                position: 'absolute',
                top: '50%',
                left: '50%',
                transform: 'translate(-50%, -50%)'
              }}>
                <h2 class="text-center">Search Trades</h2>
              </div>
            </div>
            <div class="col-md-7 mx-auto" align-self-center>
              <form onSubmit={this.handleSubmit}>
                <div class="form-group form-inline mt-3">
                  <label for="searchType" class="mr-2">Search by</label>
                  <select
                    name="searchType"
                    id="searchType"
                    value={this.state.searchType}
                    onChange={this.handleChange}
                    class="form-control">
                    <option value="date">Date</option>
                    <option value="price">Price</option>
                    <option value="amount">Amount</option>
                  </select>
                </div>

                {this.state.searchType === 'price' ? (
                  <div class="form-group form-inline">
                    <div class="form-group form-inline">
                      <label for="startPrice" class="mr-2">From:</label>
                      <div class="input-group mr-3">
                        <div class="input-group-prepend">
                          <span class="input-group-text">$</span>
                        </div>
                        <input
                          type="number"
                          name="startPrice"
                          id="startPrice"
                          value={this.state.startPrice}
                          class="form-control"
                          onChange={this.handleChange} />
                      </div>
                    </div>
                    <div class="form-group form-inline">
                      <label for="endPrice" class="mr-2">To:</label>
                      <div class="input-group">
                        <div class="input-group-prepend">
                          <span class="input-group-text">$</span>
                        </div>
                        <input
                          type="number"
                          name="endPrice"
                          id="endPrice"
                          value={this.state.endPrice}
                          class="form-control"
                          onChange={this.handleChange} />
                      </div>
                    </div>
                  </div>
                ) : (<div></div>)}

                {this.state.searchType === 'amount' ? (
                  <div class="form-group form-inline">
                    <div class="form-group form-inline">
                      <label for="startAmount" class="mr-2">From:</label>
                      <div class="input-group mr-3">
                        <input
                          type="number"
                          name="startAmount"
                          id="startAmount"
                          value={this.state.startAmount}
                          class="form-control"
                          onChange={this.handleChange} />
                      </div>
                    </div>
                    <div class="form-group form-inline">
                      <label for="endAmount" class="mr-2">To:</label>
                      <div class="input-group">
                        <input
                          type="number"
                          name="endAmount"
                          id="endAmount"
                          value={this.state.endAmount}
                          class="form-control"
                          onChange={this.handleChange} />
                      </div>
                    </div>
                  </div>
                ) : (<div></div>)}

                {this.state.searchType === 'date' ? (
                  <div class="form-group form-inline">
                    <div class="form-group form-inline">
                      <label for="startDate" class="mr-2">From:</label>
                      <div class="input-group mr-3">
                        <input
                          type="date"
                          name="startDate"
                          id="startDate"
                          value={this.state.startDate}
                          class="form-control"
                          onChange={this.handleChange} />
                      </div>
                    </div>
                    <div class="form-group form-inline">
                      <label for="endDate" class="mr-2">To:</label>
                      <div class="input-group">
                        <input
                          type="date"
                          name="endDate"
                          id="endDate"
                          value={this.state.endDate}
                          class="form-control"
                          onChange={this.handleChange} />
                      </div>
                    </div>
                  </div>
                ) : (<div></div>)}

                <button type="submit" class="btn btn-primary mb-3">Search All Trades</button>
              </form>
            </div>
          </div>
          <div class="row mb-4 ml-2 mr-2" style={{ backgroundColor: "#DFE7FA" }}>
            <div class="col-md-8 mx-auto">
              <Table
                className="mt-4"
                style={{
                  overflowY: 'auto',
                  height: "750px",
                  display: 'block'
                }}
                striped
                borderless
                hover
                size="sm">
                <thead>
                  <tr>
                    <th width="15%">Date/Time</th>
                    <th width="15%">Price</th>
                    <th width="15%">Amount</th>
                  </tr>
                </thead>
                <tbody>
                  {tradesList}
                </tbody>
              </Table>
            </div>
          </div>
        </Container>
      </div>
    );
  }

}

export default SearchTrades;