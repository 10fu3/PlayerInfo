package jp.msfblue1.PlayerInfo.Utils;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

/**
 * Created by msfblue1 on 2017/11/09.
 */
public class OfflinePlayerGetter {
    public static OfflinePlayer getOPlayer(String Name){
        for (int i = 0; i < Bukkit.getOfflinePlayers().length; i++) {
            if(Bukkit.getOfflinePlayers()[i].getName().equalsIgnoreCase(Name)){
                return Bukkit.getOfflinePlayers()[i];
            }
        }
        return Bukkit.getOfflinePlayer(Name);
    }
}
