package elements;
/**
 * Parent class of BuyingOrder and SellingOrder.
 * @author 2019400033
 *
 */
public class Order {
	
	/**
	 * amount is the amount associated with the order.
	 */
	protected double amount;
	/**
	 * price is the price associated with the order.
	 */
	protected double price;
	/**
	 * traderID is the ID of the trader.
	 */
	protected int traderID;
	
	/**
	 * Constructor of Order.
	 * @param traderID traderID is the ID of the trader.
	 * @param amount amount is the amount associated with the order.
	 * @param price price is the price associated with the order.
	 */
	public Order(int traderID, double amount, double price) {
		this.traderID = traderID;
		this.amount = amount;
		this.price = price;
	}

	/**
	 * Getter method for amount field.
	 * @return the amount returns the amount.
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * Getter method for price field.
	 * @return the price returns the price.
	 */
	public double getPrice() {
		return price;
	}
	
	
	
	

}


