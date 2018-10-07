package me.DevQuicks.FluuAPI.utils;

import me.DevQuicks.FluuAPI.bansystem.MySQLHistoryReports;

public class HistoryReports {

    private String uuid;
    private String name;
    private String grund;
    private String von;
    private int id;

    public HistoryReports(int id) {
        this.uuid = MySQLHistoryReports.getUUIDID(id).toString();
        this.name = MySQLHistoryReports.getName(id);
        this.von = MySQLHistoryReports.getVonID(id);
        this.grund = MySQLHistoryReports.getGrundID(id);
        this.id = id;
    }

    public String getUUID() {
        return uuid;
    }

    public String getGrund() {
        return grund;
    }

    public String getName() {
        return name;
    }

    public String getVon() {
        return von;
    }

    public int getId() {
        return id;
    }

}
