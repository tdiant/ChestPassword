package tdiant.bukkit.chestpassword.listener;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.DoubleChestInventory;
import org.bukkit.inventory.ItemStack;
import tdiant.bukkit.chestpassword.gui.PasswordInputGui;
import tdiant.bukkit.chestpassword.manager.ChestPasswordManager;
import tdiant.bukkit.chestpassword.manager.ConfigManager;
import tdiant.bukkit.chestpassword.manager.LanguageManager;
import tdiant.bukkit.chestpassword.manager.PlayerInputManager;
import tdiant.bukkit.chestpassword.util.GUINumberTool;
import tdiant.bukkit.chestpassword.util.base.PlayerStateCard;
import tdiant.bukkit.chestpassword.util.connector.ConnectWithOtherLockChestPlugin;

/**
 * Created by tdiant on 2017/7/26.
 */
public class InventoryListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onInventoryOpen(InventoryOpenEvent e){
        if(e.getInventory().getTitle().contains(PasswordInputGui.TITLE)) {
            Player p=(Player)e.getPlayer();
            if(!BlockListener.canPlayerUse(p)) {
                p.sendMessage(ChatColor.RED+ LanguageManager.get("inventory_step_no_permission",true));
                e.setCancelled(true);
                return;
            }

            if(e.getInventory().getTitle().equalsIgnoreCase(PasswordInputGui.TITLE))
                PlayerInputManager.clearPlayerInput(e.getPlayer().getName());
            if(PlayerInputManager.getPlayerStateCard(e.getPlayer().getName())==null){
                e.getPlayer().closeInventory();
            }
        }

