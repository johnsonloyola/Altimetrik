package com.altimetrik.stock.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altimetrik.stock.entity.Stock;
import com.altimetrik.stock.repository.StockRepository;

@Transactional
@Service
public class StockService {

	@Autowired
	private StockRepository stockRepository;

	public Stock insert(Stock stock) {
		return stockRepository.save(stock);
	}

	public Optional<Stock> findById(int id) {
		return stockRepository.findById(id);
	}

	public List<Stock> findAll(String sortBy) {
		List<Stock> list = new ArrayList<Stock>();

		if (sortBy.equals("name")) {
			list = stockRepository.findByOrderByStockName();
		} else if (sortBy.equals("price")) {
			list = stockRepository.findByOrderByStockPrice();
		} else if (sortBy.equals("date")) {
			list = stockRepository.findByOrderByPurchaseDate();
		} else {
			list = stockRepository.findAll();
		}
		return list;
	}

	public Stock updateStock(Stock stock, int id) {
		Optional<Stock> fromDb = stockRepository.findById(id);
		if (fromDb.isPresent()) {
			fromDb.get().setStockNumber(stock.getStockNumber());
			fromDb.get().setPurchaseDate(stock.getPurchaseDate());
			fromDb.get().setQuantity(stock.getQuantity());
			fromDb.get().setStockPrice(stock.getStockPrice());
			return stockRepository.save(fromDb.get());
		}
		return null;
	}

	public Stock sellStock(int id, int quantity) {
		Optional<Stock> fromDb = stockRepository.findById(id);
		if (fromDb.isPresent()) {
			int available = fromDb.get().getQuantity();
			int updated = available - quantity;
			fromDb.get().setQuantity(updated);
			return stockRepository.save(fromDb.get());
		}
		return null;
	}

}