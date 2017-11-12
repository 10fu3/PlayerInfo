package jp.msfblue1.PlayerInfo.Listener;


import jp.msfblue1.PlayerInfo.PlayerInfo;
import jp.msfblue1.PlayerInfo.Tools.PrefixAdder;
import jp.msfblue1.PlayerInfo.Utils.JoinResult;
import jp.msfblue1.PlayerInfo.Utils.Judge;
import jp.msfblue1.PlayerInfo.DataBase.PlayerDataManager;

import jp.msfblue1.PlayerInfo.Utils.Display;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;
import java.util.List;

public class PlayerJoinEventCensor implements Listener {

	public void sendMessage(String target , JoinResult result){
		String mes = result == null ? "ホストネームBAN" : JoinResult.getMessage(result);
		List<CommandSender> admins = new ArrayList<>();
		admins.addAll(Bukkit.getOnlinePlayers());
		admins.add(Bukkit.getConsoleSender());
		admins.stream()
				.filter(p->p.hasPermission("advwhois.joinshow"))
				.forEach(s->{
					PrefixAdder.sendMessage(s,ChatColor.YELLOW,target+" は接続を試行していましたが、拒否されました。");
					PrefixAdder.sendMessage(s,ChatColor.YELLOW,"理由: "+mes);
				});
	}

	@EventHandler
	public void onPlayerConnect(AsyncPlayerPreLoginEvent e){
		if(PlayerInfo.Admin.contains(e.getUniqueId().toString())){
			return;
		}
		if(PlayerInfo.getBanManager().contains(e.getAddress().getCanonicalHostName())){
			e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER,"You have been banned.");
			sendMessage(e.getName(),null);
			return;
		}
		JoinResult result = Judge.canJoinPlayer(e.getName(),e.getAddress().getHostAddress(),e.getUniqueId());
		if(result != JoinResult.SUCCESS){
			e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER,JoinResult.getMessage(result));
			sendMessage(e.getName(),result);
			return;
		}
	}

	@EventHandler
	public void onPlayerJoinEvent(PlayerJoinEvent e){
		PlayerDataManager.updatePlayerData(e.getPlayer());
		if(e.getPlayer().hasPermission("advwhois.bypass")){
			return;
		}
		PlayerInfo.taskAsynchronously(()->{
			List<CommandSender> admins = new ArrayList<>();
			admins.addAll(Bukkit.getOnlinePlayers());
			admins.add(Bukkit.getConsoleSender());
			admins.stream()
					.filter(p->p.hasPermission("advwhois.joinshow"))
					.forEach(s-> Display.sendWhoisInfo(s,e.getPlayer().getName(),false));
		});
	}

}
