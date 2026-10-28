package com.TradeApp.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import com.TradeApp.dao.GenericDAO;
import com.TradeApp.service.GenericService;
import com.TradeApp.service.impl.GenericServiceImpl;
import com.TradeApp.dao.TradeFeeDAO;
import com.TradeApp.domain.TradeFee;
import com.TradeApp.dto.TradeFeeDTO;
import com.TradeApp.dto.TradeFeeSearchDTO;
import com.TradeApp.dto.TradeFeePageDTO;
import com.TradeApp.dto.TradeFeeConvertCriteriaDTO;
import com.TradeApp.dto.common.RequestDTO;
import com.TradeApp.dto.common.ResultDTO;
import com.TradeApp.service.TradeFeeService;
import com.TradeApp.util.ControllerUtils;


@Service
public class TradeFeeServiceImpl extends GenericServiceImpl<TradeFee, Integer> implements TradeFeeService {

    private final static Logger logger = LoggerFactory.getLogger(TradeFeeServiceImpl.class);

	@Autowired
	TradeFeeDAO tradeFeeDao;

	

	@Override
	public GenericDAO<TradeFee, Integer> getDAO() {
		return (GenericDAO<TradeFee, Integer>) tradeFeeDao;
	}
	
	public List<TradeFee> findAll () {
		List<TradeFee> tradeFees = tradeFeeDao.findAll();
		
		return tradeFees;	
		
	}

	public ResultDTO addTradeFee(TradeFeeDTO tradeFeeDTO, RequestDTO requestDTO) {

		TradeFee tradeFee = new TradeFee();

		tradeFee.setTradeFeeId(tradeFeeDTO.getTradeFeeId());

		tradeFee.setTradeId(tradeFeeDTO.getTradeId());

		tradeFee.setFeeType(tradeFeeDTO.getFeeType());

		tradeFee.setAmount(tradeFeeDTO.getAmount());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		tradeFee = tradeFeeDao.save(tradeFee);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<TradeFee> getAllTradeFees(Pageable pageable) {
		return tradeFeeDao.findAll(pageable);
	}

	public Page<TradeFee> getAllTradeFees(Specification<TradeFee> spec, Pageable pageable) {
		return tradeFeeDao.findAll(spec, pageable);
	}

	public ResponseEntity<TradeFeePageDTO> getTradeFees(TradeFeeSearchDTO tradeFeeSearchDTO) {
	
			Integer tradeFeeId = tradeFeeSearchDTO.getTradeFeeId(); 
 			Integer tradeId = tradeFeeSearchDTO.getTradeId(); 
 			String feeType = tradeFeeSearchDTO.getFeeType(); 
  			String sortBy = tradeFeeSearchDTO.getSortBy();
			String sortOrder = tradeFeeSearchDTO.getSortOrder();
			String searchQuery = tradeFeeSearchDTO.getSearchQuery();
			Integer page = tradeFeeSearchDTO.getPage();
			Integer size = tradeFeeSearchDTO.getSize();

	        Specification<TradeFee> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, tradeFeeId, "tradeFeeId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, tradeId, "tradeId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, feeType, "feeType"); 
			
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("feeType")), "%" + searchQuery.toLowerCase() + "%") 
		));}
		
		Sort sort = Sort.unsorted();
		if (sortBy != null && !sortBy.isEmpty() && sortOrder != null && !sortOrder.isEmpty()) {
			if (sortOrder.equalsIgnoreCase("asc")) {
				sort = Sort.by(sortBy).ascending();
			} else if (sortOrder.equalsIgnoreCase("desc")) {
				sort = Sort.by(sortBy).descending();
			}
		}
		Pageable pageable = PageRequest.of(page, size, sort);

		Page<TradeFee> tradeFees = this.getAllTradeFees(spec, pageable);
		
		//System.out.println(String.valueOf(tradeFees.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(tradeFees.getTotalPages()));
		
		List<TradeFee> tradeFeesList = tradeFees.getContent();
		
		TradeFeeConvertCriteriaDTO convertCriteria = new TradeFeeConvertCriteriaDTO();
		List<TradeFeeDTO> tradeFeeDTOs = this.convertTradeFeesToTradeFeeDTOs(tradeFeesList,convertCriteria);
		
		TradeFeePageDTO tradeFeePageDTO = new TradeFeePageDTO();
		tradeFeePageDTO.setTradeFees(tradeFeeDTOs);
		tradeFeePageDTO.setTotalElements(tradeFees.getTotalElements());
		return ResponseEntity.ok(tradeFeePageDTO);
	}

	public List<TradeFeeDTO> convertTradeFeesToTradeFeeDTOs(List<TradeFee> tradeFees, TradeFeeConvertCriteriaDTO convertCriteria) {
		
		List<TradeFeeDTO> tradeFeeDTOs = new ArrayList<TradeFeeDTO>();
		
		for (TradeFee tradeFee : tradeFees) {
			tradeFeeDTOs.add(convertTradeFeeToTradeFeeDTO(tradeFee,convertCriteria));
		}
		
		return tradeFeeDTOs;

	}
	
	public TradeFeeDTO convertTradeFeeToTradeFeeDTO(TradeFee tradeFee, TradeFeeConvertCriteriaDTO convertCriteria) {
		
		TradeFeeDTO tradeFeeDTO = new TradeFeeDTO();

		tradeFeeDTO.setTradeFeeId(tradeFee.getTradeFeeId());

		tradeFeeDTO.setTradeId(tradeFee.getTradeId());

		tradeFeeDTO.setFeeType(tradeFee.getFeeType());

		tradeFeeDTO.setAmount(tradeFee.getAmount());
		
		return tradeFeeDTO;
	}

	public ResultDTO updateTradeFee(TradeFeeDTO tradeFeeDTO, RequestDTO requestDTO) {
		
		TradeFee tradeFee = tradeFeeDao.getById(tradeFeeDTO.getTradeFeeId());
		
		tradeFee.setTradeFeeId(ControllerUtils.setValue(tradeFee.getTradeFeeId(), tradeFeeDTO.getTradeFeeId()));
		
		tradeFee.setTradeId(ControllerUtils.setValue(tradeFee.getTradeId(), tradeFeeDTO.getTradeId()));
		
		tradeFee.setFeeType(ControllerUtils.setValue(tradeFee.getFeeType(), tradeFeeDTO.getFeeType()));
		
		tradeFee.setAmount(ControllerUtils.setValue(tradeFee.getAmount(), tradeFeeDTO.getAmount()));

        tradeFee = tradeFeeDao.save(tradeFee);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public TradeFeeDTO getTradeFeeDTOById(Integer tradeFeeId) {
	
		TradeFee tradeFee = tradeFeeDao.getById(tradeFeeId);
		
		TradeFeeConvertCriteriaDTO convertCriteria = new TradeFeeConvertCriteriaDTO();
		return(this.convertTradeFeeToTradeFeeDTO(tradeFee,convertCriteria));
	}

}
