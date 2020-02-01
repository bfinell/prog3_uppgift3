public class Stocks {
    private double amount;
    private String symbol;
    private double unitPrice;

    public Stocks(String symbol,String date, int amount, double value){

    }

    public void buyStocks(String date, String symbol, int amount){

    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }



    public String info(){
        return symbol+" "+amount+" "+unitPrice+" Total value "+amount*unitPrice;
    }


}
