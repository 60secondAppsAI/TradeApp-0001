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

import com.TradeApp.domain.TradeFee;
import com.TradeApp.dto.TradeFeeDTO;
import com.TradeApp.dto.TradeFeeSearchDTO;
import com.TradeApp.dto.TradeFeePageDTO;
import com.TradeApp.service.TradeFeeService;
import com.TradeApp.dto.common.RequestDTO;
import com.TradeApp.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/tradeFee")
@RestController
public class TradeFeeController {

	private final static Logger logger = LoggerFactory.getLogger(TradeFeeController.class);

	@Autowired
	TradeFeeService tradeFeeService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<TradeFee> getAll() {

		List<TradeFee> tradeFees = tradeFeeService.findAll();
		
		return tradeFees;	
	}

	@GetMapping(value = "/{tradeFeeId}")
	@ResponseBody
	public TradeFeeDTO getTradeFee(@PathVariable Integer tradeFeeId) {
		
		return (tradeFeeService.getTradeFeeDTOById(tradeFeeId));
	}

 	@RequestMapping(value = "/addTradeFee", method = RequestMethod.POST)
	public ResponseEntity<?> addTradeFee(@RequestBody TradeFeeDTO tradeFeeDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = tradeFeeService.addTradeFee(tradeFeeDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/tradeFees")
	public ResponseEntity<TradeFeePageDTO> getTradeFees(TradeFeeSearchDTO tradeFeeSearchDTO) {
 
		return tradeFeeService.getTradeFees(tradeFeeSearchDTO);
	}	

	@RequestMapping(value = "/updateTradeFee", method = RequestMethod.POST)
	public ResponseEntity<?> updateTradeFee(@RequestBody TradeFeeDTO tradeFeeDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = tradeFeeService.updateTradeFee(tradeFeeDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}





}
