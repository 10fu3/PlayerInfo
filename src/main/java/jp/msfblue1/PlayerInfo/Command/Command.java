package jp.msfblue1.PlayerInfo.Command;

import org.bukkit.command.CommandSender;

/**
 * Created by msfblue1 on 2017/11/04.
 */
public interface Command {
    default void onCommands(CommandSender sender, String[] args){}
}
