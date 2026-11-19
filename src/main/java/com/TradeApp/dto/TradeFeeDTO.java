package com.TradeApp.dto;

import java.util.List;
import java.util.Date;
import java.sql.Timestamp;
import java.time.Year;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class TradeFeeDTO {

	private Integer tradeFeeId;

	private Integer tradeId;

	private String feeType;

	private Double amount;






}
