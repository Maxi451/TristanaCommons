package it.tristana.commons.helper;

import org.bukkit.ChatColor;
import org.bukkit.Color;

public final class TeamsColors {
	
	public static final String DEFAULT_CODE = CommonsHelper.toChatColors("&6");
	
	private TeamsColors() {}
	
	public static final Color[] TEAMS_COLOR = new Color[] {
			Color.RED,
			Color.BLUE,
			Color.GREEN,
			Color.YELLOW,
			Color.WHITE,
			Color.GRAY,
			Color.FUCHSIA,
			Color.PURPLE
	};
	
	public static final ChatColor[] CHAT_COLORS = new ChatColor[] {
			ChatColor.RED,
			ChatColor.BLUE,
			ChatColor.GREEN,
			ChatColor.YELLOW,
			ChatColor.WHITE,
			ChatColor.GRAY,
			ChatColor.LIGHT_PURPLE,
			ChatColor.DARK_PURPLE
	};
	
	public static final String[] COLORS_CODES = new String[CHAT_COLORS.length];
	static {
		for (int i = 0; i < COLORS_CODES.length; i ++) {
			COLORS_CODES[i] = CHAT_COLORS[i].toString();
		}
	}
	/*
	private static final ItemStack[] WOOLS = new ItemStack[] {
			new ItemStack(Material.RED_WOOL),
			new ItemStack(Material.BLUE_WOOL),
			new ItemStack(Material.GREEN_WOOL),
			new ItemStack(Material.YELLOW_WOOL),
			new ItemStack(Material.WHITE_WOOL),
			new ItemStack(Material.GRAY_WOOL),
			new ItemStack(Material.RED_WOOL),
			new ItemStack(Material.PURPLE_WOOL)
	};
	
	
	private static final ItemStack[] WOOLS = generateWools(new int[] { 14, 11, 5, 4, 9, 0, 6, 7 });
	
	private static ItemStack[] generateWools(int[] ids) {
		ItemStack[] wools = new ItemStack[ids.length];
		for (int i = 0; i < ids.length; i ++) {
			wools[i] = new ItemStack(Material.WOOL, 1, (short) ids[i]);
		}
		return wools;
	}
	
	public static ItemStack getWoolForTeamIndex(int index) {
		return WOOLS[index].clone();
	}
	*/
}
