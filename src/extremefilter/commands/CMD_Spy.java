package extremefilter.commands;

import extremefilter.storage.MainStorage;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by root on 04.03.2017.
 */
public class CMD_Spy implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {


        if(!(cs instanceof Player)){

            System.out.println("[ExtremeFilter] Only players can spy commands from other players!");
             return true;
        }
        if(!cs.hasPermission("ex.spy")){
            cs.sendMessage("§cSorry, no permission!");
            return true;
        }

        if(args.length != 1){

            cs.sendMessage("§c/spy <Player>");
            return true;
        }

        String player = args[0];


        try{


            Player p = Bukkit.getPlayer(player);


            if(p == null){

                cs.sendMessage(String.format("§cThe players %s isn't online!", args[0]));

                return true;

            }



            if(!MainStorage.getInstance().spiedBy.containsKey(p)){

                System.out.println("Nicht drinne");

            }

            if(MainStorage.getInstance().spiedBy.get(p).contains((Player)cs)){

                MainStorage.getInstance().spiedBy.get(p).remove((Player)cs);

                cs.sendMessage("§cNot spying " + args[0] + " anymore!");

                return true;

            }

            MainStorage.getInstance().spiedBy.get(p).add((Player)cs);

            cs.sendMessage("§2Spying " + args[0] + " now!");

        }catch(Exception ex){
            cs.sendMessage("§cPlayer " + args[0] + " is not online!");
            ex.printStackTrace();
            return true;
        }






        return true;
    }
}
