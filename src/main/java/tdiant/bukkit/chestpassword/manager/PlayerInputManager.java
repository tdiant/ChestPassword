package tdiant.bukkit.chestpassword.manager;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import tdiant.bukkit.chestpassword.util.base.PlayerStateCard;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tdiant on 2017/7/26.
 */
public class PlayerInputManager {
    private static Map<String, Integer> playerInputMap=new HashMap<>();
    private static Map<String, PlayerStateCard> playerStateCardMap=new HashMap<>();
    private static Map<String, Integer> playerInputZeroMap=new HashMap<>();

    public static int getPlayerInput(Player p){
        return getPlayerInput(p.getName().toLowerCase());
    }

    public static void clearPlayerInput(Player p){
        clearPlayerInput(p.getName().toLowerCase());
    }

    public static void setPlayerInput(Player p, int num){
        setPlayerInput(p.getName().toLowerCase(),num);
    }

    public static boolean isPlayerInput(Player p){
        return isPlayerInput(p.getName().toLowerCase());
    }

    public static int getPlayerInput(String playerName){
        playerName=playerName.toLowerCase();
        if(playerInputMap.containsKey(playerName))
            return playerInputMap.get(playerName);
        return 0;
    }

    public static void setPlayerInput(String playerName, int inputNum){
        playerName=playerName.toLowerCase();
        if(inputNum==0){
            playerInputMap.remove(playerName);
            return;
        }
        playerInputMap.put(playerName,inputNum);
    }

    public static void clearPlayerInput(String playerName){
        playerName=playerName.toLowerCase();
        playerInputMap.remove(playerName);
        playerInputZeroMap.remove(playerName);
    }

    public static boolean isPlayerInput(String playerName){
        playerName=playerName.toLowerCase();
        return getPlayerInput(playerName)<=0 ? false : true;
    }

    public static PlayerStateCard getPlayerStateCard(Player p){
        return getPlayerStateCard(p.getName().toLowerCase());
    }

    public static void pushPlayerState(Player p, PlayerStateCard.StateType st, Block b){
        pushPlayerState(p.getName().toLowerCase(),st,b);
    }

    public static void pushPlayerState(Player p, PlayerStateCard psc){
        pushPlayerState(p.getName().toLowerCase(),psc);
    }

    public static void clearPlayerState(Player p){
        clearPlayerInput(p.getName().toLowerCase());
    }

    public static void setPlayerInputZero(Player p,int num){
        setPlayerInputZero(p.getName().toLowerCase(),num);
    }

    public static int getPlayerInputZero(Player p){
        return getPlayerInputZero(p.getName().toLowerCase());
    }

    public static boolean isPlayerInputZero(Player p){
        return isPlayerInputZero(p.getName().toLowerCase());
    }

    public static PlayerStateCard getPlayerStateCard(String playerName){
        playerName=playerName.toLowerCase();
        return playerStateCardMap.containsKey(playerName)
                ? playerStateCardMap.get(playerName) : null ;
    }

    public static void pushPlayerState(String playerName, PlayerStateCard.StateType st, Block b){
        playerName=playerName.toLowerCase();
        playerStateCardMap.put(playerName,new PlayerStateCard(st,b));
        clearPlayerInput(playerName.toLowerCase());
    }

    public static void pushPlayerState(String playerName, PlayerStateCard psc){
        playerName=playerName.toLowerCase();
        playerStateCardMap.put(playerName,psc);
    }

    public static void clearPlayerState(String playerName){
        playerName=playerName.toLowerCase();
        playerStateCardMap.remove(playerName);
    }

    public static int getPlayerInputZero(String playerName){
        playerName=playerName.toLowerCase();
        if(playerInputZeroMap.containsKey(playerName))
            return playerInputZeroMap.get(playerName);
        return 0;
    }

    public static void setPlayerInputZero(String playerName, int num){
        playerName=playerName.toLowerCase();
        if(num==0){
            playerInputZeroMap.remove(playerName);
            return;
        }
        playerInputZeroMap.put(playerName,num);
    }

    public static boolean isPlayerInputZero(String playerName){
        playerName=playerName.toLowerCase();
        return playerInputZeroMap.containsKey(playerName);
    }

    public static Map<String, Integer> getPlayerInputZeroMap() {
        return playerInputZeroMap;
    }

    public static void setPlayerInputZeroMap(Map<String, Integer> playerInputZeroMap) {
        PlayerInputManager.playerInputZeroMap = playerInputZeroMap;
    }
}
