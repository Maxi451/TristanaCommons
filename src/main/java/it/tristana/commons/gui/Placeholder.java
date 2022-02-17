package it.tristana.commons.gui;

import java.util.List;

import org.bukkit.entity.Player;

public abstract class Placeholder extends BasicElement {
	
	private short damage;
	
	public Placeholder(int damage) {
		this(null, null, (short) damage);
	}
	
	public Placeholder(String name, List<String> lore, short damage) {
		super(name, lore);
		this.damage = damage;
	}

	@Override
	public void onClick(Player player) {}
	
	public final short getDamage() {
		return damage;
	}
}
