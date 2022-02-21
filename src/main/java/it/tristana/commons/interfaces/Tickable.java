package it.tristana.commons.interfaces;

/**
 * The root hierarchy of the tickable system, used for example by the arena component system
 */

@FunctionalInterface
public interface Tickable {

	/**
	 * An action to be run each time this class is ticked
	 */
	void runTick();
}