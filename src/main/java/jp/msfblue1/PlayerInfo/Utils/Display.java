package jp.msfblue1.PlayerInfo.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jp.msfblue1.PlayerInfo.DataBase.PlayerDataManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import jp.msfblue1.PlayerInfo.Tools.PrefixAdder;

public class Display {

	public static String nomalMessage (ChatColor color , String category , String value){
		return color+category+ChatColor.WHITE+" : "+ChatColor.GREEN+value;
	}

	public static String warnMessage (ChatColor color , String category , String value){
		return color+category+ChatColor.WHITE+" : "+ChatColor.RED+value;
	}

	public static String cautionMessage (ChatColor color , String category , String value){
		return color+category+ChatColor.WHITE+" : "+ChatColor.YELLOW+value;
	}

	private static List<String> getbanInfo(CommandSender sender , String s_target , boolean all){
		List<String> messages = new ArrayList<>();
		BanInfo ban = new BanInfo(s_target);
		ChatColor color;
		if(sender instanceof Player){
			color = ChatColor.GOLD;
		}else if(sender instanceof ConsoleCommandSender){
			color = ChatColor.AQUA;
		}else{
			color = ChatColor.RESET;
		}
		if(ban.getMcbansCount() > 0){
			messages.add(cautionMessage(color,"GBan数",String.valueOf(ban.getMcbansCount())));
		}

		if(10.0 >ban.getMcbansReputation()){
			messages.add(cautionMessage(color,"Rep",String.valueOf(ban.getMcbansReputation())));
		}

		if(all){
			if(ban.getMcbansHistory().length > 0){
				messages.add(ChatColor.RED + "=======  GBan Info  =======");
				for (int i = 0; i < ban.getMcbansHistory().length; i++) {
					messages.add(cautionMessage(ChatColor.DARK_AQUA,String.valueOf(i),ban.getMcbansHistory()[i]));
					//messages.add(String.valueOf(i)+ "件目"+ " : " + user.getBan().getMcbansHistory()[i]);
				}
				messages.add(ChatColor.RED + "   ======================  ");
			}
		}
		if (ban.getJMPBanReason().equalsIgnoreCase("Compromised Account")) {
			messages.add(warnMessage(color,"JMS","盗難アカウント"));
			//messages.add(color+"盗難アカウントの可能性" + ChatColor.WHITE + " : " + ChatColor.RED + "高");
		}

		if (ban.getJMPBanReason().isEmpty()){
			messages.add(cautionMessage(color,"JMS",ban.getJMPBanReason()));
		}

		return messages;
	}

	public static void sendWhoisInfo(CommandSender sender, String s_target , boolean all){
		if (sender.hasPermission("advwhois.whoisps") || !(sender instanceof BlockCommandSender)){
			ChatColor C;
			if(sender instanceof Player){
				C = ChatColor.GOLD;
			}else if(sender instanceof ConsoleCommandSender){
				C = ChatColor.AQUA;
			}else{
				C = ChatColor.RESET;
			}

			List<String> messages = new ArrayList<>();

			Player player = Bukkit.getPlayer(s_target);

			if(player != null){
				List<String> alts = PlayerDataManager.getDuplicationIPPlayer(player.getAddress().getAddress().getHostAddress(),player.getUniqueId());

				messages.add(ChatColor.GRAY+ "======== "+ChatColor.YELLOW+player.getName()+ChatColor.GRAY+" についての情報 ========");
				if(all){
					messages.add(nomalMessage(C,"UUID",player.getUniqueId().toString()));
				}
				messages.addAll(getbanInfo(sender,s_target,all));
				if(!alts.isEmpty()){
					messages.add(ChatColor.RED+"===== サブアカウント一覧 =====");
					for(String alt : alts){
						messages.add(cautionMessage(C,"Sub accounts",alt));
					}
					messages.add(ChatColor.RED+"=============================");
				}
				if(all){
					messages.add(nomalMessage(C,"初回ログイン",new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(player.getFirstPlayed())));
				}
				if(player.hasPlayedBefore()){
					messages.add(nomalMessage(C,"最終ログイン", new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(player.getLastPlayed())));
				}
				if(all){
					messages.add(nomalMessage(C,"座標",player.getLocation().getWorld().getName() + ", " + player.getLocation().getBlockX() + ", " + player.getLocation().getBlockY() + ", " + player.getLocation().getBlockZ()));
				}
				if(player.isOp()){
					messages.add(nomalMessage(C,"OP",String.valueOf(player.isOp())));
				}
				if(player.isBanned()){
					messages.add(nomalMessage(C,"BAN",String.valueOf(player.isBanned())));
				}
				if(player.isWhitelisted()){
					messages.add(nomalMessage(C,"ホワイトリスト",String.valueOf(player.isWhitelisted())));
				}
				messages.add(nomalMessage(C,"IP",player.getAddress().getAddress().getHostAddress()+" "+String.format(ChatColor.GRAY+"(%s)",player.getAddress().getHostName())));
				messages.add(nomalMessage(C,"接続国名",PlayerDataManager.getJoinCountry(player.getUniqueId()) + " (" + PlayerDataManager.getJoinCountryCode(player.getUniqueId()) + ")"));

			}else{
				BanInfo ban = new BanInfo(s_target);
				if(!ban.exist()){
					PrefixAdder.sendMessage(sender, ChatColor.RED , "プレイヤーが存在しません");
					return;
				} else{
					UUID puuid = ban.getUUID();
					OfflinePlayer target = ban.getOfflinePlayer();
					//OfflinePlayer offlinePlayer = OfflinePlayerGetter.getOPlayer();
					List<String> alts = PlayerDataManager.getDuplicationIPPlayer(PlayerDataManager.getIP(puuid),target.getUniqueId());
					messages.add(ChatColor.GRAY+ "======== "+ChatColor.YELLOW+target.getName()+ChatColor.GRAY+" についての情報 ========");
					if(all){
						messages.add(nomalMessage(C,"UUID",target.getUniqueId().toString()));
					}
					messages.addAll(getbanInfo(sender,s_target,all));
					if(!alts.isEmpty()){
						messages.add(ChatColor.RED+"====== サブアカウント一覧 =====");
						int count = 1;
						for(String alt : alts){
							messages.add(cautionMessage(C,String.valueOf(count),alt));
							count += 1;
						}
						messages.add(ChatColor.RED+"==============================");
					}
					if(target.isOp()){
						messages.add(nomalMessage(C,"OP", String.valueOf(target.isOp())));
					}
					if(target.isBanned()){
						messages.add(nomalMessage(C,"BAN", String.valueOf(target.isBanned())));
					}
					if(target.isWhitelisted()){
						messages.add(nomalMessage(C,"ホワイトリスト", String.valueOf(target.isWhitelisted())));
					}
					if(target.hasPlayedBefore()){
						if(all){
							messages.add(nomalMessage(C,"初回ログイン",new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(target.getFirstPlayed())));
						}
						messages.add(nomalMessage(C,"最終ログイン",new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(target.getLastPlayed())));
						messages.add(nomalMessage(C,"最後記録アドレス",PlayerDataManager.getIP(target.getUniqueId())));
						if(all){
							messages.add(nomalMessage(C,"最終記録接続国",PlayerDataManager.getJoinCountry(target.getUniqueId())));
						}

					}

				}
			}
			messages.forEach(sender::sendMessage);

		} else {
			PrefixAdder.sendMessage(sender, ChatColor.RED, "You don't have Permission.");
			return;
		}
	}

}
