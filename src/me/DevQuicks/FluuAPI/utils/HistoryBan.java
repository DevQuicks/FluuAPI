package me.DevQuicks.FluuAPI.utils;

import me.DevQuicks.FluuAPI.bansystem.MySQLHitoryBans;

public class HistoryBan {


    private String uuid;
    private String name;
    private String grund;
    private String von;
    private String zeitraum;
    private String datum;
    private String ablauf;
    private int id;

    public HistoryBan(int id) {
        this.uuid = MySQLHitoryBans.getUUID(id);
        this.name = MySQLHitoryBans.getName(id);
        this.ablauf = MySQLHitoryBans.getAblaufID(id);
        this.datum = MySQLHitoryBans.getDatumID(id);
        this.von = MySQLHitoryBans.getVonID(id);
        this.zeitraum = MySQLHitoryBans.getZeitraumID(id);
        this.grund = MySQLHitoryBans.getGrundID(id);
        this.id = id;
    }

    public String getUUID() {
        return uuid;
    }

    public String getAblauf() {
        return ablauf;
    }

    public String getDatum() {
        return datum;
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
