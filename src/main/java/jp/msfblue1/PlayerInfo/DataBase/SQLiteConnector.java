package jp.msfblue1.PlayerInfo.DataBase;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static jp.msfblue1.PlayerInfo.PlayerInfo.plugin;

public class SQLiteConnector implements DBConnector {
	private String SQLURL;
	private Connection connection;

	public SQLiteConnector() {
		SQLURL = "jdbc:sqlite:" + plugin.getDataFolder() + "/playerdata.db";
		mkTable();
	}

	@Override
	public Connection getConnection(String SQLURL) throws SQLException {
		return DriverManager.getConnection(SQLURL);
	}

	@Override
	public void mkTable() {
		try {
			connection = getConnection(SQLURL);
			PreparedStatement ps = connection.prepareStatement("CREATE TABLE IF NOT EXISTS PlayerData (username TEXT, uuid TEXT, ip TEXT, joincountry TEXT, joinccode TEXT)");
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			connection = null;
		}
	}

	@Override
	public boolean savePlayerData(Map<String, String> data){
		boolean result = false;
		try {
			connection = getConnection(SQLURL);
			PreparedStatement ps = connection.prepareStatement("INSERT INTO PlayerData VALUES(?, ?, ?, ?, ?)");
			ps.setString(1, data.get("username"));
			ps.setString(2, data.get("uuid"));
			ps.setString(3, data.get("ip"));
			ps.setString(4, data.get("joincountry"));
			ps.setString(5, data.get("joinccode"));
			result = ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			connection = null;
		}
		return result;
	}

	@Override
	public boolean updatePlayerData(UUID uuid, Map<String, String> data) {
		boolean result = false;
		try {
			connection = getConnection(SQLURL);
			PreparedStatement ps = connection.prepareStatement("UPDATE PlayerData SET username = ?, ip = ?, joincountry = ?, joinccode = ? WHERE uuid = ?");
			ps.setString(1, data.get("username"));
			ps.setString(2, data.get("ip"));
			ps.setString(3, data.get("joincountry"));
			ps.setString(4, data.get("joinccode"));
			ps.setString(5, data.get("uuid"));
			result = ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			connection = null;
		}
		return result;
	}

	@Override
	public Map<String, String> getPlayerData(UUID uuid) {
		Map<String, String> result = new HashMap<>();
		try {
			connection = getConnection(SQLURL);
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM PlayerData WHERE uuid = ?");
			ps.setString(1, uuid.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				result.put("username", rs.getString("username"));
				result.put("uuid", rs.getString("uuid"));
				result.put("ip", rs.getString("ip"));
				result.put("joincountry", rs.getString("joincountry"));
				result.put("joinccode", rs.getString("joinccode"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			connection = null;
		}
		return result;
	}

	@Override
	public Map<String, String> getPlayerData(String player) {
		Map<String, String> result = new HashMap<>();
		try {
			connection = getConnection(SQLURL);
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM PlayerData WHERE username = ?");
			ps.setString(1, player);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				result.put("username", rs.getString("username"));
				result.put("uuid", rs.getString("uuid"));
				result.put("ip", rs.getString("ip"));
				result.put("joincountry", rs.getString("joincountry"));
				result.put("joinccode", rs.getString("joinccode"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			connection = null;
		}
		return result;
	}

	@Override
	public List<String> getDuplicationIPPlayer(String ip,UUID uuid) {
		List<String> result = new ArrayList<>();
		try {
			connection = getConnection(SQLURL);
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM PlayerData WHERE ip = ?");
			ps.setString(1, ip);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				if(!uuid.toString().equalsIgnoreCase(rs.getString("uuid"))){
					result.add(rs.getString("username"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			connection = null;
		}
		return result;
	}

}
