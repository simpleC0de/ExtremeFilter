package extremefilter.events;

import extremefilter.main.ExtremeFilter;
import extremefilter.storage.MainStorage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;

/**
 * Created by root on 04.03.2017.
 */
public class onJoin implements Listener {

    @EventHandler
    public void joinEvent(PlayerJoinEvent e){

        System.out.println("Triggered join");

        Player p = e.getPlayer();
        String uuid = p.getUniqueId().toString();

        MainStorage.getInstance().spiedBy.put(e.getPlayer(), new ArrayList<>());

      /*  if(ExtremeFilter.getInstance().getMySQL().isBanned(uuid)){

            p.kickPlayer("§c You're banned for Insulting! \n" +
                    "§5Bantime: " + floatInTime(ExtremeFilter.getInstance().getMySQL().getBantime(uuid)));
        }
        */
    }


    protected String floatInTime(float f){
        float now, banTime, out;

        int minutes, hours, days, weeks, years;

        minutes = 0;
        days = 0;
        weeks = 0;
        years = 0;
        hours = 0;
        now = System.currentTimeMillis();
        banTime = f;

        out = banTime - now;

        while(out > 60 * 1000){
            minutes++;
            out =- 60000;
        }

        while(minutes >= 60){
            hours++;
            minutes =- 60;
        }

        while(hours >= 24){
            days++;
            hours =- 24;
        }

        while(days >= 7){
            weeks++;
            days =- 7;
        }

        while(weeks >= 52){
            years++;
            weeks =- 52;
        }

        String outComeDate = "§8" + years + " Years, " + weeks + " Weeks, " + days + " Tage \n" +
                "§8" + hours + " Hours, " + minutes + " Minutes!";

        return outComeDate;
    }
}
