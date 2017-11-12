package jp.msfblue1.PlayerInfo.Command;

import jp.msfblue1.PlayerInfo.Tools.PrefixAdder;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.Map;

public class MainCommand implements CommandExecutor {

	Map<String,Command> commands = new HashMap<>();

	public MainCommand(){
		commands.put("history",new CommandLoginHistory());
		commands.put("ban",new CommandBan());
		commands.put("pardon",new CommandPardon());
		commands.put("reload",new CommandReload());
		commands.put("search",new CommandSearch());
	}

	public static void sendHelp(CommandSender sender){
		PrefixAdder.sendMessage(sender, "/whoisps search <playerID> <-a>: プレイヤーの情報を表示します");
		PrefixAdder.sendMessage(sender, "/whoisps ban <hostname/playerID>: 指定したホスト名からの接続を拒否します");
		PrefixAdder.sendMessage(sender, "/whoisps ban pardon <hostname/playerID>: 指定したホスト名からの接続を拒否します");
		PrefixAdder.sendMessage(sender, "/whoisps reload: Configをリロードします");
	}

	@Override
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
		if (args.length == 0){
			sendHelp(sender);
			return true;
		} else if (args[0].equalsIgnoreCase("help") || !commands.containsKey(args[0])){
			sendHelp(sender);
			return true;
		}else{
			commands.get(args[0]).onCommands(sender,args);
			return true;
		}
	}

}
