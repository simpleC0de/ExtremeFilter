package extremefilter.commands;

import extremefilter.main.ExtremeFilter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created by root on 05.03.2017.
 */
public class CMD_Broadcast implements CommandExecutor{
    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {

        if(!cs.hasPermission("ex.broadcast")){

            cs.sendMessage(ExtremeFilter.getInstance().getNoPerm());
            return true;
        }


        if(!(args.length >= 1)){

            String message = ExtremeFilter.getInstance().getCustomConfig().getString("Message.CorrectCommand");

            cs.sendMessage(message);

            return true;
        }

        String message = "";

        for(int i = 0; i < args.length; i++){

            message += args[i];

        }

        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', ExtremeFilter.getInstance().getFilter().testMessage(message)));


        return true;
    }
}
