
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.ini4j.*;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import com.sun.media.sound.InvalidFormatException;




public class ViewSceneController {
    @FXML
    private TextArea tArea,portfolioTextArea;
    @FXML
    private Button doQuery,buyButton,sellButton,ClearButton,addPortfoliobtn;
    @FXML
    private ComboBox<String> symbol,symbol2, buyStockList,sellStockList,size,API_KEY,timeInterval,timeSeries,dataSeries,portfoliobox;
    @FXML
    private LineChart<Number,Number> graph;
    @FXML
    private TextField startDate,stopDate,buyDate,sellDate,buyAmount,sellAmount,addportfoliotname,pearson,totalAmountBox;

    private XYChart.Series<Number,Number> series = new XYChart.Series<>();
    private XYChart.Series<Number,Number> series2 = new XYChart.Series<>();
    private ArrayList<Portfolio> portfolios = new ArrayList<>();

    ObservableList<String> portfolioName = FXCollections.observableArrayList();
    ObservableList<String> apiKey = FXCollections.observableArrayList();
    ObservableList<String> dList = FXCollections.observableArrayList();
    ObservableList<String> tsList = FXCollections.observableArrayList();
    ObservableList<String> symbolList = FXCollections.observableArrayList();
    ObservableList<String> pSymbol2List = FXCollections.observableArrayList();
    ObservableList<String> tiList = FXCollections.observableArrayList();
    ObservableList<String> sizeList = FXCollections.observableArrayList();



//fill combo boxes from .ini file
    private void fillLists()throws IOException{
        Ini ini = new Ini(new FileReader("./src/StockAnalyzer.ini"));
        String key = ini.get("controllInfo","API_KEY");
        apiKey.setAll(key);

        String dString = ini.get("controllInfo","DATA_SERIES");
        String[] dInfo = dString.split(",");
        dList.addAll(dInfo);

        String tsString = ini.get("controllInfo","TIME_SERIES");
        String[] tsInfo = tsString.split(",");
        tsList.addAll(tsInfo);

        String sString = ini.get("controllInfo","SYMBOL");
        String[] sInfo = sString.split(",");
        symbolList.addAll(sInfo);

        String tiString = ini.get("controllInfo","TIME_INTERVAL");
        String[] tiInfo = tiString.split(",");
        tiList.addAll(tiInfo);

        String osString = ini.get("controllInfo","OUTPUT_SIZE");
        String[] osInfo = osString.split(",");
        sizeList.addAll(osInfo);
    }

    public void initialize()throws IOException {
        fillLists();
        API_KEY.setItems(apiKey);
        dataSeries.setItems(dList);
        timeSeries.setItems(tsList);
        symbol.setItems(symbolList);
        symbol2.setItems(symbolList);
        buyStockList.setItems(symbolList);
        timeInterval.setItems(tiList);
        size.setItems(sizeList);
    }


    @FXML
    protected void handleQueryAction(ActionEvent event) throws InvalidFormatException{

                //checks if user wants 1 or 2 tickers
                if (symbol2.getValue().isEmpty()) {
                    //builds URL
                    URLBuilder urlBuilder = new URLBuilder(dataSeries.getValue(), timeSeries.getValue(),
                            symbol.getValue(), timeInterval.getValue(), size.getValue(), API_KEY.getValue());
                    //Fetches data from alphavantage
                    DataFromURL dataFromURL = new DataFromURL(urlBuilder.getFinalURL(),
                            startDate.getText(), stopDate.getText(), dataSeries.getValue());
                    //Fills textArea with data
                    setData(dataFromURL.getKeyset(), dataFromURL.getOpen(), dataFromURL.getOpen2(),
                            dataFromURL.getStart(), dataFromURL.getStop());

                    Graph g = new Graph(symbol.getValue(), dataFromURL.getOpen(), dataFromURL.getKeyset(), symbol2.getValue(),
                            dataFromURL.getOpen2(), dataFromURL.getKeyset2(), dataFromURL.getStart(), dataFromURL.getStop());

                    g.setGraph();
                    setGraph(g.getSeries(), symbol.getValue());


                }
                else{
                    //If user chooses 2 tickers
                    URLBuilder urlBuilder = new URLBuilder(dataSeries.getValue(), timeSeries.getValue(),
                            symbol.getValue(), symbol2.getValue(), timeInterval.getValue(), size.getValue(), API_KEY.getValue());

                    DataFromURL dataFromURL = new DataFromURL(urlBuilder.getFinalURL(), urlBuilder.getFinalURL2(),
                            startDate.getText(), stopDate.getText(), dataSeries.getValue());

                    setData(dataFromURL.getKeyset(), dataFromURL.getOpen(), dataFromURL.getOpen2(),
                            dataFromURL.getStart(), dataFromURL.getStop());

                    Graph g = new Graph(symbol.getValue(), dataFromURL.getOpen(), dataFromURL.getKeyset(), symbol2.getValue(),
                            dataFromURL.getOpen2(), dataFromURL.getKeyset2(), dataFromURL.getStart(), dataFromURL.getStop());

                    g.setGraph();
                    setGraph(g.getSeries(), symbol.getValue());
                    setGraph(g.getSeries2(), symbol2.getValue());

                    PearsonCorrelation p = new PearsonCorrelation(dataFromURL.getOpen(),dataFromURL.getOpen2());

                    pearson.setText(String.valueOf(p.calculate()));
                }


    }

