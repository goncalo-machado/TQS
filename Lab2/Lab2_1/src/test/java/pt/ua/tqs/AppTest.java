package pt.ua.tqs;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.ArrayList;

/**
 * Unit test for simple App.
 */
public class AppTest 
{

    @Mock
    private IStockmarketService serviceMock;

    @InjectMocks
    private StocksPortfolio portfolio;

    @Before
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @AfterAll
    public void delete() throws Exception{
        MockitoAnnotations.openMocks(this).close();
    }

    @Test
    @DisplayName("Test the implementation of SotckPortfolio#getTotalValue()")
    public void test_StocksPortfolio_GetTotalValue()
    {
        Stock stock1 = new Stock("Teste1", 3);
        Stock stock2 = new Stock("Teste2", 4);
        Stock stock3 = new Stock("Teste3", 5);


        
        when(serviceMock.lookUpPrice("Teste1")).thenReturn(1.0);
        when(serviceMock.lookUpPrice("Teste2")).thenReturn(2.0);
        when(serviceMock.lookUpPrice("Teste3")).thenReturn(3.0);

        portfolio = new StocksPortfolio(serviceMock);
        portfolio.addStock(stock1);
        portfolio.addStock(stock2);
        portfolio.addStock(stock3);

        assertEquals(portfolio.getTotalValue(), 26.0);
        
    }
}
