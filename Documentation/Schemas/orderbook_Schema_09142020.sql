-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema OrderbookTest
-- -----------------------------------------------------
DROP DATABASE IF EXISTS `OrderbookTest` ;

-- -----------------------------------------------------
-- DATABASE OrderbookTest
-- -----------------------------------------------------
CREATE DATABASE IF NOT EXISTS `OrderbookTest` DEFAULT CHARACTER SET utf8 ;
USE `OrderbookTest` ;

-- -----------------------------------------------------
-- Table `OrderbookTest`.`User`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `OrderbookTest`.`User` ;

CREATE TABLE IF NOT EXISTS `OrderbookTest`.`User` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(20) NOT NULL,
  `password` VARCHAR(25) NOT NULL,
  `coin` DECIMAL(7,2),
  `dollars` DECIMAL(7,2),
  PRIMARY KEY (`id`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `OrderbookTest`.`Order`
-- -----------------------------------------------------
<<<<<<< HEAD
DROP TABLE IF EXISTS `Orderbook`.`OrderTable` ;

CREATE TABLE IF NOT EXISTS `Orderbook`.`OrderTable` (
=======
DROP TABLE IF EXISTS `OrderbookTest`.`OrderTable` ;

CREATE TABLE IF NOT EXISTS `OrderbookTest`.`OrderTable` (
>>>>>>> origin/kenneth
  `id` INT NOT NULL AUTO_INCREMENT,
  `type` TINYINT(1) NOT NULL,
  `price` DECIMAL(10,5) NOT NULL,
  `amount` DECIMAL(8,2) NOT NULL,
  `datetime` DATETIME NOT NULL,
  `completed` TINYINT(1) NOT NULL,
  `userId` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Order_User_idx` (`userId` ASC) VISIBLE,
  CONSTRAINT `fk_Order_User`
    FOREIGN KEY (`userId`)
    REFERENCES `OrderbookTest`.`User` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `OrderbookTest`.`Trade`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `OrderbookTest`.`Trade` ;

CREATE TABLE IF NOT EXISTS `OrderbookTest`.`Trade` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `datetime` DATETIME NOT NULL,
  `price` DECIMAL(10,5) NOT NULL,
  `amount` DECIMAL(8,2) NOT NULL,
  `buyOrder` INT NOT NULL,
  `sellOrder` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `sellOrder_fk_idx` (`buyOrder` ASC) VISIBLE,
  CONSTRAINT `buyOrder_fk`
    FOREIGN KEY (`buyOrder`)
<<<<<<< HEAD
    REFERENCES `Orderbook`.`OrderTable` (`id`)
=======
    REFERENCES `OrderbookTest`.`OrderTable` (`id`)
>>>>>>> origin/kenneth
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `sellOrder_fk`
    FOREIGN KEY (`sellOrder`)
<<<<<<< HEAD
    REFERENCES `Orderbook`.`OrderTable` (`id`)
=======
    REFERENCES `OrderbookTest`.`OrderTable` (`id`)
>>>>>>> origin/kenneth
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `OrderbookTest`.`Share`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `OrderbookTest`.`Share` ;

CREATE TABLE IF NOT EXISTS `OrderbookTest`.`Share` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `symbol` VARCHAR(5) NOT NULL,
  `tickSize` DECIMAL(6,5) NOT NULL,
  `percentage` DECIMAL(5,2) NOT NULL,
  `high` DECIMAL(10,5) NOT NULL,
  `low` DECIMAL(10,5) NOT NULL,
  `price` DECIMAL(10,5) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `OrderbookTest`.`OpeningPrices`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `OrderbookTest`.`OpeningPrices` ;

CREATE TABLE IF NOT EXISTS `OrderbookTest`.`OpeningPrices` (
  `datetime` DATETIME NOT NULL,
  `price` DECIMAL(10,5) NOT NULL,
  `shareId` INT NOT NULL,
  PRIMARY KEY (`datetime`, `shareId`),
  INDEX `fk_OpeningPrices_Share1_idx` (`shareId` ASC) VISIBLE,
  CONSTRAINT `fk_OpeningPrices_Share1`
    FOREIGN KEY (`shareId`)
    REFERENCES `OrderbookTest`.`Share` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
