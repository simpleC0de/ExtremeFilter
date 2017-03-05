package extremefilter.main;

import extremefilter.commands.*;
import extremefilter.events.ChatListener;
import extremefilter.events.CommandListener;
import extremefilter.events.JoinListener;
import extremefilter.handler.AutoBroadcast;
import extremefilter.handler.PluginFile;
import extremefilter.objects.WordFilter;
import extremefilter.storage.MainStorage;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 04.03.2017.
 */
public class ExtremeFilter extends JavaPlugin{

    private static ExtremeFilter instance;
    MySQL sql;
    WordFilter filter;

    private String noPerm = "";

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
        noPerm = getCustomConfig().getString("Message.NoPermission");

        System.out.println("[ExtremeFilter] Enabled!");
    }

    public String getNoPerm(){
        return noPerm;
    }

    public void onDisable(){

        System.out.println("[ExtremeFilter] Disabling ExtremeFilter...");
        sql = null;
        System.gc();
        System.out.println("[ExtremeFilter] Disabled ExtremeFilter successful!");

    }

    PluginFile customConfig;

    public void loadConfig(){


        if(getDataFolder().exists()){
            return;
        }


        customConfig = new PluginFile(this, "lang.yml");

        customConfig.set("Message.NoPermission", "§cSorry, no permission!");
        customConfig.set("Message.ClearedChat", "§5 $player just cleared the chat!");
        customConfig.set("Message.LockChat", "§cThe chat has been locked by $player");
        customConfig.set("Message.UnlockChat", "§cThe chat has been unlocked by $player");
        customConfig.set("Message.Mute", "§cYou just muted $target");
        customConfig.set("Message.Unmute", "§cYou just unmuted $target");
        customConfig.set("Message.GotMuted", "§cYou just got muted by $player");
        customConfig.set("Message.GotUnMuted", "§cYou just got unmuted by $player");
        customConfig.set("Message.Spy", "§cYoure now spying $target");
        customConfig.set("Message.SpyExecute", "§c$player just executed $command");
        customConfig.set("Message.DontuseCaps", "§cHey $player! Stop using so many Caps!");
        customConfig.set("Message.TooManyWhitespaces", "§cHey $player! You're using too many whitespaces!");
        customConfig.set("Message.WaitUntilChat", "§cWait $seconds until you can chat again!");
        customConfig.set("Message.CorrectCommand", "§c$command");



        //Placeholders: $player, $target, $command, $seconds



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
        getConfig().set("General.Messages.Filter.LocalServer.Port", 9892);
        saveConfig();
        customConfig.save();

    }

    public void loadHandler(){

        new AutoBroadcast();
    }

    public void loadEvents(){

        getServer().getPluginManager().registerEvents(new JoinListener(), this);
        getServer().getPluginManager().registerEvents(new ChatListener(), this);
        getServer().getPluginManager().registerEvents(new CommandListener(), this);


    }

    public void loadCommands(){


        getCommand("mute").setExecutor(new CMD_Mute());
        getCommand("unmute").setExecutor(new CMD_UnMute());
        getCommand("chatclear").setExecutor(new CMD_ChatClear());
        getCommand("chatlock").setExecutor(new CMD_LockChat());
        getCommand("chatulock").setExecutor(new CMD_UnlockChat());
        getCommand("spy").setExecutor(new CMD_Spy());
        getCommand("bc").setExecutor(new CMD_Broadcast());

    }

    public MySQL getMySQL(){
        return sql;
    }

    public WordFilter getFilter(){return filter;}

    public PluginFile getCustomConfig(){
        return customConfig;
    }

    public static ExtremeFilter getInstance(){
        return instance;
    }

}
