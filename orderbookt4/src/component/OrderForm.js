import React from 'react';
import { Form, Button, Row, Col, FormGroup, Input, Label } from 'reactstrap';

class OrderForm extends React.Component {

    constructor(props) {
        super(props);
        this.state = { isMarket: false, price: "", amount: "", isLoading: true };

        this.handleChangePrice = this.handleChangePrice.bind(this);
        this.handleChangeAmount = this.handleChangeAmount.bind(this);
        this.handleIsMarket = this.handleIsMarket.bind(this);
        this.sellOrder = this.sellOrder.bind(this);
        this.buyOrder = this.buyOrder.bind(this);
    }


    handleChangePrice = (event) => {
        this.setState({ price: event.target.value });
        console.log(this.state.price);
    }

    handleChangeAmount(event) {
        this.setState({ amount: event.target.value });
        console.log(this.state.amount);
    }


    handleIsMarket(event) {
        if (event.target.value == "true") {
            this.setState({ isMarket: true });
            this.setState({ price: "" });
        }
        else {
            this.setState({ isMarket: false });
        }
    }

    async matchBid() {
        console.log(this.state);

        let bidPrice = await fetch(`/orderbook/bidprice/`)
            .then(response => response.json())
        console.log(bidPrice);
        this.setState({ price: bidPrice });
    }

    async matchAsk() {
        console.log(this.state);

        let askPrice = await fetch(`/orderbook/askprice/`)
            .then(response => response.json());

        this.setState({ price: askPrice });
    }

    async buyOrder() {
        console.log(this.state);
        if (this.state.amount > 0) {
            if (this.state.isMarket) {
                let market = await fetch('/orderbook/trades')
                    .then(response => response.json())
                let marketPrice = market[0].price;

                await fetch(`/orderbook/createOrder/${2}/${false}/${marketPrice}/${this.state.amount}`, {
                    method: 'POST'
                });
                this.setState({ isMarket: false, price: "", amount: "", isLoading: false });
            }
            else {
                if (this.state.price > 0) {
                    await fetch(`/orderbook/createOrder/${2}/${false}/${this.state.price}/${this.state.amount}`, {
                        method: 'POST'
                    });
                    this.setState({ isMarket: false, price: "", amount: "", isLoading: false });
                }
            }
        }

    }

    async sellOrder() {
        console.log(this.state);
        if (this.state.amount > 0) {
            if (this.state.isMarket) {
                let market = await fetch('/orderbook/trades')
                    .then(response => response.json())
                let marketPrice = market[0].price;

                await fetch(`/orderbook/createOrder/${2}/${true}/${marketPrice}/${this.state.amount}`, {
                    method: 'POST'
                });
                this.setState({ isMarket: false, price: "", amount: "", isLoading: false });
            }
            else {
                if (this.state.price > 0) {
                    await fetch(`/orderbook/createOrder/${2}/${true}/${this.state.price}/${this.state.amount}`, {
                        method: 'POST'
                    });
                    this.setState({ isMarket: false, price: "", amount: "", isLoading: false });
                }
            }
        }

    }

    render() {
        return (
            <div style={{ width: "95%", backgroundColor: "#CAD3E8", marginTop: "2em", padding: "15px" }}>
                <Form style={{ width: "100%" }} >
                    <FormGroup>
                        <Label className="text-center" style={{ width: "100%" }}>
                            <h5 >Type of order:</h5>
                        </Label>
                        <Input type="select" className="text-center" onChange={this.handleIsMarket}>
                            <option value={false}>Limit</option>
                            <option value={true}>Market</option>
                        </Input>
                    </FormGroup>

                    <FormGroup>
                        <Label className="text-center" style={{ width: "100%" }}>
                            <h5 >Price:</h5>
                        </Label>
                        <Button outline onClick={() => this.matchAsk()} disabled={this.state.isMarket} size="sm" color="success" style={{ width: "49%", marginBottom: "1em" }} >Match Ask</Button>
                        <Button outline onClick={() => this.matchBid()} disabled={this.state.isMarket} size="sm" color="danger" style={{ width: "49%", float: "right" }} >Match Bid</Button>
                        <Input type="number" min={0.00001} step={0.00001} oninput="validity.valid||(value='');" className="text-center" readOnly={this.state.isMarket} value={this.state.price} onChange={this.handleChangePrice}>
                        </Input>
                    </FormGroup>

                    <FormGroup>
                        <Label className="text-center" style={{ width: "100%" }}>
                            <h5 >Amount:</h5>
                        </Label>
                        <Input type="number" min={0.00001} step={0.00001} oninput="validity.valid||(value='');" className="text-center" value={this.state.amount} onChange={this.handleChangeAmount}>
                        </Input>
                    </FormGroup>

                    <Row >
                        <Col>
                            <Button onClick={() => this.buyOrder()} color="success" style={{ width: "49%" }} >Buy</Button>
                            <Button onClick={() => this.sellOrder()} color="danger" style={{ width: "49%", float: "right" }} >Sell</Button>
                        </Col>

                    </Row>


                </Form>
            </div>
        )
    }
}

export default OrderForm