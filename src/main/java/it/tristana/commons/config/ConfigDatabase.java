package it.tristana.commons.config;

import java.io.File;

public class ConfigDatabase extends Config {

	public static final String HOST = "host";
	public static final String PORT = "port";
	public static final String USER = "user";
	public static final String PASSWORD = "password";
	public static final String DATABASE = "database";

	public ConfigDatabase(File file) {
		super(file);
	}
	
	@Override
	protected void createDefault() {
		fileConfiguration.set(HOST, "127.0.0.1");
		fileConfiguration.set(PORT, "3306");
		fileConfiguration.set(USER, "root");
		fileConfiguration.set(PASSWORD, "password");
		fileConfiguration.set(DATABASE, "database");
	}
}