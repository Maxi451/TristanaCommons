package it.tristana.commons.scoreboard;

import java.io.File;
import java.util.List;

import it.tristana.commons.config.Settings;

public class SettingsScoreboard<C extends ConfigScoreboard> extends Settings<C> {
	
	protected String name;
	protected List<String> lines;
	
	public SettingsScoreboard(File folder, Class<C> configClass) {
		super(folder, configClass);
	}

	@Override
	protected void reload(C config) {
		name = config.getString(ConfigScoreboard.NAME);
		lines = config.getList(ConfigScoreboard.LINES);
	}

	public String getName() {
		return name;
	}

	public List<String> getLines() {
		return lines;
	}
}
