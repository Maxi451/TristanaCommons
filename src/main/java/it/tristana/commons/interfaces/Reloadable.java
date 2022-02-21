package it.tristana.commons.interfaces;

/**
 * A class which implements this interface means<br>
 * that its internal configuration can be reloaded
 */
public interface Reloadable {

	/**
	 * When this method is called the internal configuration of this class is reloaded.<br>
	 * What it actually means depends from the implementation, but it can be expected<br>
	 * to read again from some files, or to cause a reload on other components too
	 */
	void reload();
}
