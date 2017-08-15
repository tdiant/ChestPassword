package tdiant.bukkit.chestpassword.api;

import org.bukkit.block.Block;
import tdiant.bukkit.chestpassword.ChestPassword;
import tdiant.bukkit.chestpassword.manager.ChestPasswordManager;

/**
 * Created by tdiant on 2017/8/15.
 */
public class ChestPasswordHook {
    public static ChestPassword getPlugin() {
        return ChestPassword.plugin;
    }

    public static boolean isChestPasswordChest(Block b){
        return (ChestPasswordManager.isChest(b)&&ChestPasswordManager.isChestSetPassword(b))
                ? true : false;
    }
}
