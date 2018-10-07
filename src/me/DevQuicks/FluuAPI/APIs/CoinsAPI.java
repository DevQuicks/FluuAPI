package me.DevQuicks.FluuAPI.APIs;

import me.DevQuicks.FluuAPI.mysql.MySQL;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CoinsAPI {
	
	
	private static boolean isExist(String uuid) {

        try {
            ResultSet rs = MySQL.mysql.query("SELECT * FROM Coins WHERE UUID = '" + uuid + "'");
            while (rs.next()) {
                return rs.getString("UUID") != null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }

    public static void setCoins(String uuid, int size) {
    	if(!isExist(uuid)) {
            MySQL.mysql.update("INSERT INTO Coins(UUID, ANZAHL) VALUES ('" + uuid + "', '" + size + "');");
    	} else {
    	     MySQL.mysql.update("UPDATE Coins SET ANZAHL = '" + size + "'  WHERE UUID = '" + uuid + "';");
    	}
    }

    public static void addCoins(String uuid, int anzahl) {
	    int coins = getCoins(uuid) + anzahl;
	    setCoins(uuid, coins);
    }


    public static void removeCoins(String uuid, int anzahl) {
        int coins = getCoins(uuid) - anzahl;
        setCoins(uuid, coins);
    }

    
    public static int getCoins(String uuid) {
    	try {
            ResultSet rs = MySQL.mysql.query("SELECT * FROM Coins WHERE UUID = '" + uuid + "'");
            while (rs.next()) {
                return rs.getInt("ANZAHL");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
