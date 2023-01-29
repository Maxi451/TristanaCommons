package it.tristana.commons.arena;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

import it.tristana.commons.interfaces.Tickable;

/**
 * A Clock manages a collection of {@link Tickable}s<br>
 * that will be executed all together each X server ticks
 */
public final class Clock implements Runnable {

	private static final BukkitScheduler scheduler = Bukkit.getScheduler();
	private static final int NOT_SCHEDULED = -1;

	private final Collection<Tickable> tickables;
	private int task;

	public Clock() {
		tickables = Collections.synchronizedList(new LinkedList<Tickable>());
		task = NOT_SCHEDULED;
	}

	public void add(Tickable tickable) {
		synchronized (tickables) {
			tickables.add(tickable);
		}
	}

	public void remove(Tickable tickable) {
		synchronized (tickables) {
			tickables.remove(tickable);
		}
	}

	public void schedule(Plugin plugin, long period) {
		schedule(plugin, 1, period);
	}

	public void schedule(Plugin plugin, long delay, long period) {
		if (!plugin.isEnabled()) {
			return;
		}

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
			tickables.forEach(Tickable::runTick);
		}
	}

	public Collection<Tickable> getTickables() {
		synchronized (tickables) {
			return new LinkedList<>(tickables);
		}
	}
}
