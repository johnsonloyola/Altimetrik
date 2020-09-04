package com.altimetrik.stock.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.altimetrik.stock.entity.Stock;
import com.altimetrik.stock.service.StockService;

@RestController
@RequestMapping("/stocks")
public class StockController {
	@Autowired
	private StockService stockService;

	@RequestMapping("")
	public Iterable<Stock> getAllStock(@RequestParam String sortBy) {
		return stockService.findAll(sortBy);
	}

	@RequestMapping("/{id}")
	public Optional<Stock> searchStock(@PathVariable int id) {
		return stockService.findById(id);
	}

	@RequestMapping(method = RequestMethod.POST, value = "")
	public Stock addStock(@RequestBody Stock stock) {
		return stockService.insert(stock);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public Stock updateStock(@RequestBody Stock stock, @PathVariable int id) {
		return stockService.updateStock(stock, id);
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/sell/{id}")
	public Stock sellStock(@PathVariable int id, @RequestParam int quantity) {
		return stockService.sellStock(id, quantity);
	}

}
