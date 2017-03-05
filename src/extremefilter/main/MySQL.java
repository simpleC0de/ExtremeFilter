package extremefilter.main;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.*;

/**
 * Created by root on 04.03.2017.
 */
public class MySQL {


    private Connection conn;
    private String hostname;
    private String user;
    private String password;
    private String database;
    private int port;
    public MySQL() throws Exception
    {

        this.hostname = ExtremeFilter.getInstance().getConfig().getString("MySQL.Hostname");
        this.port = ExtremeFilter.getInstance().getConfig().getInt("MySQL.Port");
        this.database =  ExtremeFilter.getInstance().getConfig().getString("MySQL.Database");
        this.user = ExtremeFilter.getInstance().getConfig().getString("MySQL.User");
        this.password = ExtremeFilter.getInstance().getConfig().getString("MySQL.Password");
        this.openConnection();

    }
    public Connection openConnection()
    {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://"+hostname + ":" + port + "/" + database + "?user=" + user + "&password=" + password + "&useUnicode=true&characterEncoding=UTF-8");
            this.conn = conn;
            //UUID, GAMES, GOALS

            queryUpdate("CREATE TABLE extreme_players(UUID varchar(64), INSULTS int, BANTIME float)");

            return conn;
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Bukkit.shutdown();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Bukkit.shutdown();
        }
        return conn;
    }



    public Connection getConnection()
    {
        return this.conn;
    }
    public boolean hasConnection()
    {
        try {
            return this.conn != null || this.conn.isValid(1);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }

    public void closeRessources(ResultSet rs, PreparedStatement st)
    {
        if(rs != null)
        {
            try {
                rs.close();
            } catch (SQLException e) {

            }
        }
        if(st != null)
        {
            try {
                st.close();
            } catch (SQLException e) {

            }
        }
    }


    public void closeConnection()
    {
        try {
            this.conn.close();
        } catch (SQLException e) {

            e.printStackTrace();
        }finally
        {
            this.conn = null;
        }

    }
    public void queryUpdate(final String query)
    {
        new BukkitRunnable() {

            public void run() {
                try {
                    if(getConnection().isValid(2000))
                    {
                        PreparedStatement st = null;

                        Connection conn = getConnection();
                        try {
                            st = conn.prepareStatement(query);
                            st.executeUpdate();
                        } catch (SQLException e) {
                            System.err.println("Failed to send update '" + query + "'.");
                            e.printStackTrace();
                        }finally
                        {
                            closeRessources(null, st);
                        }
                    }
                    else
                    {
                        try {
                            openConnection();
                            PreparedStatement st = null;

                            Connection conn = getConnection();
                            try {
                                st = conn.prepareStatement(query);
                                st.executeUpdate();
                            } catch (SQLException e) {
                                System.err.println("Failed to send update '" + query + "'.");
                                e.printStackTrace();
                            }finally
                            {
                                closeRessources(null, st);
                            }
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }


            }
        }.runTaskAsynchronously(ExtremeFilter.getInstance());

    }

    public boolean playerExists(String uuid){

        try{
            if(getConnection().isValid(2000)){
                openConnection();

                Connection conn = getConnection();
                PreparedStatement st = conn.prepareStatement("SELECT * FROM extreme_players WHERE UUID = '" + uuid + "'");
                ResultSet rs = st.executeQuery();
                return rs.next();
            }
        }catch(Exception ex){
            System.out.println("[ExtremeFilter] Failed to connect to database!");
            return false;
        }
        return false;
    }

    //UUID varchar(64), INSULTS int, BANTIME float

    public void addPlayer(String uuid){
        try{
            if(!getConnection().isValid(2000)){
                openConnection();
            }

            queryUpdate("INSERT INTO extreme_players (`UUID`, `INSULTS`, `BANTIME`) VALUES ('" + uuid + "', 0, 0");

        }catch(Exception ex){
            System.out.println("[ExtremeFilter] Failed to connect to database!");
        }
    }

    public void banPlayer(String uuid, float until){
        try{

            queryUpdate("UPDATE extreme_players SET BANTIME = " + until + " WHERE UUID = '" + uuid + "';");

        }catch (Exception ex){
            System.out.println("[ExtremeFilter] Failed to connect to database!");
        }
    }

    public void unbanPlayer(String uuid){
        try{

            queryUpdate("UPDATE extreme_players SET BANTIME = 0 WHERE UUID = '" + uuid + "';");

        }catch(Exception ex){
            System.out.println("[ExtremeFilter] Failed to connect to database!");
        }
    }

    public float getBantime(String uuid){

        try{

            if(!getConnection().isValid(2000)){
                openConnection();
            }

            float f;
            Connection conn = getConnection();
            PreparedStatement st = conn.prepareStatement("SELECT BANTIME FROM extreme_players WHERE UUID = '" + uuid + "';");
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                return rs.getFloat(1);
            }

            return 0;

        }catch(Exception ex){
            System.out.println("[ExtremeFilter] Failed to connect to database!");
            return 0;
        }

    }

    public boolean isBanned(String uuid){

        try{

            if(!getConnection().isValid(2000)){
                openConnection();
            }
            float f = 0;
            Connection conn = getConnection();
            PreparedStatement st = conn.prepareStatement("SELECT BANTIME FROM extreme_players WHERE UUID = '" + uuid + "';");
            ResultSet rs = st.executeQuery();

            while(rs.next()){
                f = rs.getFloat(1);
            }

            if(f == 0){
                return false;
            }

            if(f <= System.currentTimeMillis()){
                unbanPlayer(uuid);
                return false;
            }

            return true;

        }catch(Exception ex){
            System.out.println("[ExtremeFilter] Failed to connect to database!");
            return false;
        }
    }

}
