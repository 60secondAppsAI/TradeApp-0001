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
import com.TradeApp.dao.TradeSettlementDAO;
import com.TradeApp.domain.TradeSettlement;
import com.TradeApp.dto.TradeSettlementDTO;
import com.TradeApp.dto.TradeSettlementSearchDTO;
import com.TradeApp.dto.TradeSettlementPageDTO;
import com.TradeApp.dto.TradeSettlementConvertCriteriaDTO;
import com.TradeApp.dto.common.RequestDTO;
import com.TradeApp.dto.common.ResultDTO;
import com.TradeApp.service.TradeSettlementService;
import com.TradeApp.util.ControllerUtils;


@Service
public class TradeSettlementServiceImpl extends GenericServiceImpl<TradeSettlement, Integer> implements TradeSettlementService {

    private final static Logger logger = LoggerFactory.getLogger(TradeSettlementServiceImpl.class);

	@Autowired
	TradeSettlementDAO tradeSettlementDao;

	

	@Override
	public GenericDAO<TradeSettlement, Integer> getDAO() {
		return (GenericDAO<TradeSettlement, Integer>) tradeSettlementDao;
	}
	
	public List<TradeSettlement> findAll () {
		List<TradeSettlement> tradeSettlements = tradeSettlementDao.findAll();
		
		return tradeSettlements;	
		
	}

	public ResultDTO addTradeSettlement(TradeSettlementDTO tradeSettlementDTO, RequestDTO requestDTO) {

		TradeSettlement tradeSettlement = new TradeSettlement();

		tradeSettlement.setTradeSettlementId(tradeSettlementDTO.getTradeSettlementId());

		tradeSettlement.setTradeId(tradeSettlementDTO.getTradeId());

		tradeSettlement.setSettlementDate(tradeSettlementDTO.getSettlementDate());

		tradeSettlement.setSettlementAmount(tradeSettlementDTO.getSettlementAmount());

		tradeSettlement.setStatus(tradeSettlementDTO.getStatus());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		tradeSettlement = tradeSettlementDao.save(tradeSettlement);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<TradeSettlement> getAllTradeSettlements(Pageable pageable) {
		return tradeSettlementDao.findAll(pageable);
	}

	public Page<TradeSettlement> getAllTradeSettlements(Specification<TradeSettlement> spec, Pageable pageable) {
		return tradeSettlementDao.findAll(spec, pageable);
	}

	public ResponseEntity<TradeSettlementPageDTO> getTradeSettlements(TradeSettlementSearchDTO tradeSettlementSearchDTO) {
	
			Integer tradeSettlementId = tradeSettlementSearchDTO.getTradeSettlementId(); 
 			Integer tradeId = tradeSettlementSearchDTO.getTradeId(); 
 			Date settlementDate = tradeSettlementSearchDTO.getSettlementDate();
  			String status = tradeSettlementSearchDTO.getStatus(); 
 			String sortBy = tradeSettlementSearchDTO.getSortBy();
			String sortOrder = tradeSettlementSearchDTO.getSortOrder();
			String searchQuery = tradeSettlementSearchDTO.getSearchQuery();
			Integer page = tradeSettlementSearchDTO.getPage();
			Integer size = tradeSettlementSearchDTO.getSize();

	        Specification<TradeSettlement> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, tradeSettlementId, "tradeSettlementId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, tradeId, "tradeId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, settlementDate, "settlementDate");
			
			
			spec = ControllerUtils.andIfNecessary(spec, status, "status"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("status")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<TradeSettlement> tradeSettlements = this.getAllTradeSettlements(spec, pageable);
		
		//System.out.println(String.valueOf(tradeSettlements.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(tradeSettlements.getTotalPages()));
		
		List<TradeSettlement> tradeSettlementsList = tradeSettlements.getContent();
		
		TradeSettlementConvertCriteriaDTO convertCriteria = new TradeSettlementConvertCriteriaDTO();
		List<TradeSettlementDTO> tradeSettlementDTOs = this.convertTradeSettlementsToTradeSettlementDTOs(tradeSettlementsList,convertCriteria);
		
		TradeSettlementPageDTO tradeSettlementPageDTO = new TradeSettlementPageDTO();
		tradeSettlementPageDTO.setTradeSettlements(tradeSettlementDTOs);
		tradeSettlementPageDTO.setTotalElements(tradeSettlements.getTotalElements());
		return ResponseEntity.ok(tradeSettlementPageDTO);
	}

	public List<TradeSettlementDTO> convertTradeSettlementsToTradeSettlementDTOs(List<TradeSettlement> tradeSettlements, TradeSettlementConvertCriteriaDTO convertCriteria) {
		
		List<TradeSettlementDTO> tradeSettlementDTOs = new ArrayList<TradeSettlementDTO>();
		
		for (TradeSettlement tradeSettlement : tradeSettlements) {
			tradeSettlementDTOs.add(convertTradeSettlementToTradeSettlementDTO(tradeSettlement,convertCriteria));
		}
		
		return tradeSettlementDTOs;

	}
	
	public TradeSettlementDTO convertTradeSettlementToTradeSettlementDTO(TradeSettlement tradeSettlement, TradeSettlementConvertCriteriaDTO convertCriteria) {
		
		TradeSettlementDTO tradeSettlementDTO = new TradeSettlementDTO();

		tradeSettlementDTO.setTradeSettlementId(tradeSettlement.getTradeSettlementId());

		tradeSettlementDTO.setTradeId(tradeSettlement.getTradeId());

		tradeSettlementDTO.setSettlementDate(tradeSettlement.getSettlementDate());

		tradeSettlementDTO.setSettlementAmount(tradeSettlement.getSettlementAmount());

		tradeSettlementDTO.setStatus(tradeSettlement.getStatus());
		
		return tradeSettlementDTO;
	}

	public ResultDTO updateTradeSettlement(TradeSettlementDTO tradeSettlementDTO, RequestDTO requestDTO) {
		
		TradeSettlement tradeSettlement = tradeSettlementDao.getById(tradeSettlementDTO.getTradeSettlementId());
		
		tradeSettlement.setTradeSettlementId(ControllerUtils.setValue(tradeSettlement.getTradeSettlementId(), tradeSettlementDTO.getTradeSettlementId()));
		
		tradeSettlement.setTradeId(ControllerUtils.setValue(tradeSettlement.getTradeId(), tradeSettlementDTO.getTradeId()));
		
		tradeSettlement.setSettlementDate(ControllerUtils.setValue(tradeSettlement.getSettlementDate(), tradeSettlementDTO.getSettlementDate()));
		
		tradeSettlement.setSettlementAmount(ControllerUtils.setValue(tradeSettlement.getSettlementAmount(), tradeSettlementDTO.getSettlementAmount()));
		
		tradeSettlement.setStatus(ControllerUtils.setValue(tradeSettlement.getStatus(), tradeSettlementDTO.getStatus()));

        tradeSettlement = tradeSettlementDao.save(tradeSettlement);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public TradeSettlementDTO getTradeSettlementDTOById(Integer tradeSettlementId) {
	
		TradeSettlement tradeSettlement = tradeSettlementDao.getById(tradeSettlementId);
		
		TradeSettlementConvertCriteriaDTO convertCriteria = new TradeSettlementConvertCriteriaDTO();
		return(this.convertTradeSettlementToTradeSettlementDTO(tradeSettlement,convertCriteria));
	}

}
