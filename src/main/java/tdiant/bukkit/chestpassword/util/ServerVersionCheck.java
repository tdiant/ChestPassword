package tdiant.bukkit.chestpassword.util;

/**
 * Created by tdiant on 2017/7/29.
 */
public class ServerVersionCheck {
    public static boolean isCorrectVersion(){
        if(true) return true;

        String ver=new NMSTool().getVersion().replace("v","").replace("_",".");

        if(isModServer()) return false; //不支持MOD服

        if (!ver.contains("1.11") && !ver.contains("1.12") && !ver.contains("1.10") && !ver.contains("1.9") && !ver.contains("1.8")) return false; //判断服务器版本

        return true;
    }

    private static boolean isSpigot() {
        try {
            Class.forName("org.spigotmc.SpigotConfig");
        } catch (ClassNotFoundException e) {
            return false;
        }
        return true;
    }

    private static boolean isModServer(){
        try{
            Class.forName("net.minecraftforge.event.entity.player.PlayerEvent");
        }catch (Exception e){
            return false;
        }
        return true;
    }
}
