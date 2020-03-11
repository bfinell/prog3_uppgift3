import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.NumberAxis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Graph {

    @FXML
    private LineChart<Number,Number> stock_Graph;


    private String symbol1,symbol2;
    private ArrayList price1,price2,date1,date2;
    private int start,stop;
    private XYChart.Series series;
    private XYChart.Series series2;

    public Graph(String symbol1,ArrayList price1,ArrayList date1,String symbol2, ArrayList price2, ArrayList date2, int start, int stop) {
    this.symbol1=symbol1;
    this.symbol2=symbol2;
    this.price1=price1;
    this.price2=price2;
    this.date1=date1;
    this.date2=date2;
    this.start=start;
    this.stop=stop;

    }
    public Graph(){

    }
    public void setGraph(){
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        stock_Graph = new LineChart<Number, Number>(xAxis,yAxis);

        this.series = new XYChart.Series<>();
        Collections.reverse(price1);
        Collections.reverse(price2);
        Collections.reverse(date1);
        Collections.reverse(date2);



      //  XYChart.Series<Number, Number> series = new XYChart.Series<>();

        series.setName(symbol1);
        //System.out.println("graph test"+symbol1+" aa "+date1.size());
         stock_Graph.setCreateSymbols(false);
        for (int i = 1; i < date1.size() - 2; i++) {
            int temp = Integer.parseInt(date1.get(i).toString().replace("-", "").replaceAll(" ", "").substring(0, 8));
            if (temp >= start && temp <= stop) {
                String tempD = date1.get(i).toString().replace("-", ".").replace(":", "").replace(" ", "").substring(5, 10);
                String tempP = price1.get(i).toString().replace("\"", "");
                Float p = Float.parseFloat(tempP);
                series.getData().add(new XYChart.Data(tempD, p));
              //  return series;
            }
        }
        if (price2.get(0).toString()!="0") {
            this.series2 = new XYChart.Series<>();
            series2.setName(symbol2);
            this.stock_Graph.getData().add(series2);
            for (int i = 1; i < date2.size() - 2; i++) {
                int temp = Integer.parseInt(date2.get(i).toString().replace("-", "").replaceAll(" ", "").substring(0, 8));
                if (temp >= start && temp <= stop) {
                    String tempD = date2.get(i).toString().replace("-", ".").replace(":", "").replace(" ", "").substring(5, 10);
                    String tempP = price2.get(i).toString().replace("\"", "");
                    Float p = Float.parseFloat(tempP);
                    series2.getData().add(new XYChart.Data(tempD, p));
                    //return series2;
                }
            }
        }

    }
    public void clear()throws NullPointerException{
        series.getData().clear();
    }
    public XYChart.Series getSeries(){
        return series;
    }
    public XYChart.Series getSeries2(){
        return series2;
   }
}

