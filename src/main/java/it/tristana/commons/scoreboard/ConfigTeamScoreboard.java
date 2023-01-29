package it.tristana.commons.scoreboard;

import java.io.File;
import java.util.Arrays;

public class ConfigTeamScoreboard extends ConfigScoreboard {

	public static final String TEAMS_PLACEHOLDER = "{teams}";

	public ConfigTeamScoreboard(File file) {
		super(file);
	}

	@Override
	protected void createDefault() {
		super.createDefault();
		set(LINES, Arrays.asList("header1", "header2", TEAMS_PLACEHOLDER, "footer"));
	}
}
