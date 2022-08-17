package it.tristana.commons.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import it.tristana.commons.helper.CommonsHelper;
import it.tristana.commons.interfaces.gui.Element;
import it.tristana.commons.interfaces.gui.Gui;

public abstract class BasicGui implements Gui {

	protected String name;
	
	public BasicGui(String name) {
		this.name = name;
	}
	
	@Override
	public void update(Player player) {
		open(player);
	}

	@Override
	public Element getById(Player player, int id) {
		Element[] elements = getElements(null);
		return id >= 0 && id < elements.length ? elements[id] : null;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public void onClick(Player player, int slot) {
		Element[] elements = getElements(player);
		if (slot < 0 || slot >= elements.length || elements[slot] == null) {
			return;
		}
		elements[slot].onClick(player);
		if (elements[slot].closesInventory(player)) {
			close(player);
		} else {
			open(player);
		}
	}

	@Override
	public void open(Player player) {
		player.openInventory(getInventory(player));
	}
	
	@Override
	public void close(Player player) {
		player.closeInventory();
	}

	@Override
	public Inventory getInventory(Player player) {
		Element[] elements = getElements(player);
		Inventory inventory = Bukkit.createInventory(null, CommonsHelper.getGuiSizeFromNumOfElements(elements), name);
		for (int i = 0; i < elements.length; i ++) {
			if (elements[i] != null) {
				inventory.setItem(i, elements[i].getDisplayItem(player));
			}
		}
		return inventory;
	}
}
