package tdiant.bukkit.chestpassword.util.base;

import org.bukkit.block.Block;

/**
 * Created by tdiant on 2017/7/26.
 */
public class PlayerStateCard {
    private StateType st=null;
    private Block b=null;

    public PlayerStateCard(StateType st, Block b){
        this.st=st;
        this.b=b;
    }

    public StateType getStateType() {
        return st;
    }

    public void setStateType(StateType st) {
        this.st = st;
    }

    public Block getBlock() {
        return b;
    }

    public void setBlock(Block b) {
        this.b = b;
    }

    public enum StateType{
        Setting, Opening, Removing
    }
}
