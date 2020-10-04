-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema Orderbook
-- -----------------------------------------------------
DROP DATABASE IF EXISTS `Orderbook` ;

-- -----------------------------------------------------
-- Schema Orderbook
-- -----------------------------------------------------
CREATE DATABASE IF NOT EXISTS `Orderbook` DEFAULT CHARACTER SET utf8 ;
USE `Orderbook` ;

-- -----------------------------------------------------
-- Table `Orderbook`.`User`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Orderbook`.`User` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(20) NOT NULL,
  `password` VARCHAR(25) NOT NULL,
  `coin` DECIMAL(6,2),
  PRIMARY KEY (`id`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Orderbook`.`Order`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Orderbook`.`Order` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `type` TINYINT(1) NOT NULL,
  `price` DECIMAL(10,5) NOT NULL,
  `amount` DECIMAL(8,2) NOT NULL,
  `date` DATETIME NOT NULL,
  `completed` TINYINT(1) NOT NULL,
  `userId` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Order_User_idx` (`userId` ASC) VISIBLE,
  CONSTRAINT `fk_Order_User`
    FOREIGN KEY (`userId`)
    REFERENCES `Orderbook`.`User` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Orderbook`.`Trade`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Orderbook`.`Trade` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `date` DATETIME NOT NULL,
  `price` DECIMAL(10,5) NOT NULL,
  `amount` DECIMAL(8,2) NOT NULL,
  `buyOrder` INT NOT NULL,
  `sellOrder` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `sellOrder_fk_idx` (`buyOrder` ASC) VISIBLE,
  CONSTRAINT `buyOrder_fk`
    FOREIGN KEY (`buyOrder`)
    REFERENCES `Orderbook`.`Order` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `sellOrder_fk`
    FOREIGN KEY (`sellOrder`)
    REFERENCES `Orderbook`.`Order` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Orderbook`.`Share`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Orderbook`.`Share` (
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
-- Table `Orderbook`.`OpeningPrices`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Orderbook`.`OpeningPrices` (
  `date` DATETIME NOT NULL,
  `price` DECIMAL(10,5) NOT NULL,
  `shareId` INT NOT NULL,
  PRIMARY KEY (`date`, `shareId`),
  INDEX `fk_OpeningPrices_Share1_idx` (`shareId` ASC) VISIBLE,
  CONSTRAINT `fk_OpeningPrices_Share1`
    FOREIGN KEY (`shareId`)
    REFERENCES `Orderbook`.`Share` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;