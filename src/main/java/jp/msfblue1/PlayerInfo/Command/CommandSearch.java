package jp.msfblue1.PlayerInfo.Command;

import jp.msfblue1.PlayerInfo.PlayerInfo;
import jp.msfblue1.PlayerInfo.Utils.Display;
import org.bukkit.command.CommandSender;

/**
 * Created by msfblue1 on 2017/11/12.
 */
public class CommandSearch implements Command{
    @Override
    public void onCommands(CommandSender sender, String[] args) {
        if(args.length == 2){
            PlayerInfo.taskAsynchronously(()->{
                Display.sendWhoisInfo(sender, args[1],false);
            });
        }else if(args.length == 3 && args[2].equalsIgnoreCase("-a")){
            PlayerInfo.taskAsynchronously(()->{
                Display.sendWhoisInfo(sender, args[1],true);
            });
        }else{
            MainCommand.sendHelp(sender);
        }
    }
}
