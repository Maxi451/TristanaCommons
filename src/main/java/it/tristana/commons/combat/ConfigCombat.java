package it.tristana.commons.combat;

import java.io.File;

import it.tristana.commons.config.Config;

public class ConfigCombat extends Config {

	private static final String COMBAT_TAG = "combat-tag.";
	public static final String COMBAT_TAG_DURATION = COMBAT_TAG + "duration";
	public static final String COMBAT_TAG_ASSIST_DURATION = COMBAT_TAG + "assist-duration";
	
	/**
	 * Requires its file, NOT the parent folder
	 * @param file The yml file
	 */
	
	public ConfigCombat(File file) {
		super(file);
	}

	@Override
	protected void createDefault() {
		set(COMBAT_TAG_DURATION, "20000");
		set(COMBAT_TAG_ASSIST_DURATION, "10000");
	}
}
