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
@Table(name="trade_settlements")
@Getter @Setter @NoArgsConstructor
public class TradeSettlement {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
  	@Column(name="trade_settlement_id")
	private Integer tradeSettlementId;
    
  	@Column(name="trade_id")
	private Integer tradeId;
    
  	@Column(name="settlement_date")
	private Date settlementDate;
    
  	@Column(name="settlement_amount")
	private Double settlementAmount;
    
  	@Column(name="status")
	private String status;
    

}
