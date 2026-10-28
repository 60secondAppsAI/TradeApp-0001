package com.TradeApp.dto;

import java.util.List;
import java.util.Date;
import java.sql.Timestamp;
import java.time.Year;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class TradeDTO {

	private Integer tradeId;

	private Integer executionId;

	private Integer portfolioId;

	private Integer instrumentId;

	private String side;

	private Double quantity;

	private Double price;

	private Date tradeDate;






}
