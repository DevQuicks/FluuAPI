package me.DevQuicks.FluuAPI.bansystem;

import me.DevQuicks.FluuAPI.mysql.MySQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class MySQLBanManager {

	public static boolean isUserExists(UUID uuid) {
		try {
			ResultSet rs = MySQL.mysql.query("SELECT * FROM Bans WHERE UUID= '" + uuid + "'");
			if (rs.next()) {
				return rs.getString("UUID") != null;
			}

			return false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;

	}

	@SuppressWarnings("deprecation")
	public static void banPlayer(UUID uuid, String playername, boolean ban, String grund, String von, long end,
			String ip, String zeitraum, String replayuuid) {

		if (isUserExists(uuid)) {

			if(isBanned(uuid) && grund == getGrund(uuid)) {
				return;
			}
			MySQL.mysql.update("UPDATE Bans SET DATUM= '' WHERE UUID = '" + uuid + "';");
			MySQL.mysql.update("UPDATE Bans SET ZEITRAUM= '" + zeitraum + "' WHERE UUID = '" + uuid + "';");
			MySQL.mysql.update("UPDATE Bans SET BAN= '" + ban + "' WHERE UUID = '" + uuid + "';");
			MySQL.mysql.update("UPDATE Bans SET GRUND= '" + grund + "' WHERE UUID = '" + uuid + "';");
			MySQL.mysql.update("UPDATE Bans SET VON= '" + von + "' WHERE UUID = '" + uuid + "';");
			MySQL.mysql.update("UPDATE Bans SET ENDE= '" + end + "' WHERE UUID = '" + uuid + "';");
			
			if (ban) {

				if (Integer.parseInt(zeitraum) != -1) {

					int d = Integer.parseInt(zeitraum);
					Date now = new Date();
					Calendar calendar = new GregorianCalendar();
					calendar.setTime(now);
					calendar.add(Calendar.DAY_OF_MONTH, d);
					Date future = calendar.getTime();
					DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

					MySQL.mysql.update(
							"UPDATE Bans SET ABLAUF= '" + dateFormat.format(future) + "' WHERE UUID = '" + uuid + "';");
					int month = Calendar.getInstance().getTime().getMonth() + 1;
					String day = "" + Calendar.getInstance().getTime().getDate() + "." + month + ".2018 ";

					MySQL.mysql.update("UPDATE Bans SET DATUM= '" + day + "' WHERE UUID = '" + uuid + "';");

					MySQLHitoryBans.addBan(new Random().nextInt(1000000), uuid, playername, grund, von, zeitraum, dateFormat.format(future), day);
				} else {
					int month = Calendar.getInstance().getTime().getMonth() + 1;
					String day = "" + Calendar.getInstance().getTime().getDate() + "." + month + ".2018 ";

					MySQL.mysql.update("UPDATE Bans SET DATUM= '" + day + "' WHERE UUID = '" + uuid + "';");

					MySQLHitoryBans.addBan(new Random().nextInt(1000000), uuid, playername, grund, von, zeitraum, "PERMANENT", day);
					MySQL.mysql.update("UPDATE Bans SET ABLAUF= 'PERMANENT' WHERE UUID = '" + uuid + "';");
				}
			}
		} else {
			createPlayer(uuid, playername, ip);
			banPlayer(uuid, playername, ban, grund, von, end, ip, zeitraum, replayuuid);
		}
	}

	public static void unbanPlayerIp(UUID uuid, String playername, boolean ban, String grund, String von, long end,
			String ip) {
		if (isUserExists(uuid)) {
			MySQL.mysql.update("UPDATE Bans SET DATUM= '' WHERE UUID = '" + uuid + "';");
			MySQL.mysql.update("UPDATE Bans SET ZEITRAUM= '' WHERE UUID = '" + uuid + "';");
			MySQL.mysql.update("UPDATE Bans SET BAN= '" + ban + "' WHERE IP = '" + ip + "';");
			MySQL.mysql.update("UPDATE Bans SET GRUND= '" + grund + "' WHERE IP = '" + ip + "';");
			MySQL.mysql.update("UPDATE Bans SET VON= '" + von + "' WHERE IP = '" + ip + "';");
			MySQL.mysql.update("UPDATE Bans SET ENDE= '" + end + "' WHERE IP = '" + ip + "';");
			MySQL.mysql.update("UPDATE Bans SET ABLAUF= '' WHERE IP = '" + ip + "';");
		} else {
			createPlayer(uuid, playername, ip);
			banPlayer(uuid, playername, ban, grund, von, end, ip, "", "");
		}
	}

	public static void updatePlayername(UUID uuid, String playername) {
		if (isUserExists(uuid)) {
			MySQL.mysql.update("UPDATE Bans SET SPIELERNAME= '" + playername + "' WHERE UUID = '" + uuid + "';");
		}
	}

	public static void createPlayer(UUID uuid, String playername, String ip) {
		if (!isUserExists(uuid)) {
			MySQL.mysql.update(
					"INSERT INTO Bans(UUID, SPIELERNAME, BAN, GRUND, VON, ENDE, ZEITRAUM, DATUM, ABLAUF, IP) VALUES ('"
							+ uuid + "', '" + playername + "', 'false', '', '', '" + Long.valueOf(0L)
							+ "', '', '', '', '" + ip + "');");

		}
	}

	public static List<String> getAllBans() {
		List<String> bans = new ArrayList<>();
		try {
			ResultSet rs = MySQL.mysql.query("SELECT * FROM Bans WHERE BAN = 'true';");
			while (rs.next()) {
				bans.add(rs.getString("SPIELERNAME"));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return bans;
	}

	public static String getName(UUID u) {
		try {
			ResultSet rs = MySQL.mysql.query("SELECT * FROM Bans WHERE UUID = '" + u + "';");
			while (rs.next()) {
				String name = rs.getString("SPIELERNAME");
				return name;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static int getEnd(UUID u) {
		try {
			ResultSet rs = MySQL.mysql.query("SELECT * FROM Bans WHERE UUID = '" + u + "';");
			while (rs.next()) {
				int ende = rs.getInt("ENDE");
				return ende;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return 0;
	}

	public static UUID getUUID(String Spielername) {

		try {
			ResultSet rs = MySQL.mysql.query("SELECT * FROM Bans WHERE SPIELERNAME = '" + Spielername + "';");
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

	public static String getVon(UUID u) {

		try {
			ResultSet rs = MySQL.mysql.query("SELECT * FROM Bans WHERE UUID = '" + u + "';");
			while (rs.next()) {
				String von = rs.getString("VON");
				return von;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;

	}

	public static String getGrund(UUID u) {
		try {
			ResultSet rs = MySQL.mysql.query("SELECT * FROM Bans WHERE UUID = '" + u + "';");
			while (rs.next()) {
				String grund = rs.getString("GRUND");
				return grund;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;

	}

	public static String getIP(String ip) {
		try {
			ResultSet rs = MySQL.mysql.query("SELECT * FROM Bans WHERE IP = '" + ip + "';");
			while (rs.next()) {
				String ipget = rs.getString("IP");
				return ipget;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;

	}
	
	public static String getReplayUUIDIP(String ip) {
		try {
			ResultSet rs = MySQL.mysql.query("SELECT * FROM Bans WHERE IP = '" + ip + "';");
			while (rs.next()) {
				String ipget = rs.getString("REPLAYUUID");
				return ipget;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "";

	}
	
	public static String getReplayUUID(String uuid) {
		try {
			ResultSet rs = MySQL.mysql.query("SELECT * FROM Bans WHERE UUID = '" + uuid + "';");
			while (rs.next()) {
				String ipget = rs.getString("REPLAYUUID");
				return ipget;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "";

	}

	public static String getIPUUID(UUID u) {
		try {
			ResultSet rs = MySQL.mysql.query("SELECT * FROM Bans WHERE UUID = '" + u + "';");
			while (rs.next()) {
				String ipget = rs.getString("IP");
				return ipget;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;

	}

	public static boolean isBanned(UUID u) {
		if (isUserExists(u)) {
			try {
				ResultSet rs = MySQL.mysql.query("SELECT * FROM Bans WHERE UUID = '" + u + "';");
				while (rs.next()) {
					String status = rs.getString("BAN");
					if (status.equalsIgnoreCase("true")) {
						return true;
					} else {
						return false;
					}

				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return false;

	}

	public static String getZeitraumIp(String ip) {
		try {
			ResultSet rs = MySQL.mysql.query("SELECT * FROM Bans WHERE IP = '" + ip + "';");
			while (rs.next()) {
				String zeitraum = rs.getString("ZEITRAUM");
				return zeitraum;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static int getEndIp(String ip) {
		try {
			ResultSet rs = MySQL.mysql.query("SELECT * FROM Bans WHERE IP = '" + ip + "';");
			while (rs.next()) {
				int ende = rs.getInt("ENDE");
				return ende;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return 0;
	}

	public static String getVonIp(String ip) {

		try {
			ResultSet rs = MySQL.mysql.query("SELECT * FROM Bans WHERE IP = '" + ip + "';");
			while (rs.next()) {
				String von = rs.getString("VON");
				return von;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;

	}

	public static String getGrundIp(String ip) {
		try {
			ResultSet rs = MySQL.mysql.query("SELECT * FROM Bans WHERE IP = '" + ip + "';");
			while (rs.next()) {
				String grund = rs.getString("GRUND");
				return grund;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;

	}

	public static boolean isBannedIp(String ip) {
		try {
			ResultSet rs = MySQL.mysql.query("SELECT * FROM Bans WHERE IP = '" + ip + "';");
			while (rs.next()) {
				String status = rs.getString("BAN");
				if (status.equalsIgnoreCase("true")) {
					return true;
				} else {
					return false;
				}

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return false;

	}

}
