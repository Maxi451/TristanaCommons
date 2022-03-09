package it.tristana.commons.helper;

import java.lang.reflect.Constructor;

public class EntryConstructor<T> {

	public final Class<? extends T> clazz;
	public final Constructor<? extends T> constructor;
	
	public EntryConstructor(Class<? extends T> clazz, Class<?>[] constructorParameters) throws NoSuchMethodException {
		this.clazz = clazz;
		constructor = clazz.getConstructor(constructorParameters);
	}
}
