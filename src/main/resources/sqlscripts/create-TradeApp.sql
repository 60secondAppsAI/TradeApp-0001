CREATE DATABASE IF NOT EXISTS TradeApp;

CREATE TABLE TradeApp.trades (
    `trade_id` INT AUTO_INCREMENT,
    `execution_id` INT,
    `portfolio_id` INT,
    `instrument_id` INT,
    `side` VARCHAR(255),
    `quantity` DOUBLE,
    `price` DOUBLE,
    `trade_date` DATETIME,
PRIMARY KEY (trade_id)) ENGINE=MyISAM DEFAULT CHARSET=latin1;

CREATE TABLE TradeApp.trade_settlements (
    `trade_settlement_id` INT AUTO_INCREMENT,
    `trade_id` INT,
    `settlement_date` DATETIME,
    `settlement_amount` DOUBLE,
    `status` VARCHAR(255),
PRIMARY KEY (trade_settlement_id)) ENGINE=MyISAM DEFAULT CHARSET=latin1;

CREATE TABLE TradeApp.trade_fees (
    `trade_fee_id` INT AUTO_INCREMENT,
    `trade_id` INT,
    `fee_type` VARCHAR(255),
    `amount` DOUBLE,
PRIMARY KEY (trade_fee_id)) ENGINE=MyISAM DEFAULT CHARSET=latin1;

CREATE TABLE TradeApp.trade_audits (
    `trade_audit_id` INT AUTO_INCREMENT,
    `trade_id` INT,
    `event_type` VARCHAR(255),
    `description` VARCHAR(255),
    `occurred_at` DATETIME,
PRIMARY KEY (trade_audit_id)) ENGINE=MyISAM DEFAULT CHARSET=latin1;

USE drcvelocity;
