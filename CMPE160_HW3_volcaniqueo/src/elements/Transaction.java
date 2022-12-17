package elements;
/**
 * Transaction class for transactions.
 * @author 2019400033
 *
 */
public class Transaction {
	/**
	 * sellingOrder is the SellingOrder that is associated with the transaction.
	 */
	private SellingOrder sellingOrder;
	/**
	 * buyingOrder is the BuyingOrder that is associated with the transaction.
	 */
	private BuyingOrder buyingOrder;
	/**
	 * Constructor of the Transaction class.
	 * @param sellingOrder sellingOrder is the SellingOrder that is associated with the transaction.
	 * @param buyingOrder buyingOrder is the BuyingOrder that is associated with the transaction.
	 */
	public Transaction(SellingOrder sellingOrder, BuyingOrder buyingOrder) {
		this.sellingOrder = sellingOrder;
		this.buyingOrder = buyingOrder;
	}
	
	

}
