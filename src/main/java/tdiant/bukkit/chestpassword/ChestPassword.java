package tdiant.bukkit.chestpassword;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import tdiant.bukkit.chestpassword.listener.BlockListener;
import tdiant.bukkit.chestpassword.listener.InventoryListener;
import tdiant.bukkit.chestpassword.listener.PlayerListener;
import tdiant.bukkit.chestpassword.manager.ConfigManager;
import tdiant.bukkit.chestpassword.manager.DataManager;
import tdiant.bukkit.chestpassword.manager.LanguageManager;
import tdiant.bukkit.chestpassword.util.ServerVersionCheck;

import java.io.IOException;

/**
 * Created by tdiant on 2017/7/25.
 */
public class ChestPassword extends JavaPlugin {
    public static ChestPassword plugin=null;
    private static boolean DISABLE_TAG=false;
    private static final String PLUGIN_VERSION = "1.3.1";

    public void onEnable(){
        plugin=this;
        //
        checkVersion();
        //ConnectWithOtherLockChestPlugin.checkOtherPlugin();
        if(DISABLE_TAG) return;

        try {
            ConfigManager.reloadConfig();
        } catch (IOException e) {
            e.printStackTrace();
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"ChestPassword 加载配置文件出现错误！");
        }
        LanguageManager.loadData();

        setSqlite(true);
        registerListener();

        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW+"ChestPassword "+getPluginVersion()+" 插件已经加载！");
    }

    public void onDisable(){
        setSqlite(false);
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW+"ChestPassword "+getPluginVersion()+" 插件已经卸载！");
    }

    private void registerListener(){
        Bukkit.getPluginManager().registerEvents(new InventoryListener(),this);
        Bukkit.getPluginManager().registerEvents(new BlockListener(),this);
        Bukkit.getPluginManager().registerEvents(new PlayerListener(),this);
    }

    private void setSqlite(boolean b){
        if(!this.getDataFolder().exists()){
            this.getDataFolder().mkdir();
        }
        if(b){
            try {
                DataManager.initSqilte();
                if(!DataManager.isTableExist("ChestPassword"))
                    DataManager.createChestPasswordTable();
                DataManager.addZeroNumColumn();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            try {
                DataManager.disableSqilte();
            }catch (Exception e){}
        }
    }

    private void checkVersion(){
        if(!ServerVersionCheck.isCorrectVersion()){
            Bukkit.getPluginManager().disablePlugin(this);
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"您的服务器不支持 ChestPassword "+getPluginVersion()+" 版本，插件已经自动卸载！");
            DISABLE_TAG=true;
        }
    }

    public String getPluginVersion(){
        return PLUGIN_VERSION;
    }
}

