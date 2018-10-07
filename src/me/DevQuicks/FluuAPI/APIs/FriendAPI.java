package me.DevQuicks.FluuAPI.APIs;






import me.DevQuicks.FluuAPI.mysql.MySQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FriendAPI {

    public static boolean isFriend(UUID uuid, UUID friend) {

        try {
            ResultSet rs = MySQL.mysql.query("SELECT * FROM Friends WHERE UUID= '" + uuid + "' AND FRIENDUUID = '" + friend + "'");
            if (rs.next()) {
                return rs.getString("UUID") != null;
            }

            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }

    public static void addFriend(UUID uuid, UUID friend) {
        if (!isFriend(uuid, friend)) {
            MySQL.mysql.update("INSERT INTO Friends(UUID, FRIENDUUID) VALUES ('" + uuid + "', '" + friend + "');");

        }
    }


    public static List<UUID> getAllFriends(UUID uuid) {
        List<UUID> friendlist = new ArrayList<>();
        try {
            ResultSet rs = MySQL.mysql.query("SELECT * FROM Friends WHERE UUID = '" + uuid + "';");
            while (rs.next()) {
                String frienduuid = rs.getString("FRIENDUUID");
                UUID u = UUID.fromString(frienduuid);
                friendlist.add(u);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return friendlist;
    }

    public static void removeFriend(UUID uuid, UUID friend) {
        if (isFriend(uuid, friend)) {
            MySQL.mysql.update("DELETE FROM Friends WHERE UUID = '" + uuid + "' AND FRIENDUUID = '" + friend + "'");

        }
    }

}
