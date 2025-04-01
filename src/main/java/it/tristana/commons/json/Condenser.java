package it.tristana.commons.json;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Condenser {

	private static final char[] whitespaces = { ' ', '\n', '\r', '\t' };
	private static final char[] toEscape = "\"\\/\b\f\n\r\t".toCharArray();
	static {
		Arrays.sort(whitespaces);
		Arrays.sort(toEscape);
	}

	static CondensedPiece<Map<String, Object>> objectFromString(String json, int startIndex) {
		Map<String, Object> objects = new LinkedHashMap<>();
		int len = json.length();
		int idx = startIndex;
		boolean isFirst = true;
		do {
			if (isFirst) {
				isFirst = false;
			} else {
				idx ++;
			}

			if (idx >= len) {
				throw new InvalidJsonException("Missing '}' at index " + idx);
			}

			System.out.println("elementFromString(" + idx + ")");
			CondensedPiece<Entry<String, Object>> piece = elementFromString(json, idx);
			Entry<String, Object> entry = piece.piece();
			objects.put(entry.getKey(), entry.getValue());
			idx = piece.endIndex();
		} while (json.charAt(idx) != '}');
		return new CondensedPiece<>(objects, idx);
	}

	static CondensedPiece<List<Object>> arrayFromString(String json, int startIndex) {
		List<Object> objects = new ArrayList<>();
		int len = json.length();
		int idx = startIndex;
		char current;
		boolean isFirstIteration = true;
		while ((current = json.charAt(idx)) != ']') {
			if (isFirstIteration) {
				isFirstIteration = false;
			} else {
				idx ++;
			}

			if (idx >= len - 1) {
				throwBracketException(false, idx);
			}

			switch (current) {
			case '[':
			case '{':
				CondensedPiece<?> subArray = current == '[' ? arrayFromString(json, current + 1) : objectFromString(json, current + 1);
				objects.add(subArray.piece());
				idx = subArray.endIndex();
				break;
			case '}':
				throwBracketException(false, idx);
				break;
			default:
				CondensedPiece<?> piece = parseValue(json, idx);
				if (piece == null) {
					idx = json.indexOf(']', idx);
					break;
				}

				objects.add(piece.piece());
				idx = piece.endIndex();
			}
		}
		return new CondensedPiece<>(objects, idx);
	}

	static CondensedPiece<Entry<String, Object>> elementFromString(String json, int startIndex) {
		int len = json.length();
		int idx = skipWhitespaces(json, startIndex);

		if (json.charAt(idx) != '"') {
			throw new InvalidJsonException("Expected '\"' at index " + startIndex);
		}
		idx ++;

		char current;
		StringBuilder builder = new StringBuilder();
		while ((current = json.charAt(idx)) != '"') {
			if (idx >= len - 1) {
				throw new InvalidJsonException("Expected '\"' at index " + idx);
			}

			if (current == '\\') {
				if (idx >= len - 2) {
					throw new InvalidJsonException("Expected an escaped character after '\\' at index " + idx);
				}
				idx ++;
				current = json.charAt(idx);
				if (!contains(toEscape, current)) {
					throw new InvalidJsonException("Invalid escaped character at index " + idx);
				}
			}
			builder.append(current);
			idx ++;
		}

		idx = skipWhitespaces(json, idx + 1);

		if (json.charAt(idx) != ':') {
			throw new InvalidJsonException("Expected ':' at index " + idx);
		}
		idx ++;

		CondensedPiece<?> piece = parseValue(json, idx);
		return new CondensedPiece<>(new AbstractMap.SimpleEntry<>(builder.toString(), piece.piece()), piece.endIndex());
	}

	static CondensedPiece<?> parseValue(String json, int startIndex) {
		System.out.println(startIndex);
		startIndex = skipWhitespaces(json, startIndex);
		int len = json.length();
		char current = json.charAt(startIndex);
		int idx = startIndex;
		return switch (current) {
		case '"' -> parseString(json, idx + 1);
		case '{' -> objectFromString(json, idx + 1);
		case '[' -> arrayFromString(json, idx + 1);
		default -> {
			int endValueIndex = -1;
			do {
				if (idx >= len) {
					throw new InvalidJsonException("Expected a value separator (',', ']', '}') at index " + idx + " (or spaces until one of those separators)");
				}

				boolean isSpace = contains(whitespaces, current);
				if (endValueIndex >= 0) {
					if (!isSpace && !isEndValue(current)) {
						throw new InvalidJsonException("Unexpected character after spaces at index " + idx);
					}
				} else if (isSpace) {
					endValueIndex = idx;
				}

				idx ++;
				current = json.charAt(idx);
			} while (!isEndValue(current));
			if (endValueIndex < 0) {
				endValueIndex = idx;
			}

			String value = json.substring(startIndex, endValueIndex);
			if (value.isBlank() || isEndValue(value.charAt(0))) {
				yield null;
			}

			yield switch (value) {
			case "true":
				yield new CondensedPiece<>(Boolean.TRUE, idx);
			case "false":
				yield new CondensedPiece<>(Boolean.FALSE, idx);
			case "null":
				yield new CondensedPiece<>(null, idx);
			default:
				try {
					yield new CondensedPiece<>(Double.parseDouble(value), idx);
				} catch (NumberFormatException e) {
					throw new InvalidJsonException("Unrecognized value at index " + startIndex);
				}
			};
		}
		};
	}

	private static CondensedPiece<String> parseString(String json, int startIndex) {
		int len = json.length();
		int idx = startIndex;
		char current;
		StringBuilder builder = new StringBuilder();
		while ((current = json.charAt(idx)) != '"') {
			if (idx >= len - 1) {
				throw new InvalidJsonException("Expected '\"' at index " + idx);
			}

			if (current == '\\') {
				if (idx >= len - 2) {
					throw new InvalidJsonException("Expected an escaped character after '\\' at index " + idx);
				}
				idx ++;
				current = json.charAt(idx);
				if (!contains(toEscape, current)) {
					throw new InvalidJsonException("Invalid escaped character at index " + idx);
				}
			}
			builder.append(current);
			idx ++;
		}
		return new CondensedPiece<>(builder.toString(), idx + 1);
	}

	private static int skipWhitespaces(String json, int from) {
		int len = json.length();
		int idx = from;
		while (contains(whitespaces, json.charAt(idx))) {
			idx ++;
			if (idx >= len - 1) {
				throw new InvalidJsonException("Empty json at index " + idx);
			}
		}
		return idx;
	}

	private static boolean isEndValue(char c) {
		return c == ',' || c == '}' || c == ']';
	}

	private static boolean contains(char[] array, char c) {
		return Arrays.binarySearch(array, c) >= 0;
	}

	private static void throwBracketException(boolean curly, int idx) {
		throw new InvalidJsonException("Unbalanced " + (curly ? "curly" : "squared") + " brackets at index " + idx);
	}
}
