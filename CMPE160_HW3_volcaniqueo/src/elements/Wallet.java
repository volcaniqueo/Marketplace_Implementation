package elements;
/**
 * Wallet class for a trader's wallet.
 * @author 2019400033
 *
 */
public class Wallet {
	/**
	 * dollars is the dollars that the wallet have.
	 */
	private double dollars;
	/**
	 * coins is the coins that the wallet have.
	 */
	private double coins;
	/**
	 * blockedDollars is the dollars blocked from this wallet.
	 */
	private double blockedDollars;
	/**
	 * blockedCoins is the coins blocked from this wallet.
	 */
	private double blockedCoins;
	
	/**
	 * Constructor of the Wallet class.
	 * @param dollars dollars is the dollars that the wallet initially have.
	 * @param coins coins is the coins that the wallet initially have.
	 */
	public Wallet(double dollars, double coins) {
		this.dollars = dollars;
		this.coins = coins;
	}

	/**
	 * Getter method for dollars field.
	 * @return the dollars returns the dollars.
	 */
	public double getDollars() {
		return dollars;
	}

	/**
	 * Setter method for dollars field.
	 * @param dollars the dollars to set.
	 */
	public void setDollars(double dollars) {
		this.dollars = dollars;
	}

	/**
	 * Getter method for coins field.
	 * @return the coins returns the coins.
	 */
	public double getCoins() {
		return coins;
	}

	/**
	 * Setter method for coins field.
	 * @param coins the coins to set.
	 */
	public void setCoins(double coins) {
		this.coins = coins;
	}

	/**
	 * Getter method for blockedDollars field.
	 * @return the blockedDollars returns the blockedDollars.
	 */
	public double getBlockedDollars() {
		return blockedDollars;
	}

	/**
	 * Setter method for blockedDollars field.
	 * @param blockedDollars the blockedDollars to set.
	 */
	public void setBlockedDollars(double blockedDollars) {
		this.blockedDollars = blockedDollars;
	}

	/**
	 * Getter method for blockedCoins field.
	 * @return the blockedCoins returns the blockedCoins.
	 */
	public double getBlockedCoins() {
		return blockedCoins;
	}

	/**
	 * Setter method for blockedCoins field.
	 * @param blockedCoins the blockedCoins to set.
	 */
	public void setBlockedCoins(double blockedCoins) {
		this.blockedCoins = blockedCoins;
	}
	
	
	
	
	

}
