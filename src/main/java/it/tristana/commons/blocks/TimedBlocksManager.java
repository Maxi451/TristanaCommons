package it.tristana.commons.blocks;

import org.bukkit.block.Block;

import it.tristana.commons.interfaces.Tickable;

public interface TimedBlocksManager extends Tickable {

	void add(Block block);
	
	void remove(Block block);
	
	void removeAll();
	
	void setLifeTicks(int lifeTicks);
}
