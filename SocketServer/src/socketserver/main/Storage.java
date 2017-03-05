package socketserver.main;

import sun.misc.IOUtils;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import java.util.ArrayList;
/**
 * Created by root on 05.03.2017.

 */
public class Storage {
    public Storage(){


        instance = this;

        addInsults();

    }

    public ArrayList<String> insults = new ArrayList<>();


    private static Storage instance;

    public static Storage getInstance(){
        return instance;
    }

    protected void addInsults(){

        InputStream input = null;
        try {
            input = new URL( "http://80.241.214.249/insults.txt" ).openStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            java.util.Scanner scan = new java.util.Scanner(input).useDelimiter("\\A");
            String s = scan.toString();

            //Putting into List

            String[] split = s.split(System.getProperty("line.separator"));

            for(int i = 0; i < split.length; i++){

                if(split[i] != null){

                    if(!insults.contains(split[i])){

                        insults.add(split[i]);


                    }

                }

            }




        }finally {
            try{
                input.close();
            }catch(IOException ex){
                ex.printStackTrace();
            }
        }

    }



}
