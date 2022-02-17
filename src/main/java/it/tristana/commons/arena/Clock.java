package it.tristana.commons.arena;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

import it.tristana.commons.interfaces.Tickable;

public final class Clock implements Runnable {

	private static final BukkitScheduler scheduler = Bukkit.getScheduler();
	private static final int NOT_SCHEDULED = -1;
	
	private final Collection<Tickable> tickables;
	private int task;

	public Clock() {
		tickables = Collections.synchronizedList(new ArrayList<Tickable>());
		task = NOT_SCHEDULED;
	}

	public void add(Tickable tickable) {
		tickables.add(tickable);
	}
	
	public void remove(Tickable tickable) {
		tickables.remove(tickable);
	}

	public void schedule(Plugin plugin, long period) {
		schedule(plugin, 1, period);
	}
	
	public void schedule(Plugin plugin, long delay, long period) {
		if (task != NOT_SCHEDULED) {
			throw new IllegalStateException("Task already scheduled! It has to be cancelled first");
		}
		task = scheduler.scheduleSyncRepeatingTask(plugin, this, delay, period);
		if (task == NOT_SCHEDULED) {
			throw new IllegalStateException("The task could not be scheduled");
		}
	}
	
	public void cancel() {
		if (task != NOT_SCHEDULED) {
			scheduler.cancelTask(task);
			task = NOT_SCHEDULED;
		}
	}
	
	@Override
	public void run() {
		synchronized (tickables) {
			tickables.forEach(tickable -> tickable.runTick());
		}
	}
	
	public Collection<Tickable> getTickables() {
		return tickables;
	}
}
