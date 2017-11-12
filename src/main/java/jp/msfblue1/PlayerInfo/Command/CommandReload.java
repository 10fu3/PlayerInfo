package jp.msfblue1.PlayerInfo.Command;

import jp.msfblue1.PlayerInfo.PlayerInfo;
import jp.msfblue1.PlayerInfo.Tools.PrefixAdder;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 * Created by msfblue1 on 2017/11/12.
 */
public class CommandReload implements Command{
    @Override
    public void onCommands(CommandSender sender, String[] args) {
        if(sender.hasPermission("advwhois.reload")){
            PlayerInfo.reloadConf();
            PrefixAdder.sendMessage(sender, ChatColor.GREEN, "成功しました");
        } else {
            PrefixAdder.sendMessage(sender, ChatColor.RED, "You don't have Permission.");
        }
    }
}
