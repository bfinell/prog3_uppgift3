package com.portfolio.model;

import java.text.DecimalFormat;

public class Stocks {
    private double amount;
    private double stockValue;
    private String symbol;
    private double totValue;

    public Stocks(String symbol,int amount, double stockValue) {
        this.symbol = symbol;
        this.amount = amount;
        this.stockValue = stockValue;
        this.totValue = stockValue*amount;

    }
    public void changeAmount(double newAmount,double stockValue){

        this.amount = amount+newAmount;
        System.out.println(amount);
        this.stockValue = stockValue;
        this.totValue = totValue + (newAmount*stockValue);
    }
    public double getTotValue() {
        return totValue;
    }

    public double getAmount() {
        return amount;
    }

    public String getSymbol() {
        return symbol;
    }

    public String info(){
        System.out.println(symbol+" amount: "+amount+" price "+stockValue+" Total value "+totValue +"\n");
        return symbol+" amount: "+amount+" price "+stockValue+" Total value "+totValue +"\n";
    }


}
