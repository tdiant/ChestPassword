package tdiant.bukkit.chestpassword.manager;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.sql.SQLException;

/**
 * Created by tdiant on 2017/7/25.
 */
public class ChestPasswordManager {
    @Deprecated
    public static int getChestPassword(Block b){
        try {
            return DataManager.getChestPassword(getStringLocation(b.getLocation()));
        } catch (SQLException e) {}
        return -1;
    }

    @Deprecated
    public static int getChestPasswordZeroNum(Block b){
        try {
            return DataManager.getChestPasswordZeroNum(getStringLocation(b.getLocation()));
        } catch (SQLException e) {}
        return -1;
    }

    public static boolean isTrulyChestPasswordInput(Block b, int password, int zeroNum){
        if(!isChestSetPassword(b)) return false;
        if(getChestPassword(b)==password && getChestPasswordZeroNum(b)==zeroNum)
            return true;
        return false;
    }

    public static boolean isChest(Block b){
        if(b.getType()== Material.CHEST
                || b.getType()==Material.ENDER_CHEST
                || b.getType()==Material.TRAPPED_CHEST)
            return true;
        return false;
    }

    public static String getStringLocation(Location l){
        return l.getWorld().getName()+"::"+l.getBlockX()+"::"+l.getBlockY()+"::"+l.getBlockZ();
    }

    public static boolean setChestPassword(Block b, int password, int zeroNum){
        try {
            if(isChestSetPassword(b)){
                return false;
            }
            DataManager.createChestPassword(getStringLocation(b.getLocation()),password, zeroNum);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isChestSetPassword(Block b){
        if(getChestPassword(b)<0 && getChestPasswordZeroNum(b)<0) return false;
        else return true;
    }

    public static boolean changeChestPassword(Block b, int password, int zeroNum){
        if(!isChestSetPassword(b))
            return false;
        try{
            DataManager.changeChestPassword(getStringLocation(b.getLocation()),password, zeroNum);
            return true;
        }catch (Exception e){}
        return false;
    }

    public static void removeChestPassword(Block b){
        try{
            DataManager.deleteChestPassword(getStringLocation(b.getLocation()));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
