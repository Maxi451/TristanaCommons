package it.tristana.commons.blocks;

import org.bukkit.block.Block;

import it.tristana.commons.interfaces.Tickable;

public class TimedBlock implements Tickable {

	private Block block;
	private int ticks;
	
	public TimedBlock(Block block) {
		this.block = block;
	}

	@Override
	public void runTick() {
		ticks ++;
	}
	
	public Block getBlock() {
		return block;
	}
	
	public int getTicks() {
		return ticks;
	}
}
