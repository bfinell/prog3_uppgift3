import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;

import java.util.ArrayList;

public class PearsonCorrelation {
    private ArrayList <String> open1,open2 = new ArrayList<>();
    private double pValue;
    public PearsonCorrelation(ArrayList open1,ArrayList open2){
    this.open1=open1;
    this.open2=open2;
    }
    public double calculate(){
        double[] a=new double[open1.size()];
        double[] b=new double[open2.size()];
        for (int i = 0; i<open1.size(); i++){
            a[i]=Double.parseDouble(open1.get(i).replace("\"",""));
            b[i]=Double.parseDouble(open2.get(i).replace("\"", ""));
        }
        double corr = new PearsonsCorrelation().correlation(a,b);
        return corr;
    }
}
