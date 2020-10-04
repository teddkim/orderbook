import React, { Component } from 'react';
import { Collapse, Nav, Navbar, NavbarBrand, NavbarToggler, NavItem, NavLink } from 'reactstrap';
import Logo from './T4-01.png';

export default class AppNavbar extends Component {
  constructor(props) {
    super(props);
    this.state = { isOpen: false };
    this.toggle = this.toggle.bind(this);
  }

  toggle() {
    this.setState({
      isOpen: !this.state.isOpen
    });
  }

  render() {
    const links = [
      { href: '/', text: 'Home' },
      { href: '/orders', text: 'Manage Orders' },
      { href: '/searchtrades', text: 'Search Trades' },
    ];

    const createNavItem = ({ href, text, className }) => (
      <NavItem>
        <NavLink href={href} className={className}>{text}</NavLink>
      </NavItem>
    );

    return <Navbar color="dark" dark expand="md" style={{ padding: "0rem 1rem" }}>
      <NavbarBrand href="/" class="mr-auto" style={{ display: "contents" }}><img src={Logo} style={{ width: "5%", height: "5%" }} /></NavbarBrand>
      <NavbarToggler onClick={this.toggle} />
      <Collapse isOpen={this.state.isOpen} navbar>
        <Nav className="ml-auto" navbar>
          {links.map(createNavItem)}
          {/* <NavItem>
            <NavLink
              href="/">Home</NavLink>
          </NavItem>
          <NavItem>
            <NavLink
              href="/orders">Manage Orders</NavLink>
          </NavItem>
          <NavItem>
            <NavLink href="/searchtrades">Search Trades</NavLink>
          </NavItem> */}
        </Nav>
      </Collapse>
    </Navbar>;
  }
}
