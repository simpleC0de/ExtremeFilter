package extremefilter.commands;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by root on 04.03.2017.
 */
public class CMD_ChatClear implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {

        if(!cs.hasPermission("ex.chatclear")){

            cs.sendMessage("§cSorry, no permission!");

        }


        for(Player all : Bukkit.getOnlinePlayers()){

            if(all.hasPermission("ex.bypassclear")){

                continue;

            }else{

                for(int i = 0; i < 100; i++){

                    all.sendMessage("");
                    all.playSound(all.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 1, 1);
                }



            }

        }

        Bukkit.broadcastMessage("§2[§5ExtremeFilter§2]§5 The chat was cleared by §6" + cs.getName());


        return true;
    }
}
