import javafx.fxml.FXML;
import javafx.scene.control.Alert;

import java.awt.*;
import java.util.ArrayList;

public class Portfolio {
    private String name;
    private double portfolioValue=0;
    private double cashMoney=0;
    @FXML
    private TextArea portfolioTextArea;

    private ArrayList<Stocks> stocks = new ArrayList<>();
    private ArrayList<String> info = new ArrayList<>();
    public Portfolio(String name) {
        this.name = name;
    }

    public void addStocks(String symbol, String date, int amount, double stockValue) {
        System.out.println(symbol);
        System.out.println(date);
        System.out.println(amount);
        System.out.println(stockValue);
        Stocks stock = new Stocks(symbol,date , amount, stockValue);
        stocks.add(stock);
        info.add(stock.info());
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
    public ArrayList getStocks(){
        return info;
    }


    public double getPortfolioValue() {
        portfolioValue = 0;
        for (Stocks s : stocks) {
            portfolioValue += s.getTotValue();
        }
        return portfolioValue;
    }

}