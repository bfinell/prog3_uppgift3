import java.util.HashMap;

public class portofolioItems {
    HashMap<String,Number> stocks;

    public portofolioItems(){

    }
    public void addstock(String stock, float amount){
        this.stocks.put(stock,amount);
    }
    public HashMap getstock(){
        return this.stocks;
    }

}
