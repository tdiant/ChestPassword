package tdiant.bukkit.chestpassword.manager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.yaml.snakeyaml.DumperOptions;
import tdiant.bukkit.chestpassword.ChestPassword;
import tdiant.bukkit.chestpassword.util.base.LanguageCore;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tdiant on 2017/8/15.
 */
public class LanguageManager {
    private static List<LanguageCore> coreList = new ArrayList<>();
    private static String usingCoreName = null;
    private static LanguageCore defaultCore = null;
    private static LanguageCore defaultZHCNCore = null;

    public static void setUsingCore(String name){
        if(name==null) return;
        if(getCore(name)==null) return;
        usingCoreName = name;
    }

    public static void setUsingCore(LanguageCore core){
        if(core==null) return;
        registerCore(core);
        usingCoreName = core.getName();
    }

    public static LanguageCore getUsingCore(){
        LanguageCore core = getCore(usingCoreName);
        if(core!=null)
            return core;
        else
            return getDefaultCore();
    }

    public static LanguageCore getCore(String name){
        if(name==null) return null;
        for(LanguageCore core:coreList){
            if(core.getName().replace(" ","").equalsIgnoreCase(name)){
                return core;
            }
        }
        return null;
    }

    public static void registerCore(LanguageCore core){
        if(coreList.contains(core)) return;
        if(getCore(core.getName())!=null) return;
        core.checkAndFix();
        coreList.add(core);
    }

    public static void removeCore(LanguageCore core){
        if(core!=null)
            coreList.remove(core);
    }

    public static void removeCore(String coreName){
        removeCore(getCore(coreName));
    }

    public static LanguageCore getDefaultCore(){
        if(defaultCore == null) {
            Map<String,String> langMap = new HashMap<>();

            langMap.put("tip_prefix",  "&6[ChestPassword] &r");
            langMap.put("gui_emptyPassword",  "Empty Password");
            langMap.put("gui_button_submit",  "Submit");
            langMap.put("gui_button_backspace",  "Backspace");
            langMap.put("inventory_step_no_permission",  "&4You do not have permission to use ChestPassword chests.");
            langMap.put("inventory_step_too_long_password",  "&4Your password is too long to input, please input a better one!");
            langMap.put("inventory_step_set_cannot_set",  "&4This chest do not allow the password.");
            langMap.put("inventory_step_set_wrong_already",  "&4Wrong! This chest have set the password already! You cannot set the password repeatedly.");
            langMap.put("inventory_step_set_success",  "&2Success! The password is @password , keep it carefully!");
            langMap.put("inventory_step_open_by_op_permission",  "&2You open the chest by OP permission.");
            langMap.put("inventory_step_open_wrong_wrong_password",  "&4Wrong! Password wrong! Please try again!");
            langMap.put("inventory_step_remove_successfully",  "&2Clearing the password successfully!");
            langMap.put("inventory_step_remove_wrong_wrong_password",  "&4Wrong! Password Wrong so you cannot clear the password.");
            langMap.put("block_step_tip_open_right",  "&4This chest is locked so you cannot open it directly now. Please press SHIFT and RIGHT click it, input the correct password.");
            langMap.put("block_step_tip_break_right",  "&4This chest is locked so you cannot break it directly now. Please press SHIFT and LEFT click it, input the correct password.");
            langMap.put("block_step_tip_break_by_op_permission",  "&2You have broken the chest by OP permission.");

            LanguageCore core = new LanguageCore("default",langMap);
            return core;
        }else{
            return defaultCore;
        }
    }

    public static String get(String key){
        return get(key,false);
    }

    public static String get(String key, boolean b){
        String message = ChatColor.translateAlternateColorCodes('&',
                getUsingCore().get(key.toLowerCase()));
        return b ? get("tip_prefix",false)+message : message;
    }

    public static void loadData(){
        if(ChestPassword.plugin.getConfig().contains("lang")){
            String langName = ChestPassword.plugin.getConfig().getString("lang");
            try{
                DumperOptions yamlOptions = new DumperOptions(){
                    @Override
                    public void setAllowUnicode(boolean allowUnicode) {
                        super.setAllowUnicode(false);
                    }

                    @Override
                    public void setLineBreak(DumperOptions.LineBreak lineBreak) {
                        super.setLineBreak(DumperOptions.LineBreak.getPlatformLineBreak());
                    }
                };

                File langFile = new File(ChestPassword.plugin.getDataFolder(), "lang/"+langName+".yml");
                FileConfiguration config = YamlConfiguration.loadConfiguration(langFile);

                Field field_yamlOptions = YamlConfiguration.class.getDeclaredField("yamlOptions");
                field_yamlOptions.setAccessible(true);
                yamlOptions.setLineBreak(DumperOptions.LineBreak.getPlatformLineBreak());
                field_yamlOptions.set(config, yamlOptions);

                Map<String,String> langMap = new HashMap<>();
                for(String key:config.getKeys(false)){
                    String info = config.getString(key);
                    langMap.put(key.toLowerCase(),info);
                }
                LanguageCore core = new LanguageCore(langName,langMap);
                setUsingCore(core);
            }catch (Exception e){
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"ChestPassword cannot load the language data named "+langName+" !");
            }
        }else{
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"ChestPassword cannot load the language data! The system will load the Default Language.");
            setUsingCore(getDefaultCore());
        }
    }
}
