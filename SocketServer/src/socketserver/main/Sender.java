package socketserver.main;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by root on 05.03.2017.
 */
public class Sender implements Runnable{

    public Sender(){

        run();

    }



    @Override
    public void run() {

        Socket client;
        try {
            //Creating Clientside Socket
            client = new Socket("localhost", 1337); // Change 'localhost' to your IP and the Port to your Receiver port.

            OutputStream out = client.getOutputStream();
            PrintWriter writer = new PrintWriter(out);

            //Writing a String to the Server via a PrintWriter
            writer.write("I sent this String to the Server");
            //Flushing the Writer to actually send the Data
            writer.flush();


            writer.close();
            client.close();
        } catch (UnknownHostException e) {
            System.out.println("[Error] Failed to connect to the remote Server.");
        } catch (IOException e) {
            System.out.println("[Error] Failed to create Writers.");
        }
    }
}
