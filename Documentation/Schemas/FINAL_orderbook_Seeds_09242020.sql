USE `Orderbook` ;

-- -----------------------------------------------------
-- Team 4 OrderbookTest Seeds
-- -----------------------------------------------------

-- 
-- Users
-- 

-- user 1 dummy
INSERT INTO user (username, password, coin, dollars) VALUES 
('dummy','password1', 10000.00, 5000.00);
-- user 2 team44lyfe
INSERT INTO user (username, password, coin, dollars) VALUES 
('team44lyfe','password1', 500.00, 5000.00);
-- user 3 team2guy
INSERT INTO user (username, password, coin, dollars) VALUES
('team2guy','password1', 100.00, 5000.00);
-- user 4 shadyrichguy
INSERT INTO user (username, password, coin, dollars) VALUES
('shadyrichguy','password1', 250.00, 5000.00);
-- user 5 richinvestor
INSERT INTO user (username, password, coin, dollars) VALUES
('richinvestor','password1', 400.00, 5000.00);

-- 
-- Shares
-- 

-- Katacoin
INSERT INTO share (name, symbol, tickSize, percentage, high, low, price) VALUES
('Katacoin','KTC',.001,0.0, 11000, 500, 10000);
-- USD
INSERT INTO share (name, symbol, tickSize, percentage, high, low, price) VALUES
('Dollars','USD',.01,0.0, 1, 1, 1);

-- 
-- Trades
-- 

-- Trade 1
--
-- Order 1
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(0, 5000, 1, '2020-09-11', 1, 4);
-- Order 2
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(1, 5000, 1, '2020-09-11', 1, 2);
-- Trade 1
INSERT INTO trade (datetime, price, amount, buyOrder, sellOrder) VALUES
('2020-09-11', 5000, 1, 1, 2);
--
-- Trade 2
--
-- Order 3
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(0, 5000, 2, '2020-09-11', 1, 2);
-- Order 4
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(1, 5000, 2, '2020-09-11', 1, 3);
-- Trade 2
INSERT INTO trade (datetime, price, amount, buyOrder, sellOrder) VALUES
('2020-09-11', 5000, 1, 3, 4);

-- Trade 3
--
-- Order 1
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(0, 6000, 1, '2020-09-11', 1, 2);
-- Order 2
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(1, 6000, 1, '2020-09-11', 1, 4);
-- Trade 1
INSERT INTO trade (datetime, price, amount, buyOrder, sellOrder) VALUES
('2020-09-11', 6000, 1, 5, 6);

-- Trade 4
--
-- Order 1
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(0, 5000, 3, '2020-09-13', 1, 2);
-- Order 2
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(1, 5000, 3, '2020-09-13', 1, 3);
-- Trade 1
INSERT INTO trade (datetime, price, amount, buyOrder, sellOrder) VALUES
('2020-09-13', 5000, 3, 7, 8);

-- Trade 5
--
-- Order 1
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(0, 6000, 2, '2020-09-14', 1, 2);
-- Order 2
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(1, 6000, 2, '2020-09-14', 1, 4);
-- Trade 1
INSERT INTO trade (datetime, price, amount, buyOrder, sellOrder) VALUES
('2020-09-14', 6000, 2, 9, 10);

-- Trade 6
--
-- Order 1
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(0, 6000, 2, '2020-09-14', 1, 4);
-- Order 2
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(1, 6000, 2, '2020-09-14', 1, 2);
-- Trade 1
INSERT INTO trade (datetime, price, amount, buyOrder, sellOrder) VALUES
('2020-09-14', 6000, 2, 11, 12);

-- Trade 7
--
-- Order 1
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(0, 6200, 2, '2020-09-14', 1, 4);
-- Order 2
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(1, 6200, 2, '2020-09-14', 1, 2);
-- Trade 1
INSERT INTO trade (datetime, price, amount, buyOrder, sellOrder) VALUES
('2020-09-14', 6200, 2, 13, 14);

-- Trade 8
--
-- Order 1
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(0, 4000, 1, '2020-09-16', 1, 2);
-- Order 2
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(1, 4000, 1, '2020-09-16', 1, 3);
-- Trade 1
INSERT INTO trade (datetime, price, amount, buyOrder, sellOrder) VALUES
('2020-09-16', 4000, 1, 15, 16);

