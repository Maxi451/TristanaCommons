package it.tristana.commons.bungeecord;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public abstract class Config {

	protected static final ConfigurationProvider provider = ConfigurationProvider.getProvider(YamlConfiguration.class);

	private File file;
	protected Configuration config;

	public Config(File file) throws IOException {
		this.file = file;
		if (file.exists()) {
			this.config = provider.load(file);
			return;
		}

		file.createNewFile();
		this.config = provider.load(file);
		createDefault();
		save();
	}

	public String getString(String key) {
		return parseString(config.getString(key));
	}

	public List<String> getList(String key) {
		return config.getStringList(key).stream().map(Config::parseString).collect(Collectors.toList());
	}

	public void set(String key, Object obj) {
		config.set(key, obj);
	}

	public void save() throws IOException {
		provider.save(config, file);
	}

	private static String parseString(String line) {
		return ChatColor.translateAlternateColorCodes('&', line);
	}

	protected abstract void createDefault();
}
