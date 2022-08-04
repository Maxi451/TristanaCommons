package it.tristana.commons.quest;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import it.tristana.commons.gui.BasicElement;
import it.tristana.commons.interfaces.arena.player.ArenaPlayer;
import it.tristana.commons.interfaces.quest.Quest;

public class BasicQuestElement<P extends ArenaPlayer<?>> extends BasicElement {

	protected final Quest<P> quest;
	
	public BasicQuestElement(Quest<P> quest) {
		super(quest.getName(), quest.getDescription());
		this.quest = quest;
	}

	@Override
	public void onClick(Player player) {}

	@Override
	public boolean closesInventory(Player player) {
		return false;
	}

	@Override
	protected ItemStack getRawDisplayItem(Player player) {
		return quest.getDisplayItem();
	}

}
