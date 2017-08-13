package tdiant.bukkit.chestpassword.listener;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import tdiant.bukkit.chestpassword.gui.PasswordInputGui;
import tdiant.bukkit.chestpassword.manager.ChestPasswordManager;
import tdiant.bukkit.chestpassword.manager.ConfigManager;
import tdiant.bukkit.chestpassword.manager.PlayerInputManager;
import tdiant.bukkit.chestpassword.util.base.PlayerStateCard;

import java.lang.reflect.Field;

/**
 * Created by tdiant on 2017/7/26.
 */
public class BlockListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent e) throws NoSuchFieldException, IllegalAccessException {
        if(!canPlayerUse(e.getPlayer())) {
            e.getPlayer().sendMessage(ChatColor.RED+"您没有权限使用箱子锁！");
            e.setCancelled(true);
            return;
        }
        onPlayerInteract(e,false);
    }

    public void onPlayerInteract(PlayerInteractEvent e,boolean bool) throws NoSuchFieldException, IllegalAccessException {
        Block b=e.getClickedBlock();
        Player p=e.getPlayer();

        if(!canPlayerUse(p)) {
            p.sendMessage(ChatColor.RED+"您没有权限使用箱子锁！");
            e.setCancelled(true);
            return;
        }

        if(b==null)
            return;

        //把不是箱子而设置的密码删除掉
        if(!ChestPasswordManager.isChest(b)){
            if(ChestPasswordManager.isChestSetPassword(b)){
                ChestPasswordManager.removeChestPassword(b);
            }
            return;
        }

        if(!(e.getAction()==Action.LEFT_CLICK_BLOCK || e.getAction()==Action.RIGHT_CLICK_BLOCK))
            return;

        //如果设置了密码
        if(ChestPasswordManager.isChestSetPassword(b)){
            if(p.isOp() && ConfigManager.isOPFreedomMode() && e.getAction()==Action.LEFT_CLICK_BLOCK)
                return;

            if(!p.isSneaking()) { //潜行模式限定
                if(e.getAction()==Action.RIGHT_CLICK_BLOCK)
                    p.sendMessage(ChatColor.RED + "这个箱子已经上锁，不能直接打开！请按SHIFT键后右键箱子，输入密码打开！");
                if(e.getAction()==Action.LEFT_CLICK_BLOCK)
                    p.sendMessage(ChatColor.RED + "这个箱子已经上锁，不能直接拆除！请先按SHIFT键后左键箱子，输入密码以清除密码！");
                e.setCancelled(true);
                return;
            }
            //打开模式
            if(e.getAction()==Action.RIGHT_CLICK_BLOCK){
                PlayerInputManager.pushPlayerState(p, PlayerStateCard.StateType.Opening, b);
                PlayerInputManager.clearPlayerInput(p);
                PasswordInputGui.show(p);
                e.setCancelled(true);
                return;
            }
            //清除模式
            if(e.getAction()==Action.LEFT_CLICK_BLOCK){
                PlayerInputManager.pushPlayerState(p, PlayerStateCard.StateType.Removing, b);
                PlayerInputManager.clearPlayerInput(p);
                PasswordInputGui.show(p);
                e.setCancelled(true);
                return;
            }
            e.setCancelled(true);
        } else { //如果没有设置密码
            if(bool==false) {
                Block nearChestBlock = isChestAround(b);
                if (nearChestBlock != null && ChestPasswordManager.isChestSetPassword(nearChestBlock)) {

                    Field clickedBlockField = PlayerInteractEvent.class.getDeclaredField("blockClicked");
                    clickedBlockField.setAccessible(true);
                    clickedBlockField.set(e,nearChestBlock);

                    onPlayerInteract(e, true);
                    return;
                }
            }

            if(!p.isSneaking()) return; //潜行模式限定
            //设置密码模式
            if(e.getAction()==Action.RIGHT_CLICK_BLOCK){
                PlayerInputManager.pushPlayerState(p, PlayerStateCard.StateType.Setting, b);
                PlayerInputManager.clearPlayerInput(p);
                PasswordInputGui.show(p);
                e.setCancelled(true);
                return;
            }
        }
    }


    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onBlockBreak(BlockBreakEvent e){
        Block b=e.getBlock();
        Player p=e.getPlayer();

        if(!canPlayerUse(p)) {
            p.sendMessage(ChatColor.RED+"您没有权限使用箱子锁！");
            e.setCancelled(true);
            return;
        }

        if(!(b instanceof Block)) return;

        if(ChestPasswordManager.isChestSetPassword(b)){
            if(p.isOp() && ConfigManager.isOPFreedomMode()){
                ChestPasswordManager.removeChestPassword(b);
                p.sendMessage(ChatColor.GREEN+"您已用OP权限暴力拆除了上锁的箱子。");
                return;
            }
            p.sendMessage(ChatColor.RED+"你不能拆除设有密码的箱子！请先按住SHIFT键左键箱子，清除密码！");
            e.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent e){
        Block b=e.getBlockPlaced();
        if(ChestPasswordManager.isChest(b)){
            if(ChestPasswordManager.isChestSetPassword(b)){
                ChestPasswordManager.removeChestPassword(b);
            }
        }
    }

    private Block isChestAround(Block b){
        if(!ChestPasswordManager.isChest(b)) return null;
        Location l=b.getLocation().clone();
        l.setX(l.getX()-1);
        if(l.getBlock().getType()==b.getType()) return l.getBlock();

        l=b.getLocation().clone();
        l.setX(l.getX()+1);
        if(l.getBlock().getType()==b.getType()) return l.getBlock();

        l=b.getLocation().clone();
        l.setZ(l.getZ()-1);
        if(l.getBlock().getType()==b.getType()) return l.getBlock();

        l=b.getLocation().clone();
        l.setZ(l.getZ()+1);
        if(l.getBlock().getType()==b.getType()) return l.getBlock();

        return null;
    }

    public static boolean canPlayerUse(Player p){
        return p.hasPermission("chestpassword.use");
    }
}
