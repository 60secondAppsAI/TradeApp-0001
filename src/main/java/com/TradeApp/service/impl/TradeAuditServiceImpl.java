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
import com.TradeApp.dao.TradeAuditDAO;
import com.TradeApp.domain.TradeAudit;
import com.TradeApp.dto.TradeAuditDTO;
import com.TradeApp.dto.TradeAuditSearchDTO;
import com.TradeApp.dto.TradeAuditPageDTO;
import com.TradeApp.dto.TradeAuditConvertCriteriaDTO;
import com.TradeApp.dto.common.RequestDTO;
import com.TradeApp.dto.common.ResultDTO;
import com.TradeApp.service.TradeAuditService;
import com.TradeApp.util.ControllerUtils;


@Service
public class TradeAuditServiceImpl extends GenericServiceImpl<TradeAudit, Integer> implements TradeAuditService {

    private final static Logger logger = LoggerFactory.getLogger(TradeAuditServiceImpl.class);

	@Autowired
	TradeAuditDAO tradeAuditDao;

	

	@Override
	public GenericDAO<TradeAudit, Integer> getDAO() {
		return (GenericDAO<TradeAudit, Integer>) tradeAuditDao;
	}
	
	public List<TradeAudit> findAll () {
		List<TradeAudit> tradeAudits = tradeAuditDao.findAll();
		
		return tradeAudits;	
		
	}

	public ResultDTO addTradeAudit(TradeAuditDTO tradeAuditDTO, RequestDTO requestDTO) {

		TradeAudit tradeAudit = new TradeAudit();

		tradeAudit.setTradeAuditId(tradeAuditDTO.getTradeAuditId());

		tradeAudit.setTradeId(tradeAuditDTO.getTradeId());

		tradeAudit.setEventType(tradeAuditDTO.getEventType());

		tradeAudit.setDescription(tradeAuditDTO.getDescription());

		tradeAudit.setOccurredAt(tradeAuditDTO.getOccurredAt());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		tradeAudit = tradeAuditDao.save(tradeAudit);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<TradeAudit> getAllTradeAudits(Pageable pageable) {
		return tradeAuditDao.findAll(pageable);
	}

	public Page<TradeAudit> getAllTradeAudits(Specification<TradeAudit> spec, Pageable pageable) {
		return tradeAuditDao.findAll(spec, pageable);
	}

	public ResponseEntity<TradeAuditPageDTO> getTradeAudits(TradeAuditSearchDTO tradeAuditSearchDTO) {
	
			Integer tradeAuditId = tradeAuditSearchDTO.getTradeAuditId(); 
 			Integer tradeId = tradeAuditSearchDTO.getTradeId(); 
 			String eventType = tradeAuditSearchDTO.getEventType(); 
 			String description = tradeAuditSearchDTO.getDescription(); 
 			Date occurredAt = tradeAuditSearchDTO.getOccurredAt();
 			String sortBy = tradeAuditSearchDTO.getSortBy();
			String sortOrder = tradeAuditSearchDTO.getSortOrder();
			String searchQuery = tradeAuditSearchDTO.getSearchQuery();
			Integer page = tradeAuditSearchDTO.getPage();
			Integer size = tradeAuditSearchDTO.getSize();

	        Specification<TradeAudit> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, tradeAuditId, "tradeAuditId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, tradeId, "tradeId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, eventType, "eventType"); 
			
			spec = ControllerUtils.andIfNecessary(spec, description, "description"); 
			
			spec = ControllerUtils.andIfNecessary(spec, occurredAt, "occurredAt");
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("eventType")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("description")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<TradeAudit> tradeAudits = this.getAllTradeAudits(spec, pageable);
		
		//System.out.println(String.valueOf(tradeAudits.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(tradeAudits.getTotalPages()));
		
		List<TradeAudit> tradeAuditsList = tradeAudits.getContent();
		
		TradeAuditConvertCriteriaDTO convertCriteria = new TradeAuditConvertCriteriaDTO();
		List<TradeAuditDTO> tradeAuditDTOs = this.convertTradeAuditsToTradeAuditDTOs(tradeAuditsList,convertCriteria);
		
		TradeAuditPageDTO tradeAuditPageDTO = new TradeAuditPageDTO();
		tradeAuditPageDTO.setTradeAudits(tradeAuditDTOs);
		tradeAuditPageDTO.setTotalElements(tradeAudits.getTotalElements());
		return ResponseEntity.ok(tradeAuditPageDTO);
	}

	public List<TradeAuditDTO> convertTradeAuditsToTradeAuditDTOs(List<TradeAudit> tradeAudits, TradeAuditConvertCriteriaDTO convertCriteria) {
		
		List<TradeAuditDTO> tradeAuditDTOs = new ArrayList<TradeAuditDTO>();
		
		for (TradeAudit tradeAudit : tradeAudits) {
			tradeAuditDTOs.add(convertTradeAuditToTradeAuditDTO(tradeAudit,convertCriteria));
		}
		
		return tradeAuditDTOs;

	}
	
	public TradeAuditDTO convertTradeAuditToTradeAuditDTO(TradeAudit tradeAudit, TradeAuditConvertCriteriaDTO convertCriteria) {
		
		TradeAuditDTO tradeAuditDTO = new TradeAuditDTO();

		tradeAuditDTO.setTradeAuditId(tradeAudit.getTradeAuditId());

		tradeAuditDTO.setTradeId(tradeAudit.getTradeId());

		tradeAuditDTO.setEventType(tradeAudit.getEventType());

		tradeAuditDTO.setDescription(tradeAudit.getDescription());

		tradeAuditDTO.setOccurredAt(tradeAudit.getOccurredAt());
		
		return tradeAuditDTO;
	}

	public ResultDTO updateTradeAudit(TradeAuditDTO tradeAuditDTO, RequestDTO requestDTO) {
		
		TradeAudit tradeAudit = tradeAuditDao.getById(tradeAuditDTO.getTradeAuditId());
		
		tradeAudit.setTradeAuditId(ControllerUtils.setValue(tradeAudit.getTradeAuditId(), tradeAuditDTO.getTradeAuditId()));
		
		tradeAudit.setTradeId(ControllerUtils.setValue(tradeAudit.getTradeId(), tradeAuditDTO.getTradeId()));
		
		tradeAudit.setEventType(ControllerUtils.setValue(tradeAudit.getEventType(), tradeAuditDTO.getEventType()));
		
		tradeAudit.setDescription(ControllerUtils.setValue(tradeAudit.getDescription(), tradeAuditDTO.getDescription()));
		
		tradeAudit.setOccurredAt(ControllerUtils.setValue(tradeAudit.getOccurredAt(), tradeAuditDTO.getOccurredAt()));

        tradeAudit = tradeAuditDao.save(tradeAudit);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public TradeAuditDTO getTradeAuditDTOById(Integer tradeAuditId) {
	
		TradeAudit tradeAudit = tradeAuditDao.getById(tradeAuditId);
		
		TradeAuditConvertCriteriaDTO convertCriteria = new TradeAuditConvertCriteriaDTO();
		return(this.convertTradeAuditToTradeAuditDTO(tradeAudit,convertCriteria));
	}

}
