package elements;
/**
 * Trader class for traders.
 * @author 2019400033
 *
 */
public class Trader {
	
	/**
	 * id is the id of the trader.
	 */
	private int id;
	/**
	 * wallet is the wallet of the trader.
	 */
	private Wallet wallet;
	/**
	 * numberOfUsers is the total number of traders.
	 */
	public static int numberOfUsers = 0;
	/**
	 * Constructor of Trader class.
	 * @param dollars dollars is the dollars that trader initially have.
	 * @param coins coins is the coins that trader initially have.
	 */
	public Trader(double dollars, double coins) {
		this.id = Trader.numberOfUsers;
		this.wallet = new Wallet(dollars, coins);
		Trader.numberOfUsers ++;
	}
	/**
	 * Method for creating SellingOrder if trader has enough assets.
	 * @param amount amount is the amount of the SellingOrder.
	 * @param price price is the price of the SellingOrder.
	 * @param market market is the market that makes the swap.
	 * @return returns 1 if query is successful, 0 if it is invalid.
	 */
	public int sell(double amount, double price, Market market) {
		if ((this.wallet.getCoins() >= amount)  && (amount > 0)) {
			this.wallet.setCoins(this.wallet.getCoins() - amount);
			this.wallet.setBlockedCoins(this.wallet.getBlockedCoins() + amount);
			SellingOrder nouvelleOrder = new SellingOrder(this.id, amount, price);
			market.giveSellOrder(nouvelleOrder);
			return 1;
		}
		else
			return 0;
	}
	/**
	 * 
	 * Method for creating BuyingOrder if trader has enough assets.
	 * @param amount amount is the amount of the BuyingOrder.
	 * @param price price is the price of the BuyingOrder.
	 * @param market market is the market that makes the swap.
	 * @return returns 1 if query is successful, 0 if it is invalid.
	 */
	public int buy(double amount, double price, Market market) {
		if ((this.wallet.getDollars() >= (amount * price) && (amount > 0))) {
			this.wallet.setDollars(this.wallet.getDollars() - (amount * price));
			this.wallet.setBlockedDollars(this.wallet.getBlockedDollars() + (amount * price));
			BuyingOrder nouvelleOrder = new BuyingOrder(this.id, amount, price);
			market.giveBuyOrder(nouvelleOrder);
			return 1;
		}
		else
			return 0;
	}

	/**
	 * Getter method for id field.
	 * @return the id returns the id.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Setter method for id field.
	 * @param id the id to set.
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Getter method for wallet field.
	 * @return the wallet returns the wallet.
	 */
	public Wallet getWallet() {
		return wallet;
	}

	/**
	 * Setter method for wallet field.
	 * @param wallet the wallet to set.
	 */
	public void setWallet(Wallet wallet) {
		this.wallet = wallet;
	}
	
	
}
