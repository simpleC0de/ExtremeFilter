package extremefilter.storage;

import extremefilter.main.ExtremeFilter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by root on 04.03.2017.
 */
public class MainStorage {

    private static MainStorage instance;

    public MainStorage(){

        instance = this;

        insults = ExtremeFilter.getInstance().getConfig().getList("Words.Insults");

    }

    public List<?> insults = new ArrayList();

    public ArrayList<Player> muted = new ArrayList<>();

    public boolean chatLock = false;

    public HashMap<Player, Long> chatDelay = new HashMap<>();

    public HashMap<Player, ArrayList<Player>> spiedBy = new HashMap<>();

    public boolean broadcast = ExtremeFilter.getInstance().getConfig().getBoolean("General.Broadcast.Active");


    public static MainStorage getInstance(){
        return instance;
    }
}
