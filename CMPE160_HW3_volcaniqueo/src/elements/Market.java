package elements;

import java.util.ArrayList;
import java.util.PriorityQueue;
/**
 * Market class for implementing market functionalities.
 * @author 2019400033
 *
 */
public class Market {
	
	/**
	 * sellingOrders is the PriorityQueue that holds the selling orders.
	 */
	private PriorityQueue<SellingOrder> sellingOrders;
	/**
	 * buyingOrders is the PriorityQueue that holds the buying orders.
	 */
	private PriorityQueue<BuyingOrder> buyingOrders;
	/**
	 * transactions is the ArrayList that holds the successful transactions.
	 */
	private ArrayList<Transaction> transactions;
	/**
	 * fee is the fee that market charges traders. (Not adjusted yet.)
	 */
	private int fee;
	/**
	 * feeAdjustedRatio is the adjusted specific ratio that directly enters calculation. (1- (fee/1000)).
	 */
	private double feeAdjustedRatio;
	/**
	 * currentBuyPrice is the current buy price of the market. (Price of sellingOrders.peek()).
	 * 0.00 if sellingOrders is empty.
	 */
	private double currentBuyPrice;
	/**
	 * currentSellPrice is the current sell price of the market. (Price of buyingOrders.peek()).
	 * 0.00 if buyingOrders is empty.
	 */
	private double currentSellPrice;
	/**
	 * currentPrice is the average of currentSellPrice and currentBuyPrice.
	 * currentSellPrice if currentBuyPrice is 0.00.
	 * currentBuyPrice if currentSellPrice is 0.00.
	 * 0.00 if both 0.00.
	 * 
	 */
	private double currentPrice;
	
	/**
	 * Constructor of Market class. Initializes other fields.
	 * @param fee fee is the fee that market charges traders.
	 */
	public Market(int fee) {
		this.fee = fee;
		this.feeAdjustedRatio = (1 - (fee / 1000.0));
		this.sellingOrders = new PriorityQueue<SellingOrder>();
		this.buyingOrders = new PriorityQueue<BuyingOrder>();
		this.transactions = new ArrayList<Transaction>();
		this.currentBuyPrice = 0.00;
		this.currentSellPrice = 0.00;
		this.currentPrice = 0.00;
	}
	/**
	 * Adds order to the sellingOrders queue.
	 * @param order order is the SellingOrder that will be added to sellingOrders queue.
	 */
	public void giveSellOrder(SellingOrder order) {
		this.sellingOrders.add(order);
		return;
	}
	/**
	 * Adds order to the buyingOrders queue.
	 * @param order order is the BuyingOrder that will be added to buyingOrders queue.
	 */
	public void giveBuyOrder(BuyingOrder order) {
		this.buyingOrders.add(order);
		return;
	}
	/**
	 * Method for checking any possible transactions.
	 * If the least priced selling order is smaller or equal than the biggest priced buying order, makes the swap.
	 * Always takes the price as the price of SellingOrder. If there is any difference, returns the surplus to the BuyingOrder's associated trader.
	 * If the amounts are different, divides the bigger one and sends the remaining back to the associated queue.
	 * @param traders traders is the ArrayList that holds all the traders.
	 */
	public void checkTransactions(ArrayList<Trader> traders) {
		while ((sellingOrders.peek() != null) && (buyingOrders.peek() != null) && (sellingOrders.peek().price <= buyingOrders.peek().price)) {
			SellingOrder mySO = sellingOrders.poll();
			BuyingOrder myBO = buyingOrders.poll();
			double processedPrice = mySO.price;
			double processedAmount = Math.min(mySO.amount, myBO.amount);
			traders.get(mySO.traderID).getWallet().setBlockedCoins(traders.get(mySO.traderID).getWallet().getBlockedCoins() - processedAmount);
			traders.get(myBO.traderID).getWallet().setBlockedDollars(traders.get(myBO.traderID).getWallet().getBlockedDollars() - (processedAmount * myBO.price));
			if (myBO.price > processedPrice)
				traders.get(myBO.traderID).getWallet().setDollars(traders.get(myBO.traderID).getWallet().getDollars() + ((myBO.price - processedPrice) * processedAmount));
			traders.get(mySO.traderID).getWallet().setDollars(traders.get(mySO.traderID).getWallet().getDollars() + (processedPrice * processedAmount * feeAdjustedRatio));
			traders.get(myBO.traderID).getWallet().setCoins(traders.get(myBO.traderID).getWallet().getCoins() + processedAmount);
			if (myBO.amount > mySO.amount) {
				BuyingOrder returnedOrder = new BuyingOrder(myBO.traderID, (myBO.amount - processedAmount), myBO.price);
				this.giveBuyOrder(returnedOrder);
				BuyingOrder actualOrder = new BuyingOrder(myBO.traderID, processedAmount, processedPrice);
				Transaction processedTransaction = new Transaction(mySO, actualOrder);
				this.transactions.add(processedTransaction);
			}
			else if (mySO.amount > myBO.amount) {
				SellingOrder returnedOrder = new SellingOrder(mySO.traderID, (mySO.amount - processedAmount), mySO.price);
				this.giveSellOrder(returnedOrder);
				SellingOrder actualOrder = new SellingOrder(mySO.traderID, processedAmount, processedPrice);
				Transaction processedTransaction = new Transaction(actualOrder, myBO);
				this.transactions.add(processedTransaction);
			}
			else {
				Transaction processedTransaction = new Transaction(mySO, myBO);
				this.transactions.add(processedTransaction);
			}
		}
		return;
	}
	/**
	 * Gives artificial orders with trader 0 to converge the market price to the given price as much as possible.
	 * At every order, calls checkTransactions method.
	 * @param price price is the price that market will aim to converge as much as possible.
	 * @param traders traders is the ArrayList that holds all the traders.
	 */
	public void makeOpenMarketOperation(double price, ArrayList<Trader> traders) {
		while (buyingOrders.peek().price >= price) {
			SellingOrder artificialOrder = new SellingOrder(0, buyingOrders.peek().amount, buyingOrders.peek().price);
			this.giveSellOrder(artificialOrder);
			this.checkTransactions(traders);
		}
		while (sellingOrders.peek().price <= price) {
			BuyingOrder artificialOrder = new BuyingOrder(0, sellingOrders.peek().amount, sellingOrders.peek().price);
			this.giveBuyOrder(artificialOrder);
			this.checkTransactions(traders);
		}
		return;
	}
	
