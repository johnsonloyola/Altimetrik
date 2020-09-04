package com.altimetrik.stock.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.altimetrik.stock.entity.Stock;

public interface StockRepository extends PagingAndSortingRepository<Stock, Integer> {
	@Override
    List<Stock> findAll();
	List<Stock> findByOrderByStockName();
	List<Stock> findByOrderByPurchaseDate();
	List<Stock> findByOrderByStockPrice();
	
}