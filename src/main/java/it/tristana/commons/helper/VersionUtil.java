package it.tristana.commons.helper;

import org.bukkit.Bukkit;

public class VersionUtil {

	public static final int vOTHER = -1;
	public static final int v1_7 = 0;
	public static final int v1_8 = 1;
	public static final int v1_9 = 2;
	public static final int v1_10 = 3;
	public static final int v1_11 = 4;
	public static final int v1_12 = 5;
	public static final int v1_13 = 6;
	public static final int v1_14 = 7;
	public static final int v1_15 = 8;
	public static final int v1_16 = 9;
	public static final int v1_17 = 10;
	public static final int v1_18 = 11;
	public static final int v1_19 = 12;
	public static final int v1_20 = 13;

	private VersionUtil() {}

	public static int getServerVersion() {
		String version = Bukkit.getBukkitVersion().split("-")[0];
		switch (version) {
		case "1.7":
			return v1_7;
		case "1.8":
			return v1_8;
		case "1.9":
			return v1_9;
		case "1.10":
			return v1_10;
		case "1.11":
			return v1_11;
		case "1.12":
			return v1_12;
		case "1.13":
			return v1_13;
		case "1.14":
			return v1_14;
		case "1.15":
			return v1_15;
		case "1.16":
			return v1_16;
		case "1.17":
			return v1_17;
		case "1.18":
			return v1_18;
		case "1.19":
			return v1_19;
		case "1.20":
			return v1_20;
		default:
			return vOTHER;
		}
	}
}
