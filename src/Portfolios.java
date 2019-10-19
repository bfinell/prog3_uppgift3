import javafx.fxml.FXML;

import java.awt.*;
import java.util.ArrayList;

public class Portfolios {
    private String name;
    private double totalSpent = 0;
    private double currentValue = 0;
    @FXML
    private TextArea portfolioTextArea;

    private ArrayList<Stocks> stocks;

    public Portfolios(String name) {
        this.name = name;
    }
    public void addStocks(String name,int amount){
        stocks.add(new Stocks(name,amount));

    }
    public void fillTArea() {
        for (Stocks s:stocks){
            portfolioTextArea.append(s.info());
        }
    }

    public double getTotalSpent() {
        return totalSpent;
    }
}