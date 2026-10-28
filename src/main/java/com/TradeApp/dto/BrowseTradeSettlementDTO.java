package com.TradeApp.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseTradeSettlementDTO {

	private Integer ownerId;

	private Integer tradeSettlementId;

	private Integer tradeSettlementStatus;
	
	private Integer nextOrPrevious;
}

