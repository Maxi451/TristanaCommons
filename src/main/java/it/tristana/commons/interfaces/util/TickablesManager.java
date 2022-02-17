package it.tristana.commons.interfaces.util;

import java.util.Collection;

import org.bukkit.plugin.Plugin;

import it.tristana.commons.interfaces.Tickable;

public interface TickablesManager {

	void registerTickable(Tickable tickable);
	
	void removeTickable(Tickable tickable);
	
	void startClock(Plugin plugin, long period);
	
	void startClock(Plugin plugin, long delay, long period);
	
	void stopClock();
	
	Collection<Tickable> getTickables();
}
