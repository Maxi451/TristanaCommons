package it.tristana.commons.config;

import java.io.File;
import java.util.Arrays;

public class ConfigTeams extends Config {

	public static final String TEAMS = "teams";
	
	public ConfigTeams(File folder) {
		super(new File(folder, "teams.yml"));
	}

	@Override
	protected void createDefault() {
		set(TEAMS, Arrays.asList(
				"Red",
				"Blue",
				"Green",
				"Yellow",
				"White",
				"Grey",
				"Fuchsia",
				"Aqua"
		));
	}
}
