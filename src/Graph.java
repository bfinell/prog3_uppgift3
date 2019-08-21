import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

import java.util.ArrayList;

public class Graph {
    private LineChart<Number,Number> graph;
    private String symbol1,symbol2;
    private ArrayList price1,price2,date1,date2;
    private int start,stop;

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
    public void setGraph(){
//        this.graph.getData().clear();
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName(symbol1);

        this.graph.getData().add(series);
        for (int i = 1; i < date1.size() - 2; i++) {
            int temp = Integer.parseInt(date1.get(i).toString().replace("-", "").replaceAll(" ", "").substring(0, 8));
            if (temp >= start && temp <= stop) {
                String tempD = date1.get(i).toString().replace("-", ".").replace(":", "").replace(" ", "").substring(5, 10);
                String tempP = price1.get(i).toString().replace("\"", "");
                Float p = Float.parseFloat(tempP);
                series.getData().add(new XYChart.Data(tempD, p));
            }
        }
        if (!price2.isEmpty()) {
            XYChart.Series<Number, Number> series2 = new XYChart.Series<>();
            series2.setName(symbol2);
            this.graph.getData().add(series2);
            for (int i = 1; i < date2.size() - 2; i++) {
                int temp = Integer.parseInt(date2.get(i).toString().replace("-", "").replaceAll(" ", "").substring(0, 8));
                if (temp >= start && temp <= stop) {
                    String tempD = date2.get(i).toString().replace("-", ".").replace(":", "").replace(" ", "").substring(5, 10);
                    String tempP = price2.get(i).toString().replace("\"", "");
                    Float p = Float.parseFloat(tempP);
                    series2.getData().add(new XYChart.Data(tempD, p));
                }
            }
        }


    }
}
