import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.jndi.toolkit.url.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class DataFromURL {
    private ArrayList<JsonObject> data = new ArrayList<>();
    private ArrayList<String> keyset = new ArrayList<>();
    private ArrayList<String> keyset2 = new ArrayList<>();
    private ArrayList<String> open = new ArrayList<>();
    private ArrayList<String> open2 = new ArrayList<>();
    private String start,stop;
    private String URL1,URL2;
    private String dSeries;

    public DataFromURL(String URL1,String URL2,String start,String stop,String dSeries){
     this.URL1= URL1;
     this.URL2=URL2;
     this.dSeries=dSeries;
     this.start=start;
     this.stop=stop;
     getData();
    }
    public DataFromURL(String URL1,String start,String stop,String dSeries){
        this.URL1= URL1;
        this.dSeries=dSeries;
        this.start=start;
        this.stop=stop;
        getData();
    }

    private void getData(){

        try {
            System.out.println(URL1);
            URL url = new URL(URL1);
            URLConnection request = url.openConnection();
            request.connect();


            JsonParser jp = new JsonParser();
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent())); //Convert the input stream to a json element
            JsonObject obj = root.getAsJsonObject();
            System.out.println(obj);
            String[] tempObjPart = obj.keySet().toString().split(",");
            String tempObj = tempObjPart[1].replace("]","").substring(1);
            System.out.println(tempObj);
            data.add(obj.get(tempObj).getAsJsonObject());
            String prePart = obj.get(tempObj).getAsJsonObject().keySet().toString().replace("[","") ;

            String [] parts = prePart.split(",");

            for (int i = 0; i<obj.get(tempObj).getAsJsonObject().size(); i++){
                keyset.add(parts[i]);
            }
            open.add(obj.get(tempObj).getAsJsonObject().get(keyset.get(0)).getAsJsonObject().get(dSeries).toString());
            for (int i = 1; i<keyset.size();i++){
                open.add(obj.get(tempObj).getAsJsonObject().get(keyset.get(i).substring(1)).getAsJsonObject().get(dSeries).toString());

            }
            open.add(obj.get(tempObj).getAsJsonObject().get(keyset.get(keyset.size()-1).substring(1,20)).getAsJsonObject().get(dSeries).toString());

        }catch (IOException | NullPointerException e){


        }
        if ((URL2!=null)){
            System.out.println(URL2);
            try{
                URL url = new URL(URL2);
                URLConnection request = url.openConnection();
                request.connect();

                JsonParser jp = new JsonParser();
                JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent())); //Convert the input stream to a json element
                JsonObject obj = root.getAsJsonObject();

                String[] tempObjPart = obj.keySet().toString().split(",");
                String tempObj = tempObjPart[1].replace("]","").substring(1);
                System.out.println(tempObj);
                data.add(obj.get(tempObj).getAsJsonObject());
                String prePart = obj.get(tempObj).getAsJsonObject().keySet().toString().replace("[","") ;

                String [] parts = prePart.split(",");

                for (int i = 0; i<obj.get(tempObj).getAsJsonObject().size(); i++){
                    keyset2.add(parts[i]);
                }
                open2.add(obj.get(tempObj).getAsJsonObject().get(keyset2.get(0)).getAsJsonObject().get(dSeries).toString());
                for (int i = 1; i<keyset2.size();i++){
                    open2.add(obj.get(tempObj).getAsJsonObject().get(keyset2.get(i).substring(1)).getAsJsonObject().get(dSeries).toString());

                }
                open2.add(obj.get(tempObj).getAsJsonObject().get(keyset2.get(keyset2.size()-1).substring(1,20)).getAsJsonObject().get(dSeries).toString());

            }catch (IOException | NullPointerException e){

            }
        }

    }
    protected int getStart()throws NullPointerException{
        System.out.println(start);
        String temp = start.replace(".","");
        return Integer.parseInt(temp);
    }
    protected int getStop(){
        String temp = stop.replace(".","");
        System.out.println(temp);
        return Integer.parseInt(temp);
    }

    public ArrayList<String> getOpen() {
        return open;
    }

    public ArrayList<String> getOpen2() {
        if (open2.isEmpty()){
            for (String a:open){
                open2.add("0");
            }
        }
        return open2;
    }

    public ArrayList<String> getKeyset() {
        System.out.println(keyset.size());
        return keyset;
    }

    public ArrayList<String> getKeyset2() {
        if (keyset2.isEmpty()){
            for (String a:keyset){
                keyset2.add("0");
            }
        }
        return keyset2;
    }
}
