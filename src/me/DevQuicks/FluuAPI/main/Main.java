package me.DevQuicks.FluuAPI.main;

import me.DevQuicks.FluuAPI.mysql.MySQL;
import me.DevQuicks.FluuAPI.mysql.MySQLFile;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{

    private static MySQLFile mySQLFile;

    @Override
    public void onEnable() {
        connectMySQL();
        registerEvents();
    }

    private void registerEvents() {
        PluginManager pm = Bukkit.getPluginManager();
    }

    private void connectMySQL() {
        mySQLFile = new MySQLFile();
        mySQLFile.setStandart();
        mySQLFile.readData();

        MySQL.mysql.update("CREATE TABLE IF NOT EXISTS PartyInvites (ID INT(100), UUID VARCHAR(100), VON VARCHAR(100))");
        MySQL.mysql.update("CREATE TABLE IF NOT EXISTS Party (ID INT(100), UUID VARCHAR(100))");
        MySQL.mysql.update("CREATE TABLE IF NOT EXISTS Autonick (UUID VARCHAR(100), AKTIV INT(100))");
        MySQL.mysql.update("CREATE TABLE IF NOT EXISTS Onlinetime (UUID VARCHAR(100), MINUTES INT(100))");
        MySQL.mysql.update("CREATE TABLE IF NOT EXISTS PartyLeiter (UUID VARCHAR(100))");
        MySQL.mysql.update("CREATE TABLE IF NOT EXISTS PartyMod (UUID VARCHAR(100))");

        MySQL.mysql.update("CREATE TABLE IF NOT EXISTS HistoryBan (ID INT(100), UUID VARCHAR(100), Spielername VARCHAR(100), Grund VARCHAR(100), Von VARCHAR(100), Datum VARCHAR(100), Zeitraum VARCHAR(100), Ablauf VARCHAR(100))");
        MySQL.mysql.update("CREATE TABLE IF NOT EXISTS HistoryMutes (ID INT(100), UUID VARCHAR(100), Spielername VARCHAR(100), Mute VARCHAR(100), Grund VARCHAR(100), Von VARCHAR(100), Zeitraum VARCHAR(100))");
        MySQL.mysql.update("CREATE TABLE IF NOT EXISTS Bans (UUID VARCHAR(100), Spielername VARCHAR(100), Ban VARCHAR(100), Grund VARCHAR(100), Von VARCHAR(100), Ende INT(100), Datum VARCHAR(100), Zeitraum VARCHAR(100), Ablauf VARCHAR(100), Ip VARCHAR(100))");
        MySQL.mysql.update("CREATE TABLE IF NOT EXISTS Mutes (UUID VARCHAR(100), Spielername VARCHAR(100), Mute VARCHAR(100), Grund VARCHAR(100), Von VARCHAR(100), Ende INT(100))");
        MySQL.mysql.update("CREATE TABLE IF NOT EXISTS Friends (UUID varchar(100), FRIENDUUID varchar(100))");
        MySQL.mysql.update("CREATE TABLE IF NOT EXISTS HistoryReports (ID INT(100), UUID VARCHAR(100), Spielername VARCHAR(100), Mute VARCHAR(100), Grund VARCHAR(100), Von VARCHAR(100))");
        MySQL.mysql.update("CREATE TABLE IF NOT EXISTS Reports (ID INT(100), UUID VARCHAR(100), Spielername VARCHAR(100), Mute VARCHAR(100), Grund VARCHAR(100), Von VARCHAR(100))");
        MySQL.mysql.update("CREATE TABLE IF NOT EXISTS Coins (UUID varchar(100), ANZAHL INT(100))");
        MySQL.mysql.update("CREATE TABLE IF NOT EXISTS Requests (UUID varchar(100), FRIENDUUID varchar(100))");
        MySQL.mysql.update("CREATE TABLE IF NOT EXISTS Info (UUID varchar(100), NAME varchar(100), SERVER varchar(100), ONLINE varchar(100), LASTONLINE varchar(100))");
        MySQL.mysql.update("CREATE TABLE IF NOT EXISTS Settings (UUID varchar(100), NACHJOINEN varchar(100), PARTY varchar(100), REQUESTS varchar(100), MSG varchar(100), JOINMESSAGES varchar(100))");
    }

    private void disconnectMySQL() {
        MySQL.mysql.close();
    }


    @Override
    public void onDisable() {
        disconnectMySQL();
    }


}
