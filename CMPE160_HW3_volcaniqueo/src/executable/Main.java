package executable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import elements.Trader;
import elements.Market;
/**
 * Main class for input output operations.
 * @author 2019400033
 *
 */
public class Main {
	/**
	 * Main method for input output operations.
	 * @param args args are the input and output files.
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException{
		
		Random myRandom;
		
		Scanner in = new Scanner(new File(args[0]));
		PrintStream out = new PrintStream(new File(args[1]));
		
		int A, B, C, D;
		A = in.nextInt();
		B = in.nextInt();
		C = in.nextInt();
		D = in.nextInt();
		
		ArrayList<Trader> traderList = new ArrayList<Trader>();
		Market myMarket = new Market(B);
		int numberOfInvalids = 0;
		myRandom = new Random(A);
		
		for (int i = 1; i <= C + D + 1; i++) {
			String myLine = in.nextLine();
			String[] myOrder = myLine.split("\s");
			if (i == 1)
				continue;
			if (i <= C + 1) {
				Trader nouvelleTrader = new Trader(Double.parseDouble(myOrder[0]), Double.parseDouble(myOrder[1]));
				traderList.add(nouvelleTrader);
				continue;
			}
			if (Integer.parseInt(myOrder[0]) == 10) {
				int myControl = traderList.get(Integer.parseInt(myOrder[1])).buy(Double.parseDouble(myOrder[3]), Double.parseDouble(myOrder[2]), myMarket);
				if (myControl == 0)
					numberOfInvalids ++;
				myMarket.checkTransactions(traderList);
				continue;
			}
			if (Integer.parseInt(myOrder[0]) == 11) {
				if (myMarket.getCurrentBuyPrice() == 0.00) {
					numberOfInvalids ++;
					continue;
				}
				int myControl = traderList.get(Integer.parseInt(myOrder[1])).buy(Double.parseDouble(myOrder[2]), myMarket.getCurrentBuyPrice(), myMarket);
				if (myControl == 0)
					numberOfInvalids ++;
				myMarket.checkTransactions(traderList);
				continue;
			}
			if (Integer.parseInt(myOrder[0]) == 20) {
				int myControl = traderList.get(Integer.parseInt(myOrder[1])).sell(Double.parseDouble(myOrder[3]), Double.parseDouble(myOrder[2]), myMarket);
				if (myControl == 0)
					numberOfInvalids ++;
				myMarket.checkTransactions(traderList);
				continue;
			}
			if (Integer.parseInt(myOrder[0]) == 21) {
				if (myMarket.getCurrentSellPrice() == 0.00) {
					numberOfInvalids ++;
					continue;
				}
				int myControl = traderList.get(Integer.parseInt(myOrder[1])).sell(Double.parseDouble(myOrder[2]), myMarket.getCurrentSellPrice(), myMarket);
				if (myControl == 0)
					numberOfInvalids ++;
				myMarket.checkTransactions(traderList);
				continue;
			}
			if (Integer.parseInt(myOrder[0]) == 3) {
				traderList.get(Integer.parseInt(myOrder[1])).getWallet().setDollars(traderList.get(Integer.parseInt(myOrder[1])).getWallet().getDollars() + Double.parseDouble(myOrder[2]));
				continue;
			}
			if (Integer.parseInt(myOrder[0]) == 4) {
				if (traderList.get(Integer.parseInt(myOrder[1])).getWallet().getDollars() >= Double.parseDouble(myOrder[2])) {
					traderList.get(Integer.parseInt(myOrder[1])).getWallet().setDollars(traderList.get(Integer.parseInt(myOrder[1])).getWallet().getDollars() - Double.parseDouble(myOrder[2]));
				}
				else
					numberOfInvalids ++;
				continue;
			}
			if (Integer.parseInt(myOrder[0]) == 5) {
				int traderID = Integer.parseInt(myOrder[1]);
				double dollars = traderList.get(traderID).getWallet().getDollars() + traderList.get(traderID).getWallet().getBlockedDollars();
				double coins = traderList.get(traderID).getWallet().getCoins() + traderList.get(traderID).getWallet().getBlockedCoins();
				double roundedDollars = Math.round(dollars * 100000.0) / 100000.0;
				double roundedCoins = Math.round(coins * 100000.0) / 100000.0;
				String sRoundedDollars = String.format("%.5f", roundedDollars);
				String sRoundedCoins = String.format("%.5f", roundedCoins);
				out.println("Trader " + traderID + ": " + sRoundedDollars + "$ " + sRoundedCoins + "PQ" );
				continue;
			}
			if (Integer.parseInt(myOrder[0]) == 777) {
				for (Trader myTrader : traderList) {
					double toBeAdded = myRandom.nextDouble() * 10;
					myTrader.getWallet().setCoins(myTrader.getWallet().getCoins() + toBeAdded);
				}
				continue;
			}
			if (Integer.parseInt(myOrder[0]) == 666) {
				myMarket.makeOpenMarketOperation(Double.parseDouble(myOrder[1]), traderList);
				continue;
			}
			if (Integer.parseInt(myOrder[0]) == 501) {
				out.println("Number of successful transactions: " + myMarket.getTransactions().size());
				continue;
			}
			if (Integer.parseInt(myOrder[0]) == 502) {
				out.println("Number of invalid queries: " + numberOfInvalids);
				continue;
			}
			if (Integer.parseInt(myOrder[0]) == 505) {
				double roundedSellPrice = Math.round(myMarket.getCurrentSellPrice() * 100000.0) / 100000.0;
				double roundedBuyPrice = Math.round(myMarket.getCurrentBuyPrice() * 100000.0) / 100000.0;
				double roundedPrice = Math.round(myMarket.getCurrentPrice() * 100000.0) / 100000.0;
				String sRoundedSellPrice = String.format("%.5f", roundedSellPrice);
				String sRoundedBuyPrice = String.format("%.5f", roundedBuyPrice);
				String sRoundedPrice = String.format("%.5f", roundedPrice);
				out.println("Current prices: " + sRoundedSellPrice + " " + sRoundedBuyPrice + " " + sRoundedPrice);
				continue;
			}
			if (Integer.parseInt(myOrder[0]) == 555) {
				for (Trader myTrader : traderList) {
					double dollars = myTrader.getWallet().getDollars() + myTrader.getWallet().getBlockedDollars();
					double coins = myTrader.getWallet().getCoins() + myTrader.getWallet().getBlockedCoins();
					double roundedDollars = Math.round(dollars * 100000.0) / 100000.0;
					double roundedCoins = Math.round(coins * 100000.0) / 100000.0;
					String sRoundedDollars = String.format("%.5f", roundedDollars);
					String sRoundedCoins = String.format("%.5f", roundedCoins);
					out.println("Trader " + myTrader.getId() + ": " + sRoundedDollars + "$ " + sRoundedCoins + "PQ" );
				}
				continue;
			}
			if (Integer.parseInt(myOrder[0]) == 500) {
				double roundedBuyingSize = Math.round(myMarket.getBuyingSize() * 100000.0) / 100000.0;
				double roundedSellingSize = Math.round(myMarket.getSellingSize() * 100000.0) / 100000.0;
				String sRoundedBuyingSize = String.format("%.5f", roundedBuyingSize);
				String sRoundedSellingSize = String.format("%.5f", roundedSellingSize);
				out.println("Current market size: " + sRoundedBuyingSize + " " + sRoundedSellingSize);
				continue;
			}
		}
		
		
		in.close();
		out.close();
		
		
	}

}


