package extremefilter.commands;

import extremefilter.storage.MainStorage;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created by root on 04.03.2017.
 */
public class CMD_UnlockChat implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {

        if(!cs.hasPermission("ex.lockchat")){

            cs.sendMessage("§cSorry, no permission!");

        }


        if(MainStorage.getInstance().chatLock){

            MainStorage.getInstance().chatLock = false;

            cs.sendMessage("§2Chat unlocked!");

            Bukkit.broadcastMessage("§cThe chat just got unlocked by " + cs.getName());

            return true;
        }

        cs.sendMessage("§5The chat is already locked!");

        return true;
    }
}
