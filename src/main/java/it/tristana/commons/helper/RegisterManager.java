package it.tristana.commons.helper;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

public abstract class RegisterManager<T> {
	
	protected PluginDraft plugin;
	protected List<EntryConstructor<? extends T>> entries;
	
	public RegisterManager(PluginDraft plugin) {
		this.plugin = plugin;
		entries = new ArrayList<>();
	}
	
	public void register(Class<? extends T> clazz) throws NoSuchMethodException {
		for (EntryConstructor<?> entry : entries) {
			if (entry.clazz == clazz) {
				return;
			}
		}
		entries.add(new EntryConstructor<>(clazz, getConstructorParameters()));
	}
	
	public <U extends T> U getInstance(Class<U> clazz) {
		Constructor<U> constructor = getConstructor(clazz);
		try {
			return constructor == null ? null : constructor.newInstance(getConstructorArgs());
		} catch (Exception e) {
			plugin.writeThrowableOnErrorsFile(e);
		}
		return null;
	}
	
	public T getRandom() {
		int size = entries.size();
		return size > 0 ? getInstance(entries.get((int) (Math.random() * size)).clazz) : null;
	}
	
	@SuppressWarnings("unchecked")
	private <U extends T> Constructor<U> getConstructor(Class<U> clazz) {
		for (EntryConstructor<?> entry : entries) {
			if (entry.clazz == clazz) {
				return (Constructor<U>) entry.constructor;
			}
		}
		return null;
	}
	
	protected abstract Object[] getConstructorArgs();
	
	protected abstract Class<?>[] getConstructorParameters();
}
