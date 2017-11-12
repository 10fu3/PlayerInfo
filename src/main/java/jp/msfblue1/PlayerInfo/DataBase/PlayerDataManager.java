package jp.msfblue1.PlayerInfo.DataBase;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import jp.msfblue1.PlayerInfo.DataBase.DBConnector;
import jp.msfblue1.PlayerInfo.DataBase.SQLiteConnector;
import jp.msfblue1.PlayerInfo.Utils.JoinCountryGetter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import static jp.msfblue1.PlayerInfo.PlayerInfo.plugin;


public class PlayerDataManager {

	private static DBConnector getSQL() {
		return new SQLiteConnector();
	}

	public static String getIP(UUID uuid) {
		DBConnector sql = getSQL();
		return sql.getPlayerData(uuid).get("ip");
	}

	public static boolean checkNewIP(UUID uuid){
		if (Bukkit.getPlayer(uuid) != null){
			String NowIP = Bukkit.getPlayer(uuid).getAddress().getAddress().getHostAddress();
			return !NowIP.equals(getIP(uuid));
		}
		return false;
	}

	public static void updatePlayerData(Player p) {
		DBConnector sql = getSQL();
		Map<String, String> data = new HashMap<>();
		data.put("username", p.getName());
		data.put("uuid", p.getUniqueId().toString());
		data.put("ip", p.getAddress().getAddress().getHostAddress());

		data.put("joincountry", JoinCountryGetter.JoinCountry(p.getAddress().getAddress().getHostAddress()));
		data.put("joinccode", JoinCountryGetter.JoinCountryCode(p.getAddress().getAddress().getHostAddress()));
		Map<String, String> old_data = sql.getPlayerData(p.getUniqueId());

		if (!old_data.isEmpty()) {
			if (checkNewIP(p.getUniqueId())) {
				sql.updatePlayerData(p.getUniqueId(), data);
			}
		} else {
			sql.savePlayerData(data);
		}
	}

	public static String getJoinCountry(UUID uuid) {
		DBConnector sql = getSQL();
		return sql.getPlayerData(uuid).get("joincountry");
	}

	public static String getJoinCountryCode(UUID uuid){
		DBConnector sql = getSQL();
		return sql.getPlayerData(uuid).get("joinccode");
	}

	public static List<String> getDuplicationIPPlayer(String ip,UUID uuid){
		DBConnector sql = getSQL();
		return sql.getDuplicationIPPlayer(ip,uuid);
	}
}