-- Trade 9
--
-- Order 1
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(0, 7000, 2, '2020-09-18', 1, 2);
-- Order 2
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(1, 7000, 2, '2020-09-18', 1, 4);
-- Trade 1
INSERT INTO trade (datetime, price, amount, buyOrder, sellOrder) VALUES
('2020-09-18', 7000, 2, 17, 18);

-- Trade 10
--
-- Order 1
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(0, 6000, 2, '2020-09-20', 1, 2);
-- Order 2
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(1, 6000, 2, '2020-09-20', 1, 4);
-- Trade 1
INSERT INTO trade (datetime, price, amount, buyOrder, sellOrder) VALUES
('2020-09-20', 6000, 2, 19, 20);

-- Trade 11
--
-- Order 1
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(0, 6000, 2, '2020-09-20T13:54:11', 1, 2);
-- Order 2
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(1, 6000, 2, '2020-09-20T13:54:11', 1, 4);
-- Trade 1
INSERT INTO trade (datetime, price, amount, buyOrder, sellOrder) VALUES
('2020-09-20T13:54:11', 6000, 2, 21, 22);

-- Trade 12
--
-- Order 1
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(0, 7000, 1, '2020-09-20T16:51:19', 1, 4);
-- Order 2
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(1, 7000, 1, '2020-09-20T16:51:19', 1, 3);
-- Trade 1
INSERT INTO trade (datetime, price, amount, buyOrder, sellOrder) VALUES
('2020-09-20T16:51:19', 7000, 1, 23, 24);

-- Trade 13
--
-- Order 1
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(0, 7100, 2, '2020-09-21T09:31:19', 1, 4);
-- Order 2
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(1, 7100, 2, '2020-09-21T09:31:19', 1, 3);
-- Trade 1
INSERT INTO trade (datetime, price, amount, buyOrder, sellOrder) VALUES
('2020-09-21T09:31:19', 7100, 2, 25, 26);

-- Trade 14
--
-- Order 1
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(0, 7050, 2, '2020-09-21T09:32:33', 1, 3);
-- Order 2
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(1, 7050, 2, '2020-09-21T09:32:33', 1, 4);
-- Trade 1
INSERT INTO trade (datetime, price, amount, buyOrder, sellOrder) VALUES
('2020-09-21T09:32:33', 7050, 2, 27, 28);

-- Trade 15
--
-- Order 1
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(0, 7150, 1, '2020-09-21T11:30:34', 1, 5);
-- Order 2
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(1, 7150, 1, '2020-09-21T11:30:34', 1, 4);
-- Trade 1
INSERT INTO trade (datetime, price, amount, buyOrder, sellOrder) VALUES
('2020-09-21T11:30:34', 7150, 1, 29, 30);

-- Trade 16
--
-- Order 1
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(0, 7250, 1, '2020-09-21T13:42:11', 1, 2);
-- Order 2
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(1, 7250, 1, '2020-09-21T13:42:11', 1, 4);
-- Trade 1
INSERT INTO trade (datetime, price, amount, buyOrder, sellOrder) VALUES
('2020-09-21T13:42:11', 7250, 1, 31, 32);

-- Trade 17
--
-- Order 1
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(0, 7200, 2, '2020-09-21T15:59:11', 1, 2);
-- Order 2
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(1, 7200, 2, '2020-09-21T15:59:11', 1, 4);
-- Trade 1
INSERT INTO trade (datetime, price, amount, buyOrder, sellOrder) VALUES
('2020-09-21T15:59:11', 7200, 2, 33, 34);

-- Trade 18
--
-- Order 1
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(0, 7250, 2, '2020-09-21T15:59:45', 1, 2);
-- Order 2
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(1, 7250, 2, '2020-09-21T15:59:45', 1, 4);
-- Trade 1
INSERT INTO trade (datetime, price, amount, buyOrder, sellOrder) VALUES
('2020-09-21T15:59:45', 7250, 2, 35, 36);

-- Trade 19
--
-- Order 1
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(0, 7400, 1, '2020-09-22T09:57:32', 1, 4);
-- Order 2
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(1, 7400, 1, '2020-09-22T09:57:32', 1, 5);
-- Trade 1
INSERT INTO trade (datetime, price, amount, buyOrder, sellOrder) VALUES
('2020-09-22T09:57:32', 7400, 1, 37, 38);

-- Trade 20
--
-- Order 1
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(0, 7420, 1, '2020-09-22T10:30:36', 1, 4);
-- Order 2
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(1, 7420, 1, '2020-09-22T10:30:36', 1, 5);
-- Trade 1
INSERT INTO trade (datetime, price, amount, buyOrder, sellOrder) VALUES
('2020-09-22T10:30:36', 7420, 1, 39, 40);

