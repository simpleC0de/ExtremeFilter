package extremefilter.objects;

import com.google.common.io.CountingOutputStream;
import extremefilter.handler.ResolvServer;
import extremefilter.main.ExtremeFilter;
import org.apache.commons.io.IOUtils;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * Created by root on 05.03.2017.
 */
public class WordFilter {


    protected long traffic = 0;
    protected ArrayList<String> insults = new ArrayList<>();


    public WordFilter(){
        addInsults();

    }

    private String messageCache = "";

    public String testMessage(String message){

        String split[] = message.split("(?!^)");

        String doneMessage = "";



        if(traffic >= 500){

            //Use local method, locally testing messages

        }

        /*
        getConfig().set("General.Messages.Filter.SecondServer", false);
        getConfig().set("General.Messages.Filter.Hostname", "Second Server ip");
        getConfig().set("General.Messages.Filter.Port", 3719);
        getConfig().set("General.Messages.Filter.LookforTraffic", false);
        getConfig().set("General.Messages.Filter.MaxTraffic", 500);
        */
        if(ExtremeFilter.getInstance().getConfig().getBoolean("General.Messages.Filter.SecondServer") && traffic <= ExtremeFilter.getInstance().getConfig().getLong("General.Messages.Filter.LookforTraffic")){


            new BukkitRunnable(){

                @Override
                public void run() {


                    Socket client;
                    ResolvServer server;
                    try {
                        //Creating Clientside Socket
                        client = new Socket(ExtremeFilter.getInstance().getConfig().getString("General.Messages.Filter.Hostname"), ExtremeFilter.getInstance().getConfig().getInt("General.Messages.Filter.Port")); // Change 'localhost' to your IP and the Port to your Receiver port.
                        CountingOutputStream cos = new CountingOutputStream(client.getOutputStream());
                        OutputStream out = client.getOutputStream();
                        PrintWriter writer = new PrintWriter(out);

                        server = new ResolvServer();

                        //Writing a String to the Server via a PrintWriter
                        writer.write("[PROCESS] " + message);
                        //Flushing the Writer to actually send the Data
                        writer.flush();


                        writer.close();
                        traffic += cos.getCount() / 1000000;
                        client.close();

                        //Done with Sending

                        while(server.getMessage().isEmpty()){

                            if(server.getMessage() != ""){

                                messageCache = server.getMessage();

                            }

                        }


                    } catch (UnknownHostException e) {
                        System.out.println("[Error] Failed to connect to the remote Server.");
                    } catch (IOException e) {
                        System.out.println("[Error] Failed to create Writers.");
                    }
                }

            }.runTaskLaterAsynchronously(ExtremeFilter.getInstance(), 0);

            doneMessage = messageCache;

        }

        return doneMessage;

    }

    protected void addInsults(){

        InputStream input = null;
        try {
            input = new URL( "http://80.241.214.249/insults.txt" ).openStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            String s = IOUtils.toString(input);

            //Putting into List

            String[] split = s.split(System.getProperty("line.separator"));

            for(int i = 0; i < split.length; i++){

                if(split[i] != null){

                    if(!insults.contains(split[i])){

                        insults.add(split[i]);


                    }

                }

            }




        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(input);
        }

    }



}
