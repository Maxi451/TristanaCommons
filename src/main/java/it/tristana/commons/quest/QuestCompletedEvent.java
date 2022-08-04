package it.tristana.commons.quest;

import org.bukkit.event.Event;

import it.tristana.commons.interfaces.arena.player.ArenaPlayer;
import it.tristana.commons.interfaces.quest.Quest;

public abstract class QuestCompletedEvent<P extends ArenaPlayer<?>, Q extends Quest<P>> extends Event {

	protected final Q quest;
	protected final P player;
	
	public QuestCompletedEvent(Q quest) {
		this.quest = quest;
		this.player = quest.getPlayer();
	}
	
	public Q getQuest() {
		return quest;
	}
	
	public P getPlayer() {
		return player;
	}
}
