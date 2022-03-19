package pt.ua.tqs;

import java.util.ArrayList;

public class StocksPortfolio {

    ArrayList<Stock> stocks;
    IStockmarketService stockmarket;

    public StocksPortfolio(IStockmarketService stockmarketService){
        this.stocks = new ArrayList<Stock>();
        this.stockmarket = stockmarketService;
    }

    public void addStock(Stock stock){
        this.stocks.add(stock);
    }

    public double getTotalValue(){
        double total = 0;
        for (Stock stock : this.stocks){
            total += this.stockmarket.lookUpPrice(stock.getLabel()) * stock.getQuantity();
        }
        return total;
    }
}
