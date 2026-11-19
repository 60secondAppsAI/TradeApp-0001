package com.TradeApp.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseTradeFeeDTO {

	private Integer ownerId;

	private Integer tradeFeeId;

	private Integer tradeFeeStatus;
	
	private Integer nextOrPrevious;
}

