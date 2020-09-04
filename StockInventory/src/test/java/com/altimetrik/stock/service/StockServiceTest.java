package com.altimetrik.stock.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.altimetrik.stock.entity.Stock;
import com.altimetrik.stock.repository.StockRepository;

@RunWith(SpringJUnit4ClassRunner.class)
public class StockServiceTest {
	
	@Mock
    private StockRepository repository;

    @InjectMocks
    private StockService service;

    List<Stock> sList = new ArrayList<>();
    
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        sList = new ArrayList<>();
		Stock s1 = new Stock(1, "S1", "Test1", new BigDecimal(300), new Date(), 100);
		Stock s2 = new Stock(2, "S2", "Test2", new BigDecimal(200), new Date(), 100);
		Stock s3 = new Stock(3, "S3", "Test3", new BigDecimal(100), new Date(), 100);
		sList.add(s1);
		sList.add(s2);
		sList.add(s3);
    }

	@Test
	public void testInsert() {
		Stock stock = new Stock(1, "S1", "Test1", new BigDecimal(300), new Date(), 100);
        when(repository.save(stock)).thenReturn(stock);
        Stock result = service.insert(stock);
        assertEquals(1, result.getId());
        assertEquals("Test1", result.getStockName());
        assertEquals(100, result.getQuantity());
	}

	@Test
	public void testFindById() {
		Stock stock = new Stock(1, "S1", "Test1", new BigDecimal(300), new Date(), 100);
        when(repository.findById(1)).thenReturn(Optional.of(stock));
        Optional<Stock> resultOpt = service.findById(1);
        Stock result = resultOpt.get();
        assertEquals(1, result.getId());
        assertEquals("Test1", result.getStockName());
        assertEquals(100, result.getQuantity());
	}

	@Test
	public void testFindAll() {
		
		String sortBy = "";
        when(repository.findAll()).thenReturn(sList);
        List<Stock> result = service.findAll(sortBy);
        assertEquals(3, result.size());
	}
	
	@Test
	public void testFindAllByName() {
		
		String sortBy = "name";
        when(repository.findByOrderByStockName()).thenReturn(sList);
        List<Stock> result = service.findAll(sortBy);
        assertEquals(3, result.size());
	}
	
	@Test
	public void testFindAllByDate() {
		
		String sortBy = "date";
        when(repository.findByOrderByPurchaseDate()).thenReturn(sList);
        List<Stock> result = service.findAll(sortBy);
        assertEquals(3, result.size());
        assertEquals(new Date(), result.get(2).getPurchaseDate());
        
	}
	
	@Test
	public void testFindAllByPrice() {
		
		String sortBy = "price";
        when(repository.findByOrderByStockPrice()).thenReturn(sList);
        List<Stock> result = service.findAll(sortBy);
        assertEquals(3, result.size());
        assertEquals(new BigDecimal(100), result.get(2).getStockPrice());
	}

	@Test
	public void testUpdateStock() {
		Stock stock = new Stock(1, "S1", "Test1", new BigDecimal(300), new Date(), 100);
		Stock newStock = new Stock(1, "S2", "Test2", new BigDecimal(400), new Date(), 200);
        when(repository.findById(1)).thenReturn(Optional.of(stock));
        when(repository.save(stock)).thenReturn(newStock);
        Stock result = service.updateStock(newStock, 1);
        assertEquals(1, result.getId());
        assertEquals("Test2", result.getStockName());
        assertEquals(200, result.getQuantity());
	}

	@Test
	public void testSellStock() {
		Stock stock = new Stock(1, "S1", "Test1", new BigDecimal(300), new Date(), 100);
		Stock newStock = new Stock(1, "S1", "Test1", new BigDecimal(300), new Date(), 50);
        when(repository.findById(1)).thenReturn(Optional.of(stock));
        when(repository.save(stock)).thenReturn(newStock);
        Stock result = service.sellStock(1, 50);
        assertEquals(1, result.getId());
        assertEquals("Test1", result.getStockName());
        assertEquals(50, result.getQuantity());
	}

}
