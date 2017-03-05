package socketserver.main;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by root on 05.03.2017.
 */
public class ResolveHandler implements Runnable{


    protected String message = "";
    protected Socket client;
    protected ServerSocket server;

    public ResolveHandler(String messageE, Socket clienTt, ServerSocket serverR){

        message = messageE;
        client = clienTt;
        server = serverR;

    }


    @Override
    public void run() {




    }
}
