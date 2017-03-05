package extremefilter.main;

import extremefilter.commands.*;
import extremefilter.events.onChat;
import extremefilter.events.onCommand;
import extremefilter.events.onJoin;
import extremefilter.handler.AutoBroadcast;
import extremefilter.objects.WordFilter;
import extremefilter.storage.MainStorage;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 04.03.2017.
 */
public class ExtremeFilter extends JavaPlugin{

    private static ExtremeFilter instance;
    MySQL sql;
    WordFilter filter;

    public void onEnable(){
        System.out.println("[ExtremeFilter] Enabling ExtremeFilter...");
    instance = this;
        loadConfig();
        new MainStorage();

        try{
          //  sql = new MySQL();
        }catch(Exception ex){
            System.out.println("[ExtremeFilter] Failed to connect to database!");
        }

        filter = new WordFilter();

        loadCommands();
        loadEvents();
        loadHandler();

        System.out.println("[ExtremeFilter] Enabled!");
    }

    public void onDisable(){

        System.out.println("[ExtremeFilter] Disabling ExtremeFilter...");
        sql = null;
        System.gc();
        System.out.println("[ExtremeFilter] Disabled ExtremeFilter successful!");

    }

    public void loadConfig(){


        if(getDataFolder().exists()){
            return;
        }

        getConfig().set("MySQL.User" , "Username");
        getConfig().set("MySQL.Password", "Password");
        getConfig().set("MySQL.Database", "Databasename");
        getConfig().set("MySQL.Host", "localhost");
        getConfig().set("MySQL.Port", 3306);
        getConfig().set("General.InsultDatabase", false);
        getConfig().set("General.ChatDelay", 1500);
        List<String> list = new ArrayList<>();
        list.clear();
        list.add("Only add lowercase words!");
        list.add("template");
        list.add("template");
        list.add("template");
        list.add("template");
        getConfig().set("Words.Insults", new ArrayList<>(list));
        getConfig().set("General.Broadcast.Delay", 30);
        getConfig().set("General.Broadcast.Active", true);
        getConfig().set("General.BroadCast.List", new ArrayList<>(list));
        getConfig().set("General.Messages.Filter.SecondServer", false);
        getConfig().set("General.Messages.Filter.Hostname", "Second Server ip");
        getConfig().set("General.Messages.Filter.Port", 3719);
        getConfig().set("General.Messages.Filter.LookforTraffic", false);
        getConfig().set("General.Messages.Filter.MaxTraffic", 500);
        saveConfig();

    }

    public void loadHandler(){

        new AutoBroadcast();
    }

    public void loadEvents(){

        getServer().getPluginManager().registerEvents(new onJoin(), this);
        getServer().getPluginManager().registerEvents(new onChat(), this);
        getServer().getPluginManager().registerEvents(new onCommand(), this);


    }

    public void loadCommands(){


        getCommand("mute").setExecutor(new CMD_Mute());
        getCommand("unmute").setExecutor(new CMD_UnMute());
        getCommand("chatclear").setExecutor(new CMD_ChatClear());
        getCommand("chatlock").setExecutor(new CMD_LockChat());
        getCommand("chatulock").setExecutor(new CMD_UnlockChat());
        getCommand("spy").setExecutor(new CMD_Spy());

    }

    public MySQL getMySQL(){
        return sql;
    }

    public static ExtremeFilter getInstance(){
        return instance;
    }

}
