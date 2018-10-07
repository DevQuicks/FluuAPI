package me.DevQuicks.FluuAPI.APIs;






import me.DevQuicks.FluuAPI.mysql.MySQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class InfoAPI {


    public static boolean isPlayerExist(UUID uuid) {

        try {
            ResultSet rs = MySQL.mysql.query("SELECT * FROM Info WHERE UUID= '" + uuid + "'");
            if (rs.next()) {
                return rs.getString("UUID") != null;
            }

            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }
    public static void updateInfos(UUID uuid, String server, boolean online) {
        if (isPlayerExist(uuid)) {
            MySQL.mysql.update("UPDATE Info SET SERVER = '" + server + "'  WHERE UUID = '" + uuid + "';");
            MySQL.mysql.update("UPDATE Info SET ONLINE = '" + online + "'  WHERE UUID = '" + uuid + "';");
        }
    }
    public static void updateLastOnline(UUID uuid, String online) {
        if (isPlayerExist(uuid)) {
            MySQL.mysql.update("UPDATE Info SET LASTONLINE = '" + online + "'  WHERE UUID = '" + uuid + "';");
        }
    }
    public static void updateSpieler(UUID uuid, String name) {
        if (isPlayerExist(uuid)) {
            MySQL.mysql.update("UPDATE Info SET NAME = '" + name + "'  WHERE UUID = '" + uuid + "';");
        } else {
            addPlayer(uuid, name);
            updateSpieler(uuid, name);
        }
    }

    public static void addPlayer(UUID uuid, String name) {
        if (!isPlayerExist(uuid)) {
            MySQL.mysql.update("INSERT INTO Info(UUID, NAME) VALUES ('" + uuid + "', '" + name + "');");

        }
    }
    public static String getLastOnline(UUID uuid) {
        try {
            ResultSet rs = MySQL.mysql.query("SELECT * FROM Info WHERE UUID = '" + uuid + "';");
            while (rs.next()) {
                return rs.getString("LASTONLINE");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String getName(UUID uuid) {
        try {
            ResultSet rs = MySQL.mysql.query("SELECT * FROM Info WHERE UUID = '" + uuid + "';");
            while (rs.next()) {
                return rs.getString("NAME");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String getServer(UUID uuid) {
        try {
            ResultSet rs = MySQL.mysql.query("SELECT * FROM Info WHERE UUID = '" + uuid + "';");
            while (rs.next()) {
                return rs.getString("SERVER");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isOnline(UUID uuid) {
        try {
            ResultSet rs = MySQL.mysql.query("SELECT * FROM Info WHERE UUID = '" + uuid + "';");
            while (rs.next()) {
                String status = rs.getString("ONLINE");
                if(status.equalsIgnoreCase("true")) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static UUID getUUID(String name) {
        try {
            ResultSet rs = MySQL.mysql.query("SELECT * FROM Info WHERE NAME = '" + name + "';");
            while (rs.next()) {
                return UUID.fromString(rs.getString("UUID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
