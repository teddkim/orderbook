/* KTCGraph.js */
import React, { Component } from 'react';
import { Container, Button, Row, Col } from 'reactstrap';

import CanvasJSReact from "../canvasjs.react";
//var CanvasJSReact = require('../canvasjs.react');
var CanvasJS = CanvasJSReact.CanvasJS;
var CanvasJSChart = CanvasJSReact.CanvasJSChart;

class KTCGraph extends Component {

	constructor(props) {
		super(props);
		this.state = {
			points: [],
			isLoading: true,
			graphTime: "day"
		};
	}

	componentDidMount() {
		this.setState({ isLoading: true });

		this.interval = setInterval(() => this.getGraph(), 10000)
	}

	componentWillUnmount() {
		clearInterval(this.interval)
	}

	getGraph() {
		if (this.state.graphTime === "day") {
			this.getDayGraph();
		} else {
			this.getWeekGraph();
		}
	}

	getDayGraph() {
		fetch('orderbook/trades/graph')
			.then(response => response.json())
			.then(data => this.setState({ points: data, isLoading: false }));
	}

	getWeekGraph() {
		fetch('orderbook/trades/graph/week')
			.then(response => response.json())
			.then(data => this.setState({ points: data, isLoading: false }));
	}

	handleClick = (event) => {
		if (event) event.preventDefault();

		const value = event.target.value;
		this.setState({
			graphTime: value,
			isLoading: true
		}, () => {
			console.log(this.state.graphTime);
		})
	}

	getGraphPoints(dataPoints) {
		var result = [];
		dataPoints.forEach(element => {
			var newObj = {};
			newObj.x = new Date(element.datetime);
			newObj.y = element.price;
			result.push(newObj)
		});
		return result;
	}

	render() {

		const { points, isLoading } = this.state;

		if (isLoading) {
			return (
				<p style={{ textAlign: "center", margin: "auto", padding: "2em" }}>Loading graph by {this.state.graphTime}...</p>
			);
		}

		const options = {
			theme: "light2",
			animationEnabled: true,
			exportEnabled: false,
			title: {
				text: ""
			},
			toolTip: {
				borderColor: "green"   //change color 
			},
			axisY: {
				title: "Price"
			},
			data: [{
				type: "area",
				xValueFormatString: "MM DD YYYY hh mm",
				fillOpacity: .1,
				markerSize: 1,
				nullDataLineDashType: "dot",
				cursor: "crosshair",
				dataPoints: this.getGraphPoints(points)
				// [
				// 	{ x: new Date("2017-01-01"), y: 1792 },
				// 	{ x: new Date("2017-02-20"), y: 1526 },
				// 	{ x: new Date("2017-03-11"), y: 1955 },
				// 	{ x: new Date("2017-04-05"), y: 1727 },
				// 	{ x: new Date("2017-05-04"), y: 1523 },
				// 	{ x: new Date("2017-06-21"), y: 1257 },
				// 	{ x: new Date("2017-07-05"), y: 1520 },
				// 	{ x: new Date("2017-08-03"), y: 1853 },
				// 	{ x: new Date("2017-09-11"), y: 1738 },
				// 	{ x: new Date("2017-10-03"), y: 1754 }
				// ]
			}]
		}

		return (
			<Container fluid style={{ marginBottom: "2em" }}>
				<Row>
					<CanvasJSChart options={options} />
				</Row>
				<Row >
					<Col>
						<div style={{ float: "right", marginTop: "1em" }}>
							<Button outline onClick={this.handleClick} value="day" color="secondary" style={{ marginRight: "5px" }} size="sm">1D</Button>
							<Button outline onClick={this.handleClick} value="week" color="secondary" style={{}} size="sm">1W</Button>
						</div>
					</Col>
				</Row>
			</Container >
		);
	}
}
export default KTCGraph;        