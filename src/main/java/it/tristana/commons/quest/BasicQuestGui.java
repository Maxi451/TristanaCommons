package it.tristana.commons.quest;

import org.bukkit.entity.Player;

import it.tristana.commons.gui.BasicGui;
import it.tristana.commons.interfaces.arena.player.ArenaPlayer;
import it.tristana.commons.interfaces.gui.Element;
import it.tristana.commons.interfaces.quest.Quest;

public class BasicQuestGui<P extends ArenaPlayer<?>> extends BasicGui {

	protected Quest<P>[] quests;
	
	public BasicQuestGui(String name, Quest<P>[] quests) {
		super(name);
		this.quests = quests;
	}

	@Override
	public Element[] getElements(Player player) {
		Element[] elements = new Element[quests.length];
		for (int i = 0; i < quests.length; i ++) {
			elements[i] = new BasicQuestElement<>(quests[i]);
		}
		return elements;
	}
}
