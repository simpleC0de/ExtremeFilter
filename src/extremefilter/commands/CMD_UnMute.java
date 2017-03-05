package extremefilter.commands;

import extremefilter.storage.MainStorage;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created by root on 04.03.2017.
 */
public class CMD_UnMute implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {

        if(!cs.hasPermission("ex.mute")){

            cs.sendMessage("§cSorry, no permission!");

        }

        if(args.length != 1){

            cs.sendMessage("§c/unmute <Player>");
            return true;
        }

        if(Bukkit.getPlayer(args[0]).isOnline()){

            if(!MainStorage.getInstance().muted.contains(Bukkit.getPlayer(args[0]))){


                cs.sendMessage("§5The Player " + args[0] + " wasn't already muted!");
                return true;

            }

            MainStorage.getInstance().muted.remove(Bukkit.getPlayer(args[0]));

            cs.sendMessage("§Unmuted the Player §5" + args[0]);

        }else{

            cs.sendMessage("§cThe Players §5" + args[0] + "§c isn't online!");

        }


        return true;
    }
}
