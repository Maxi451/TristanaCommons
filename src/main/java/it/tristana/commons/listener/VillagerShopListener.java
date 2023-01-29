package it.tristana.commons.listener;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import it.tristana.commons.interfaces.arena.ArenasManager;
import it.tristana.commons.interfaces.arena.ShopArena;
import it.tristana.commons.interfaces.gui.ClickedGuiManager;
import it.tristana.commons.interfaces.gui.Gui;
import it.tristana.commons.interfaces.util.VillagerShop;

public class VillagerShopListener<G extends Gui> implements Listener {

	private ArenasManager<? extends ShopArena<?, ?>, ?> arenasManager;
	private ClickedGuiManager clickedGuiManager;
	private Class<G> shopGui;

	public VillagerShopListener(ArenasManager<? extends ShopArena<?, ?>, ?> arenasManager, ClickedGuiManager clickedGuiManager, Class<G> shopGui) {
		this.arenasManager = arenasManager;
		this.clickedGuiManager = clickedGuiManager;
		this.shopGui = shopGui;
	}

	@EventHandler
	public void on(PlayerInteractEntityEvent event) {
		Entity target = event.getRightClicked();
		if (!(target instanceof Villager)) {
			return;
		}

		Player player = event.getPlayer();
		ShopArena<?, ?> arena = arenasManager.getArenaWithPlayer(player);
		if (arena == null) {
			return;
		}

		for (VillagerShop shop : arena.getShops()) {
			if (shop.getEntity() == target) {
				clickedGuiManager.getGui(shopGui).open(player);
				event.setCancelled(true);
				return;
			}
		}
	}
}
