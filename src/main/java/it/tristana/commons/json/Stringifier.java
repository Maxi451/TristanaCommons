package it.tristana.commons.json;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Function;

class Stringifier {

	private static final char[] toEscape = "\"\\/\b\f\n\r\t".toCharArray();
	static {
		Arrays.sort(toEscape);
	}
	
	private static final Map<Class<? extends Object>, Function<Object, String>> primitiveArraysToString = new HashMap<>();
	static {
		register(byte[].class, val -> Arrays.toString((byte[]) val));
		register(short[].class, val -> Arrays.toString((short[]) val));
		register(char[].class, val -> Arrays.toString((char[]) val));
		register(int[].class, val -> Arrays.toString((int[]) val));
		register(long[].class, val -> Arrays.toString((long[]) val));
		register(float[].class, val -> Arrays.toString((float[]) val));
		register(double[].class, val -> Arrays.toString((double[]) val));
	}

	private Stringifier() {}

	static String toString(Object object, Collection<Object> passedReferences) throws Exception {
		if (object instanceof Object[] array) {
			return toString(array, passedReferences);
		}

		if (object instanceof Collection collection) {
			return collectionToString(collection, passedReferences);
		}

		StringBuilder builder = new StringBuilder("{");
		Field[] fields = object.getClass().getDeclaredFields();
		boolean isFirst = true;
		for (int i = 0; i < fields.length; i ++) {
			if (!isFieldAllowed(fields[i])) {
				continue;
			}

			if (isFirst) {
				isFirst = false;
			} else {
				builder.append(", ");
			}

			builder
			.append('"')
			.append(fields[i].getName())
			.append("\": ")
			.append(toString(object, fields[i], passedReferences));
		}
		return builder.append('}').toString();
	}

	private static String toString(Object object, Field field, Collection<Object> passedReferences) throws Exception {
		field.setAccessible(true);
		Object value = field.get(object);

		if (value == null) {
			return "null";
		}

		Class<?> fieldType = field.getType();
		if (fieldType.isArray()) {
			Function<Object, String> toString = primitiveArraysToString.get(fieldType);
			if (toString != null) {
				return toString.apply(value);
			}

			return toString((Object[]) value, passedReferences);
		}

		if (object instanceof Collection collection) {
			return collectionToString(collection, passedReferences);
		}

		return toObjectString(value, passedReferences);
	}

	private static String toString(Object[] array, Collection<Object> passedReferences) throws Exception {
		StringBuilder builder = new StringBuilder("[");
		if (array.length > 0) {
			builder.append(toObjectString(array[0], passedReferences));
		}
		for (int i = 1; i < array.length; i ++) {
			builder.append(", ").append(toObjectString(array[i], passedReferences));
		}
		return builder.append(']').toString();
	}

	private static String collectionToString(Collection<?> collection, Collection<Object> passedReferences) throws Exception {
		Object[] array = new Object[collection.size()];
		int idx = 0;
		for (Object obj : collection) {
			array[idx] = obj;
			idx ++;
		}
		return toString(array, passedReferences);
	}

	private static String toObjectString(Object object, Collection<Object> passedReferences) throws Exception {
		checkPassedReferences(passedReferences, object);
		return switch (object) {
		case String string -> "\"" + escape(string) + "\"";
		case Number number -> {
			double val = number.doubleValue();
			yield Double.isNaN(val) || Double.isInfinite(val) ? "null": number.toString();
		}
		case Boolean bool -> bool.toString();
		default -> toString(object, passedReferences);
		};
	}

	private static boolean isFieldAllowed(Field field) {
		Json annotation = field.getDeclaredAnnotation(Json.class);
		return annotation == null || !annotation.ignore();
	}

	private static String escape(String string) {
		StringBuilder builder = new StringBuilder(string.length());
		int len = string.length();
		for (int i = 0; i < len; i ++) {
			char c = string.charAt(i);
			if (Arrays.binarySearch(toEscape, c) >= 0) {
				builder.append('\\');
			}
			builder.append(c);
		}
		return builder.toString();
	}

	private static <T> void register(Class<T> clazz, Function<Object, String> toString) {
		primitiveArraysToString.put(clazz, toString);
	}

	private static <T> void checkPassedReferences(Collection<T> collection, T obj) {
		if (contains(collection, obj)) {
			throw new CircularReferenceException("");
		}

		collection.add(obj);
	}

	private static boolean contains(Iterable<?> iterable, Object obj) {
		Iterator<?> iterator = iterable.iterator();
		while (iterator.hasNext()) {
			if (iterator.next() == obj) {
				return true;
			}
		}
		return false;
	}
}
