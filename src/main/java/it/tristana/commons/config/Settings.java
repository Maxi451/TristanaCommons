package it.tristana.commons.config;

import it.tristana.commons.interfaces.Reloadable;

public abstract class Settings<C extends Config> implements Reloadable {

	protected C config;
	
	public Settings(C config) {
		setConfig(config);
	}
	
	public final void setConfig(C config) {
		this.config = config;
		reload();
	}
}
