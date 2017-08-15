package tdiant.bukkit.chestpassword.manager;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import tdiant.bukkit.chestpassword.ChestPassword;

import java.io.IOException;

/**
 * Created by tdiant on 2017/7/27.
 */
public class ConfigManager {
    public static void reloadConfig() throws IOException {
        ChestPassword.plugin.saveDefaultConfig();
        //默认语言文件
        ChestPassword.plugin.saveResource("lang/zh_cn.yml",false);
        ChestPassword.plugin.saveResource("lang/en_us.yml",false);

        ChestPassword.plugin.reloadConfig();
        ChestPassword.plugin.getConfig();
    }

    public static ButtonStyleMode getButtonStyleMode() {
        int mode=ChestPassword.plugin.getConfig().getInt("Button.StyleMode");
        switch (mode){
            default: return null;
            case 1: return ButtonStyleMode.ONLY;
            case 2: return ButtonStyleMode.SKULL;
            case 3: return ButtonStyleMode.AMOUNT;
        }
    }

    public static Material getButtonMaterial(){
        String str=ChestPassword.plugin.getConfig().getString("Button.Material");
        if(str.matches("[0-9]+")){ //数字ID
            try {
                return Material.getMaterial(Integer.parseInt(str));
            }catch (Exception e){
                return Material.WOOL;
            }
        }else{ //Material Name
            if(Material.getMaterial(str)==null)
                return Material.WOOL;
            return Material.getMaterial(str);
        }
    }

    public static boolean isOPFreedomMode(){
        return ChestPassword.plugin.getConfig().getBoolean("OP.freedomMode");
    }

    public static boolean isInputOpGodPassword(Player p){
        String password=ChestPassword.plugin.getConfig().getString("OP.godPassword");
        int zeroNum=0,pwd=0;
        while(password.charAt(0)=='0'){
            zeroNum++;
            password=password.replaceFirst("[0]","");
        }
        try {
            pwd = Integer.parseInt(password);
        }catch (Exception e){}

        if(PlayerInputManager.getPlayerInput(p)==pwd && PlayerInputManager.getPlayerInputZero(p)==zeroNum) return true;
        return false;
    }

    public enum ButtonStyleMode{
        ONLY, SKULL, AMOUNT
    }
}
