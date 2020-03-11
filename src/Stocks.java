import java.text.DecimalFormat;

public class Stocks {
    private double amount;
    private double stockValue;
    private String symbol;
    private String date;
    private double totValue;

    public Stocks(String symbol,String date, int amount, double stockValue) {
        this.symbol = symbol;
        this.date = date;
        this.amount = amount;
        this.stockValue = stockValue;
        this.totValue = stockValue*amount;

    }
    public void changeAmount(double newAmount,double stockValue){

        this.amount = amount+newAmount;
        this.totValue = totValue + (newAmount*stockValue);

    }
    public double getTotValue() {

        return totValue;

    }

    public String getSymbol() {
        return symbol;
    }

    public String info(){
        return symbol+" amount: "+amount+" price "+stockValue+" Total value "+totValue +"\n";
    }


}
