package it.tristana.commons.helper;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import it.tristana.commons.arena.player.BasicParty;
import it.tristana.commons.interfaces.arena.player.PartiesManager;
import it.tristana.commons.interfaces.arena.player.Party;

public class BasicPartiesManager implements PartiesManager {
	
	private List<Party> parties;
	
	public BasicPartiesManager() {
		parties = new ArrayList<Party>();
	}
	
	@Override
	public Party createParty(Player owner) {
		Party party = generateParty(owner);
		parties.add(party);
		return party;
	}
	
	@Override
	public void disbandParty(Party party) {
		parties.remove(party);
	}
	
	@Override
	public Party getPartyFromPlayer(Player player) {
		Party result = null;
		for (Party party : parties) {
			if (party.getLeader() == player || party.getPlayers().contains(player)) {
				result = party;
				break;
			}
		}
		return result;
	}
	
	protected Party generateParty(Player owner) {
		return new BasicParty(owner);
	}
}