        /*if(e.getInventory().getType()== InventoryType.CHEST && !e.getInventory().getTitle().equalsIgnoreCase(PasswordInputGui.TITLE)){
            if(PlayerInputManager.getPlayerStateCard(e.getPlayer().getName())!=null
                    && PlayerInputManager.getPlayerStateCard(e.getPlayer().getName()).getStateType()== PlayerStateCard.StateType.Setting){
                e.setCancelled(true);
                e.getPlayer().closeInventory();
                PasswordInputGui.show(Bukkit.getPlayerExact(e.getPlayer().getName()));
            }
        }*/
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent e){
        if(e.getRawSlot()<0)
            return;

        if(e.getClickedInventory().getTitle().contains(PasswordInputGui.TITLE))
            e.setCancelled(true);
        else
            return;

        if(!(e.getWhoClicked() instanceof Player))
            return;

        ItemStack item=e.getCurrentItem();
        int num= GUINumberTool.getButtonNum(item);
        Player p = (Player) e.getWhoClicked();

        if(!BlockListener.canPlayerUse(p)) {
            p.sendMessage(ChatColor.RED+ LanguageManager.get("inventory_step_no_permission",true));
            e.setCancelled(true);
            p.closeInventory();
            return;
        }

        if(num>=0 && num<=9) {
            if (PlayerInputManager.isPlayerInput(p)) {
                PlayerInputManager.setPlayerInput(p, PlayerInputManager.getPlayerInput(p) * 10 + num);
            } else {
                PlayerInputManager.setPlayerInput(p, num);
            }
            if(num==0 && (!PlayerInputManager.isPlayerInput(p) | PlayerInputManager.getPlayerInput(p)==0)){ //首位支持输入0
                PlayerInputManager.setPlayerInputZero(p,PlayerInputManager.getPlayerInputZero(p)+1);
            }
            p.closeInventory();
            PasswordInputGui.show(p,233,233);
            if (PlayerInputManager.getPlayerInput(p) > 999999999) {
                p.closeInventory();
                p.sendMessage(ChatColor.RED + LanguageManager.get("inventory_step_too_long_password",true));
                PlayerInputManager.clearPlayerInput(p);
                return;
            }
        }else{
            if(PasswordInputGui.isSubmitButton(item)){
                p.closeInventory();
                //检查提交请求是否合理
                if(PlayerInputManager.getPlayerStateCard(p)==null){
                    p.closeInventory();
                    return;
                }
                PlayerStateCard psc=PlayerInputManager.getPlayerStateCard(p);

                //提交方块新密码信息
                if(psc.getStateType()== PlayerStateCard.StateType.Setting){
                    Block b=psc.getBlock();
                    //兼容性检测
                    if(ConnectWithOtherLockChestPlugin.isNeedCancelled(b)){
                        p.sendMessage(ChatColor.RED+LanguageManager.get("inventory_step_set_cannot_set",true));
                        PlayerInputManager.clearPlayerInput(p);
                        PlayerInputManager.clearPlayerState(p);
                        return;
                    }

                    if(ChestPasswordManager.isChestSetPassword(b)){
                        p.sendMessage(ChatColor.RED+LanguageManager.get("inventory_step_set_wrong_already",true));
                        PlayerInputManager.clearPlayerInput(p);
                        PlayerInputManager.clearPlayerState(p);
                        return;
                    }else {
                        ChestPasswordManager.setChestPassword(b, PlayerInputManager.getPlayerInput(p), PlayerInputManager.getPlayerInputZero(p));
                        String newPassword="";
                        if(PlayerInputManager.getPlayerInput(p)>0) newPassword=""+PlayerInputManager.getPlayerInput(p);
                        for(int i=1;i<=PlayerInputManager.getPlayerInputZero(p);i++) newPassword="0"+newPassword;
                        if(newPassword.trim().equalsIgnoreCase("")) newPassword=LanguageManager.get("gui_emptyPassword");
                        p.sendMessage(ChatColor.GREEN + LanguageManager.get("inventory_step_set_success",true).replace("@password",newPassword));
                        PlayerInputManager.clearPlayerInput(p);
                        PlayerInputManager.clearPlayerState(p);
                    }
                }

                //打开方块请求
                if(psc.getStateType()== PlayerStateCard.StateType.Opening){
                    Block b=psc.getBlock();
                    if(ChestPasswordManager.isTrulyChestPasswordInput(b,PlayerInputManager.getPlayerInput(p),PlayerInputManager.getPlayerInputZero(p))){
                        if(ChestPasswordManager.isChest(b)){
                            Chest chest=(Chest)b.getState();
                            if(chest.getBlockInventory().getHolder().getInventory() instanceof DoubleChestInventory){
                                p.openInventory(chest.getBlockInventory().getHolder().getInventory());
                            }else {
                                p.openInventory(chest.getBlockInventory());
                            }
                        }
                        PlayerInputManager.clearPlayerInput(p);
                        PlayerInputManager.clearPlayerState(p);
                        return;
                    }else{
                        if(p.isOp()){
                            if(ConfigManager.isInputOpGodPassword(p)){
                                if(ChestPasswordManager.isChest(b)){
                                    Chest chest=(Chest)b.getState();
                                    if(chest.getBlockInventory().getHolder() instanceof DoubleChest){
                                        p.openInventory(chest.getBlockInventory().getHolder().getInventory());
                                    }else {
                                        p.openInventory(chest.getBlockInventory());
                                    }
                                }
                                PlayerInputManager.clearPlayerInput(p);
                                PlayerInputManager.clearPlayerState(p);
                                p.sendMessage(ChatColor.GREEN+LanguageManager.get("inventory_step_open_by_op_permission",true));
                                return;
                            }
                        }
                        p.sendMessage(ChatColor.RED+LanguageManager.get("inventory_step_open_wrong_wrong_password",true));
                        PlayerInputManager.clearPlayerInput(p);
                        PlayerInputManager.clearPlayerState(p);
                        return;
                    }
                }

                //清除密码请求
                if(psc.getStateType()== PlayerStateCard.StateType.Removing){
                    Block b=psc.getBlock();
                    if(!ChestPasswordManager.isChestSetPassword(b)) return;
                    if(ChestPasswordManager.isTrulyChestPasswordInput(b,PlayerInputManager.getPlayerInput(p),PlayerInputManager.getPlayerInputZero(p))){
                        ChestPasswordManager.removeChestPassword(b);
                        p.sendMessage(ChatColor.GREEN+LanguageManager.get("inventory_step_remove_successfully",true));
                        PlayerInputManager.clearPlayerInput(p);
                        PlayerInputManager.clearPlayerState(p);
                    }else{
                        //OP专用万能密码
                        if(p.isOp()){
                            if(ConfigManager.isInputOpGodPassword(p)){
                                ChestPasswordManager.removeChestPassword(b);
                                p.sendMessage(ChatColor.GREEN+"您使用了OP权限暴力清除了箱子的密码。");
                                PlayerInputManager.clearPlayerInput(p);
                                PlayerInputManager.clearPlayerState(p);
                                return;
                            }
                        }
                        p.sendMessage(ChatColor.RED+LanguageManager.get("inventory_step_remove_wrong_wrong_password",true));
                        PlayerInputManager.clearPlayerInput(p);
                        PlayerInputManager.clearPlayerState(p);
                    }
                }
            }else if(PasswordInputGui.isBackspaceButton(item)){
                if(PlayerInputManager.getPlayerInput(p)>0) {
                    if (PlayerInputManager.isPlayerInput(p)) {
                        if (PlayerInputManager.getPlayerInput(p) > 0)
                            PlayerInputManager.setPlayerInput(p, PlayerInputManager.getPlayerInput(p) / 10);
                    } else {
                        PlayerInputManager.setPlayerInput(p, 0);
                    }
                }else{
                    if (PlayerInputManager.isPlayerInputZero(p)) {
                        if (PlayerInputManager.getPlayerInputZero(p) > 0)
                            PlayerInputManager.setPlayerInputZero(p, PlayerInputManager.getPlayerInputZero(p)-1);
                    } else {
                        PlayerInputManager.setPlayerInputZero(p, 0);
                    }
                }
                if(PlayerInputManager.isPlayerInput(p) || PlayerInputManager.isPlayerInputZero(p))
                    PasswordInputGui.show(p,233,233);
                else {
                    p.closeInventory();
                    PlayerInputManager.clearPlayerInput(p);
                    PlayerInputManager.clearPlayerState(p);
                }
            }else{
                return;
            }
        }
    }
}
