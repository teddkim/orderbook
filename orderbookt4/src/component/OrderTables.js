import React, { Component } from 'react';
import { Container, Table, Row, Col } from 'reactstrap';
import BuyOrderList from './BuyOrderList'
import SellOrderList from './SellOrderList';

const OrderTables = () => {
    return(
        <div>
            <Row>
                <Col>
                    <BuyOrderList/>
                </Col>
                <Col>
                    <SellOrderList/>
                </Col>
            </Row>

        </div>
    );
}

export default OrderTables