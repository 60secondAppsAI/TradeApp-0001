package com.TradeApp.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;
import java.util.List;
import java.sql.Timestamp;
import java.time.Year;
import jakarta.persistence.Transient;

@Entity
@Table(name="trade_fees")
@Getter @Setter @NoArgsConstructor
public class TradeFee {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
  	@Column(name="trade_fee_id")
	private Integer tradeFeeId;
    
  	@Column(name="trade_id")
	private Integer tradeId;
    
  	@Column(name="fee_type")
	private String feeType;
    
  	@Column(name="amount")
	private Double amount;
    

}
