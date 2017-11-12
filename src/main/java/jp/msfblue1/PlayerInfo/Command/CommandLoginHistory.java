package jp.msfblue1.PlayerInfo.Command;

/**
 * Created by msfblue1 on 2017/11/12.
 */

import jp.msfblue1.PlayerInfo.Tools.PrefixAdder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
public class CommandLoginHistory implements Command{
    public void onCommands(CommandSender sender,String[] args){
        if(sender instanceof BlockCommandSender){
            sender.sendMessage(ChatColor.RED+"Disallow access from command block.");
            return;
        }
        if(sender instanceof Player){
            Player target = (Player)sender;
            if(target.hasPermission("advwhois.history")){
                PrefixAdder.sendMessage(sender,ChatColor.RED,"You don't have permissions.");
                return;
            }
        }
        List<OfflinePlayer> offlinePlayers = Arrays.asList(Bukkit.getOfflinePlayers());
        List<OfflinePlayer> afterPlayers = new ArrayList<>();
        List<Long> dates = new ArrayList<>();

        for (int i = 0; i < offlinePlayers.size(); i++) {
            dates.add(offlinePlayers.get(i).getLastPlayed());
        }

        Long[] dateArray = dates.toArray(new Long[dates.size()]);
        Arrays.sort(dateArray, Collections.reverseOrder());

        for (int i = 0; i < dateArray.length; i++) {
            Long date = dateArray[i];
            for (int j = 0; j < offlinePlayers.size(); j++) {
                OfflinePlayer player = offlinePlayers.get(j);
                if(date.equals(player.getLastPlayed())){
                    afterPlayers.add(player);
                }
            }
        }

        List<String> messages = new ArrayList<>();

        StringBuilder builder = new StringBuilder();

        builder.append(ChatColor.BLUE);
        builder.append("======最終接続リスト======");
        messages.add(builder.toString());

        builder = new StringBuilder();

        for (int i = 0; i < afterPlayers.size(); i++) {
            OfflinePlayer player = afterPlayers.get(i);
            String time = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(player.getLastPlayed());
            builder.append(time);
            builder.append(" ");
            builder.append(ChatColor.GREEN);
            builder.append(player.getName());
            messages.add(builder.toString());
            builder = new StringBuilder();
        }

        for (int i = 0; i < messages.size(); i++) {
            sender.sendMessage(messages.get(i));
        }
    }
}
