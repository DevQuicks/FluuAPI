package me.DevQuicks.FluuAPI.bansystem;

import me.DevQuicks.FluuAPI.mysql.MySQL;
import me.DevQuicks.FluuAPI.utils.HistoryBan;

import java.sql.ResultSet;
import java.util.*;

public class MySQLHitoryBans {


    public static void addBan(int id, UUID uuid, String playername, String grund, String von, String zeitraum, String datum, String ablauf) {

        MySQL.mysql.update(
                "INSERT INTO HistoryBan(ID, UUID, SPIELERNAME, GRUND, VON, DATUM, ZEITRAUM, ABLAUF) VALUES ('" + id + "','"
                        + uuid + "', '" + playername + "', '" + grund + "', '" + von + "', '" + datum
                        + "', '" + zeitraum + "', '" + ablauf + "');");


    }

    public static List<HistoryBan> getBanHistory(String uuid) {
        List<Integer> bans = new ArrayList<>();
        try {
            ResultSet rs = MySQL.mysql.query("SELECT * FROM HistoryBan WHERE UUID = '" + uuid + "';");
            while (rs.next()) {
                bans.add(rs.getInt("ID"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        List<HistoryBan> historyBans = new ArrayList<>();
        for(int id : bans) {
            historyBans.add(new HistoryBan(id));
        }
        return historyBans;
    }

    public static String getName(int id) {
        try {
            ResultSet rs = MySQL.mysql.query("SELECT * FROM HistoryBan WHERE ID = '" + id + "';");
            while (rs.next()) {
                String name = rs.getString("SPIELERNAME");
                return name;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static String getUUID(int id) {
        try {
            ResultSet rs = MySQL.mysql.query("SELECT * FROM HistoryBan WHERE ID = '" + id + "';");
            while (rs.next()) {
                String uuid = rs.getString("UUID");
                return uuid;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static String getZeitraumID(int id) {
        try {
            ResultSet rs = MySQL.mysql.query("SELECT * FROM HistoryBan WHERE ID = '" + id + "';");
            while (rs.next()) {
                String zeitraum = rs.getString("ZEITRAUM");
                return zeitraum;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static String getVonID(int id) {

        try {
            ResultSet rs = MySQL.mysql.query("SELECT * FROM HistoryBan WHERE ID = '" + id + "';");
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
            ResultSet rs = MySQL.mysql.query("SELECT * FROM HistoryBan WHERE ID = '" + id + "';");
            while (rs.next()) {
                String grund = rs.getString("GRUND");
                return grund;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;

    }
    public static String getDatumID(int id) {
        try {
            ResultSet rs = MySQL.mysql.query("SELECT * FROM HistoryBan WHERE ID = '" + id + "';");
            while (rs.next()) {
                String datum = rs.getString("DATUM");
                return datum;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;

    }

    public static String getAblaufID(int id) {
        try {
            ResultSet rs = MySQL.mysql.query("SELECT * FROM HistoryBan WHERE ID = '" + id + "';");
            while (rs.next()) {
                String ablauf = rs.getString("ABLAUF");
                return ablauf;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;

    }
}


