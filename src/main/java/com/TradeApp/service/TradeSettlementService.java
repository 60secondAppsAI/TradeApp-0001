package com.TradeApp.service;

import java.util.List;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.TradeApp.domain.TradeSettlement;
import com.TradeApp.dto.TradeSettlementDTO;
import com.TradeApp.dto.TradeSettlementSearchDTO;
import com.TradeApp.dto.TradeSettlementPageDTO;
import com.TradeApp.dto.TradeSettlementConvertCriteriaDTO;
import com.TradeApp.service.GenericService;
import com.TradeApp.dto.common.RequestDTO;
import com.TradeApp.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface TradeSettlementService extends GenericService<TradeSettlement, Integer> {

	List<TradeSettlement> findAll();

	ResultDTO addTradeSettlement(TradeSettlementDTO tradeSettlementDTO, RequestDTO requestDTO);

	ResultDTO updateTradeSettlement(TradeSettlementDTO tradeSettlementDTO, RequestDTO requestDTO);

    Page<TradeSettlement> getAllTradeSettlements(Pageable pageable);

    Page<TradeSettlement> getAllTradeSettlements(Specification<TradeSettlement> spec, Pageable pageable);

	ResponseEntity<TradeSettlementPageDTO> getTradeSettlements(TradeSettlementSearchDTO tradeSettlementSearchDTO);
	
	List<TradeSettlementDTO> convertTradeSettlementsToTradeSettlementDTOs(List<TradeSettlement> tradeSettlements, TradeSettlementConvertCriteriaDTO convertCriteria);

	TradeSettlementDTO getTradeSettlementDTOById(Integer tradeSettlementId);



	
}
