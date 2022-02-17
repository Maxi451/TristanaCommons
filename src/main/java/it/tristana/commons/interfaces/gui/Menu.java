package it.tristana.commons.interfaces.gui;

import org.bukkit.entity.Player;

public interface Menu extends Element {

	Gui getGui(Player player);
	
	Element[] getElements();
}