-- Trade 21
--
-- Order 1
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(0, 7500, 1, '2020-09-22T10:37:37', 1, 3);
-- Order 2
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(1, 7500, 1, '2020-09-22T10:37:37', 1, 4);
-- Trade 1
INSERT INTO trade (datetime, price, amount, buyOrder, sellOrder) VALUES
('2020-09-22T10:37:37', 7500, 1, 41, 42);

-- Trade 22
--
-- Order 1
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(0, 7400, 1, '2020-09-22T11:41:51', 1, 4);
-- Order 2
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(1, 7400, 1, '2020-09-22T11:41:51', 1, 3);
-- Trade 1
INSERT INTO trade (datetime, price, amount, buyOrder, sellOrder) VALUES
('2020-09-22T11:41:51', 7400, 1, 43, 44);

-- Trade 23
--
-- Order 1
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(0, 7450, 1, '2020-09-22T12:15:51', 1, 4);
-- Order 2
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(1, 7450, 1, '2020-09-22T12:15:51', 1, 3);
-- Trade 1
INSERT INTO trade (datetime, price, amount, buyOrder, sellOrder) VALUES
('2020-09-22T12:15:51', 7450, 1, 45, 46);

-- Trade 24
--
-- Order 1
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(0, 7500, 1, '2020-09-22T12:31:51', 1, 2);
-- Order 2
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(1, 7500, 1, '2020-09-22T12:31:51', 1, 3);
-- Trade 1
INSERT INTO trade (datetime, price, amount, buyOrder, sellOrder) VALUES
('2020-09-22T12:31:51', 7500, 1, 47, 48);

-- Trade 25
--
-- Order 1
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(0, 7560, 1, '2020-09-22T12:59:31', 1, 4);
-- Order 2
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(1, 7560, 1, '2020-09-22T12:59:31', 1, 3);
-- Trade 1
INSERT INTO trade (datetime, price, amount, buyOrder, sellOrder) VALUES
('2020-09-22T12:59:31', 7560, 1, 49, 50);

-- Trade 26
--
-- Order 1
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(0, 7600, 1, '2020-09-22T13:40:21', 1, 4);
-- Order 2
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(1, 7600, 1, '2020-09-22T13:40:21', 1, 3);
-- Trade 1
INSERT INTO trade (datetime, price, amount, buyOrder, sellOrder) VALUES
('2020-09-22T13:40:21', 7600, 1, 51, 52);

-- Trade 27
--
-- Order 1
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(0, 7650, 1, '2020-09-22T13:52:22', 1, 3);
-- Order 2
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(1, 7650, 1, '2020-09-22T13:52:22', 1, 4);
-- Trade 1
INSERT INTO trade (datetime, price, amount, buyOrder, sellOrder) VALUES
('2020-09-22T13:52:22', 7650, 1, 53, 54);

-- Trade 28
--
-- Order 1
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(0, 7700, 1, '2020-09-22T14:32:22', 1, 2);
-- Order 2
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(1, 7700, 1, '2020-09-22T14:32:22', 1, 4);
-- Trade 1
INSERT INTO trade (datetime, price, amount, buyOrder, sellOrder) VALUES
('2020-09-22T14:32:22', 7700, 1, 55, 56);

-- Trade 29
--
-- Order 1
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(0, 7650, 1, '2020-09-22T15:13:45', 1, 3);
-- Order 2
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(1, 7650, 1, '2020-09-22T15:13:45', 1, 4);
-- Trade 1
INSERT INTO trade (datetime, price, amount, buyOrder, sellOrder) VALUES
('2020-09-22T15:13:45', 7650, 1, 57, 58);

-- Trade 30
--
-- Order 1
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(0, 7700, 1, '2020-09-22T15:45:45', 1, 3);
-- Order 2
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(1, 7700, 1, '2020-09-22T15:45:45', 1, 4);
-- Trade 1
INSERT INTO trade (datetime, price, amount, buyOrder, sellOrder) VALUES
('2020-09-22T15:45:45', 7700, 1, 59, 60);

-- Trade 31
--
-- Order 1
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(0, 7750, 1, '2020-09-23T09:45:45', 1, 2);
-- Order 2
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(1, 7750, 1, '2020-09-23T09:45:45', 1, 5);
-- Trade 1
INSERT INTO trade (datetime, price, amount, buyOrder, sellOrder) VALUES
('2020-09-23T09:45:45', 7750, 1, 61, 62);

