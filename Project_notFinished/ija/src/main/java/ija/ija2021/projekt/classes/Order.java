package ija.ija2021.projekt.classes;
import java.util.ArrayList;

public class Order {
	int id;
	private ArrayList <Buy> buyList;
	public Order() {
		++id;
		this.buyList = new ArrayList<Buy>();
	}
	
	public boolean addBuy(Buy buy) {
		// not more than 5 pieces of Buy
		boolean done = false;
		if(this.buyList.isEmpty() && buy.getBuyCount() <= 5) {
			this.buyList.add(buy);
			return true;
		}
		int countInList = 0;
		for (Buy elem : this.buyList) {
			countInList += elem.getBuyCount();
		}
		
		if((countInList + buy.getBuyCount()) <= 5) {
			done = true;
			this.buyList.add(buy);
		}
		
		return done;
	}
	
	public void printInfo(){
		
		int i = 1;
		if(this.buyList == null)
			System.out.println("BUYLIST IS NULL");
		
		for (Buy elem : this.buyList) {
			elem.printInform();
			System.out.print(" ");
		}
		System.out.println("");

	}
	
	public Buy getFirstBuy() {
		if (this.buyList.isEmpty() == true)
			return null;
		
		return this.buyList.get(0);
	}
	
	public void removeBuy(Buy buy) {
		this.buyList.remove(buy);
	}
}

