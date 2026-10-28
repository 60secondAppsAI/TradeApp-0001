package com.TradeApp.service;

import java.util.List;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.TradeApp.domain.TradeAudit;
import com.TradeApp.dto.TradeAuditDTO;
import com.TradeApp.dto.TradeAuditSearchDTO;
import com.TradeApp.dto.TradeAuditPageDTO;
import com.TradeApp.dto.TradeAuditConvertCriteriaDTO;
import com.TradeApp.service.GenericService;
import com.TradeApp.dto.common.RequestDTO;
import com.TradeApp.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface TradeAuditService extends GenericService<TradeAudit, Integer> {

	List<TradeAudit> findAll();

	ResultDTO addTradeAudit(TradeAuditDTO tradeAuditDTO, RequestDTO requestDTO);

	ResultDTO updateTradeAudit(TradeAuditDTO tradeAuditDTO, RequestDTO requestDTO);

    Page<TradeAudit> getAllTradeAudits(Pageable pageable);

    Page<TradeAudit> getAllTradeAudits(Specification<TradeAudit> spec, Pageable pageable);

	ResponseEntity<TradeAuditPageDTO> getTradeAudits(TradeAuditSearchDTO tradeAuditSearchDTO);
	
	List<TradeAuditDTO> convertTradeAuditsToTradeAuditDTOs(List<TradeAudit> tradeAudits, TradeAuditConvertCriteriaDTO convertCriteria);

	TradeAuditDTO getTradeAuditDTOById(Integer tradeAuditId);



	
}
