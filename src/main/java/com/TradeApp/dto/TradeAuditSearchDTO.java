package com.TradeApp.dto;

import java.sql.Timestamp;
import java.time.Year;
import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TradeAuditSearchDTO {

	private Integer page = 0;
	private Integer size;
	private String sortBy;
	private String sortOrder;
	private String searchQuery;

	private Integer tradeAuditId;
	
	private Integer tradeId;
	
	private String eventType;
	
	private String description;
	
	private Date occurredAt;
	
}
