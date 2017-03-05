package extremefilter.handler;

import extremefilter.main.ExtremeFilter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by root on 05.03.2017.
 */
public class ResolvServer implements Runnable{

    private String message = "";

    public ResolvServer(){

        run();

    }

    @Override
    public void run() {

        Socket client = null;
        ServerSocket server = null;
        InputStream in = null;
        BufferedReader reader = null;
        try{
            server = new ServerSocket(ExtremeFilter.getInstance().getConfig().getInt("General.Messages.Filter.LocalServer.Port")); // You can enter almost every port here.

            while(true) {
                client = server.accept(); // The client is the sender.
                client.setKeepAlive(true);


                in = client.getInputStream();
                reader = new BufferedReader(new InputStreamReader(in));

                String s = null;
                //Creating String for reading
                if ((s = reader.readLine()) != null) {
                    //Reading the line out of the Socket and printing it out
                    System.out.println("Received: " + s);
                    message = s;
                }
            }

        }catch(IOException e){
            System.out.println("[Error] Socket closed or connection got reseted");
        }finally {
            try{

                client.close();
                server.close();
                in.close();
                reader.close();

            }catch(IOException ex){



            }

        }


    }

    public String getMessage() {
        return message;
    }

}
