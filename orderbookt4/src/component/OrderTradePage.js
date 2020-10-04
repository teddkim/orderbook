import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table, Row, Col } from 'reactstrap';
import AppNavbar from './AppNavbar';
import TradeList from './TradeList.js';
import OrderList from './OrderList';

const OrderTradePage = () => {
    return(
        <div>
            <AppNavbar />
            <Row>
                <Col style={{paddingRight: "0px", paddingLeft: "20px"}}>
                    <OrderList/>
                </Col>
                <Col style={{paddingRight: "20px", paddingLeft: "0px"}}>
                    <TradeList/>
                </Col>
            </Row>

        </div>
    );
}

export default OrderTradePage;