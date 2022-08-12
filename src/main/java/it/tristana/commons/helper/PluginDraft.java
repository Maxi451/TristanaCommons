package it.tristana.commons.helper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import it.tristana.commons.Main;
import it.tristana.commons.command.MainCommand;
import it.tristana.commons.config.SettingsDefaultCommands;

public class PluginDraft extends JavaPlugin {

	protected static final String ERRORS_FILE = "errors.txt";
	
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
			if (!errorsFile.exists()) {
				errorsFile.createNewFile();
				lines = new ArrayList<String>();
			} else {
				lines = CommonsHelper.getLinesFromFile(errorsFile.getAbsolutePath());
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
	
	
	protected <T extends Plugin> void registerCommand(T main, Class<? extends MainCommand<T>> commandClass, String label) {
		try {
			Bukkit.getPluginCommand(label).setExecutor(commandClass.getConstructor(main.getClass(), SettingsDefaultCommands.class, String.class).newInstance(main, new SettingsDefaultCommands(JavaPlugin.getPlugin(Main.class).getFolder()), label));
		} catch (Exception e) {
			writeThrowableOnErrorsFile(e);
			throw new IllegalArgumentException("The constructor requires exactly the parameters of the MainCommand class constructor, in that specific order");
		}
	}
}
