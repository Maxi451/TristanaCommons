package it.tristana.commons.interfaces.shop;

/**
 * A BalanceHolder is an entity that holds a bank account and can pay or receive money
 */
public interface BalanceHolder {

	/**
	 * Adds the given amount of money to this balance holder.<br>
	 * @throws IllegalArgumentException If money < 0
	 * @param money The amount of money to add
	 */
	void giveMoney(double money);
	
	/**
	 * Retrieves the amount of money that this balance holder has
	 * @return The amount of money
	 */
	double getMoney();
	
	/**
	 * Checks if this balance holder has at least enaugh money as the<br>
	 * given amount, and if true then removes such amount from its account
	 * @param money The amount of money that should be removed
	 * @return True if the amount of money has been paid, false otherwise
	 */
	boolean tryToPay(double money);
}
