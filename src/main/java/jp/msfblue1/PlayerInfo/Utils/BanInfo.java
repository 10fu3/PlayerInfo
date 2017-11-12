package jp.msfblue1.PlayerInfo.Utils;

import jp.msfblue1.PlayerInfo.Network.HTTPConnect;
import jp.msfblue1.PlayerInfo.Tools.PrefixAdder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by msfblue1 on 2017/09/08.
 */
public class BanInfo extends HTTPConnect {
    private OfflinePlayer offlinePlayer;
    private Boolean Exist;
    private UUID UUID;
    private Double MCBansRep = 10.0;
    private Integer MCBans = 0;
    private String[] McbansHistory = new String[]{};
    private String JMPBanReason = "NONE";

    public BanInfo(String Name){
        JSONObject userdata = getJSONData("https://api-irondome.herokuapp.com/getdata?username="+Name);

        if(userdata == null || userdata.isEmpty() || !userdata.containsKey("uuid")){
            Exist = false;
            return;
        }else{
            Exist = true;
        }
        if(userdata.containsKey("uuid")){
            try{
                String stringUUID = String.valueOf(userdata.get("uuid"));
                String[] slice_uuid = new String[5];

                slice_uuid[0] = stringUUID.substring(0, 8);
                slice_uuid[1] = stringUUID.substring(8, 12);
                slice_uuid[2] = stringUUID.substring(12, 16);
                slice_uuid[3] = stringUUID.substring(16, 20);
                slice_uuid[4] = stringUUID.substring(20, 32);

                this.UUID = java.util.UUID.fromString(slice_uuid[0] + "-" + slice_uuid[1] + "-" + slice_uuid[2] + "-" + slice_uuid[3] + "-" + slice_uuid[4]);
                this.offlinePlayer = OfflinePlayerGetter.getOPlayer(Name);

            }catch (StringIndexOutOfBoundsException | IllegalArgumentException | ArrayIndexOutOfBoundsException | NullPointerException e){
                PrefixAdder.sendMessage(Bukkit.getConsoleSender(), ChatColor.RED,"エラーが発生しました。");
                e.printStackTrace();
            }
        }
        if(userdata.containsKey("total")){
            this.MCBans = Integer.parseInt(userdata.get("total").toString());
        }
        if(userdata.containsKey("reputation")){
            this.MCBansRep = Double.valueOf(userdata.get("reputation").toString());
        }
        if(userdata.containsKey("global")){
            JSONArray history = (JSONArray) userdata.get("global");
            ArrayList<String> returndata = new ArrayList<>();
            if(!history.isEmpty()){
                for (int i = 0; i < history.size(); i++) {
                    returndata.add(String.valueOf(history.get(i)));
                }
            }
            this.McbansHistory = returndata.toArray(new String[returndata.size()]);

        }
        if(userdata.containsKey("jmp")){
            this.JMPBanReason = String.valueOf(userdata.get("jmp"));
        }
    }

    public OfflinePlayer getOfflinePlayer() {
        return offlinePlayer;
    }

    public Boolean exist() {
        return Exist;
    }

    public java.util.UUID getUUID() {
        return UUID;
    }

    public Integer getMcbansCount() {
        return MCBans;
    }

    public Double getMcbansReputation() {
        return MCBansRep;
    }

    public String[] getMcbansHistory() {
        return McbansHistory;
    }

    public String getJMPBanReason() {
        return JMPBanReason;
    }

}
