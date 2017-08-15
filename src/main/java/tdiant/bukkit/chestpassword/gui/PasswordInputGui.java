package tdiant.bukkit.chestpassword.gui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import tdiant.bukkit.chestpassword.manager.ConfigManager;
import tdiant.bukkit.chestpassword.manager.LanguageManager;
import tdiant.bukkit.chestpassword.manager.PlayerInputManager;
import tdiant.bukkit.chestpassword.util.GUINumberTool;
import tdiant.bukkit.chestpassword.util.NMSTool;

/**
 * Created by tdiant on 2017/7/26.
 */
public class PasswordInputGui {
    public static final String TITLE=ChatColor.RED+"请输入密码";

    public static void show(Player p){
        show(p,-1,-1);
    }

    public static void show(Player p, int password, int zeroNum){
        Inventory inv;
        if(zeroNum>0||password>0) {
            String newPassword="";
            if(PlayerInputManager.getPlayerInput(p)>0) newPassword=""+PlayerInputManager.getPlayerInput(p);
            for(int i=1;i<=PlayerInputManager.getPlayerInputZero(p);i++) newPassword="0"+newPassword;
            if(newPassword.trim().equalsIgnoreCase("")) newPassword = LanguageManager.get("gui_emptyPassword");//newPassword="空密码";
            inv = Bukkit.createInventory(null, 4 * 9, TITLE + ChatColor.DARK_BLUE + "  您输入了：" + ChatColor.DARK_BLUE + ChatColor.BOLD + newPassword);
        }
        else {
            inv = Bukkit.createInventory(null, 4 * 9, TITLE);
        }

        for(int i=1;i<=3;i++) inv.setItem(i-1+1,getPasswordButtonItem(i));
        for(int i=4;i<=6;i++) inv.setItem(9-1+i-3+1,getPasswordButtonItem(i));
        for(int i=7;i<=9;i++) inv.setItem(18-1+i-6+1,getPasswordButtonItem(i));
        inv.setItem(28+1,getPasswordButtonItem(0));

        ItemStack item_submit=new ItemStack(Material.WOOL);
        ItemMeta im_submit=Bukkit.getItemFactory().getItemMeta(item_submit.getType());
        im_submit.setDisplayName(ChatColor.RED+LanguageManager.get("gui_button_submit"));//"确定");
        item_submit.setDurability((short) 14);
        item_submit.setItemMeta(im_submit);

        ItemStack item_backspace=new ItemStack(Material.WOOL);
        ItemMeta im_backspace=Bukkit.getItemFactory().getItemMeta(item_backspace.getType());
        im_backspace.setDisplayName(ChatColor.BLUE+LanguageManager.get("gui_button_backspace"));//"退位");
        item_backspace.setDurability((short) 11);
        item_backspace.setItemMeta(im_backspace);

        inv.setItem(17-1,item_submit);
        inv.setItem(26-1,item_backspace);

        p.openInventory(inv);
    }

    public static ItemStack getPasswordButtonItem(int num){
        if(ConfigManager.getButtonStyleMode()== ConfigManager.ButtonStyleMode.ONLY) {
            ItemStack item = new ItemStack(ConfigManager.getButtonMaterial());
            ItemMeta im = Bukkit.getItemFactory().getItemMeta(item.getType());
            im.setDisplayName(ChatColor.GREEN + "" + num);
            item.setItemMeta(im);
            return item;
        }else if(ConfigManager.getButtonStyleMode()== ConfigManager.ButtonStyleMode.AMOUNT){
            ItemStack item = new ItemStack(ConfigManager.getButtonMaterial());
            ItemMeta im = Bukkit.getItemFactory().getItemMeta(item.getType());
            im.setDisplayName(ChatColor.GREEN + "" + num);
            if(num!=0 && !(Bukkit.getVersion().contains("1.6")||Bukkit.getVersion().contains("1.7")||Bukkit.getVersion().contains("1.8")||Bukkit.getVersion().contains("1.9")))
                item.setAmount((short) num);
            item.setItemMeta(im);
            return item;
        }else if(ConfigManager.getButtonStyleMode()== ConfigManager.ButtonStyleMode.SKULL){
            //ItemStack item = new ItemStack(Material.SKULL_ITEM);
            ItemStack item=new NMSTool().getSkullItem(GUINumberTool.getNumberSkullJson(num,1));
            ItemMeta im=item.getItemMeta();
            im.setDisplayName(ChatColor.GREEN+""+num);
            /*SkullMeta sm = (SkullMeta)Bukkit.getItemFactory().getItemMeta(Material.SKULL_ITEM);
            sm.setDisplayName(ChatColor.GREEN + "" + num);
            sm.setOwner(Bukkit.getOfflinePlayer(UUID.fromString(getNumberSkullUUID(num))).getName());*/
            item.setItemMeta(im);
            return item;
        }
        return new ItemStack(Material.STONE);
    }

    public static boolean isSubmitButton(ItemStack item){
        if(!item.hasItemMeta() || !item.getItemMeta().hasDisplayName())
            return false;
        return
                item.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.RED+LanguageManager.get("gui_button_submit"))
                        ? true : false;
    }

    public static boolean isBackspaceButton(ItemStack item){
        if(!item.hasItemMeta() || !item.getItemMeta().hasDisplayName())
            return false;
        return
                item.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.BLUE+LanguageManager.get("gui_button_backspace"))
                        ? true : false;
    }
}
