package jp.msfblue1.PlayerInfo.Utils;

import jp.msfblue1.PlayerInfo.DataBase.PlayerDataManager;
import jp.msfblue1.PlayerInfo.PlayerInfo;

import java.util.List;
import java.util.UUID;

/**
 * Created by msfblue1 on 2017/11/10.
 */
public class Judge {
    public static JoinResult canJoinPlayer(String player, String IP, UUID uuid){

        BanInfo user = new BanInfo(player);
        Integer bc = user.getMcbansCount();
        Double br = user.getMcbansReputation();
        Boolean stolen = user.getJMPBanReason().equalsIgnoreCase("Compromised Account");

        if(PlayerInfo.MCTotal >= 0 && PlayerInfo.MCTotal <= bc){
            return JoinResult.FAILED_BAN_COUNT;
        }
        //p:7 s:10
        if(PlayerInfo.MCRep < 10.0 && br <= PlayerInfo.MCTotal){
            return JoinResult.FAILED_TOO_REP;
        }

        if(stolen && PlayerInfo.JPStolen){
            return JoinResult.FAILED_STOLEN;
        }


        if(IP != null && !IP.isEmpty() && !isPrivateIpAddres(IP)){
            List<String> alts = PlayerDataManager.getDuplicationIPPlayer(IP,uuid);
            if(alts.size() > 0 && PlayerInfo.AltAccounts > -1 && PlayerInfo.AltAccounts <= alts.size()){
                return JoinResult.FAILED_ALT_MAX;
            }
        }
        return JoinResult.SUCCESS;

    }

    public static boolean isPrivateIpAddres(String checkIp){
        if (chekRangeIpAddress(checkIp, "10.0.0.0", "10.255.255.255")
                ||  chekRangeIpAddress(checkIp, "172.16.0.0", "172.31.255.255")
                ||  chekRangeIpAddress(checkIp, "192.168.0.0", "192.168.255.255")){
            return true;
        }
        if(checkIp.equalsIgnoreCase("127.0.0.1")){
            return true;
        }
        return false;
    }
    /**
     * IPアドレスが指定された範囲ないかどうかをチェックする。<br />
     * @param checkIp チェックするIPアドレス
     * @param firstIp 先頭IPアドレス
     * @param lastIp  最終IPアドレス
     * @return true:範囲内、false:範囲外
     */
    private static boolean chekRangeIpAddress(String checkIp, String firstIp, String lastIp){
        // 桁数揃え
        checkIp = formatIp(checkIp);
        firstIp = formatIp(firstIp);
        lastIp  = formatIp(lastIp);
        // 整形できない場合はエラー
        if (checkIp == null || firstIp == null || lastIp == null) {
            return false;
        }

        // 範囲内かチェック
        if (checkIp.compareTo(firstIp) < 0 || checkIp.compareTo(lastIp) > 0) {
            return false;
        }
        return true;
    }
    /**
     * IPアドレスの桁数を揃える(各セグメントを3桁に揃える)
     * @param ip IPアドレス
     * @return 整形後のIPアドレス
     */
    private static String formatIp(String ip){
        // 4つに分割できない時はエラー
        String[] ipArray = ip.split("\\.");
        if (ipArray.length != 4) {
            return null;
        }
        // 数値とピリオド以外にマッチした場合はエラー
        for (int i = 0; i < 4; i++) {
            String ipPart = ipArray[i];
            if (ipPart == null || ipPart.isEmpty() || (ipPart.matches("[^0-9]"))){
                return null;
            }
        }

        // 3桁に満たない場合は3桁にする
        for (int i = 0; i < 4; i++){
            int len = ipArray[i].length();
            for (int j = len; j < 3; j++) {
                ipArray[i] = "0" + ipArray[i];
            }
        }
        // 3桁*4のIPを組立
        String delimiter = "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++){
            sb.append(delimiter);
            sb.append(ipArray[i]);
            delimiter = ".";
        }
        return sb.toString();
    }

}
