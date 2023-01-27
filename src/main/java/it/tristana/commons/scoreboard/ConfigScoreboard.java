package it.tristana.commons.scoreboard;

import java.io.File;
import java.util.Arrays;

import it.tristana.commons.config.Config;

public class ConfigScoreboard extends Config {

	public static final String NAME = "name";
	public static final String LINES = "lines";

	public ConfigScoreboard(File file) {
		super(file);
	}

	@Override
	protected void createDefault() {
		set(NAME, "Scoreboard");
		set(LINES, Arrays.asList("line1", "line", "line3"));
	}
}
