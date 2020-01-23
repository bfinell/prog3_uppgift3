import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;

import java.util.ArrayList;

public class PearsonCorrelation {
    private ArrayList <String> open1,open2;
    public PearsonCorrelation(ArrayList open1,ArrayList open2){
    this.open1=open1;
    this.open2=open2;
    }
    public double calculate(){

        while(open1.size()!=open2.size()){
            if (open1.size()>open2.size()){
                open1.remove(open1.size()-1);
            }
            else {open2.remove(open2.size()-1);}
        }
        double[] a=new double[open1.size()];
        double[] b=new double[open2.size()];
        int max = open2.size();

        for (int i = 0; i<max; i++){
            a[i]=Double.parseDouble(open1.get(i).replace("\"",""));
            b[i]=Double.parseDouble(open2.get(i).replace("\"", ""));
        }
        double corr = new PearsonsCorrelation().correlation(a,b);
        return corr;
    }
}
