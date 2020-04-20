package com.portfolio.model;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import java.awt.*;
import java.util.ArrayList;

public class Portfolio {
    private String name;
    private double portfolioValue=0;
    private double cashMoney=100000;
    @FXML
    private TextArea portfolioTextArea;

    private ArrayList<Stocks> stocks = new ArrayList<>();
    private ArrayList<String> info = new ArrayList<>();
    public Portfolio(String name) {
        this.name = name;
    }

    public void addStocks(String symbol,int amount, double stockValue) {
        boolean newStock = true;
        for (int i=0; i<stocks.size();i++){
            if (symbol.equals(stocks.get(i).getSymbol())){

                stocks.get(i).changeAmount(amount,stockValue);
                info.remove(info.get(i));
                info.add(i,stocks.get(i).info());
                cashMoney -= (amount*stockValue);
                newStock = false;
            }
        }
        if (newStock) {
            Stocks stock = new Stocks(symbol, amount, stockValue);
            stocks.add(stock);
            info.add(stock.info());
            cashMoney -= stock.getTotValue();
            }
        }



        //will implement this  att a later date
    public void sellStocks(String symbol,int amount,double stockValue) {
        for (int i = 0; i< stocks.size(); i++){
            if (stocks.get(i).getSymbol().equals(symbol)){
                if (stocks.get(i).getAmount()>=amount) {
                        stocks.get(i).changeAmount(-amount, stockValue);
                        cashMoney+=amount*stockValue;
                        info.remove(info.get(i));
                        info.add(i,stocks.get(i).info());


                }else {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("WARNING");
                        alert.setContentText("you cannot sell more then you own!");

                }
            }
        }
    }

    public String getName() {
        return name;
    }
    public ArrayList getStocks(){
        return info;
    }
    public double getCashMoney(){
        return cashMoney;
    }

    public double getPortfolioValue() {
        portfolioValue = 0;
        for (Stocks s : stocks) {
            portfolioValue += s.getTotValue();
        }
        return portfolioValue;
    }

}