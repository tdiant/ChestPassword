package tdiant.bukkit.chestpassword.listener;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import tdiant.bukkit.chestpassword.manager.ChestPasswordManager;

/**
 * Created by tdiant on 2017/7/26.
 */
public class PlayerListener implements Listener {
    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onPlayerPlaceHopper(BlockPlaceEvent e){
        if(e.getBlock().getType() == Material.HOPPER || e.getBlock().getType() == Material.HOPPER_MINECART){
            Location loc = e.getBlock().getLocation().clone();
            loc.setY(loc.getY()+1);
            Block b=loc.getBlock();
            if(ChestPasswordManager.isChest(b) && ChestPasswordManager.isChestSetPassword(b)){
                String blockName = "这个方块";
                if(e.getBlock().getType()==Material.HOPPER) blockName="漏斗";
                if(e.getBlock().getType()==Material.HOPPER_MINECART) blockName="漏斗矿车";
                e.getPlayer().sendMessage(ChatColor.RED+"在上锁的箱子下放置 "+blockName+" 是不行的！");
                e.setCancelled(true);
            }
        }
    }
}
