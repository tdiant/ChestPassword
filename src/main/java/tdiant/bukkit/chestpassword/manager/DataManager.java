package tdiant.bukkit.chestpassword.manager;

import tdiant.bukkit.chestpassword.ChestPassword;

import java.io.File;
import java.sql.*;

/**
 * Created by tdiant on 2017/7/25.
 */
public class DataManager {
    private static Connection c=null;
    private static Statement stmt = null;

    private static boolean first_run=false;

    public static void initSqilte() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        c = DriverManager.getConnection("jdbc:sqlite:"+new File(ChestPassword.plugin.getDataFolder(),"pass.db").getAbsolutePath().replace("\\","/"));
        stmt = c.createStatement();
        c.setAutoCommit(false);
    }

    public static void disableSqilte() throws SQLException {
        stmt.close();
        c.close();
    }

    public static boolean isTableExist(String tableName) throws SQLException {
        boolean flag = false;
        String sql = "select count(*) from sqlite_master where type ='table' and name =\""+tableName.trim()+"\" ";
        ResultSet set = stmt.executeQuery(sql);
        int count = set.getInt(1);
        if(count>0) flag = true;
        return flag;
    }

    public static void createChestPasswordTable() throws SQLException {
        String sql = "CREATE TABLE ChestPassword " +
                "(Location TEXT PRIMARY KEY NOT NULL," +
                " Password INT NOT NULL DEFAULT 0," +
                " zeroNum INT NOT NULL DEFAULT 0 )";
        stmt.executeUpdate(sql);
        c.commit();
        first_run=true;
    }

    public static int getChestPassword(String LocationStr) throws SQLException {
        ResultSet rs = stmt.executeQuery( "SELECT * FROM ChestPassword;" );
        while ( rs.next() ) {
            String location=rs.getString("Location");
            int password=rs.getInt("Password");

            if(LocationStr.equalsIgnoreCase(location)){
                return password;
            }
        }
        return -1;
    }

    public static int getChestPasswordZeroNum(String LocationStr) throws SQLException {
        ResultSet rs = stmt.executeQuery( "SELECT * FROM ChestPassword;" );
        while ( rs.next() ) {
            String location=rs.getString("Location");
            int zeroNum=rs.getInt("zeroNum");

            if(LocationStr.equalsIgnoreCase(location)){
                return zeroNum;
            }
        }
        return -1;
    }

    public static void changeChestPassword(String location, int password, int zeroNum) throws SQLException {
        String sql = "UPDATE ChestPassword set Password = "+password+" and zeroNum = "+zeroNum+" where Location=\"`"+location+"\";";
        stmt.executeUpdate(sql);
        c.commit();
    }

    public static void createChestPassword(String location, int password, int zeroNum) throws SQLException {
        String sql = "INSERT INTO ChestPassword (Location,Password,zeroNum) " +
                "VALUES (\""+location+"\", "+password+",\""+zeroNum+"\" );";
        stmt.executeUpdate(sql);
        c.commit();
    }

    public static void deleteChestPassword(String location) throws SQLException {
        String sql = "DELETE from ChestPassword where Location=\""+location+"\";";
        stmt.executeUpdate(sql);
        c.commit();
        //ChestPassword
    }

    public static void addZeroNumColumn() throws SQLException {
        if(first_run) return;
        String sql="SELECT * FROM ChestPassword;";
        ResultSet rs=stmt.executeQuery(sql);
        rs.next();
        boolean b=false;
        try {
            if (rs.getObject("zeroNum") != null) b = true;
        }catch (SQLException e){}
        if(!b){
            try {
                rs.close();
                sql = "alter table ChestPassword add zeroNum INT NOT NULL DEFAULT 0";
                stmt.executeUpdate(sql);
                c.commit();
            }catch (Exception e){}
        }

    }
}

//http://www.xiami.com/widget/315883934_17198,_230_150_5695c1_457cb4_1/multiPlayer.swf