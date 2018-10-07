package me.DevQuicks.FluuAPI.manager;

import de.dytanic.cloudnet.api.CloudAPI;
import me.DevQuicks.FluuAPI.APIs.*;
import me.DevQuicks.FluuAPI.bansystem.*;
import me.DevQuicks.FluuAPI.utils.HistoryBan;
import me.DevQuicks.FluuAPI.utils.HistoryMutes;
import me.DevQuicks.FluuAPI.utils.HistoryReports;
import me.DevQuicks.FluuAPI.utils.Onlinetime;
import me.DevQuicks.NickSystem.manager.NickManager;
import org.bukkit.Bukkit;

import java.util.List;
import java.util.UUID;

public class FluuPlayer {


    private String name;
    private UUID uuid;

    private String rang;

    private int coins;


    public FluuPlayer (UUID uuid) {
        this.uuid = uuid;
        this.name = InfoAPI.getName(this.uuid);
        this.rang = CloudAPI.getInstance().getOnlinePlayer(uuid).getPermissionEntity().getGroups().iterator().next().getGroup();
        this.coins = CoinsAPI.getCoins(uuid.toString());

    }

    public FluuPlayer (String name) {
        this.name = name;
        this.uuid = InfoAPI.getUUID(name);
        this.rang = CloudAPI.getInstance().getOnlinePlayer(uuid).getPermissionEntity().getGroups().iterator().next().getGroup();
        this.coins = CoinsAPI.getCoins(uuid.toString());
    }

    public void update() {
        this.rang = CloudAPI.getInstance().getOnlinePlayer(uuid).getPermissionEntity().getGroups().iterator().next().getGroup();
        this.coins = CoinsAPI.getCoins(uuid.toString());
    }


    public List<UUID> getFriends() {
        return FriendAPI.getAllFriends(uuid);
    }

    public List<UUID> getFriendRequests() {
        return RequestAPI.getAllRequests(uuid);
    }


    public String getName() {
        return name;
    }

    public UUID getUUID() {
        return uuid;
    }

    public int getCoins() {
        return coins;
    }

    public int getReportsAnzahl() {
        return getReportHistory().size();
    }

    public int getBansAnzahl() {
        return getBanHistory().size();
    }

    public int getMutesAnzahl() {
        return getMuteHistory().size();
    }

    public Onlinetime getOnlinetime() {
        return new Onlinetime(OnlinetimeAPI.getOnlinetime(uuid.toString()));
    }

    public boolean isOnline() {
        return InfoAPI.isOnline(uuid);
    }

    public String getRang() {
        return rang;
    }

    public boolean inBanned() {
        return MySQLBanManager.isBanned(uuid);
    }

    public boolean isNicked() {
        if(isOnline()) {
            return NickManager.isNicked(Bukkit.getPlayer(uuid));
        } else {
            return false;
        }
    }

    public boolean isAutonick() {
        return NickAPI.isAutonick(uuid.toString());
    }

    public List<HistoryReports> getReportHistory() {
        return MySQLHistoryReports.getReportHistory(uuid.toString());
    }

    public List<HistoryBan> getBanHistory() {
        return MySQLHitoryBans.getBanHistory(uuid.toString());
    }

    public List<HistoryMutes> getMuteHistory() {
        return MySQLHistoryMutes.getMuteHistory(uuid.toString());
    }

    public boolean inMuted() {
        return MySQLMuteManager.isMuted(uuid);
    }


}
