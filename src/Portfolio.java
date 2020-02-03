import javafx.fxml.FXML;

import java.awt.*;
import java.util.ArrayList;

public class Portfolio {
    private String name;
    private double totalSpent = 0;
    private double currentValue = 0;
    @FXML
    private TextArea portfolioTextArea;

    private ArrayList<Stocks> stocks;
    private ArrayList<Object> portfolios;

    public Portfolio(String name) {
        this.name = name;
    }
    public void addStocks(String symbol,String date,int amount, double stockValue){
                stocks.add(new Stocks(date,symbol,amount,stockValue));
    }
    public void fillTArea() {
        for (Stocks s:stocks){
            portfolioTextArea.append(s.info());
        }
    }
    public void addPortfolio(Object p){

        portfolios.add((p));
    }
    public double buySell(){

    }

    public String getName() {
        return name;
    }

    public ArrayList<Object> getPortfolios() {
        return portfolios;
    }

    public double getTotalSpent() {
        return totalSpent;
    }
}