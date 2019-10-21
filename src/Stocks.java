public class Stocks {
    private double amount;
    private String symbol;
    private double unitPrice;

    public Stocks(String symbol, double amount){
        this.symbol=symbol;
        this.amount=amount;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }
    public String info(){
        return symbol+" "+amount+" "+unitPrice+" Total value "+amount*unitPrice;
    }
    public double getAmount() {
        return amount;
    }
    public double getUnitPrice() { return unitPrice; }
    public String getSymbol() {
        return symbol;
    }
}
