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
public class CMD_LockChat implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {

        if(!cs.hasPermission("ex.lockchat")){

            cs.sendMessage(ExtremeFilter.getInstance().getNoPerm());

            return true;
        }


            if(MainStorage.getInstance().chatLock){

                cs.sendMessage("§cThe chat is already locked!");
                return true;
            }


            MainStorage.getInstance().chatLock = true;

            cs.sendMessage("§2Locked the chat!");

        Bukkit.broadcastMessage("§cThe chat just got locked by " + cs.getName());


        return true;
    }
}
