package it.tristana.commons.helper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class CommonsHelper {

	private static final ConsoleCommandSender console = Bukkit.getConsoleSender();

	private static final int CHARS_AFTER_UNICODE_ESCAPE = 5;

	private CommonsHelper() {}

	public static void info(CommandSender sender, String msg) {
		sender.sendMessage(toChatColors(msg));
	}

	public static void consoleInfo(String msg) {
		info(console, msg);
	}

	public static void broadcast(String msg) {
		Bukkit.getOnlinePlayers().forEach(player -> info(player, msg));
		consoleInfo(msg);
	}

	public static String replaceFirst(String line, String lookingFor, String replacement) {
		int index = line.indexOf(lookingFor);
		if (index >= 0) {
			line = line.substring(0, index) + replacement + line.substring(index + lookingFor.length());
		}
		return line;
	}

	public static String replaceAll(String line, String lookingFor, String replacement) {
		StringBuilder result = new StringBuilder();
		int theLookingForLength = lookingFor.length();
		if (line.length() < theLookingForLength) {
			return line;
		}
		for (int i = 0; i < line.length(); i ++) {
			int inc = 0;
			boolean found = false;
			while (inc != theLookingForLength && lookingFor.charAt(inc) == line.charAt(i + inc)) {
				if (++ inc == theLookingForLength) {
					found = true;
				}
			}
			if (found) {
				i += inc - 1;
				result.append(replacement);
			} else {
				result.append(line.charAt(i));
			}
		}
		return result.toString();
	}

	public static String replaceAll(String line, String[] lookingFor, String[] replacements) {
		if (lookingFor.length != replacements.length) {
			throw new IllegalArgumentException("The two arrays lengths are not equal");
		}
		for (int i = 0; i < lookingFor.length; i ++) {
			line = replaceAll(line, lookingFor[i], replacements[i]);
		}
		return line;
	}

	public static boolean isColorCode(Character previous, char c) {
		if (c >= 'A' && c <= 'Z') {
			c -= 0x20;
		}
		return previous != null && previous.charValue() == '&' && c >= '0' && c <= '9' || c >= 'a' && c <= 'f' || c >= 'k' && c <= 'o' || c == 'r';
	}

	public static String toChatColors(String line) {
		return ChatColor.translateAlternateColorCodes('&', line);
	}

	public static int getGuiSizeFromNumOfElements(Object[] objects) {
		return (objects.length / 9 + (objects.length % 9 != 0 ? 1 : 0)) * 9;
	}

	public static String parseUnicode(final String line) {
		final StringBuilder builder = new StringBuilder();
		int i;
		for (i = 0; i < line.length() - CHARS_AFTER_UNICODE_ESCAPE; i ++) {
			if (line.charAt(i) == '\\' && line.charAt(i + 1) == 'u' && (i == 0 || line.charAt(i - 1) != '\\') && isXDigit(line.charAt(i + 2)) && isXDigit(line.charAt(i + 3)) && isXDigit(line.charAt(i + 4)) && isXDigit(line.charAt(i + 5))) {
				builder.append((char) Integer.parseInt(line.substring(i + 2, i + CHARS_AFTER_UNICODE_ESCAPE + 1), 16));
				i += CHARS_AFTER_UNICODE_ESCAPE;
			} else {
				builder.append(line.charAt(i));
			}
		}
		builder.append(line.substring(i));
		return builder.toString();
	}

	public static boolean isDigit(final char c) {
		return c >= '0' && c <= '9';
	}

	public static boolean isXDigit(final char c) {
		return isDigit(c) || c >= 'A' && c <= 'F' || c >= 'a' && c <= 'f';
	}

	public static boolean isAlpha(final char c) {
		return c >= 'A' && c <= 'Z' || c >= 'a' && c <= 'z';
	}

	/**
	 * Reads a number from the right of the line as long as there are digits
	 * @param line The line which contains the number
	 * @return The number formed by all the digits read from right
	 */

	public static String getNumberFromRight(final String line) {
		StringBuilder result = new StringBuilder("");
		int i = line.length() - 1;
		char c;
		while (i != -1 && isDigit(c = line.charAt(i))) {
			result.insert(0, c);
			i --;
		}
		return result.toString();
	}

	public static void printThrowable(final CommandSender sender, final Throwable t) {
		info(sender, "&c" + t.toString());
		final List<String> lines = getLinesFromThrowable(t);
		for (final String line : lines) {
			info(sender, "&c" + line);
		}
	}

	public static List<String> getLinesFromThrowable(final Throwable t) {
		final List<String> lines = new ArrayList<String>();
		final StackTraceElement[] trace = t.getStackTrace();
		for (int i = 0; i < trace.length; i ++) {
			lines.add(trace[i].toString());
		}
		return lines;
	}

	/**
	 * Reads the content of a file
	 * @param filePath The file path
	 * @return The content of the file
	 */

	public static List<String> getLinesFromFile(final String filePath) {
		final List<String> lines = new ArrayList<String>();
		try {
			final File file = new File(filePath);
			if (!file.exists()) {
				file.createNewFile();
			}
			try (BufferedReader br = new BufferedReader(new FileReader(file))) {
				String line = null;
				do {
					line = br.readLine();
					if (line != null) {
						lines.add(line);
					}
				} while (line != null);
			}
		} catch (IOException e) {
			consoleInfo("&cCan't read file " + filePath + "!");
			throw new RuntimeException(e);
		}
		return lines;
	}

	/**
	 * Saves a list of {@code String} into a file
	 * @param lines The lines to save
	 * @param filePath The file path
	 */

	public static void writeLinesOnFile(final List<String> lines, final String filePath) {
		final File file = new File(filePath);
		final StringBuilder names = new StringBuilder();
		final String newLine = System.getProperty("line.separator");
		try {
			final int size = lines.size();
			for (int i = 0; i < size - 1; i ++) {
				names.append(lines.get(i)).append(newLine);
			}
			if (size > 0) {
				names.append(lines.get(size - 1));
			}
			final BufferedWriter out = new BufferedWriter(new FileWriter(file));
			out.write(names.toString());
			out.close();
		} catch (Exception e) {
			consoleInfo("&cCan't write file " + filePath + "!");
			throw new RuntimeException(e);
		}
	}

	public static String getFormattedTime(long time) {
		StringBuilder formattedTime = new StringBuilder();
		time /= 1000;
		int days = (int) time / 86400;
		time %= 86400;
		int hours = (int) time / 3600;
		time %= 3600;
		int minutes = (int) time / 60;
		int seconds = (int) time % 60;
		if (days != 0) {
			formattedTime.append(days).append("d ");
		}
		if (hours != 0) {
			formattedTime.append(hours).append("h ");
		}
		if (minutes != 0) {
			formattedTime.append(minutes).append("min ");
		}
		formattedTime.append(seconds).append("s");
		return formattedTime.toString();
	}

	public static String getFormattedDate(long time) {
		String formattedDate = null;
		try {
			formattedDate = new SimpleDateFormat("dd MMMM yyyy HH:mm:ss").format(new Date(time));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return formattedDate;
	}

	public static boolean samePosInt(Location pos1, Location pos2) {
		return sameWorld(pos1, pos2) && pos1.getBlockX() == pos2.getBlockX() && pos1.getBlockY() == pos2.getBlockY() && pos1.getBlockZ() == pos2.getBlockZ();
	}

	public static boolean sameWorld(Location pos1, Location pos2) {
		return pos1.getWorld() == pos2.getWorld();
	}

	public static boolean parseBoolean(String value) {
		return value.equalsIgnoreCase("true");
	}
	
	public static int parseIntOrGetDefault(String value, int defaultValue) {
		int result;
		try {
			result = Integer.parseInt(value);
		} catch (NumberFormatException e) {
			result = defaultValue;
		}
		return result;
	}

	public static double parseDoubleOrGetDefault(String value, double defaultValue) {
		double result;
		try {
			result = Double.parseDouble(value);
		} catch (NumberFormatException e) {
			result = defaultValue;
		}
		return result;
	}

	public static Location centerLocation(Location location) {
		return new Location(location.getWorld(), location.getBlockX() + 0.5, location.getBlockY(), location.getBlockZ() + 0.5, location.getYaw(), location.getPitch());
	}

	public static String addZeroIfNeeded(int num) {
		return num > 9 ? Integer.toString(num) : "0" + num;
	}

	public static String centerString(String line, int maxLength, char paddingChar) {
		int strlen = line.length();
		if (strlen >= maxLength) {
			return line;
		}
		int leftSpaces = (maxLength - strlen) / 2;
		int rightSpaceIndex = strlen + leftSpaces;
		char[] array = new char[maxLength];
		for (int i = 0; i < leftSpaces; i ++) {
			array[i] = paddingChar;
		}
		for (int i = leftSpaces; i < rightSpaceIndex; i ++) {
			array[i] = line.charAt(i - leftSpaces);
		}
		for (int i = rightSpaceIndex; i < maxLength; i ++) {
			array[i] = paddingChar;
		}
		return new String(array);
	}

	public static int getEmptySlots(Object[] elements) {
		return (9 - (elements.length % 9)) % 9;
	}

	public static String truncateLine(String line, int maxLength) {
		return line.length() > maxLength ? line.substring(0, maxLength) : line;
	}

	public static String format(String line) {
		return CommonsHelper.toChatColors(CommonsHelper.parseUnicode(line));
	}

	public static char getChatColorFromBlockData(byte data) {
		switch (data) {
		case 0:
			return 'f';
		case 1:
			return '6';
		case 2:
			return '5';
		case 3:
			return 'b';
		case 4:
			return 'e';
		case 5:
			return 'a';
		case 6:
			return 'd';
		case 7:
			return '8';
		case 8:
			return '7';
		case 9:
			return '3';
		case 10:
			return '3';
		case 13:
			return '2';
		case 14:
			return '4';
		case 11:
		case 12:
		case 15:
			return '0';
		default:
			throw new IllegalArgumentException("Block data color must be between 0 and 15 inclusive, but here arrived " + data);
		}
	}

	public static <T> List<T> copyList(List<? extends T> list) {
		return new ArrayList<>(list);
	}

	public static String playerListToString(List<String> players, String nobody, String andWord) {
		int size = players.size();
		if (size == 0) {
			return nobody;
		}
		StringBuilder playerList = new StringBuilder();
		if (size > 1) {
			for (int i = 0; i < size - 2; i ++) {
				playerList.append(players.get(i)).append(", ");
			}
			playerList.append(players.get(size - 2)).append(' ').append(andWord).append(' ');
		}
		playerList.append(players.get(size - 1));
		return playerList.toString();
	}

	public static List<String> playerListToPlayerNames(List<Player> players) {
		List<String> names = new ArrayList<String>();
		for (Player player : players) {
			names.add(player.getName());
		}
		return names;
	}

	public static <T> void shuffle(List<T> list) {
		Collections.shuffle(list);
	}

	public static String locationToString(Location location) {
		return String.format("x = %.2f y = %.2f z = %.2f yaw = %.2f pitch = %.2f @ %s", location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch(), location.getWorld().getName());
	}
	/*
	public static void removeAi(LivingEntity entity) {
		((CraftLivingEntity) entity).getHandle().getDataWatcher().watch(15, (byte) 1);
	}

	public static void removeGravity(Entity entity) {
		((CraftEntity) entity).getHandle().getDataWatcher().watch(10, (byte) 2);
	}
	 */
}
