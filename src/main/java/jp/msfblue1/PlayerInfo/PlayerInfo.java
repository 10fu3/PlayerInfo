package jp.msfblue1.PlayerInfo;

import jp.msfblue1.PlayerInfo.Command.MainCommand;
import jp.msfblue1.PlayerInfo.Listener.PlayerJoinEventCensor;
import jp.msfblue1.PlayerInfo.Utils.HostNameBanManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;


public class PlayerInfo extends JavaPlugin {
	public static HostNameBanManager banManager;
	public static Plugin plugin;
	public static Boolean CheckUser;
	public static Double MCRep ;
	public static Integer MCTotal;
	public static Boolean JPStolen;
	public static Integer AltAccounts;
	//public static List<String> BlockHostName;
	public static List<String> Admin;
	public static boolean debug;

	@Override
	public void onEnable(){
		super.onEnable();
		banManager = new HostNameBanManager(this);
		plugin = this;
		saveDefaultConfig();
		reloadConf();
		getServer().getPluginManager().registerEvents(new PlayerJoinEventCensor(), this);
		getCommand("advancedwhois").setExecutor(new MainCommand());
	}

	@Override
	public void onDisable(){
		this.banManager.save();
		super.onDisable();
	}

	public static void reloadConf() {
		plugin.reloadConfig();
		FileConfiguration conf = plugin.getConfig();
		CheckUser = Boolean.valueOf(conf.getString("LoginChecker","true"));

		MCRep = Double.valueOf(conf.getString("MCBans.Rep","7.0"));
		MCTotal = Integer.valueOf(conf.getString("MCBans.TotalBans","2"));
		JPStolen = Boolean.valueOf(conf.getString("Stolen","true"));
		AltAccounts = Integer.valueOf(conf.getString("TotalSubAccount","0"));
		Admin = conf.getStringList("BypathChecker");
		debug = conf.getBoolean("debug");
	}

	public static void taskAsynchronously(Runnable task){
		Bukkit.getScheduler().runTaskAsynchronously(plugin,task);
	}

	public static HostNameBanManager getBanManager(){
		return banManager;
	}

	public static void task(Runnable task){
		Bukkit.getScheduler().runTask(plugin,task);
	}

}