-- Trade 32
--
-- Order 1
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(0, 7800, 1, '2020-09-23T10:21:36', 1, 4);
-- Order 2
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(1, 7800, 1, '2020-09-23T10:21:36', 1, 2);
-- Trade 1
INSERT INTO trade (datetime, price, amount, buyOrder, sellOrder) VALUES
('2020-09-23T10:21:36', 7800, 1, 63, 64);

-- Trade 33
--
-- Order 1
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(0, 7900, 1, '2020-09-23T10:58:11', 1, 3);
-- Order 2
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(1, 7900, 1, '2020-09-23T10:58:11', 1, 4);
-- Trade 1
INSERT INTO trade (datetime, price, amount, buyOrder, sellOrder) VALUES
('2020-09-23T10:58:11', 7900, 1, 65, 66);

-- Trade 34
--
-- Order 1
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(0, 7850, 1, '2020-09-23T11:09:30', 1, 3);
-- Order 2
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(1, 7850, 1, '2020-09-23T11:09:30', 1, 4);
-- Trade 1
INSERT INTO trade (datetime, price, amount, buyOrder, sellOrder) VALUES
('2020-09-23T11:09:30', 7850, 1, 67, 68);

-- Trade 35
--
-- Order 1
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(0, 7900, 1, '2020-09-23T11:54:30', 1, 2);
-- Order 2
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(1, 7900, 1, '2020-09-23T11:54:30', 1, 4);
-- Trade 1
INSERT INTO trade (datetime, price, amount, buyOrder, sellOrder) VALUES
('2020-09-23T11:54:30', 7900, 1, 69, 70);

-- Trade 36
--
-- Order 1
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(0, 8000, 2, '2020-09-23T12:30:41', 1, 2);
-- Order 2
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(1, 8000, 2, '2020-09-23T12:30:41', 1, 3);
-- Trade 1
INSERT INTO trade (datetime, price, amount, buyOrder, sellOrder) VALUES
('2020-09-23T12:30:41', 8000, 2, 71, 72);

-- Trade 37
--
-- Order 1
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(0, 8100, 1, '2020-09-23T13:42:41', 1, 3);
-- Order 2
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(1, 8100, 1, '2020-09-23T13:42:41', 1, 4);
-- Trade 1
INSERT INTO trade (datetime, price, amount, buyOrder, sellOrder) VALUES
('2020-09-23T13:42:41', 8100, 1, 73, 74);

-- Trade 38
--
-- Order 1
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(0, 8200, 1, '2020-09-23T13:51:36', 1, 3);
-- Order 2
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(1, 8200, 1, '2020-09-23T13:51:36', 1, 4);
-- Trade 1
INSERT INTO trade (datetime, price, amount, buyOrder, sellOrder) VALUES
('2020-09-23T13:51:36', 8200, 1, 75, 76);

-- Trade 39
--
-- Order 1
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(0, 8150, 1, '2020-09-23T15:01:01', 1, 2);
-- Order 2
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(1, 8150, 1, '2020-09-23T15:01:01', 1, 4);
-- Trade 1
INSERT INTO trade (datetime, price, amount, buyOrder, sellOrder) VALUES
('2020-09-23T15:01:01', 8150, 1, 77, 78);

-- Trade 40
--
-- Order 1
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(0, 8250, 1, '2020-09-23T15:40:11', 1, 3);
-- Order 2
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(1, 8250, 1, '2020-09-23T15:40:11', 1, 4);
-- Trade 1
INSERT INTO trade (datetime, price, amount, buyOrder, sellOrder) VALUES
('2020-09-23T15:40:11', 8250, 1, 79, 80);

-- Trade 41
--
-- Order 1
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(0, 8250, 2, '2020-09-24T09:31:40', 1, 3);
-- Order 2
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(1, 8250, 2, '2020-09-24T09:31:40', 1, 4);
-- Trade 1
INSERT INTO trade (datetime, price, amount, buyOrder, sellOrder) VALUES
('2020-09-24T09:31:40', 8250, 2, 81, 82);

-- Trade 42
--
-- Order 1
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(0, 8300, 2, '2020-09-24T10:01:40', 1, 2);
-- Order 2
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(1, 8300, 2, '2020-09-24T10:01:40', 1, 4);
-- Trade 1
INSERT INTO trade (datetime, price, amount, buyOrder, sellOrder) VALUES
('2020-09-24T10:01:40', 8300, 2, 83, 84);

