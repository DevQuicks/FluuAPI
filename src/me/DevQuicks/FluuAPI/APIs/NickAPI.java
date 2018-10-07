package me.DevQuicks.FluuAPI.APIs;

import me.DevQuicks.FluuAPI.mysql.MySQL;

import java.sql.ResultSet;
import java.sql.SQLException;


public class NickAPI {

    private static boolean playerExists(String uuid) {
        try {
            ResultSet rs = MySQL.mysql.query("SELECT * FROM Autonick WHERE UUID= '" + uuid + "'");

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
            MySQL.mysql.update("INSERT INTO Autonick(UUID, AKTIV) VALUES ('" + uuid + "', '0');");
        }
    }

    public static boolean isAutonick(String uuid) {
        Integer i = Integer.valueOf(0);

        if (playerExists(uuid)) {
            try {
                ResultSet rs = MySQL.mysql.query("SELECT * FROM Autonick WHERE UUID= '" + uuid + "'");

                if ((rs.next()) && (Integer.valueOf(rs.getInt("AKTIV")) == null)) ;
                i = Integer.valueOf(rs.getInt("AKTIV"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            createPlayer(uuid);
        }
        if (i == 1) {
            return true;
        }
        return false;
    }

    public static void setAutonick(String uuid, boolean aktiv) {
        int i = 0;
        if (aktiv) {
            i = 1;
        }
        if (playerExists(uuid)) {
            MySQL.mysql.update("UPDATE Autonick SET AKTIV= '" + i + "' WHERE UUID= '" + uuid + "';");
        } else {
            createPlayer(uuid);
            setAutonick(uuid, aktiv);
        }
    }
}
