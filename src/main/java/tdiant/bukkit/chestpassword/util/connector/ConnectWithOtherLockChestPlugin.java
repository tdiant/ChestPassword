package tdiant.bukkit.chestpassword.util.connector;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;
import tdiant.bukkit.chestpassword.ChestPassword;

import java.lang.reflect.Method;

/**
 * Created by tdiant on 2017/7/28.
 */
public class ConnectWithOtherLockChestPlugin {
    public static void checkOtherPlugin(){
        if(true) return;

        new BukkitRunnable(){
            @Override
            public void run() {
                if(Bukkit.getPluginManager().isPluginEnabled("LockettePro")){
                    ChestPassword.plugin.getConfig().set("compatible.LockettePro.excludeBeLockedByLockettePro",true);
                    Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"ChestPassword检测到你安装了LockettePro(牌子锁插件)。");
                    Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"现已展开ChestPassword-LockettePro兼容性配置！默认将不允许用户在设置牌子锁后再次设置GUI密码锁！");
                    Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"您可以在配置文件中更改兼容性配置。");
                    ChestPassword.plugin.saveConfig();
                }
            }
        }.runTaskLater(ChestPassword.plugin,1*20);
    }

    public static boolean isLockInLockettePro(Block b){
        if(!Bukkit.getPluginManager().isPluginEnabled("LockettePro")) return false;
        try {
            Class c=Class.forName("me.crafter.mc.lockettepro.LocketteProAPI");
            Method m_isLocked=c.getDeclaredMethod("isLocked");
            Object o=m_isLocked.invoke(null,b);
            if(o instanceof Boolean){
                return (Boolean)o;
            }
        } catch (Exception e){}
        return false;
    }

    public static boolean isNeedCancelled(Block b){
        if(isLockInLockettePro(b))
            return true;
        return false;
    }
}
