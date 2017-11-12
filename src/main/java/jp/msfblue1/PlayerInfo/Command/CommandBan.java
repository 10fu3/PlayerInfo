package jp.msfblue1.PlayerInfo.Command;

import jp.msfblue1.PlayerInfo.PlayerInfo;
import jp.msfblue1.PlayerInfo.Tools.PrefixAdder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by msfblue1 on 2017/11/12.
 */
public class CommandBan implements Command{
    @Override
    public void onCommands(CommandSender sender, String[] args) {
        if(args.length == 2){
            Player player = Bukkit.getPlayer(args[1]);
            if(player == null){
                PrefixAdder.sendMessage(sender, ChatColor.YELLOW, "プレーヤーが存在しないため、ホスト名として登録しました。");
                PlayerInfo.getBanManager().addBan(args[1]);
                PrefixAdder.sendMessage(sender, ChatColor.GREEN, "成功しました");
            }else{
                PlayerInfo.getBanManager().addBan(player.getAddress().getHostName());
                player.kickPlayer("You have been banned.");
                PrefixAdder.sendMessage(sender, ChatColor.GREEN, "成功しました");
            }
        }else{
            MainCommand.sendHelp(sender);
        }
    }
}
