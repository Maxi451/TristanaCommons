package it.tristana.commons.config;

import java.io.File;

public class ConfigTeams extends Config {

	private static final String NAME = "name";
	private static final String COLOR = "chat-color";
	
	private static final String RED = "red.";
	public static final String RED_NAME = RED + NAME;
	public static final String RED_COLOR = RED + COLOR;
	
	private static final String BLUE = "blue.";
	public static final String BLUE_NAME = BLUE + NAME;
	public static final String BLUE_COLOR = BLUE + COLOR;
	
	private static final String GREEN = "green.";
	public static final String GREEN_NAME = GREEN + NAME;
	public static final String GREEN_COLOR = GREEN + COLOR;
	
	private static final String YELLOW = "yellow.";
	public static final String YELLOW_NAME = YELLOW + NAME;
	public static final String YELLOW_COLOR = YELLOW + COLOR;
	
	private static final String AQUA = "aqua.";
	public static final String AQUA_NAME = AQUA + NAME;
	public static final String AQUA_COLOR = AQUA + COLOR;
	
	private static final String WHITE = "white.";
	public static final String WHITE_NAME = WHITE + NAME;
	public static final String WHITE_COLOR = WHITE + COLOR;
	
	private static final String FUCHSIA = "fuchsia.";
	public static final String FUCHSIA_NAME = FUCHSIA + NAME;
	public static final String FUCHSIA_COLOR = FUCHSIA + COLOR;
	
	private static final String GREY = "grey.";
	public static final String GREY_NAME = GREY + NAME;
	public static final String GREY_COLOR = GREY + COLOR;
	
	public ConfigTeams(File folder) {
		super(new File(folder, "teams.yml"));
	}

	@Override
	protected void createDefault() {
		set(RED_NAME, "Red");
		set(RED_COLOR, "&c");
		
		set(BLUE_NAME, "Blue");
		set(BLUE_COLOR, "&9");
		
		set(GREEN_NAME, "Green");
		set(GREEN_COLOR, "&a");
		
		set(YELLOW_NAME, "Yellow");
		set(YELLOW_COLOR, "&e");
		
		set(AQUA_NAME, "Aqua");
		set(AQUA_COLOR, "&b");
		
		set(WHITE_NAME, "White");
		set(WHITE_COLOR, "&f");
		
		set(FUCHSIA_NAME, "Fuchsia");
		set(FUCHSIA_COLOR, "&d");
		
		set(GREY_NAME, "Grey");
		set(GREY_COLOR, "&8");
	}
}
