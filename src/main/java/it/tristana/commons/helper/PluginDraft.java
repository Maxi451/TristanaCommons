package it.tristana.commons.helper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import it.tristana.commons.command.MainCommand;
import it.tristana.commons.config.SettingsDefaultCommands;

public class PluginDraft extends JavaPlugin {

	protected static final String ERRORS_FILE = "errors.txt";
	private static final String NEW_LINE = System.getProperty("line.separator");
	
	private SettingsDefaultCommands settingsDefaultCommands;

	public File getFolder() {
		File folder = getDataFolder();
		if (!folder.exists()) {
			folder.mkdirs();
		}
		return folder;
	}
	
	public void writeThrowableOnErrorsFile(final Throwable throwable) {
		File errorsFile = new File(getFolder(), ERRORS_FILE);
		try {
			if (errorsFile.exists()) {
				errorsFile.createNewFile();
			}

			BufferedWriter bw = new BufferedWriter(new FileWriter(errorsFile, true));
			bw.write("In date " + CommonsHelper.getFormattedDate(System.currentTimeMillis()) + " an exception caused by \"" + throwable + "\" has been generated! D:" + NEW_LINE + NEW_LINE);
			bw.write("*** BEGIN EXCEPTION STACKTRACE ***" + NEW_LINE + NEW_LINE);
			for (final String line : CommonsHelper.getLinesFromThrowable(throwable)) {
				bw.write(line + NEW_LINE);
			}
			bw.write(NEW_LINE + "*** END EXCEPTION STACKTRACE ***" + NEW_LINE + NEW_LINE);
			bw.close();
		} catch (IOException e) {
			CommonsHelper.consoleInfo("&cCan't report the plugin throwable! Here is shown:");
			throwable.printStackTrace();
			CommonsHelper.consoleInfo("&cReason why it couldn't be saved:");
			e.printStackTrace();
		}
	}

	protected void register(Listener listener) {
		Bukkit.getPluginManager().registerEvents(listener, this);
	}


	protected <P extends JavaPlugin, C extends MainCommand<P>> C registerCommand(P main, Class<C> commandClass, String label, String defaultSettingsFileName) {
		if (settingsDefaultCommands == null) {
			settingsDefaultCommands = new SettingsDefaultCommands(new File(getFolder(), defaultSettingsFileName));
		}
		C command = null;
		try {
			command = commandClass.getConstructor(main.getClass(), SettingsDefaultCommands.class, String.class).newInstance(main, settingsDefaultCommands, label);
			Bukkit.getPluginCommand(label).setExecutor(command);
		} catch (Exception e) {
			writeThrowableOnErrorsFile(e);
			throw new IllegalArgumentException("The constructor requires exactly the parameters of the it.tristana.commons.command.MainCommand class constructor, in that specific order");
		}
		return command;
	}
}
