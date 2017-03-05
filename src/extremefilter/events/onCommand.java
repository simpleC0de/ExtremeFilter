package extremefilter.events;

import extremefilter.storage.MainStorage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.ArrayList;

/**
 * Created by root on 04.03.2017.
 */
public class onCommand implements Listener {


    @EventHandler
    public void commandExecute(PlayerCommandPreprocessEvent e){

        Player p = e.getPlayer();

        if(MainStorage.getInstance().spiedBy.containsKey(p) && !MainStorage.getInstance().spiedBy.get(p).isEmpty()){

            ArrayList<Player> spyers = MainStorage.getInstance().spiedBy.get(p);

            for(int i = 0; i < spyers.size(); i++){

                Player staff = spyers.get(i);
                if(!staff.isOnline()){
                    MainStorage.getInstance().spiedBy.get(p).remove(staff);
                    continue;
                }

                staff.sendMessage("§2[§5ExtremeFilter§2] §c" + p.getDisplayName() + "§4 executed: §8" + e.getMessage());


            }

        }

    }
}
