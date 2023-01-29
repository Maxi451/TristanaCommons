package it.tristana.commons.helper;

import org.bukkit.ChatColor;
import org.bukkit.Color;

public final class TeamsColors {

	public static final String DEFAULT_CODE = CommonsHelper.toChatColors("&6");

	private TeamsColors() {}

	public static final Color[] TEAMS_COLORS = new Color[] {
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

	public static ChatColor fromColor(Color color) {
		for (int i = 0; i < TEAMS_COLORS.length; i ++) {
			if (TEAMS_COLORS[i] == color) {
				return CHAT_COLORS[i];
			}
		}
		return null;
	}

	public static Color fromChatColor(ChatColor color) {
		for (int i = 0; i < CHAT_COLORS.length; i ++) {
			if (CHAT_COLORS[i] == color) {
				return TEAMS_COLORS[i];
			}
		}
		return null;
	}
}
