USE `Orderbook` ;

-- -----------------------------------------------------
-- Team 4 OrderbookTest Seeds
-- -----------------------------------------------------

-- 
-- Users 
-- 

-- Dummy user 1
INSERT INTO user (username, password, coin, dollars) VALUES 
('dummy','password1', 10000.00);
-- user 2
INSERT INTO user (username, password, coin, dollars) VALUES 
('team44lyfe','password1', 500.00);
-- user 3
INSERT INTO user (username, password, coin, dollars) VALUES
('team2guy','password1', 100.00);
-- user 4
INSERT INTO user (username, password, coin, dollars) VALUES
('shadyrichguy','password1', 250.00);
-- user 5
INSERT INTO user (username, password, coin, dollars) VALUES
('richinvestor','password1', 400.00);

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
(0, 10000, 1, '2020-09-11', 1, 1);
-- Order 2
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(1, 10000, 1, '2020-09-11', 1, 2);
-- Trade 1
INSERT INTO trade (datetime, price, amount, buyOrder, sellOrder) VALUES
('2020-09-11', 10000, 1, 1, 2);
--
-- Trade 2
--
-- Order 3
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(0, 8000, 2, '2020-09-10', 1, 1);
-- Order 4
INSERT INTO Orderbook.OrderTable (type, price, amount, datetime, completed, userId) VALUES
(1, 8000, 2, '2020-09-10', 1, 3);
-- Trade 2
INSERT INTO trade (datetime, price, amount, buyOrder, sellOrder) VALUES
('2020-09-10', 8000, 1, 3, 4);

SELECT * FROM user;
SELECT * FROM share;
SELECT * FROM Orderbook.OrderTable;
SELECT * FROM trade;