package it.tristana.commons.gui;

import org.bukkit.entity.Player;

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
		if (elements == null) {
			elements = createElements();
		}
		return elements[id];
	}
	
	@Override
	public Element[] getElements() {
		return elements;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	protected abstract Element[] createElements();
}
