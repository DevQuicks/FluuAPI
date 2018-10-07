package me.DevQuicks.FluuAPI.APIs;






import me.DevQuicks.FluuAPI.mysql.MySQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class SettingsAPI {


    public static boolean isPlayerExist(UUID uuid) {

        try {
            ResultSet rs = MySQL.mysql.query("SELECT * FROM Settings WHERE UUID = '" + uuid + "'");
            if (rs.next()) {
                return rs.getString("UUID") != null;
            }

            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }

    public static void setRequests(UUID uuid, String status) {
        if (isPlayerExist(uuid)) {
            MySQL.mysql.update("UPDATE Settings SET REQUESTS = '" + status + "'  WHERE UUID = '" + uuid + "';");
        } else {
            addPlayer(uuid);
            setRequests(uuid, status);
        }
    }
    public static void setMSG(UUID uuid, String status) {
        if (isPlayerExist(uuid)) {
            MySQL.mysql.update("UPDATE Settings SET MSG = '" + status + "'  WHERE UUID = '" + uuid + "';");
        } else {
            addPlayer(uuid);
            setMSG(uuid, status);
        }
    }
    public static void setParty(UUID uuid, String status) {
        if (isPlayerExist(uuid)) {
            MySQL.mysql.update("UPDATE Settings SET PARTY = '" + status + "'  WHERE UUID = '" + uuid + "';");
        } else {
            addPlayer(uuid);
            setParty(uuid, status);
        }
    }
    public static void setNachjoinen(UUID uuid, String status) {
        if (isPlayerExist(uuid)) {
            MySQL.mysql.update("UPDATE Settings SET NACHJOINEN = '" + status + "'  WHERE UUID = '" + uuid + "';");
        } else {
            addPlayer(uuid);
            setNachjoinen(uuid, status);
        }
    }
    public static void setJoinMessages(UUID uuid, String status) {
        if (isPlayerExist(uuid)) {
            MySQL.mysql.update("UPDATE Settings SET JOINMESSAGES = '" + status + "'  WHERE UUID = '" + uuid + "';");
        } else {
            addPlayer(uuid);
            setJoinMessages(uuid, status);
        }
    }



    public static void addPlayer(UUID uuid) {
        if (!isPlayerExist(uuid)) {
            MySQL.mysql.update("INSERT INTO Settings(UUID) VALUES ('" + uuid + "');");
            updateSettings(uuid, "ALLE", "ALLE", "ALLE", "ALLE", "ALLE");
        }
    }
    public static void updateSettings(UUID uuid, String nachjoinen, String requests, String party, String joinmessages, String msg) {
        if (isPlayerExist(uuid)) {
            MySQL.mysql.update("UPDATE Settings SET NACHJOINEN = '" + nachjoinen + "'  WHERE UUID = '" + uuid + "';");
            MySQL.mysql.update("UPDATE Settings SET REQUESTS = '" + requests + "'  WHERE UUID = '" + uuid + "';");
            MySQL.mysql.update("UPDATE Settings SET PARTY = '" + party + "'  WHERE UUID = '" + uuid + "';");
            MySQL.mysql.update("UPDATE Settings SET JOINMESSAGES = '" + joinmessages + "'  WHERE UUID = '" + uuid + "';");
            MySQL.mysql.update("UPDATE Settings SET MSG = '" + msg + "'  WHERE UUID = '" + uuid + "';");
        } else {
            addPlayer(uuid);
            updateSettings(uuid, nachjoinen, requests, party, joinmessages, msg);
        }
    }
    public static String getNachjoinen(UUID uuid) {
        try {
            ResultSet rs = MySQL.mysql.query("SELECT * FROM Settings WHERE UUID = '" + uuid + "';");
            while (rs.next()) {
                String next = rs.getString("NACHJOINEN");
                if(next.equalsIgnoreCase("NULL") || next.equalsIgnoreCase("") || next.equalsIgnoreCase(" ")) {
                    return "ALLE";
                }
                return next;
            }
        } catch (SQLException e) {
        }
        return "ALLE";
    }
    public static String getRequests(UUID uuid) {
        try {
            ResultSet rs = MySQL.mysql.query("SELECT * FROM Settings WHERE UUID = '" + uuid + "';");
            while (rs.next()) {
                String next = rs.getString("REQUESTS");
                if(next.equalsIgnoreCase("NULL") || next.equalsIgnoreCase("") || next.equalsIgnoreCase(" ")) {
                    return "ALLE";
                }
                return next;
            }
        } catch (SQLException e) {
        }
        return "ALLE";
    }
    public static String getParty(UUID uuid) {
        try {
            ResultSet rs = MySQL.mysql.query("SELECT * FROM Settings WHERE UUID = '" + uuid + "';");
            while (rs.next()) {
                String next = rs.getString("PARTY");
                if(next.equalsIgnoreCase("NULL") || next.equalsIgnoreCase("") || next.equalsIgnoreCase(" ")) {
                    return "ALLE";
                }
                return next;
            }
        } catch (SQLException e) {
        }
        return "ALLE";
    }
    public static String getJoinMessages(UUID uuid) {
        try {
            ResultSet rs = MySQL.mysql.query("SELECT * FROM Settings WHERE UUID = '" + uuid + "';");
            while (rs.next()) {
                String next = rs.getString("JOINMESSAGES");
                if(next.equalsIgnoreCase("NULL") || next.equalsIgnoreCase("") || next.equalsIgnoreCase(" ")) {
                    return "ALLE";
                }
                return next;
            }
        } catch (SQLException e) {
        }
        return "ALLE";
    }
    public static String getMSG(UUID uuid) {
        try {
            ResultSet rs = MySQL.mysql.query("SELECT * FROM Settings WHERE UUID = '" + uuid + "';");
            while (rs.next()) {
                String next = rs.getString("MSG");
                if(next.equalsIgnoreCase("NULL") || next.equalsIgnoreCase("") || next.equalsIgnoreCase(" ")) {
                    return "ALLE";
                }
                return next;
            }
        } catch (SQLException e) {
        }
        return "ALLE";
    }
}
