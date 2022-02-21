package it.tristana.commons.interfaces;

import it.tristana.commons.interfaces.database.Database;

/**
 * An interface to declare that this object contains a reference to a database, such as MySQL
 */
public interface DatabaseHolder {

	/**
	 * Retrieves the database instance stored in this object
	 * @return The database instance
	 */
	Database getStorage();
}
