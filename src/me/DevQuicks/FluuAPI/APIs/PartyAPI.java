package me.DevQuicks.FluuAPI.APIs;

import me.DevQuicks.FluuAPI.mysql.MySQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class PartyAPI {


    public static int getParty(String uuid) {
        try {
            ResultSet rs = MySQL.mysql.query("SELECT * FROM Party WHERE UUID = '" + uuid + "';");
            while (rs.next()) {
                int id = rs.getInt("ID");
                return id;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }


    public static void addParty(int id, String uuid) {
        if (!isInParty(uuid)) {
            MySQL.mysql.update("INSERT INTO Party(ID, UUID) VALUES ('" + id + "', '" + uuid + "');");
        }
    }



    public static void removeParty(String uuid) {
        MySQL.mysql.update("DELETE FROM Party WHERE UUID = '" + uuid + "'");
    }


    public static boolean isInParty(String uuid) {
        try {
            ResultSet rs = MySQL.mysql.query("SELECT * FROM Party WHERE UUID= '" + uuid + "'");
            if (rs.next()) {
                return rs.getString("UUID") != null;
            }

            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static List<String> getAllMembersInParty(int id) {
        List<String> memebers = new ArrayList<>();
        try {
            ResultSet rs = MySQL.mysql.query("SELECT * FROM Party WHERE ID = '" + id + "';");
            while (rs.next()) {
               memebers.add(rs.getString("UUID"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return memebers;
    }


    public static int getLeiterSize(int id) {
        int i = 0;
        for (String uuid : getAllMembersInParty(id)) {
            if (isLeiter(uuid)) {
                i++;
            }
        }
        return i;
    }

    public static int getModeratorSize(int id) {
        int i = 0;
        for (String uuid : getAllMembersInParty(id)) {
            if (isMod(uuid)) {
                i++;
            }
        }
        return i;
    }


    public static boolean hasInvite(int id, String uuid) {
        try {
            ResultSet rs = MySQL.mysql.query("SELECT * FROM PartyInvites WHERE UUID= '" + uuid + "' AND ID = '" + id + "'");
            if (rs.next()) {
                return rs.getString("UUID") != null;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }


    public static void addToInvite(String uuid, String partymember) {
        if (!hasInvite(getParty(uuid), partymember)) {
            MySQL.mysql.update("INSERT INTO PartyInvites(ID, UUID, VON) VALUES ('" + getParty(uuid) + "', '" + partymember + "', '" + uuid + "');");
        }
    }


    public static List<Integer> getAllInvites(String uuid) {
        List<Integer> ids = new ArrayList<>();
        try {
            ResultSet rs = MySQL.mysql.query("SELECT * FROM PartyInvites WHERE UUID = '" + uuid + "';");
            while (rs.next()) {
                ids.add(rs.getInt("ID"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ids;
    }

    public static String getInviteVon(String uuid) {
        try {
            ResultSet rs = MySQL.mysql.query("SELECT * FROM PartyInvites WHERE UUID = '" + uuid + "';");
            while (rs.next()) {
                String von = rs.getString("VON");
                return von;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static void removeFromInvite(int id, String uuid) {
        if (hasInvite(id, uuid)) {
            MySQL.mysql.update("DELETE FROM PartyInvites WHERE UUID = '" + uuid + "' AND ID = '" + id + "'");
        }
    }


    public static boolean isMod(String uuid) {
        try {
            ResultSet rs = MySQL.mysql.query("SELECT * FROM PartyMod WHERE UUID= '" + uuid + "'");
            if (rs.next()) {
                return rs.getString("UUID") != null;
            }

            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static void addMod(String uuid) {
        if (!isMod(uuid)) {
            MySQL.mysql.update("INSERT INTO PartyMod(UUID) VALUES ('" + uuid + "');");
        }
    }


    public static void removeMod(String uuid) {
        if (isMod(uuid)) {
            MySQL.mysql.update("DELETE FROM PartyMod WHERE UUID = '" + uuid + "'");
        }
    }


    public static boolean isLeiter(String uuid) {
        try {
            ResultSet rs = MySQL.mysql.query("SELECT * FROM PartyLeiter WHERE UUID= '" + uuid + "'");
            if (rs.next()) {
                return rs.getString("UUID") != null;
            }

            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void addLeiter(String uuid) {
        if (!isLeiter(uuid)) {
            MySQL.mysql.update("INSERT INTO PartyLeiter(UUID) VALUES ('" + uuid + "');");
        }
    }


    public static void removeAllInvites(String uuid) {
        for(int id : getAllInvites(uuid)) {
            MySQL.mysql.update("DELETE FROM PartyInvites WHERE UUID = '" + uuid + "' AND ID = '" + id + "'");
        }
    }


    public static void removeLeiter(String uuid) {
        MySQL.mysql.update("DELETE FROM PartyLeiter WHERE UUID = '" + uuid + "'");
    }


}
