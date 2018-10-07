package me.DevQuicks.FluuAPI.utils;

import me.DevQuicks.FluuAPI.bansystem.MySQLHistoryMutes;

public class HistoryMutes {

    private String uuid;
    private String name;
    private String grund;
    private String von;
    private String zeitraum;
    private int id;

    public HistoryMutes(int id) {
        this.uuid = MySQLHistoryMutes.getUUIDID(id).toString();
        this.name = MySQLHistoryMutes.getName(id);
        this.von = MySQLHistoryMutes.getVonID(id);
        this.zeitraum = MySQLHistoryMutes.getZeitraumID(id);
        this.grund = MySQLHistoryMutes.getGrundID(id);
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

    public String getZeitraum() {
        return zeitraum;
    }

    public int getId() {
        return id;
    }

}
