package it.tristana.commons.blocks;

import java.util.Iterator;
import java.util.LinkedList;

import org.bukkit.Material;
import org.bukkit.block.Block;

public class BasicTimedBlocksManager implements TimedBlocksManager {

	protected LinkedList<TimedBlock> blocks;
	protected int lifeTicks;
	
	public BasicTimedBlocksManager(int lifeTicks) {
		this.lifeTicks = lifeTicks;
		blocks = new LinkedList<>();
	}
	
	@Override
	public void runTick() {
		Iterator<TimedBlock> iterator = blocks.iterator();
		int index = 0;
		int lifeTicks = getLifeTicks();
		while (iterator.hasNext()) {
			TimedBlock block = iterator.next();
			if (block.getTicks() >= lifeTicks) {
				break;
			}
			index ++;
		}
		while (index < blocks.size()) {
			blocks.getLast().getBlock().setType(Material.AIR);
			blocks.removeLast();
		}
		blocks.forEach(TimedBlock::runTick);
	}

	@Override
	public void add(Block block) {
		blocks.addFirst(new TimedBlock(block));
	}

	@Override
	public void remove(Block block) {
		Iterator<TimedBlock> iterator = blocks.iterator();
		while (iterator.hasNext()) {
			if (iterator.next().getBlock() == block) {
				iterator.remove();
				break;
			}
		}
	}

	@Override
	public void removeAll() {
		blocks.forEach(timedBlock -> timedBlock.getBlock().setType(Material.AIR));
		blocks.clear();
	}

	@Override
	public void setLifeTicks(int lifeTicks) {
		this.lifeTicks = lifeTicks;
	}
	
	protected int getLifeTicks() {
		return lifeTicks;
	}
}