-- Trade 43
--
-- Order 1
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(0, 8350, 1, '2020-09-24T11:11:11', 1, 2);
-- Order 2
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(1, 8350, 1, '2020-09-24T11:11:11', 1, 4);
-- Trade 1
INSERT INTO trade (datetime, price, amount, buyOrder, sellOrder) VALUES
('2020-09-24T11:11:11', 8350, 1, 85, 86);

-- Trade 44
--
-- Order 1
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(0, 8250, 1, '2020-09-24T11:41:20', 1, 3);
-- Order 2
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(1, 8250, 1, '2020-09-24T11:41:20', 1, 4);
-- Trade 1
INSERT INTO trade (datetime, price, amount, buyOrder, sellOrder) VALUES
('2020-09-24T11:41:20', 8250, 1, 87, 88);

-- Trade 45
--
-- Order 1
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(0, 8400, 1, '2020-09-24T12:30:43', 1, 4);
-- Order 2
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(1, 8400, 1, '2020-09-24T12:30:43', 1, 2);
-- Trade 1
INSERT INTO trade (datetime, price, amount, buyOrder, sellOrder) VALUES
('2020-09-24T12:30:43', 8400, 1, 89, 90);

-- Trade 46
--
-- Order 1
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(0, 8500, 1, '2020-09-24T12:50:13', 1, 3);
-- Order 2
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(1, 8500, 1, '2020-09-24T12:50:13', 1, 4);
-- Trade 1
INSERT INTO trade (datetime, price, amount, buyOrder, sellOrder) VALUES
('2020-09-24T12:50:13', 8500, 1, 91, 92);

-- Trade 47
--
-- Order 1
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(0, 8600, 1, '2020-09-24T13:12:38', 1, 3);
-- Order 2
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(1, 8600, 1, '2020-09-24T13:12:38', 1, 4);
-- Trade 1
INSERT INTO trade (datetime, price, amount, buyOrder, sellOrder) VALUES
('2020-09-24T13:12:38', 8600, 1, 93, 94);

-- Trade 48
--
-- Order 1
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(0, 8700, 1, '2020-09-24T14:30:10', 1, 2);
-- Order 2
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(1, 8700, 1, '2020-09-24T14:30:10', 1, 4);
-- Trade 1
INSERT INTO trade (datetime, price, amount, buyOrder, sellOrder) VALUES
('2020-09-24T14:30:10', 8700, 1, 95, 96);

-- Trade 49
--
-- Order 1
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(0, 8750, 1, '2020-09-24T14:50:17', 1, 3);
-- Order 2
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(1, 8750, 1, '2020-09-24T14:50:17', 1, 2);
-- Trade 1
INSERT INTO trade (datetime, price, amount, buyOrder, sellOrder) VALUES
('2020-09-24T14:50:17', 8750, 1, 97, 98);

-- Trade 50
--
-- Order 1
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(0, 8900, 1, '2020-09-24T15:50:12', 1, 3);
-- Order 2
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(1, 8900, 1, '2020-09-24T15:50:12', 1, 4);
-- Trade 1
INSERT INTO trade (datetime, price, amount, buyOrder, sellOrder) VALUES
('2020-09-24T15:50:12', 8900, 1, 99, 100);

-- Incomplete Orders
--
-- Order 1
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(0, 99999, 1, '2020-09-09T11:50:12', 0, 2);
-- Order 2
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(0, 99999, 1, '2020-09-09T15:50:12', 0, 2);

-- Opening Prices
--
-- 09 20 2020
INSERT INTO OpeningPrices (datetime, price, shareId) VALUES
('2020-09-20T16:30:00', 7000, 1);
-- 09 21 2020
INSERT INTO OpeningPrices (datetime, price, shareId) VALUES
('2020-09-21T16:30:00', 7250, 1);
-- 09 22 2020
INSERT INTO OpeningPrices (datetime, price, shareId) VALUES
('2020-09-22T16:30:00', 7700, 1);
-- 09 23 2020
INSERT INTO OpeningPrices (datetime, price, shareId) VALUES
('2020-09-23T16:30:00', 8250, 1);
-- 09 24 2020
INSERT INTO OpeningPrices (datetime, price, shareId) VALUES
('2020-09-24T16:30:00', 8900, 1);

-- SELECT * FROM user;
-- SELECT * FROM share;
-- SELECT * FROM Orderbook.OrderTable;
-- SELECT * FROM trade 
-- SELECT * FROM OpeningPrices;