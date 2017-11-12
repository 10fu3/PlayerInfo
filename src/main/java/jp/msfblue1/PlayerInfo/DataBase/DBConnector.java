package jp.msfblue1.PlayerInfo.DataBase;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface DBConnector {
	public Connection getConnection(String SQLURL) throws SQLException;
	public void mkTable();

	/**
	 * プレイヤーデータを保存します。
	 * @return 正常に保存が完了した場合true
	 */
	public boolean savePlayerData(Map<String, String> data);

	/**
	 * 登録されているプレイヤーデータを更新します。
	 * @param uuid プレイヤーのUUID
	 * @param data 正常に保存が完了した場合true
	 * @return
	 */
	public boolean updatePlayerData(UUID uuid, Map<String, String> data);

	/**
	 * プレイヤーデータを取得します。
	 * @param uuid プレイヤーのUUID
	 * @return プレイヤーデータ
	 */
	public Map<String, String> getPlayerData(UUID uuid);

	/**
	 * プレイヤーデータを取得します。
	 * @param player プレイヤーのID
	 * @return プレイヤーデータ
	 */
	public Map<String, String> getPlayerData(String player);

	/**
	 * 同一IPのプレイヤーを取得します。
	 * @param ip 検索するIP
	 * @param uuid 検索するUUID
	 * @return 該当したプレイヤーの一覧
	 */
	public List<String> getDuplicationIPPlayer(String ip,UUID uuid);

}
