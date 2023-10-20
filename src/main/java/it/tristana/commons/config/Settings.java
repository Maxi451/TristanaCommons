package it.tristana.commons.config;

import java.io.File;
import java.lang.reflect.Constructor;

import it.tristana.commons.interfaces.Reloadable;

public abstract class Settings<C extends Config> implements Reloadable {

	protected Constructor<C> constructor;
	protected File folder;

	public Settings(File folder, Class<C> configClass) {
		try {
			constructor = getConstructor(configClass);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException("The config class must have a constructor accepting a single java.io.File parameter, if Settings#getConstructor(Class) is not overridden");
		}
		this.folder = folder;
		reload();
	}
	
	@Override
	public final void reload() {
		try {
			reload(getNewInstance());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	protected Constructor<C> getConstructor(Class<C> configClass) throws Exception {
		return configClass.getConstructor(File.class);
	}
	
	protected C getNewInstance() throws Exception {
		return constructor.newInstance(getConfigFileParameter());
	}

	protected abstract void reload(C config);

	protected File getConfigFileParameter() {
		return folder;
	}
}
