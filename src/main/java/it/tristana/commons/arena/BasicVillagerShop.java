package it.tristana.commons.arena;

import org.bukkit.Location;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Villager.Profession;

import it.tristana.commons.interfaces.util.VillagerShop;

public abstract class BasicVillagerShop implements VillagerShop {

	protected Villager villager;
	protected Location location;
	protected String name;
	protected boolean spawned;

	public BasicVillagerShop(Location location, String name) {
		this.location = location;
		this.name = name;
	}

	@Override
	public Location getLocation() {
		return location;
	}

	@Override
	public void spawnEntity() {
		if (!spawned) {
			villager = location.getWorld().spawn(location, Villager.class);
			villager.setCanPickupItems(false);
			removeAi();
			villager.setProfession(Profession.LIBRARIAN);
			villager.setNoDamageTicks(999999);
			villager.setCustomName(name);
			villager.setCustomNameVisible(true);
			/*
			// Only for 1.8.x
			EntityVillager nmsVillager = ((CraftVillager) villager).getHandle();
			float yaw = location.getYaw();
			nmsVillager.aK = yaw;
			nmsVillager.aL = yaw;
			nmsVillager.yaw = yaw;
			nmsVillager.pitch = location.getPitch();
			 */
			updateLookingDirection();
			spawned = true;
		}
	}

	@Override
	public void despawnEntity() {
		if (spawned) {
			villager.remove();
			spawned = false;
		}
	}

	@Override
	public Villager getEntity() {
		return villager;
	}

	protected abstract void removeAi();

	protected abstract void updateLookingDirection();
}
