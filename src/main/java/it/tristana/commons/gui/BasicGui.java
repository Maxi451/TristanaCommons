package it.tristana.commons.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import it.tristana.commons.helper.CommonsHelper;
import it.tristana.commons.interfaces.gui.Element;
import it.tristana.commons.interfaces.gui.Gui;

public abstract class BasicGui implements Gui {

	protected Element[] elements;
	protected String name;
	
	public BasicGui(String name) {
		this.name = name;
	}
	
	@Override
	public void update(Player player) {
		open(player);
	}

	@Override
	public Element getById(int id) {
		checkElements();
		return elements[id];
	}
	
	@Override
	public Element[] getElements() {
		checkElements();
		return elements;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public void onClick(Player player, int slot) {
		checkElements();
		elements[slot].onClick(player);
		if (elements[slot].closesInventory(player)) {
			close(player);
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
		checkElements();
		Inventory inventory = Bukkit.createInventory(null, CommonsHelper.getGuiSizeFromNumOfElements(elements), name);
		for (int i = 0; i < elements.length; i ++) {
			inventory.setItem(i, elements[i].getDisplayItem(player));
		}
		return inventory;
	}
	
	private void checkElements() {
		if (elements == null) {
			elements = createElements();
		}
	}
	
	protected abstract Element[] createElements();
}
