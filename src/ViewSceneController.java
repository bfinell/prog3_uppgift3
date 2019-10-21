
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
    private TextArea tArea,StockTextArea;
    @FXML
    private Button doQuery,buyButton,sellButton;
    @FXML
    private ComboBox<String> buyStockList,sellStockList;
    @FXML
    private ComboBox<String> timeSeries,dataSeries,symbol,symbol2,timeInterval,size,API_KEY;
    @FXML
    private LineChart<Number,Number> graph;
    @FXML
    private TextField startDate;
    @FXML
    private TextField stopDate;
    @FXML
    private TextField pearson;


    private XYChart.Series<Number,Number> series = new XYChart.Series<>();

    ObservableList<String> apiKey = FXCollections.observableArrayList();
    ObservableList<String> dList = FXCollections.observableArrayList();
    ObservableList<String> tsList = FXCollections.observableArrayList();
    ObservableList<String> symbolList = FXCollections.observableArrayList();
    ObservableList<String> symbol2List = FXCollections.observableArrayList();
    ObservableList<String> tiList = FXCollections.observableArrayList();
    ObservableList<String> sizeList = FXCollections.observableArrayList();
    ObservableList<String> bStockList = FXCollections.observableArrayList();
    ObservableList<String> sStockList = FXCollections.observableArrayList();



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
        symbol2List.addAll(sInfo);
        bStockList.addAll(sInfo);


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
        symbol2.setItems(symbol2List);
        timeInterval.setItems(tiList);
        size.setItems(sizeList);
        buyStockList.setItems(bStockList);
    }







    @FXML
    protected void handleQueryAction(ActionEvent event) throws InvalidFormatException{
        tArea.clear();
        URLBuilder urlBuilder = new URLBuilder(dataSeries.getValue(),timeSeries.getValue(),
                symbol.getValue(),symbol2.getValue(),timeInterval.getValue() ,size.getValue(),API_KEY.getValue());

        DataFromURL dataFromURL = new DataFromURL(urlBuilder.getFinalURL(),urlBuilder.getFinalURL2(),
                startDate.getText(),stopDate.getText(),dataSeries.getValue());

        setData(dataFromURL.getKeyset(),dataFromURL.getOpen(),dataFromURL.getOpen2(),
                    dataFromURL.getStart(),dataFromURL.getStop());

        Graph g = new Graph(symbol.getValue(),dataFromURL.getOpen(),dataFromURL.getKeyset(),symbol2.getValue(),
                dataFromURL.getOpen2(),dataFromURL.getKeyset2(),dataFromURL.getStart(),dataFromURL.getStop());
        g.setGraph();
        setGraph(g.getSeries(),symbol.getValue());
        if (symbol2.getValue()!=""){
        System.out.println("got through if statment");
            setGraph(g.getSeries2(),symbol2.getValue());

        }
        PearsonCorrelation p = new PearsonCorrelation(dataFromURL.getOpen(),dataFromURL.getOpen2());

        pearson.setText(String.valueOf(p.calculate()));

    }
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
        System.out.println("price "+price.size()+"2 "+price2.size());
        System.out.println(date.get(2));
        int max = price.size()-1;
        if (max>price2.size()){
            max=price2.size()-1;
        }
        if (symbol2.getValue() != "") {
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

    private void setGraph(XYChart.Series<Number,Number> data,String s) {
        this.series.setName(s);
        this.graph.getData().add(data);
    }
    public void clearGraph(){
        series.getData().clear();
        graph.getData().clear();
        System.out.println();
        }

}