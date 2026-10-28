package com.TradeApp.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseTradeAuditDTO {

	private Integer ownerId;

	private Integer tradeAuditId;

	private Integer tradeAuditStatus;
	
	private Integer nextOrPrevious;
}

