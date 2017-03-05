package extremefilter.handler;

import extremefilter.main.ExtremeFilter;
import extremefilter.storage.MainStorage;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by root on 04.03.2017.
 */
public class AutoBroadcast {

    protected ArrayList<String> messages = new ArrayList<>();

    public AutoBroadcast(){


        //(ArrayList<String>) ExtremeFilter.getInstance().getConfig().getStringList("General.Broadcast.List");

       // Set<String> list = ExtremeFilter.getInstance().getConfig().getConfigurationSection("General.BroadCast.List").getKeys(false);
        messages = (ArrayList<String>) ExtremeFilter.getInstance().getConfig().getStringList("General.BroadCast.List");



        broadcast();



    }

    protected void broadcast(){

        try{

            new BukkitRunnable(){

                int counter = 0;

                @Override
                public void run(){
                    counter++;

                    if(counter >= messages.size()){

                        counter = 0;

                    }

                    if(!MainStorage.getInstance().broadcast){
                        return;
                    }


                    Bukkit.broadcastMessage("§5[§2ExtremeFilter§5] §8" + messages.get(counter));



                }

            }.runTaskTimerAsynchronously(ExtremeFilter.getInstance(), 0, ExtremeFilter.getInstance().getConfig().getInt("General.Broadcast.Delay") * 20);
        }catch(Exception ex){


            ex.printStackTrace();

        }



    }

}
