package com.TradeApp.dao;

import java.util.List;
import java.util.Date;

import com.TradeApp.dao.GenericDAO;
import com.TradeApp.domain.TradeFee;




import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface TradeFeeDAO extends GenericDAO<TradeFee, Integer> {
  
	List<TradeFee> findAll();
	


}

