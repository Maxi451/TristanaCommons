package it.tristana.commons.helper;

import java.util.Collection;

import org.bukkit.plugin.Plugin;

import it.tristana.commons.arena.Clock;
import it.tristana.commons.interfaces.Tickable;
import it.tristana.commons.interfaces.util.TickablesManager;

public class BasicTickablesManager implements TickablesManager {

	protected final Clock clock;
	
	public BasicTickablesManager() {
		clock = new Clock();
	}
	
	@Override
	public void registerTickable(Tickable tickable) {
		clock.add(tickable);
	}

	@Override
	public void removeTickable(Tickable tickable) {
		clock.remove(tickable);
	}

	@Override
	public void startClock(Plugin plugin, long period) {
		clock.schedule(plugin, period);
	}

	@Override
	public void startClock(Plugin plugin, long delay, long period) {
		clock.schedule(plugin, delay, period);
	}

	@Override
	public void stopClock() {
		clock.cancel();
	}

	@Override
	public Collection<Tickable> getTickables() {
		return clock.getTickables();
	}

}
