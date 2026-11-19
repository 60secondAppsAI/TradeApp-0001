package com.TradeApp.dto;

import java.sql.Timestamp;
import java.time.Year;
import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TradeSettlementSearchDTO {

	private Integer page = 0;
	private Integer size;
	private String sortBy;
	private String sortOrder;
	private String searchQuery;

	private Integer tradeSettlementId;
	
	private Integer tradeId;
	
	private Date settlementDate;
	
	private Double settlementAmount;
	
	private String status;
	
}
