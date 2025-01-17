package it.tristana.commons.config;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import it.tristana.commons.helper.CommonsHelper;

public abstract class Config {

	protected FileConfiguration fileConfiguration;
	private final File file;
	JavaPlugin plugin;

	public Config(final File file) {
		this(file, null);
	}

	// I have no idea why this works
	// but it does, I won't touch it
	Config(final File file, final JavaPlugin plugin) {
		this.file = file;
		this.plugin = plugin;
		if (file.exists()) {
			FileConfiguration tmp = YamlConfiguration.loadConfiguration(file);
			fileConfiguration = YamlConfiguration.loadConfiguration(file);
			createDefault();
			if (shouldUpdateConfig(tmp)) {
				updateConfig(fileConfiguration, tmp);
			}
			fileConfiguration = YamlConfiguration.loadConfiguration(file);
			return;
		}

		try {
			file.createNewFile();
			fileConfiguration = YamlConfiguration.loadConfiguration(file);
			createDefault();
			fileConfiguration.save(file);
		} catch (IOException e) {
			CommonsHelper.consoleInfo("&cCan't save configuration file \"" + file.getPath() + "\"!");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private void updateConfig(FileConfiguration defaultConfig, FileConfiguration old) {
		Map<String, Object> defaultMap = defaultConfig.getValues(true);
		Set<String> defaultKeySet = defaultMap.keySet();
		Set<String> oldKeySet = old.getValues(true).keySet();
		String firstVoice = null;
		boolean addedFirstVoice = false;
		for (String defaultKey : defaultKeySet) {
			boolean contains = false;
			for (String oldKey : oldKeySet) {
				if (oldKey.equals(defaultKey)) {
					contains = true;
					break;
				}
			}
			if (!contains) {
				old.set(defaultKey, defaultMap.get(defaultKey));
				if (!addedFirstVoice) {
					firstVoice = defaultKey;
					addedFirstVoice = true;
				}
			}
		}
		if (firstVoice != null) {
			try {
				old.save(file);
				CommonsHelper.consoleInfo("&6Added new configuration entries in file \"" + file.getPath() + "\" from \"" + firstVoice + "\" to below");
			} catch (IOException e) {
				CommonsHelper.consoleInfo("&cCan't save configuration file \"" + file.getPath() + "\"!");
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
	}

	public final String getString(String key) {
		String result = fileConfiguration.getString(key);
		if (result != null) {
			result = CommonsHelper.format(result);
		}
		return result;
	}

	public final String getRawString(String key) {
		return fileConfiguration.getString(key);
	}

	public final List<String> getList(String key) {
		List<String> result = fileConfiguration.getStringList(key);
		for (int i = 0; i < result.size(); i ++) {
			result.set(i, CommonsHelper.format(result.get(i)));
		}
		return result;
	}

	public final List<String> getRawList(String key) {
		return fileConfiguration.getStringList(key);
	}

	public final ConfigurationSection getSection(String key) {
		return fileConfiguration.getConfigurationSection(key);
	}

	public final ConfigurationSection getRoot() {
		return fileConfiguration.getRoot();
	}

	public final List<Map<String, String>> getMapList(String key, boolean colorValues) {
		List<Map<String, String>> result = new ArrayList<>();
		fileConfiguration.getMapList(key).forEach(map -> {
			Map<String, String> resultMap = new HashMap<>();
			map.entrySet().forEach(entry -> {
				if (entry.getKey() instanceof String mapKey && entry.getValue() instanceof String mapValue) {
					resultMap.put(mapKey, colorValues ? CommonsHelper.format(mapValue) : mapValue);
				}
			});
			result.add(resultMap);
		});
		return result;
	}

	public final void set(String key, Object obj) {
		fileConfiguration.set(key, obj);
	}

	public final void save() throws IOException {
		fileConfiguration.save(file);
	}

	protected boolean shouldUpdateConfig(FileConfiguration fileConfig) {
		return true;
	}

	protected abstract void createDefault();
}
