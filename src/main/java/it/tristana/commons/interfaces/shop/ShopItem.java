package it.tristana.commons.interfaces.shop;

/**
 * A ShopItem represents an item that can be bought in a shop
 * @param <B> The {@link BalanceHolder} subclass represented by this class
 */
public interface ShopItem<B extends BalanceHolder> {

	/**
	 * Retrieves the price of this item
	 * @return The price of this item
	 */
	double getPrice();
	
	/**
	 * Retrieves the name of this item
	 * @return The name of this item
	 */
	String getName();
	
	/**
	 * Executes this action if the {@link BalanceHolder#tryToPay(double)}<br>
	 * method call with {@link #getPrice()} as parameter returns true
	 * @param balanceHolder The buyer
	 * @return True if the action was performed and the money paid, false otherwise
	 */
	boolean doAction(B balanceHolder);
}
