import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table, Col, Row, Card, Text } from 'reactstrap';
import AppNavbar from './AppNavbar';

class ShareInfo extends Component {

    constructor(props) {
        super(props);
        this.state = { name: "Katacoin", symbol: "KTC", tickSize: 0.001, percentage: 0.01, high: 12000, low: 200, price: 200 };
    }

    componentDidMount() {
        this.setState({ isLoading: true, userId: this.props.userId });

        this.interval = setInterval(() => this.getShareInfo(), 1000)
    }

    componentWillUnmount() {
        clearInterval(this.interval)
    }

    getShareInfo() {
        fetch(`orderbook/shares/1`)
            .then(response => response.json())
            .then(data => this.setState({
                name: data.name,
                symbol: data.symbol,
                tickSize: data.tickSize,
                percentage: data.percentage,
                high: data.high,
                low: data.low,
                price: data.price,
                isLoading: false
            }));
    }


    render() {
        //const { trades, userId, isLoading } = this.state;

        let percentageColor = "#CE6D6D"
        if (this.state.percentage < 0) {
            percentageColor = "#CE6D6D"
        } else {
            percentageColor = "#40702A"
        }

        return (
            <Container fluid className="border border-secondary" style={{ width: "100%", marginRight: "1em", marginBottom: "2em", padding: "0px" }}>
                <Col style={{ width: "" }}>

                    <Row>
                        <Col style={{ paddingLeft: "2em" }}>
                            <Row>
                                <h4 style={{ fontSize: 40, color: "#CEB575", marginBottom: "0rem" }}>{this.state.symbol}</h4>
                                <h4 className="d-flex align-items-end" float="bottom" style={{ fontSize: 12, textAlign: "bottom", marginBottom: "0rem" }}>/USD</h4>
                            </Row>
                            <Row style={{}}>
                                <h4 className="flex-fill" style={{ fontSize: 15, marginBottom: "0rem" }}>{this.state.name}</h4>
                            </Row>
                            <Row style={{}}>
                                <span style={{ fontSize: 13 }}>Lo: {this.state.low.toFixed(1)}</span>
                            </Row>
                        </Col>
                        <Col >
                            <Row style={{ paddingTop: "0.375em", paddingBottom: "0.375em" }} className="d-flex align-items-end">
                                <h6 className="flex-fill" style={{ fontSize: 30, color: "#40702A", marginBottom: "0rem" }}>{this.state.price.toFixed(2)}</h6>
                            </Row>
                            <Row style={{ contentAlign: "right", fontSize: 16 }}>
                                <h6 className="flex-fill" style={{ fontSize: 15, marginBottom: "0rem", color: percentageColor }}>{this.state.percentage}%</h6>
                            </Row>

                            <Row style={{ paddingTop: "0px", paddingBottom: "5px", paddingRight: "20px", paddingLeft: "0px" }}>
                                <span style={{ fontSize: 13 }}>Hi: {this.state.high.toFixed(1)}</span>
                            </Row>

                        </Col>
                    </Row>

                </Col>


            </Container>
        );
    }
}

export default ShareInfo;