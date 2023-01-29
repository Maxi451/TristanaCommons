package it.tristana.commons.helper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import it.tristana.commons.command.MainCommand;
import it.tristana.commons.config.SettingsDefaultCommands;

public class PluginDraft extends JavaPlugin {

	protected static final String ERRORS_FILE = "errors.txt";
	private SettingsDefaultCommands settingsDefaultCommands;

	public File getFolder() {
		File folder = getDataFolder();
		if (!folder.exists()) {
			folder.mkdirs();
		}
		return folder;
	}

	public void writeThrowableOnErrorsFile(final Throwable t) {
		File errorsFile = new File(getFolder(), ERRORS_FILE);
		try {
			List<String> lines;
			final String newLine = System.getProperty("line.separator");
			if (errorsFile.exists()) {
				lines = Files.readAllLines(errorsFile.toPath());
			} else {
				errorsFile.createNewFile();
				lines = new ArrayList<String>();
			}
			for (int i = 0; i < lines.size(); i ++) {
				final String line = lines.get(i);
				lines.remove(i);
				lines.add(i, line + newLine);
			}
			BufferedWriter bw = new BufferedWriter(new FileWriter(errorsFile));
			List<String> exception = CommonsHelper.getLinesFromThrowable(t);
			lines.add("In date " + CommonsHelper.getFormattedDate(System.currentTimeMillis()) + " an exception caused by \"" + t.toString() + "\" has been generated! D:" + newLine + newLine);
			lines.add("*** BEGIN EXCEPTION STACKTRACE ***" + newLine + newLine);
			for (final String line : exception) {
				lines.add(line + newLine);
			}
			lines.add(newLine + "*** END EXCEPTION STACKTRACE ***" + newLine + newLine);
			for (final String  line : lines) {
				bw.write(line);
			}
			bw.close();
		} catch (IOException e2) {
			CommonsHelper.consoleInfo("&cCan't report the plugin throwable! Here is shown:");
			t.printStackTrace();
			CommonsHelper.consoleInfo("&cReason why it couldn't be saved:");
			e2.printStackTrace();
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
