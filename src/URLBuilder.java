import com.google.gson.JsonObject;

import java.util.ArrayList;

public class URLBuilder {
    private String symbol,symbol2;
    private String size,dSeries,tSeries,interval,apiKey;
    private String finalURL,finalURL2;

    public URLBuilder(String dSeries,String tSeries, String symbol, String symbol2, String interval, String size, String apiKey){
        this.dSeries=dSeries;
        this.tSeries=tSeries;
        this.symbol=symbol;
        this.symbol2=symbol2;
        this.interval=interval;
        this.size=size;
        this.apiKey = apiKey;

        buildURL();
    }public URLBuilder(String symbol,String interval, String apiKey){
        this.symbol=symbol;
        this.apiKey = apiKey;
        this.interval="60min";
        this.tSeries="TIME_SERIES_INTRADAY";
        buildURL();
    }

    public String getFinalURL() {
        return finalURL;
    }

    public String getFinalURL2() {
        return finalURL2;
    }

    private void buildURL(){
        if(symbol2!=""){
            if (this.tSeries.equals("TIME_SERIES_INTRADAY")){
                finalURL2= "https://www.alphavantage.co/query?function=" +
                        tSeries+"&symbol=" +
                        symbol2+"&interval=" +
                        interval+"&outputsize=" +
                        size+"&apikey="+apiKey;
            }
            else {
                finalURL2 =
                        "https://www.alphavantage.co/query?function=" +
                                tSeries+"&symbol=" +
                                symbol2+"&outputsize=" +
                                size+"&apikey="+apiKey;//=ZR69NHOOT7AMCZH8";
            }
        }
        if (this.tSeries.equals("TIME_SERIES_INTRADAY")){
            finalURL= "https://www.alphavantage.co/query?function=" +
                    tSeries+"&symbol=" +
                    symbol+"&interval=" +
                    interval+"&outputsize=" +
                    size+"&apikey="+apiKey;
        }
        else {
            finalURL =
                    "https://www.alphavantage.co/query?function=" +
                            tSeries+"&symbol=" +
                            symbol+"&outputsize=" +
                            size+"&apikey="+apiKey;//=ZR69NHOOT7AMCZH8";

        }

    }
}
