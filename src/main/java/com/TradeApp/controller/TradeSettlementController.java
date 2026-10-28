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

import com.TradeApp.domain.TradeSettlement;
import com.TradeApp.dto.TradeSettlementDTO;
import com.TradeApp.dto.TradeSettlementSearchDTO;
import com.TradeApp.dto.TradeSettlementPageDTO;
import com.TradeApp.service.TradeSettlementService;
import com.TradeApp.dto.common.RequestDTO;
import com.TradeApp.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/tradeSettlement")
@RestController
public class TradeSettlementController {

	private final static Logger logger = LoggerFactory.getLogger(TradeSettlementController.class);

	@Autowired
	TradeSettlementService tradeSettlementService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<TradeSettlement> getAll() {

		List<TradeSettlement> tradeSettlements = tradeSettlementService.findAll();
		
		return tradeSettlements;	
	}

	@GetMapping(value = "/{tradeSettlementId}")
	@ResponseBody
	public TradeSettlementDTO getTradeSettlement(@PathVariable Integer tradeSettlementId) {
		
		return (tradeSettlementService.getTradeSettlementDTOById(tradeSettlementId));
	}

 	@RequestMapping(value = "/addTradeSettlement", method = RequestMethod.POST)
	public ResponseEntity<?> addTradeSettlement(@RequestBody TradeSettlementDTO tradeSettlementDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = tradeSettlementService.addTradeSettlement(tradeSettlementDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/tradeSettlements")
	public ResponseEntity<TradeSettlementPageDTO> getTradeSettlements(TradeSettlementSearchDTO tradeSettlementSearchDTO) {
 
		return tradeSettlementService.getTradeSettlements(tradeSettlementSearchDTO);
	}	

	@RequestMapping(value = "/updateTradeSettlement", method = RequestMethod.POST)
	public ResponseEntity<?> updateTradeSettlement(@RequestBody TradeSettlementDTO tradeSettlementDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = tradeSettlementService.updateTradeSettlement(tradeSettlementDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}





}
