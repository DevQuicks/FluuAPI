package me.DevQuicks.FluuAPI.bansystem;

import me.DevQuicks.FluuAPI.mysql.MySQL;
import me.DevQuicks.FluuAPI.utils.HistoryReports;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MySQLHistoryReports {


    public static List<HistoryReports> getReportHistory(String uuid) {
        List<Integer> reports = new ArrayList<>();
        try {
            ResultSet rs = MySQL.mysql.query("SELECT * FROM HistoryReports WHERE UUID = '" + uuid + "';");
            while (rs.next()) {
                reports.add(rs.getInt("ID"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        List<HistoryReports> reporthistory = new ArrayList<>();
        for(int id : reports) {
            reporthistory.add(new HistoryReports(id));
        }
        return reporthistory;
    }


    public static void addReport(int id, UUID uuid, String playername, String grund, String von) {
        MySQL.mysql.update("INSERT INTO HistoryReports(ID, UUID, SPIELERNAME, GRUND, VON) VALUES ('" + id + "', '" + uuid
                + "', '" + playername + "', '" + grund + "', '"  + von + "');");


    }


    public static UUID getUUIDID(int id) {

        try {
            ResultSet rs = MySQL.mysql.query("SELECT * FROM HistoryReports WHERE ID = '" + id + "';");
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
            ResultSet rs = MySQL.mysql.query("SELECT * FROM HistoryReports WHERE ID = '" + id + "';");
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
            ResultSet rs = MySQL.mysql.query("SELECT * FROM HistoryReports WHERE ID = '" + id + "';");
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
            ResultSet rs = MySQL.mysql.query("SELECT * FROM HistoryReports WHERE ID = '" + id + "';");
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
