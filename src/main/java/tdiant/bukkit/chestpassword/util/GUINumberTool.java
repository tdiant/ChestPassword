package tdiant.bukkit.chestpassword.util;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import tdiant.bukkit.chestpassword.manager.ConfigManager;

/**
 * Created by tdiant on 2017/8/15.
 */
public class GUINumberTool {
    public static String getNumberSkullJson(int num,int type){
        if(ConfigManager.getButtonMaterial()== Material.STONE){
            type=2;
        }
        if(type==1) {
            switch (num) {
                case 1:
                    return "{display:{Name:\"1\"},SkullOwner:{Id:\"00684a88-5cc8-4713-9e91-7b1906e67580\",Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzFiYzJiY2ZiMmJkMzc1OWU2YjFlODZmYzdhNzk1ODVlMTEyN2RkMzU3ZmMyMDI4OTNmOWRlMjQxYmM5ZTUzMCJ9fX0=\"}]}}}";
                case 2:
                    return "{display:{Name:\"2\"},SkullOwner:{Id:\"f7218833-aceb-4d3e-a1bc-a334be09c375\",Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGNkOWVlZWU4ODM0Njg4ODFkODM4NDhhNDZiZjMwMTI0ODVjMjNmNzU3NTNiOGZiZTg0ODczNDE0MTk4NDcifX19\"}]}}}";
                case 3:
                    return "{display:{Name:\"3\"},SkullOwner:{Id:\"870c6ce6-78b5-4e09-8745-bd96d616e516\",Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWQ0ZWFlMTM5MzM4NjBhNmRmNWU4ZTk1NTY5M2I5NWE4YzNiMTVjMzZiOGI1ODc1MzJhYzA5OTZiYzM3ZTUifX19\"}]}}}";
                case 4:
                    return "{display:{Name:\"4\"},SkullOwner:{Id:\"d531b607-3d92-4760-b19f-b64d51da0fa5\",Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDJlNzhmYjIyNDI0MjMyZGMyN2I4MWZiY2I0N2ZkMjRjMWFjZjc2MDk4NzUzZjJkOWMyODU5ODI4N2RiNSJ9fX0=\"}]}}}";
                case 5:
                    return "{display:{Name:\"5\"},SkullOwner:{Id:\"4aaa0af9-ffde-4f5a-ad06-112dffbade0c\",Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmQ1N2UzYmM4OGE2NTczMGUzMWExNGUzZjQxZTAzOGE1ZWNmMDg5MWE2YzI0MzY0M2I4ZTU0NzZhZTIifX19\"}]}}}";
                case 6:
                    return "{display:{Name:\"6\"},SkullOwner:{Id:\"58a05887-3473-4c87-8506-2acf877d7ff1\",Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzM0YjM2ZGU3ZDY3OWI4YmJjNzI1NDk5YWRhZWYyNGRjNTE4ZjVhZTIzZTcxNjk4MWUxZGNjNmIyNzIwYWIifX19\"}]}}}";
                case 7:
                    return "{display:{Name:\"7\"},SkullOwner:{Id:\"23378bd2-28e5-4d7e-8d39-621b732e1f49\",Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmRiNmViMjVkMWZhYWJlMzBjZjQ0NGRjNjMzYjU4MzI0NzVlMzgwOTZiN2UyNDAyYTNlYzQ3NmRkN2I5In19fQ==\"}]}}}";
                case 8:
                    return "{display:{Name:\"8\"},SkullOwner:{Id:\"19c144be-a435-42a4-9503-83bcd8a7fa70\",Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTkxOTQ5NzNhM2YxN2JkYTk5NzhlZDYyNzMzODM5OTcyMjI3NzRiNDU0Mzg2YzgzMTljMDRmMWY0Zjc0YzJiNSJ9fX0=\"}]}}}";
                case 9:
                    return "{display:{Name:\"9\"},SkullOwner:{Id:\"c7cad554-93b2-4176-a4ba-8de42aa9c9f2\",Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTY3Y2FmNzU5MWIzOGUxMjVhODAxN2Q1OGNmYzY0MzNiZmFmODRjZDQ5OWQ3OTRmNDFkMTBiZmYyZTViODQwIn19fQ==\"}]}}}";
                case 0:
                    return "{display:{Name:\"0\"},SkullOwner:{Id:\"2298cff2-c1a5-4278-a277-8d8661afe1c6\",Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMGViZTdlNTIxNTE2OWE2OTlhY2M2Y2VmYTdiNzNmZGIxMDhkYjg3YmI2ZGFlMjg0OWZiZTI0NzE0YjI3In19fQ==\"}]}}}";
            }
        }else if(type==2){
            switch (num){
                case 1:
                    return "{display:{Name:\"1\"},SkullOwner:{Id:\"00684a88-5cc8-4713-9e91-7b1906e67580\",Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzFiYzJiY2ZiMmJkMzc1OWU2YjFlODZmYzdhNzk1ODVlMTEyN2RkMzU3ZmMyMDI4OTNmOWRlMjQxYmM5ZTUzMCJ9fX0=\"}]}}}";
                case 2:
                    return "{display:{Name:\"2\"},SkullOwner:{Id:\"f7218833-aceb-4d3e-a1bc-a334be09c375\",Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGNkOWVlZWU4ODM0Njg4ODFkODM4NDhhNDZiZjMwMTI0ODVjMjNmNzU3NTNiOGZiZTg0ODczNDE0MTk4NDcifX19\"}]}}}";
                case 3:
                    return "{display:{Name:\"3\"},SkullOwner:{Id:\"870c6ce6-78b5-4e09-8745-bd96d616e516\",Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWQ0ZWFlMTM5MzM4NjBhNmRmNWU4ZTk1NTY5M2I5NWE4YzNiMTVjMzZiOGI1ODc1MzJhYzA5OTZiYzM3ZTUifX19\"}]}}}";
                case 4:
                    return "{display:{Name:\"4\"},SkullOwner:{Id:\"d531b607-3d92-4760-b19f-b64d51da0fa5\",Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDJlNzhmYjIyNDI0MjMyZGMyN2I4MWZiY2I0N2ZkMjRjMWFjZjc2MDk4NzUzZjJkOWMyODU5ODI4N2RiNSJ9fX0=\"}]}}}";
                case 5:
                    return "{display:{Name:\"5\"},SkullOwner:{Id:\"4aaa0af9-ffde-4f5a-ad06-112dffbade0c\",Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmQ1N2UzYmM4OGE2NTczMGUzMWExNGUzZjQxZTAzOGE1ZWNmMDg5MWE2YzI0MzY0M2I4ZTU0NzZhZTIifX19\"}]}}}";
                case 6:
                    return "{display:{Name:\"6\"},SkullOwner:{Id:\"58a05887-3473-4c87-8506-2acf877d7ff1\",Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzM0YjM2ZGU3ZDY3OWI4YmJjNzI1NDk5YWRhZWYyNGRjNTE4ZjVhZTIzZTcxNjk4MWUxZGNjNmIyNzIwYWIifX19\"}]}}}";
                case 7:
                    return "{display:{Name:\"7\"},SkullOwner:{Id:\"23378bd2-28e5-4d7e-8d39-621b732e1f49\",Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmRiNmViMjVkMWZhYWJlMzBjZjQ0NGRjNjMzYjU4MzI0NzVlMzgwOTZiN2UyNDAyYTNlYzQ3NmRkN2I5In19fQ==\"}]}}}";
                case 8:
                    return "{display:{Name:\"8\"},SkullOwner:{Id:\"19c144be-a435-42a4-9503-83bcd8a7fa70\",Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTkxOTQ5NzNhM2YxN2JkYTk5NzhlZDYyNzMzODM5OTcyMjI3NzRiNDU0Mzg2YzgzMTljMDRmMWY0Zjc0YzJiNSJ9fX0=\"}]}}}";
                case 9:
                    return "{display:{Name:\"9\"},SkullOwner:{Id:\"8b867e70-124a-4a4f-9782-3b5581ab709b\",Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTE5MjhlMWJmZDg2YTliNzkzOTdjNGNiNGI2NWVmOTlhZjQ5YjdkNWY3OTU3YWQ2MmMwYzY5OWE2MjJjZmJlIn19fQ==\"}]}}}";
                case 0:
                    return "{display:{Name:\"0\"},SkullOwner:{Id:\"a58ddb02-267b-4122-b8cc-5a6fbef9db04\",Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTVhMjI0ODA3NjkzOTc4ZWQ4MzQzNTVmOWU1MTQ1ZjljNTZlZjY4Y2Y2ZjJjOWUxNzM0YTQ2ZTI0NmFhZTEifX19\"}]}}}";
            }
        }
        return null;
    }

    public static int getButtonNum(ItemStack item){
        if(!item.hasItemMeta() || !item.getItemMeta().hasDisplayName())
            return -1;
        String name= ChatColor.stripColor(item.getItemMeta().getDisplayName());
        if(name.contains("1")) return 1;
        if(name.contains("2")) return 2;
        if(name.contains("3")) return 3;
        if(name.contains("4")) return 4;
        if(name.contains("5")) return 5;
        if(name.contains("6")) return 6;
        if(name.contains("7")) return 7;
        if(name.contains("8")) return 8;
        if(name.contains("9")) return 9;
        if(name.contains("0")) return 0;
        return -1;
    }
}
