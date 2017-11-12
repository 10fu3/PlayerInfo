package jp.msfblue1.PlayerInfo.Utils;

import jp.msfblue1.PlayerInfo.PlayerInfo;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by msfblue1 on 2017/11/11.
 */
public class HostNameBanManager {
    PlayerInfo plugin;
    List<String> hostnames = new LinkedList<>();
    public HostNameBanManager(PlayerInfo me){
        this.plugin = me;
        FileConfiguration conf = new YamlConfiguration();
        File database = new File(plugin.getDataFolder()+"/BanList.yml");
        if(!plugin.getDataFolder().isDirectory()){
            plugin.getDataFolder().mkdirs();
        }
        if(!database.exists()){
            try {
                database.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
        try {
            conf.load(database);
            hostnames.addAll(conf.getStringList("Bans"));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
            return;
        }
    }

    public void addBan(String host){
        this.hostnames.add(host);
    }

    public boolean contains(String host){
        if(this.hostnames.size() > 0){
            for (int i = 0; i < this.hostnames.size(); i++) {
                if(host.equalsIgnoreCase(this.hostnames.get(i))){
                    return true;
                }
            }
        }
        return false;
    }

    public void save(){
        FileConfiguration conf = new YamlConfiguration();
        File database = new File(plugin.getDataFolder()+"/BanList.yml");
        if(!plugin.getDataFolder().isDirectory()){
            plugin.getDataFolder().mkdirs();
        }
        if(!database.exists()){
            try {
                database.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
        try {
            conf.set("Bans",this.hostnames);
            conf.save(database);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

    public void pardon(String host){
        this.hostnames.removeIf(o -> host.equalsIgnoreCase(String.valueOf(o)));
    }
}