	/**
	 * Method for returning current buying size of the market.
	 * @return returns current buying size of the market. (i.e. The sum of the dollars in the buyingOrders queue.)
	 */
	public double getBuyingSize() {
		double buyingSize = 0.00;
		for (BuyingOrder myBO : buyingOrders) {
			buyingSize += (myBO.amount * myBO.price);
		}
		return buyingSize;
	}
	/**
	 * Method for returning current selling size of the market.
	 * @return returns current selling size of the market. (i.e. The sum of the coins in the sellingOrders queue.)
	 */
	public double getSellingSize() {
		double sellingSize = 0.00;
		for (SellingOrder mySO : sellingOrders) {
			sellingSize += mySO.amount;
		}
		return sellingSize;
	}
	

	/**
	 * Getter method for currentBuyPrice field.
	 * @return the currentBuyPrice returns the currentBuyPrice.
	 */
	public double getCurrentBuyPrice() {
		if (this.sellingOrders.isEmpty())
			return 0.00;
		else {
		this.currentBuyPrice = this.sellingOrders.peek().price;
		return this.currentBuyPrice;
		}
	}

	/**
	 * Getter method for currentSellPrice field.
	 * @return the currentSellPrice returns the currentSellPrice.
	 */
	public double getCurrentSellPrice() {
		if (this.buyingOrders.isEmpty())
			return 0.00;
		else {
			this.currentSellPrice = this.buyingOrders.peek().price;
			return this.currentSellPrice;
		}
	}

	/**
	 * Getter method for currentPrice field.
	 * @return the currentPrice returns the currentPrice.
	 */
	public double getCurrentPrice() {
		if ((this.getCurrentBuyPrice() == 0.00) && (this.getCurrentSellPrice() == 0.00))
			return 0.00;
		if (this.getCurrentBuyPrice() == 0.00)
			return this.getCurrentSellPrice();
		if (this.getCurrentSellPrice() == 0.00)
			return this.getCurrentBuyPrice();
		this.currentPrice = ((this.getCurrentBuyPrice() + this.getCurrentSellPrice()) / 2);
		return this.currentPrice;
	}

	/**
	 * Getter method for transactions field.
	 * @return the transactions returns the ArrayList that contains all successful transactions.
	 */
	public ArrayList<Transaction> getTransactions() {
		return transactions;
	}

	/**
	 * Getter method for sellingOrders field.
	 * @return the sellingOrders returns the sellingOrders queue.
	 */
	public PriorityQueue<SellingOrder> getSellingOrders() {
		return sellingOrders;
	}

	/**
	 * Getter method for buyingOrders field.
	 * @return the buyingOrders returns the buyingOrders queue.
	 */
	public PriorityQueue<BuyingOrder> getBuyingOrders() {
		return buyingOrders;
	}

	/**
	 * Getter method for feeAdjustedRatio field.
	 * @return the feeAdjustedRatio returns the feeAdjustedRatio.
	 */
	public double getFeeAdjustedRatio() {
		return feeAdjustedRatio;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}

