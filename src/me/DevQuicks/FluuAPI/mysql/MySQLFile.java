package me.DevQuicks.FluuAPI.mysql;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class MySQLFile {

	
	public void setStandart() {
		FileConfiguration cfg = getFileConfiguration();
		
		cfg.options().copyDefaults(true);
		cfg.addDefault("host", "");
		cfg.addDefault("port", "3306");
		cfg.addDefault("username", "");
		cfg.addDefault("database", "");
		cfg.addDefault("password", "");
		
		
		try {
			cfg.save(getFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	private FileConfiguration getFileConfiguration() {
		return YamlConfiguration.loadConfiguration(getFile());
		
	}
	private File getFile() {
		return new File("plugins/FluuAPI", "mysql.yml");
	}
	
	public void readData() {
		FileConfiguration cfg = getFileConfiguration();
		MySQL.mysql = new MySQL(cfg.getString("host"), cfg.getString("database"), cfg.getString("username"),
				cfg.getString("password"));
			
		
	}

}
