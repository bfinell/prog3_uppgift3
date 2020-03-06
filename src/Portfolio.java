import javafx.fxml.FXML;
import javafx.scene.control.Alert;

import java.awt.*;
import java.util.ArrayList;

public class Portfolio {
    private String name;
    private double portfolioValue;
    private double cashMoney=0;
    @FXML
    private TextArea portfolioTextArea;

    private ArrayList<Stocks> stocks;
    private ArrayList<Object> portfolios;

    public Portfolio(String name) {
        this.name = name;
    }

    public void addStocks(String symbol, String date, int amount, double stockValue) {
        stocks.add(new Stocks(date, symbol, amount, stockValue));
    }

    public void fillTArea() {
        for (Stocks s : stocks) {
            portfolioTextArea.append(s.info());
        }
    }

    public void addPortfolio(Object p) {
        portfolios.add((p));
    }

    public double Sell(String symbol,int amount,double stockValue) {
            for (Stocks s:stocks){
                if (s.getSymbol()==symbol){
                    double tempV=s.getTotValue();
                    s.changeAmount(amount);
                    if (s.getTotValue()>=0){
                        cashMoney+=tempV-s.getTotValue();
                    }
                    if (s.getTotValue()<=0){
                        stocks.remove(s);
                        cashMoney+=tempV;
                    }
                }
            }
        return cashMoney;
    }

    public String getName() {
        return name;
    }

    public double getPortfolioValue() {
        for (Stocks s:stocks)
            portfolioValue+=s.getTotValue();


        return portfolioValue;
    }

    public ArrayList<Object> getPortfolios() {
        return portfolios;
    }
}