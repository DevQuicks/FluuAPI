package me.DevQuicks.FluuAPI.bansystem;

import me.DevQuicks.FluuAPI.mysql.MySQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class MySQLMuteManager {

	public static boolean isUserExists(UUID uuid) {
		try {
			ResultSet rs = MySQL.mysql.query("SELECT * FROM Mutes WHERE UUID= '" + uuid + "'");
			if (rs.next()) {
				return rs.getString("UUID") != null;
			}

			return false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;

	}

	public static List<String> getAllMutes() {
		List<String> bans = new ArrayList<>();
		try {
			ResultSet rs = MySQL.mysql.query("SELECT * FROM Mutes WHERE MUTE = 'true';");
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
			ResultSet rs = MySQL.mysql.query("SELECT * FROM Mutes WHERE UUID = '" + u + "';");
			while (rs.next()) {
				String name = rs.getString("SPIELERNAME");
				return name;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static void mutePlayer(UUID uuid, String playername, boolean ban, String grund, String von, long end, String zeitraum, String chatlog) {
		if (isUserExists(uuid)) {
			if(isMuted(uuid) && grund == getGrund(uuid)) {
				return;
			}

			MySQL.mysql.update("UPDATE Mutes SET MUTE= '" + ban + "' WHERE UUID = '" + uuid + "';");
			MySQL.mysql.update("UPDATE Mutes SET GRUND= '" + grund + "' WHERE UUID = '" + uuid + "';");
			MySQL.mysql.update("UPDATE Mutes SET VON= '" + von + "' WHERE UUID = '" + uuid + "';");
			MySQL.mysql.update("UPDATE Mutes SET ENDE= '" + end + "' WHERE UUID = '" + uuid + "';");
			MySQL.mysql.update("UPDATE Mutes SET DATUM= '' WHERE UUID = '" + uuid + "';");
			MySQL.mysql.update("UPDATE Mutes SET ZEITRAUM= '" + zeitraum + "' WHERE UUID = '" + uuid + "';");
			MySQL.mysql.update("UPDATE Mutes SET ABLAUF= '' WHERE UUID = '" + uuid + "';");

			MySQLHistoryMutes.addMute(new Random().nextInt(100000), uuid, playername, grund, von, zeitraum);
		
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
							"UPDATE Mutes SET ABLAUF= '" + dateFormat.format(future) + "' WHERE UUID = '" + uuid + "';");
				} else {
					MySQL.mysql.update("UPDATE Mutes SET ABLAUF= 'PERMANENT' WHERE UUID = '" + uuid + "';");
				}

				int month = Calendar.getInstance().getTime().getMonth() + 1;
				String day = "" + Calendar.getInstance().getTime().getDate() + "." + month + ".2018 ";

				MySQL.mysql.update("UPDATE Mutes SET DATUM= '" + day + "' WHERE UUID = '" + uuid + "';");
			}
				

		} else {
			createPlayer(uuid, playername);
			mutePlayer(uuid, playername, ban, grund, von, end, zeitraum, chatlog);
		}
	}
	public static void updatePlayername(UUID uuid, String playername) {
		if (isUserExists(uuid)) {
			MySQL.mysql.update("UPDATE Mutes SET SPIELERNAME= '" + playername + "' WHERE UUID = '" + uuid + "';");
		}
	}

	public static void createPlayer(UUID uuid, String playername) {
		if (!isUserExists(uuid)) {
			MySQL.mysql.update("INSERT INTO Mutes(UUID, SPIELERNAME, MUTE, GRUND, VON, ENDE) VALUES ('" + uuid
					+ "', '" + playername + "', 'false', '', '', '" + Long.valueOf(0L) + "');");

		}
	}

	public static int getEnd(UUID u) {
		try {
			ResultSet rs = MySQL.mysql.query("SELECT * FROM Mutes WHERE UUID = '" + u + "';");
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
			ResultSet rs = MySQL.mysql.query("SELECT * FROM Mutes WHERE SPIELERNAME = '" + Spielername + "';");
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
			ResultSet rs = MySQL.mysql.query("SELECT * FROM Mutes WHERE UUID = '" + u + "';");
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
			ResultSet rs = MySQL.mysql.query("SELECT * FROM Mutes WHERE UUID = '" + u + "';");
			while (rs.next()) {
				String grund = rs.getString("GRUND");
				return grund;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;

	}
	
	public static String getChatlogURL(UUID u) {
		try {
			ResultSet rs = MySQL.mysql.query("SELECT * FROM Mutes WHERE UUID = '" + u + "';");
			while (rs.next()) {
				String log = rs.getString("CHATLOGURL");
				return log;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "";

	}


	public static boolean isMuted(UUID u) {
		if (isUserExists(u)) {
			try {
				ResultSet rs = MySQL.mysql.query("SELECT * FROM Mutes WHERE UUID = '" + u + "';");
				while (rs.next()) {
					String status = rs.getString("MUTE");
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

}
