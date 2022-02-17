package it.tristana.commons.interfaces.util;

import org.bukkit.Location;
import org.bukkit.entity.Villager;

public interface VillagerShop {

	Location getLocation();
	
	void spawnEntity();
	
	void despawnEntity();
	
	Villager getEntity();
}
