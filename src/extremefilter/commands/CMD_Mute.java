package extremefilter.commands;

import extremefilter.main.ExtremeFilter;
import extremefilter.storage.MainStorage;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created by root on 04.03.2017.
 */
public class CMD_Mute implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {

        if(!cs.hasPermission("ex.mute")){

            cs.sendMessage(ExtremeFilter.getInstance().getNoPerm());

        }

        if(args.length != 1){

            cs.sendMessage("§c/mute <Player>");
            return true;
        }

        try{
            if(Bukkit.getPlayer(args[0]).isOnline()){

                if(MainStorage.getInstance().muted.contains(Bukkit.getPlayer(args[0]))){


                    cs.sendMessage("§5The Player " + args[0] + " was already muted!");
                    return true;

                }

                MainStorage.getInstance().muted.add(Bukkit.getPlayer(args[0]));

                cs.sendMessage("§cMuted the Player §5" + args[0]);

            }else{

                cs.sendMessage("§cThe Players §5" + args[0] + "§c isn't online!");

            }
        }catch(NullPointerException ex){

            cs.sendMessage("§cThe players " + args[0] + " is not online!");

        }




        return true;
    }
}
