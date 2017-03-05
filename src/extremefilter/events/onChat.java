package extremefilter.events;

import extremefilter.main.ExtremeFilter;
import extremefilter.storage.MainStorage;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import sun.applet.Main;

/**
 * Created by root on 04.03.2017.
 */
public class onChat implements Listener {

    @EventHandler
    public void ChatEvent(AsyncPlayerChatEvent e){
        System.out.println("Triggered!");
        if(e.getPlayer().hasPermission("ex.bypassfilter")){
            System.out.println("Bypass");
            return;
        }

        if(canChat(e.getPlayer())){


            e.setMessage(checkForCAPS(e.getMessage(), e.getPlayer()));

        }else{

            e.setCancelled(true);

        }

        if(e.getMessage().startsWith("#staff") && e.getPlayer().hasPermission("ex.staff")){

            System.out.println("Chatted with Staff");
            sendToStaff("§5" + e.getPlayer().getDisplayName() + "§8: §9" + e.getMessage());

        }


    }

    public String checkForCAPS(String message, Player p){

        String[] splittedString = message.split("(?!^)");
        String outCome = "";

        try{

            //Precheck
            int counter = 0;
            for(int o = 0; o < splittedString.length; o++){


                char c = splittedString[o].charAt(0);



                if(Character.isWhitespace(c)){

                    counter++;


                }



            }

                double splitLength = splittedString.length;

                if((splitLength / 100) * 25 <= counter){

                    p.sendMessage("§cUse less spaces!");

                }









            for(int i = 0; i < splittedString.length; i++){

                char c = splittedString[i].charAt(0);
                char check1, check2;

                if(splittedString[i] == splittedString[i].toUpperCase()){


                    if(i == splittedString.length - 1 && i != 0){

                        if(splittedString[i - 1] == null || splittedString[i - 1] == " " || Character.isWhitespace(c)){

                            outCome += splittedString[i];

                        }else{

                            outCome += splittedString[i].toLowerCase();

                        }


                        return outCome;
                    }


                    if(i == 0){

                        if(splittedString[i + 1] == null || splittedString[i + 1] == " " || splittedString[i + 1] == "" || i == 0 || Character.isWhitespace(c)){


                            outCome += splittedString[i];

                        }else{

                            outCome += splittedString[i].toLowerCase();

                        }

                    }else{

                        check1 = splittedString[i - 1].charAt(0);
                        check2 = splittedString[i + 1].charAt(0);

                        if(Character.isWhitespace(check1) && Character.isWhitespace(check2)){

                            ArrayUtils.removeElement(splittedString, splittedString[i - 1]);

                        }

                        if(splittedString[i - 1] == null || splittedString[i - 1] == " " || splittedString[i + 1] == null || splittedString[i + 1] == " " || splittedString[i + 1] == "" || splittedString[i - 1] == "" || Character.isWhitespace(check1) || Character.isWhitespace(check2)){

                            outCome += splittedString[i];

                        }else{

                            outCome += splittedString[i].toLowerCase();

                        }

                    }



                }else{

                    outCome += splittedString[i];

                    continue;
                }

            }

            for(int i = 0; i < MainStorage.getInstance().insults.size(); i++){

                if(outCome.toLowerCase().contains("" + MainStorage.getInstance().insults.get(i))){

                    outCome.replace("" + MainStorage.getInstance().insults.get(i), "***");

                }

            }


            MainStorage.getInstance().chatDelay.put(p, System.currentTimeMillis() + Long.parseLong("" + ExtremeFilter.getInstance().getConfig().getInt("General.ChatDelay")));


        }catch(ArrayIndexOutOfBoundsException ex){

            ex.printStackTrace();

        }catch(Exception ex){

            ex.printStackTrace();

        }

        return outCome;
    }


    protected boolean canChat(Player p){

        //General.ChatDelay

        if(!MainStorage.getInstance().chatDelay.containsKey(p)){

            System.out.println("Contained nicht");

            MainStorage.getInstance().chatDelay.put(p, System.currentTimeMillis() + Long.parseLong("" + ExtremeFilter.getInstance().getConfig().getInt("General.ChatDelay")));


            return true;

        }

        //if(MainStorage.getInstance().chatDelay.get(p) - System.currentTimeMillis() <= 0 - Long.parseLong("" + ExtremeFilter.getInstance().getConfig().getInt("General.ChatDelay"))){

        if(MainStorage.getInstance().chatDelay.get(p) < System.currentTimeMillis()){

            System.out.println("Storage: " + (MainStorage.getInstance().chatDelay.get(p) - System.currentTimeMillis()) + " Parsed:" + Long.parseLong("" + ExtremeFilter.getInstance().getConfig().getInt("General.ChatDelay")) + " Storage: " + MainStorage.getInstance().chatDelay.get(p));

            System.out.println("206");

            MainStorage.getInstance().chatDelay.remove(p);
            return true;

        }else{


            System.out.println("Storage: " + (MainStorage.getInstance().chatDelay.get(p) - System.currentTimeMillis()) + " Parsed:" + Long.parseLong("" + ExtremeFilter.getInstance().getConfig().getInt("General.ChatDelay")) + " Storage: " + MainStorage.getInstance().chatDelay.get(p));
            System.out.println("214");
            p.sendMessage("§c[§5ExtremeFilter§c]§5You have to wait §2" + (MainStorage.getInstance().chatDelay.get(p) - System.currentTimeMillis()) / 1000 + "§c seconds until you can chat again!");

            return false;

        }

    }

    public void sendToStaff(String message){

        for(Player all : Bukkit.getOnlinePlayers()){

            if(all.hasPermission("ex.staff")){

                all.sendMessage("§2[§cStaff§2]§7" + message);

            }

        }

    }
}
