package it.tristana.commons.config;

import java.io.File;
import java.lang.reflect.Constructor;

import it.tristana.commons.interfaces.Reloadable;

public abstract class Settings<C extends Config> implements Reloadable {

	private Constructor<C> constructor;
	protected File folder;
	
	public Settings(File folder, Class<C> configClass) {
		try {
			constructor = configClass.getConstructor(File.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException("The config class must have a constructor accepting a single java.io.File parameter");
		}
		this.folder = folder;
		reload();
	}
	
	@Override
	public final void reload() {
		try {
			reload(constructor.newInstance(getConfigFileParameter()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected File getConfigFileParameter() {
		return folder;
	}
	
	protected abstract void reload(C config);
}
