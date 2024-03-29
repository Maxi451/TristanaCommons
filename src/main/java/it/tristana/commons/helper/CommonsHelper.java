package it.tristana.commons.helper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

public class CommonsHelper {

	private static final ConsoleCommandSender console = Bukkit.getConsoleSender();

	private CommonsHelper() {}

	public static void info(CommandSender sender, String msg) {
		sender.sendMessage(toChatColors(msg));
	}

	public static void consoleInfo(String msg) {
		info(console, msg);
	}

	public static void broadcast(String msg) {
		playerBroadcast(msg);
		consoleInfo(msg);
	}

	public static void playerBroadcast(String msg) {
		Bukkit.getOnlinePlayers().forEach(player -> info(player, msg));
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

	public static String getUuid(OfflinePlayer player) {
		return player.getUniqueId().toString();
	}

	@SuppressWarnings("deprecation")
	public static String getUuid(String name) {
		return getUuid(Bukkit.getOfflinePlayer(name));
	}

	public static String toChatColors(String line) {
		return ChatColor.translateAlternateColorCodes('&', line);
	}

	public static String[] toChatColors(String[] array) {
		for (int i = 0; i < array.length; i++) {
			array[i] = toChatColors(array[i]);
		}
		return array;
	}

	public static int getGuiSizeFromNumOfElements(Object[] objects) {
		return (objects.length / 9 + (objects.length % 9 != 0 ? 1 : 0)) * 9;
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
		StringBuilder result = new StringBuilder();
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

	public static void correctExtremities(Vector lowerPos, Vector upperPos) {
		double tmp;
		if (lowerPos.getX() > upperPos.getX()) {
			tmp = lowerPos.getX();
			lowerPos.setX(upperPos.getX());
			upperPos.setX(tmp);
		}
		if (lowerPos.getY() > upperPos.getY()) {
			tmp = lowerPos.getY();
			lowerPos.setY(upperPos.getY());
			upperPos.setY(tmp);
		}
		if (lowerPos.getZ() > upperPos.getZ()) {
			tmp = lowerPos.getZ();
			lowerPos.setZ(upperPos.getZ());
			upperPos.setZ(tmp);
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
		return value == null ? false : value.equalsIgnoreCase("true");
	}

	public static byte parseByteOrGetDefault(String value, byte defaultValue) {
		try {
			return Byte.parseByte(value);
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}

	public static short parseShortOrGetDefault(String value, short defaultValue) {
		try {
			return Short.parseShort(value);
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}

	public static int parseIntOrGetDefault(String value, int defaultValue) {
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}

	public static long parseLongOrGetDefault(String value, long defaultValue) {
		try {
			return Long.parseLong(value);
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}

	public static float parseFloatOrGetDefault(String value, float defaultValue) {
		try {
			return Float.parseFloat(value);
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}

	public static double parseDoubleOrGetDefault(String value, double defaultValue) {
		try {
			return Double.parseDouble(value);
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}

	public static Material parseMaterialOrGetDefault(String value, Material defaultValue) {
		Material result = Material.matchMaterial(value);
		return result == null ? defaultValue : result;
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
		return CommonsHelper.toChatColors(line);
	}

	public static String playerListToString(Collection<String> players, String nobody, String and) {
		int size = players.size();
		if (size == 0) {
			return nobody;
		}

		Iterator<String> iterator = players.iterator();
		StringBuilder playerList = new StringBuilder();
		if (size > 1) {
			for (int i = 0; i < size - 2; i ++) {
				playerList.append(iterator.next()).append(", ");
			}
			playerList.append(iterator.next()).append(' ').append(and).append(' ');
		}
		playerList.append(iterator.next());
		return playerList.toString();
	}

	public static List<String> playerListToPlayerNames(Collection<Player> players) {
		return players.stream().map(Player::getName).collect(Collectors.toList());
	}

	public static <T> void shuffle(List<T> list) {
		Collections.shuffle(list);
	}

	public static String locationToString(Location location) {
		return String.format("x = %.2f y = %.2f z = %.2f yaw = %.2f pitch = %.2f @ %s", location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch(), location.getWorld().getName());
	}

	@SafeVarargs
	public static <T> T extract(T... array) {
		return array.length == 0 ? null : ((array.length == 1) ? array[0] : array[randomIndex(array.length)]);
	}

	public static <T> T extractAndRemove(List<T> list) {
		return list.remove(randomIndex(list.size()));
	}

	public static int randomIndex(int size) {
		return (int) (Math.random() * size);
	}

	public static void setDisplayName(ItemStack item, String name) {
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(toChatColors(name));
		item.setItemMeta(meta);
	}

	public static void setLore(ItemStack item, String... lore) {
		setLore(item, Arrays.asList(lore));
	}

	public static void setLore(ItemStack item, List<String> lore) {
		ItemMeta meta = item.getItemMeta();
		ListIterator<String> iterator = lore.listIterator();
		while (iterator.hasNext()) {
			iterator.set(toChatColors(iterator.next()));
		}
		meta.setLore(lore);
		item.setItemMeta(meta);
	}

	public static void giveOrDrop(Player player, ItemStack... items) {
		Map<Integer, ItemStack> notGivenItems = player.getInventory().addItem(items);
		if (notGivenItems.size() == 0) {
			return;
		}

		World world = player.getWorld();
		Location location = player.getLocation().add(0, 0.5, 0);
		Iterator<Map.Entry<Integer, ItemStack>> iterator = notGivenItems.entrySet().iterator();
		while (iterator.hasNext()) {
			world.dropItemNaturally(location, iterator.next().getValue());
		}
	}

	public static String getBetween(String line, String first, String second) {
		String result = line;
		int firstIndex = line.indexOf(first);
		if (firstIndex >= 0) {
			int secondIndex = line.indexOf(second);
			if (secondIndex >= firstIndex + first.length()) {
				result = line.substring(firstIndex + first.length(), secondIndex);
			}
		} 
		return result;
	}

	public static void wearOrGive(Player player, ItemStack item, int slot) {
		PlayerInventory inventory = player.getInventory();
		ItemStack[] armor = inventory.getArmorContents();
		if (armor[slot] == null) {
			armor[slot] = item;
			inventory.setArmorContents(armor);
		} else {
			giveOrDrop(player, item);
		} 
	}

	public static List<Block> blocksInRadius(Location location, int radius) {
		List<Block> blocks = new ArrayList<>();
		World world = location.getWorld();
		int x = location.getBlockX();
		int y = location.getBlockY();
		int z = location.getBlockZ();
		for (int i = x - radius; i <= x + radius; i ++) {
			for (int ii = y - radius; ii <= y + radius; ii ++) {
				for (int iii = z - radius; iii <= z + radius; iii ++) {
					Block block = world.getBlockAt(i, ii, iii);
					if (Math.sqrt(Math.pow(x - i, 2) + Math.pow(y - ii, 2) + Math.pow(z - iii, 2)) <= radius) {
						blocks.add(block);
					}
				} 
			} 
		} 
		return blocks;
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
