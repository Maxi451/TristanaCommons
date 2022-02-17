package it.tristana.commons.gui;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import it.tristana.commons.interfaces.gui.Element;

public abstract class BasicElement implements Element {

	protected String name;
	protected List<String> lore;

	public BasicElement(String name) {
		this(name, null);
	}

	public BasicElement(String name, List<String> lore) {
		this.name = name;
		this.lore = lore;
	}

	@Override
	public final ItemStack getDisplayItem(Player player) {
		ItemStack item = getRawDisplayItem(player);
		editItem(item, name, lore);
		return item;
	}

	protected static void editItem(ItemStack displayItem, String name, List<String> lore) {
		ItemMeta itemMeta = displayItem.getItemMeta();
		itemMeta.setDisplayName(name);
		itemMeta.setLore(lore);
		displayItem.setItemMeta(itemMeta);
	}

	protected abstract ItemStack getRawDisplayItem(Player player);
}