    @FXML //adds portfolio
    private void handelAddPortfolio(ActionEvent event)throws InvalidFormatException, NullPointerException{
        Portfolio portfolio = new Portfolio(addportfoliotname.getText());
        portfolios.add(portfolio);
        portfolioName.add(portfolio.getName());
        portfoliobox.setItems(portfolioName);
        
    }
    @FXML //uppdates stock view to current portfolio
    private void handleSwapPortfolio(ActionEvent event){
        int index = 0;

        for (int i = 0; i < portfolios.size(); i++) {
            if (portfolios.get(i).getName() == portfoliobox.getValue()) {
                index = i;
            }
        }
        portfolioTextArea.clear();
        for (int i = 0;i<portfolios.get(index).getStocks().size();i++){
            portfolioTextArea.appendText(portfolios.get(index).getStocks().get(i).toString());
        }
        totalAmountBox.setText(Double.toString(portfolios.get(index).getPortfolioValue()));
    }


    @FXML
    private void handleBuyStock(ActionEvent event) {
        int index = 0;
        // finds correct portfolio from Arraylist
        for (int i = 0; i < portfolios.size(); i++) {
            if (portfolios.get(i).getName() == portfoliobox.getValue()) {
                index = i;
            }
        }


        URLBuilder urlBuilder = new URLBuilder(buyStockList.getValue(),buyDate.getText(), API_KEY.getValue());
        DataFromURL dataFromURL = new DataFromURL(urlBuilder.getFinalURL(),
                startDate.getText(),stopDate.getText(),dataSeries.getValue());

        portfolios.get(index).addStocks(buyStockList.getValue().toString(),buyDate.getText(),
                Integer.parseInt(buyAmount.getText()),
                Double.parseDouble(dataFromURL.getOpen().get(0).replace("\"", "")));

        portfolioTextArea.clear();
        for (int i = 0;i<portfolios.get(index).getStocks().size();i++){
            portfolioTextArea.appendText(portfolios.get(index).getStocks().get(i).toString());
        }
        totalAmountBox.setText(Double.toString(portfolios.get(index).getPortfolioValue()));
    }

    //Will add SellStock for next assignment
  /*  @FXML
    private void handleSellStock(ActionEvent event) {
        URLBuilder urlBuilder = new URLBuilder(sellStockList.getValue(), sellDate.getText(), API_KEY.getValue());
        DataFromURL dataFromURL = new DataFromURL(urlBuilder.getFinalURL(),
                startDate.getText(), stopDate.getText(), dataSeries.getValue());
        Stocks stocks = new Stocks(sellStockList.getValue(), sellDate.getText(), Integer.parseInt(sellAmount.getText()), Double.parseDouble(dataFromURL.getOpen().get(0).replace("\"", "")));
        portfolio.Sell(sellStockList.getValue(),Integer.parseInt(sellAmount.getText()),Double.parseDouble(dataFromURL.getOpen().get(0).replace("\"", "")))

    }*/

    @FXML
    private void handleTimeSeriesAction(ActionEvent event){
        timeInterval.setDisable(false);
        size.setDisable(false);

        if (!timeSeries.getValue().equals("TIME_SERIES_INTRADAY")){
            timeInterval.setDisable(true);
        }
        if (timeSeries.getValue().equals("TIME_SERIES_WEEKLY")||
                timeSeries.getValue().equals("TIME_SERIES_WEEKLY_ADJUSTED")||
                timeSeries.getValue().equals("TIME_SERIES_MONTHLY")||
                timeSeries.getValue().equals("TIME_SERIES_MONTHLY_ADJUSTED")){
            size.setDisable(true);
        }
    }


    private void setData(ArrayList date, ArrayList price,ArrayList price2,int start, int stop) {
        date.remove(date.size() -1);
        int max = price.size()-1;
        if (max>price2.size()){
            max=price2.size()-1;
        }
        if (!symbol2.getValue().isEmpty()) {
            for (int i = max-1; i >= 0; i--) {

                int temp = Integer.parseInt(date.get(i).toString().replace("-", "").replaceAll(" ", "").substring(0, 8));
                if (temp >= start && temp <= stop) {

                    tArea.appendText("Date:" + date.get(i) + " " + dataSeries.getValue().substring(3) + "  " + symbol.getValue() + " " + price.get(i).toString().replace("\"", "") + "   " + symbol2.getValue() + "" +
                            ": " + price2.get(i).toString().replace("\"", "") + "\n");
                }
            }
        } else {
            for (int i =max; i >= 0; i--) {

                int temp = Integer.parseInt(date.get(i).toString().replace("-", "").replaceAll(" ", "").substring(0, 8));
                if (temp >= start && temp <= stop) {
                    tArea.appendText("Date:" + date.get(i) + " " + dataSeries.getValue().substring(3) + "  " + symbol.getValue() + ": " + price.get(i).toString().replace("\"", "") + "\n");
                }
            }
        }
    }

    public void setGraph(XYChart.Series<Number,Number> data,String s) {
        this.series.setName(s);
        this.graph.getData().add(data);
    }

    public void handleClearButton(ActionEvent event) {
     tArea.clear();
     graph.getData().clear();
    }
}