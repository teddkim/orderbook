import React, { Component } from 'react';
import { Container, Row, Col } from 'reactstrap';
import AppNavbar from './AppNavbar.js';
import BuyOrderList from './BuyOrderList.js';
import KTCGraph from './KTCGraph.js';
import SellOrderList from './SellOrderList';
import TickerFeed from './TickerFeed';
import OrderForm from './OrderForm';
import ShareInfo from './ShareInfo';

class MainPage extends Component {
    render() {
        return (
            <div class="mb-5">
                <AppNavbar />
                <Container >
                    <Row>
                        <Col sm={3}>
                            <Row >
                                <ShareInfo />
                            </Row>
                            <Row>
                                <TickerFeed />
                            </Row>
                            <Row>
                                <OrderForm />
                            </Row>
                        </Col>
                        <Col sm={9}>
                            <Row>
                                <KTCGraph />
                            </Row>
                            <Row>
                                <Col >
                                    <BuyOrderList />
                                </Col>
                                <Col >
                                    <SellOrderList />
                                </Col>
                            </Row>
                        </Col>

                    </Row>

                </Container>
            </div>
        );
    }
}

export default MainPage;