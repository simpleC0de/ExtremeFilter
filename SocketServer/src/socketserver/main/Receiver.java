package socketserver.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by root on 05.03.2017.
 */
public class Receiver implements Runnable{

    public Receiver(){

        run();

    }

    @Override
    public void run() {
        try{
            ServerSocket server = new ServerSocket(1337); // You can enter almost every port here.
            Socket client;

            while(true) {
                client = server.accept(); // The client is the sender.
                client.setKeepAlive(true);


                InputStream in = client.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                String s = null;
                //Creating String for reading
                if ((s = reader.readLine()) != null) {
                    //Reading the line out of the Socket and printing it out
                    System.out.println("Received: " + s);
                }
                in.close();
                reader.close();
                client.close();
            }
        }catch(IOException e){
            System.out.println("[Error] Socket closed or connection got reseted");
        }
    }

}
