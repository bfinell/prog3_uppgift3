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
    public void changeAmount(double newAmount){
        this.amount = amount-newAmount;

    }
    public double getTotValue() {
        return totValue;
    }

    public String getSymbol() {
        return symbol;
    }

    public String info(){
        return symbol+" "+amount+" "+stockValue+" Total value "+totValue;
    }


}
