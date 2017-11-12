package jp.msfblue1.PlayerInfo.Utils;

import org.bukkit.ChatColor;

/**
 * Created by msfblue1 on 2017/11/04.
 */
public enum JoinResult {
    SUCCESS,
    FAILED_TOO_REP,
    FAILED_BAN_COUNT,
    FAILED_ALT_MAX,
    FAILED_STOLEN,
    FAILED_ETC;

    public static String getMessage(JoinResult result){
        switch (result){
            case SUCCESS:
                return ChatColor.GREEN+"ログインできます";
            case FAILED_TOO_REP:
                return ChatColor.YELLOW+"MCBansでの評判値が当サーバーの規定に満たしていません";
            case FAILED_BAN_COUNT:
                return ChatColor.YELLOW+"MCBansでのBanの回数が当サーバーの規定を超えています";
            case FAILED_STOLEN:
                return ChatColor.YELLOW+"JapanMinecraftPvPにおいて、盗難アカウントとして登録されています";
            case FAILED_ALT_MAX:
                return ChatColor.YELLOW+"当サーバーでのサブアカウントの上限を超えています";
            case FAILED_ETC:
            default:
                return ChatColor.YELLOW+"あなたは何らかの理由でログイン不可とされています";
        }
    }
}
