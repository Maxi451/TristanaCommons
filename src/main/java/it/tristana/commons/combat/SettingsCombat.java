package it.tristana.commons.combat;

import java.io.File;

import it.tristana.commons.config.Settings;
import it.tristana.commons.helper.CommonsHelper;

public abstract class SettingsCombat<T extends ConfigCombat> extends Settings<T> {

	protected long combatTagDuration;
	protected long combatTagAssistDuration;
	
	public SettingsCombat(File folder, Class<T> configClass) {
		super(folder, configClass);
	}

	@Override
	protected void reload(T config) {
		combatTagDuration = CommonsHelper.parseLongOrGetDefault(config.getString(ConfigCombat.COMBAT_TAG_DURATION), 20000);
		combatTagAssistDuration = CommonsHelper.parseLongOrGetDefault(config.getString(ConfigCombat.COMBAT_TAG_ASSIST_DURATION), 10000);
	}
	
	@Override
	protected final File getConfigFileParameter() {
		return new File(folder, getFileName());
	}

	public long getCombatTagDuration() {
		return combatTagDuration;
	}

	public long getCombatTagAssistDuration() {
		return combatTagAssistDuration;
	}
	
	protected abstract String getFileName();
}
