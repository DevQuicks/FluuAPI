package me.DevQuicks.FluuAPI.bansystem;

import me.DevQuicks.FluuAPI.mysql.MySQL;
import me.DevQuicks.FluuAPI.utils.HistoryMutes;

import java.sql.ResultSet;
import java.util.*;

public class MySQLHistoryMutes {



    public static List<HistoryMutes> getMuteHistory(String uuid) {
        List<Integer> mutesid = new ArrayList<>();
        try {
            ResultSet rs = MySQL.mysql.query("SELECT * FROM HistoryMutes WHERE UUID = '" + uuid + "';");
            while (rs.next()) {
                mutesid.add(rs.getInt("ID"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        List<HistoryMutes> mutes = new ArrayList<>();
        for(int id : mutesid) {
            mutes.add(new HistoryMutes(id));
        }
        return mutes;
    }





    public static void addMute(int id, UUID uuid, String playername, String grund, String von, String zeitraum) {
            MySQL.mysql.update("INSERT INTO HistoryMutes(ID, UUID, SPIELERNAME, GRUND, VON, ZEITRAUM) VALUES ('" + id + "', '" + uuid
                    + "', '" + playername + "', '" + grund + "', '"  + von + "', '" + zeitraum + "');");


    }

    public static String getZeitraumID(int id) {
        try {
            ResultSet rs = MySQL.mysql.query("SELECT * FROM HistoryMutes WHERE ID = '" + id + "';");
            while (rs.next()) {
                String zeitraum = rs.getString("ZEITRAUM");
                return zeitraum;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static UUID getUUIDID(int id) {

        try {
            ResultSet rs = MySQL.mysql.query("SELECT * FROM HistoryMutes WHERE ID = '" + id + "';");
            while (rs.next()) {
                String uuid = rs.getString("UUID");
                UUID u = UUID.fromString(uuid);
                return u;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;

    }

    public static String getVonID(int id) {

        try {
            ResultSet rs = MySQL.mysql.query("SELECT * FROM HistoryMutes WHERE ID = '" + id + "';");
            while (rs.next()) {
                String von = rs.getString("VON");
                return von;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;

    }

    public static String getGrundID(int id) {
        try {
            ResultSet rs = MySQL.mysql.query("SELECT * FROM HistoryMutes WHERE ID = '" + id + "';");
            while (rs.next()) {
                String grund = rs.getString("GRUND");
                return grund;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;

    }

    public static String getName(int id) {
        try {
            ResultSet rs = MySQL.mysql.query("SELECT * FROM HistoryMutes WHERE ID = '" + id + "';");
            while (rs.next()) {
                String name = rs.getString("SPIELERNAME");
                return name;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;

    }



}
