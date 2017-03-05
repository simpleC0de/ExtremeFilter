package extremefilter.handler;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * Created by root on 05.03.2017.
 */
public class SocketSender {

    public SocketSender(){



    }

    protected void send(String message){

        SocketAddress address = new InetSocketAddress("", 3812);

        //Send the message, produce, open small server, close server.

    }

}
