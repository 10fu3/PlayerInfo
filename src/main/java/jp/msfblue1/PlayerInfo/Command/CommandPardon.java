package jp.msfblue1.PlayerInfo.Command;

import jp.msfblue1.PlayerInfo.PlayerInfo;
import jp.msfblue1.PlayerInfo.Tools.PrefixAdder;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 * Created by msfblue1 on 2017/11/12.
 */
public class CommandPardon implements Command{
    @Override
    public void onCommands(CommandSender sender, String[] args) {
        if(args.length == 3 && args[1].equalsIgnoreCase("pardon")){
            PlayerInfo.getBanManager().pardon(args[2]);
            PrefixAdder.sendMessage(sender, ChatColor.GREEN, "成功しました");
        }else{
            MainCommand.sendHelp(sender);
        }
    }
}
