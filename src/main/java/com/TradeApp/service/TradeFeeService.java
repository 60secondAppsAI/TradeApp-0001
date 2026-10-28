package com.TradeApp.service;

import java.util.List;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.TradeApp.domain.TradeFee;
import com.TradeApp.dto.TradeFeeDTO;
import com.TradeApp.dto.TradeFeeSearchDTO;
import com.TradeApp.dto.TradeFeePageDTO;
import com.TradeApp.dto.TradeFeeConvertCriteriaDTO;
import com.TradeApp.service.GenericService;
import com.TradeApp.dto.common.RequestDTO;
import com.TradeApp.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface TradeFeeService extends GenericService<TradeFee, Integer> {

	List<TradeFee> findAll();

	ResultDTO addTradeFee(TradeFeeDTO tradeFeeDTO, RequestDTO requestDTO);

	ResultDTO updateTradeFee(TradeFeeDTO tradeFeeDTO, RequestDTO requestDTO);

    Page<TradeFee> getAllTradeFees(Pageable pageable);

    Page<TradeFee> getAllTradeFees(Specification<TradeFee> spec, Pageable pageable);

	ResponseEntity<TradeFeePageDTO> getTradeFees(TradeFeeSearchDTO tradeFeeSearchDTO);
	
	List<TradeFeeDTO> convertTradeFeesToTradeFeeDTOs(List<TradeFee> tradeFees, TradeFeeConvertCriteriaDTO convertCriteria);

	TradeFeeDTO getTradeFeeDTOById(Integer tradeFeeId);



	
}
