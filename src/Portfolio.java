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
    public void addStocks(String name,int amount){

    }
    public void fillTArea() {
        for (Stocks s:stocks){
            portfolioTextArea.append(s.info());
        }
    }
    public void addPortfolio(Object p){

        portfolios.add(p);
    }

    public ArrayList<Object> getPortfolios() {
        return portfolios;
    }

    public double getTotalSpent() {
        return totalSpent;
    }
}