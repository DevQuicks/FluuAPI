package me.DevQuicks.FluuAPI.APIs;

import me.DevQuicks.FluuAPI.mysql.MySQL;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OnlinetimeAPI {

    private static boolean playerExists(String uuid) {
        try {
            ResultSet rs = MySQL.mysql.query("SELECT * FROM Onlinetime WHERE UUID= '" + uuid + "'");

            if (rs.next()) {
                return rs.getString("UUID") != null;
            }

            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static void createPlayer(String uuid) {
        if (!playerExists(uuid)) {
            MySQL.mysql.update("INSERT INTO Onlinetime(UUID, MINUTES) VALUES ('" + uuid + "', '0');");
        }
    }

    public static int getOnlinetime(String uuid) {
        Integer i = Integer.valueOf(0);
        if (playerExists(uuid)) {
            try {
                ResultSet rs = MySQL.mysql.query("SELECT * FROM Onlinetime WHERE UUID= '" + uuid + "'");
                if ((rs.next()) && (Integer.valueOf(rs.getInt("MINUTES")) == null)) ;
                i = Integer.valueOf(rs.getInt("MINUTES"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            createPlayer(uuid);
        }
        return 0;
    }

    public static void addOnlinetime(String uuid) {
        int i = getOnlinetime(uuid) + 1;
        if (playerExists(uuid)) {
            MySQL.mysql.update("UPDATE Onlinetime SET MINUTES= '" + i + "' WHERE UUID= '" + uuid + "';");
        } else {
            createPlayer(uuid);
            addOnlinetime(uuid);
        }
    }

}
