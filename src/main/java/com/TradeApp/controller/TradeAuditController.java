package com.TradeApp.controller;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.ZoneId;


import com.TradeApp.util.Util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.Timestamp;
import java.util.Date;

import com.TradeApp.domain.TradeAudit;
import com.TradeApp.dto.TradeAuditDTO;
import com.TradeApp.dto.TradeAuditSearchDTO;
import com.TradeApp.dto.TradeAuditPageDTO;
import com.TradeApp.service.TradeAuditService;
import com.TradeApp.dto.common.RequestDTO;
import com.TradeApp.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/tradeAudit")
@RestController
public class TradeAuditController {

	private final static Logger logger = LoggerFactory.getLogger(TradeAuditController.class);

	@Autowired
	TradeAuditService tradeAuditService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<TradeAudit> getAll() {

		List<TradeAudit> tradeAudits = tradeAuditService.findAll();
		
		return tradeAudits;	
	}

	@GetMapping(value = "/{tradeAuditId}")
	@ResponseBody
	public TradeAuditDTO getTradeAudit(@PathVariable Integer tradeAuditId) {
		
		return (tradeAuditService.getTradeAuditDTOById(tradeAuditId));
	}

 	@RequestMapping(value = "/addTradeAudit", method = RequestMethod.POST)
	public ResponseEntity<?> addTradeAudit(@RequestBody TradeAuditDTO tradeAuditDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = tradeAuditService.addTradeAudit(tradeAuditDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/tradeAudits")
	public ResponseEntity<TradeAuditPageDTO> getTradeAudits(TradeAuditSearchDTO tradeAuditSearchDTO) {
 
		return tradeAuditService.getTradeAudits(tradeAuditSearchDTO);
	}	

	@RequestMapping(value = "/updateTradeAudit", method = RequestMethod.POST)
	public ResponseEntity<?> updateTradeAudit(@RequestBody TradeAuditDTO tradeAuditDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = tradeAuditService.updateTradeAudit(tradeAuditDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}





}
