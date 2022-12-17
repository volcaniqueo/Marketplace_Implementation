package elements;

/**
 * SellingOrder class for selling orders. Extends Order.java, implements Comparable.
 * @author 2019400033
 *
 */
public class SellingOrder extends Order implements Comparable<SellingOrder> {
	
	/**
	 * Constructor of SellingOrder. Calls super's constructor with the parameters.
	 * @param traderID traderID is the ID of the trader.
	 * @param amount amount is the amount associated with the order.
	 * @param price price is the price associated with the order.
	 */
	public SellingOrder(int traderID, double amount, double price) {
		super(traderID, amount, price);
	}
	/**
	 * CompareTo method for priority associated with orders.
	 * Lower the price, higher the priority.
	 * If same, higher the amount, higher the priority.
	 * If same, lower the trade ID, higher the priority.
	 */
	@Override
	public int compareTo(SellingOrder o) {
		if (this.price > o.price)
			return 1;
		else if (this.price < o.price)
			return -1;
		else if (this.amount < o.amount)
			return 1;
		else if (this.amount > o.amount)
			return -1;
		else if (this.traderID > o.traderID)
			return 1;
		else
			return -1;
	}
	
	
	

}