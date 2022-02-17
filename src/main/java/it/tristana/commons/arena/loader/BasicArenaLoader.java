package it.tristana.commons.arena.loader;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import it.tristana.commons.helper.CommonsHelper;
import it.tristana.commons.interfaces.arena.Arena;
import it.tristana.commons.interfaces.arena.ArenaLoader;

public abstract class BasicArenaLoader<A extends Arena<?>> implements ArenaLoader<A> {
	
	protected final FileConfiguration fileConfiguration;
	
	private final File file;
	
	public BasicArenaLoader(File file) {
		this.file = file;
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				CommonsHelper.consoleInfo("&cCan't save configuration file \"" + file.getAbsolutePath() + "\"!");
				throw new RuntimeException(e);
			}
		}
		fileConfiguration = YamlConfiguration.loadConfiguration(file);
	}

	@Override
	public void saveArenas(Collection<A> arenas) {
		arenas.forEach(arena -> saveArena(arena));
		for (A arena : arenas) {
			saveArena(arena);
		}
		try {
			fileConfiguration.save(file);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public Collection<A> loadArenas() {
		ConfigurationSection section = fileConfiguration.getConfigurationSection(ARENAS);
		Set<A> arenas = new HashSet<>();
		if (section != null) {
			for (String key : section.getKeys(false)) {
				A arena = loadArena(key);
				if (arena != null) {
					arenas.add(arena);
				}
			}
		}
		return arenas;
	}
	
	@Override
	public String getRoot(String name) {
		return ARENAS + "." + name + ".";
	}
	
	protected Location getLocation(String root, World world) {
		root += ".";
		double x = Double.parseDouble(fileConfiguration.getString(root + X));
		double y = Double.parseDouble(fileConfiguration.getString(root + Y));
		double z = Double.parseDouble(fileConfiguration.getString(root + Z));
		float yaw;
		float pitch;
		try {
			yaw = Float.parseFloat(fileConfiguration.getString(root + YAW));
			pitch = Float.parseFloat(fileConfiguration.getString(root + PITCH));
		} catch (Exception e) {
			yaw = 0;
			pitch = 0;
		}
		return new Location(world, x, y, z, yaw, pitch);
	}
	
	protected void setLocation(String root, Location location) {
		if (!root.endsWith(".")) {
			root += ".";
		}
		fileConfiguration.set(root + X, location.getX());
		fileConfiguration.set(root + Y, location.getY());
		fileConfiguration.set(root + Z, location.getZ());
		fileConfiguration.set(root + YAW, location.getYaw());
		fileConfiguration.set(root + PITCH, location.getPitch());
	}
	
	protected void set(String root, Object obj) {
		fileConfiguration.set(root, obj);
	}
}
