package jp.msfblue1.PlayerInfo.Tools;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PrefixAdder {

	public static void setLoggerInfo(String msg){
		Bukkit.getLogger().info("[PlayerInfo] " + msg);
	}

	public static void setLoggerWarn(String msg){
		Bukkit.getLogger().warning("[PlayerInfo] " + msg);
	}

	public static void sendMessage(Player To, String msg){
		msg = ChatColor.translateAlternateColorCodes('&', msg);
		To.sendMessage(ChatColor.AQUA + "[PlayerInfo] " + ChatColor.RESET + msg);
	}

	public static void sendMessage(Player To, ChatColor color, String msg){
		To.sendMessage(color + "[PlayerInfo] " + msg);
	}

	public static void sendMessage(CommandSender To, String msg){
		msg = ChatColor.translateAlternateColorCodes('&', msg);
		To.sendMessage(ChatColor.AQUA + "[PlayerInfo] " + ChatColor.RESET + msg);
	}

	public static void sendMessage(CommandSender To, ChatColor color, String msg){
		To.sendMessage(color + "[PlayerInfo] " + msg);
	}

}
