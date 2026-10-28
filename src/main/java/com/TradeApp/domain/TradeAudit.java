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
@Table(name="trade_audits")
@Getter @Setter @NoArgsConstructor
public class TradeAudit {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
  	@Column(name="trade_audit_id")
	private Integer tradeAuditId;
    
  	@Column(name="trade_id")
	private Integer tradeId;
    
  	@Column(name="event_type")
	private String eventType;
    
  	@Column(name="description")
	private String description;
    
  	@Column(name="occurred_at")
	private Date occurredAt;
    

}
