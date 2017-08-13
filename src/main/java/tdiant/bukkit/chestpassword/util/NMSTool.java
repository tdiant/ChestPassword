package tdiant.bukkit.chestpassword.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import tdiant.bukkit.chestpassword.ChestPassword;

import java.lang.reflect.Method;

/**
 * Created by tdiant on 2017/7/28.
 */
public class NMSTool {
    Class<?> craftItemStack;
    Class<?> itemStack;
    Class<?> mojangsonParse;
    Class<?> nbtTagCompound;
    Class<?> nbtTagBase;

    Method m_asNMSCopy;
    Method m_asBukkitCopy;
    Method m_parse;
    Method m_setTag;

    public NMSTool(){
        try {
            //Classes
            craftItemStack = Class.forName("org.bukkit.craftbukkit." + getVersion() + ".inventory.CraftItemStack");
            itemStack = Class.forName("net.minecraft.server." + getVersion() + ".ItemStack");
            mojangsonParse = Class.forName("net.minecraft.server." + getVersion() + ".MojangsonParser");
            nbtTagCompound = Class.forName("net.minecraft.server." + getVersion() + ".NBTTagCompound");
            nbtTagBase = Class.forName("net.minecraft.server." + getVersion() + ".NBTBase");
            //Methods
            m_asNMSCopy = craftItemStack.getMethod("asNMSCopy", ItemStack.class);
            m_asBukkitCopy = craftItemStack.getMethod("asBukkitCopy", itemStack);
            m_parse = mojangsonParse.getMethod("parse", String.class);
            m_setTag = itemStack.getMethod("setTag", nbtTagCompound);
        } catch (Exception e) {
            e.printStackTrace();
            ChestPassword.plugin.getServer().getConsoleSender().sendMessage(ChatColor.RED+"ChestPassword 无法加载 NMS，请不要选择 头颅 按钮模式");
        }
    }

    public String getVersion(){
        try {
            return Bukkit.getServer().getClass().getPackage().getName().replace("org.bukkit.craftbukkit.","");
        }catch (Exception e){
            return null;
        }
    }

    public ItemStack getSkullItem(String json){
        ItemStack returnBack=new ItemStack(Material.SKULL_ITEM);
        returnBack.setDurability((short)3); //人头模式

        try{
            Object nms_item = m_asNMSCopy.invoke(null, returnBack);
            Object nbt_data = m_parse.invoke(null, json);
            m_setTag.invoke(nms_item, nbt_data);
            returnBack= (ItemStack) m_asBukkitCopy.invoke(null,nms_item);
        }catch(Exception e){
            e.printStackTrace();
        }

        return returnBack;
    }
}
