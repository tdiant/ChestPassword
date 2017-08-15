package tdiant.bukkit.chestpassword.util.base;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import tdiant.bukkit.chestpassword.manager.LanguageManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tdiant on 2017/8/15.
 */
public class LanguageCore {
    private String langName = "default";
    private Map<String, String> langMap = new HashMap<>();

    public LanguageCore(){}

    public LanguageCore(String name,Map<String,String> map){
        this.langName = name;
        this.langMap.putAll(map);
    }

    public Map<String, String> getLangMap() {
        return langMap;
    }

    public void setLangMap(Map<String, String> langMap) {
        this.langMap = langMap;
    }

    public void put(String key, String info){
        langMap.put(key.toLowerCase(),info);
    }

    public String get(String key){
        key = key.toLowerCase();
        if(langMap.containsKey(key))
            return langMap.get(key);
        for(String mapKey:langMap.keySet()) {
            if (mapKey.equalsIgnoreCase(key)) {
                this.put(key,langMap.get(mapKey));
                return langMap.get(mapKey);
            }else if(mapKey.trim().replace(" ","").equalsIgnoreCase(key)){
                this.put(key,langMap.get(mapKey));
                return langMap.get(mapKey);
            }
        }
        return null;
    }

    public void checkAndFix(){
        LanguageCore defaultCore = LanguageManager.getDefaultCore();
        List<String> defaultList = new ArrayList<>();
        for(String key:defaultCore.getLangMap().keySet()){
            if(this.get(key)==null){
                defaultList.add(key.toLowerCase());
                this.put(key.toLowerCase(),defaultCore.get(key.toLowerCase()));
            }
        }
        if(defaultList.size()!=0) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "警告！ChestPassword 插件语言文件缺少如下条目");
            for (String defaultKey : defaultList)
                Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + " - " + defaultKey);
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "请您补齐！");
        }
    }

    public String getName() {
        return langName;
    }

    public void setName(String langName) {
        this.langName = langName;
    }
}
