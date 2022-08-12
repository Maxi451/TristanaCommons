package it.tristana.commons.config;

import java.io.File;

public class SettingsTeams extends Settings<ConfigTeams> {

	private String redName;
	private String redColor;
	
	private String blueName;
	private String blueColor;
	
	private String greenName;
	private String greenColor;
	
	private String yellowName;
	private String yellowColor;
	
	private String aquaName;
	private String aquaColor;
	
	private String whiteName;
	private String whiteColor;
	
	private String fuchsiaName;
	private String fuchsiaColor;
	
	private String greyName;
	private String greyColor;
	
	private String[] teams;
	private String[] colors;
	
	public SettingsTeams(File folder) {
		super(folder, ConfigTeams.class);
	}

	@Override
	protected void reload(ConfigTeams config) {
		redName = config.getString(ConfigTeams.RED_NAME);
		redColor = config.getString(ConfigTeams.RED_COLOR);
		
		blueName = config.getString(ConfigTeams.BLUE_NAME);
		blueColor = config.getString(ConfigTeams.BLUE_COLOR);
		
		greenName = config.getString(ConfigTeams.GREEN_NAME);
		greenColor = config.getString(ConfigTeams.GREEN_COLOR);
		
		yellowName = config.getString(ConfigTeams.YELLOW_NAME);
		yellowColor = config.getString(ConfigTeams.YELLOW_COLOR);
		
		aquaName = config.getString(ConfigTeams.AQUA_NAME);
		aquaColor = config.getString(ConfigTeams.AQUA_COLOR);
		
		whiteName = config.getString(ConfigTeams.WHITE_NAME);
		whiteColor = config.getString(ConfigTeams.WHITE_COLOR);
		
		fuchsiaName = config.getString(ConfigTeams.FUCHSIA_NAME);
		fuchsiaColor = config.getString(ConfigTeams.FUCHSIA_COLOR);
		
		greyName = config.getString(ConfigTeams.GREY_NAME);
		greyColor = config.getString(ConfigTeams.GREY_COLOR);
		
		teams = new String[] {
				redName,
				blueName,
				greenName,
				yellowName,
				aquaName,
				whiteName,
				fuchsiaName,
				greyName
		};
		colors = new String[] {
				redColor,
				blueColor,
				greenColor,
				yellowColor,
				aquaColor,
				whiteColor,
				fuchsiaColor,
				greyColor
		};
	}
	
	public String getRedName() {
		return redName;
	}

	public String getRedColor() {
		return redColor;
	}

	public String getBlueName() {
		return blueName;
	}

	public String getBlueColor() {
		return blueColor;
	}

	public String getGreenName() {
		return greenName;
	}

	public String getGreenColor() {
		return greenColor;
	}

	public String getYellowName() {
		return yellowName;
	}

	public String getYellowColor() {
		return yellowColor;
	}

	public String getAquaName() {
		return aquaName;
	}

	public String getAquaColor() {
		return aquaColor;
	}

	public String getWhiteName() {
		return whiteName;
	}

	public String getWhiteColor() {
		return whiteColor;
	}

	public String getFuchsiaName() {
		return fuchsiaName;
	}

	public String getFuchsiaColor() {
		return fuchsiaColor;
	}

	public String getGreyName() {
		return greyName;
	}

	public String getGreyColor() {
		return greyColor;
	}

	public String[] getTeams() {
		return teams;
	}
	
	public String[] getColors() {
		return colors;
	}
}
