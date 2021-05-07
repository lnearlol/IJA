/**
 * Project: Storage Simulation
 * @author Roman Stepaniuk  <xstepa64>, Viktoryia Bahdanovich <xbahda01>
 * 
 * Class represents all functions for working with Buy
 * Date: 07.05.2021
 */

package ija.ija2021.projekt.classes;
import java.util.*;
import java.util.ArrayList;

public class Buy {
	private int orderId;
	private int buyId;
	private int returned;
	private GoodsItem [] buy;
	private ArrayList <Integer> itemCount;
	private ArrayList <String> itemName;

	public Buy(GoodsItem [] buy, int orderId, int buyId) {
		this.buy = Arrays.copyOf(buy, buy.length);
		this.orderId = orderId;
		this.buyId = buyId;
		this.itemCount = new ArrayList <Integer>();
		this.itemName = new ArrayList <String>();
		this.addNamesAndCounts();
	}

	public ArrayList<GoodsItem> getItemList(){
		ArrayList<GoodsItem> newList = new ArrayList<GoodsItem>();
		for(int i = 0; i < 5; i++){
			if(this.buy[i] != null){
				newList.add(buy[i]);
			}
		}
		return newList;
	}

	public void addNamesAndCounts(){
		if(buy[0] == null){
			System.out.println("ERROR  BUY [0] IS NULL!");
			return;
		}
		for (int i = 0; i < 5; i++){
			
			GoodsItem item = this.buy[i];
			if (item == null)
				return;
			if(!this.itemName.isEmpty()){
				boolean found = false;
				for(int j = 0; j < this.itemName.size(); j++){
					if(this.itemName.get(j).equals(item.goods().getName())){
						this.itemCount.set(j, itemCount.get(j)+1);
						found = true;
						break;
					}
				}
				if (!found){
					this.itemName.add(item.goods().getName());
					this.itemCount.add(1);
				}
			} else {
				itemCount.add(1);
				itemName.add(item.goods().getName());
			}
		}
	}
	
	public String printInform() {
		String str = "";
    	for (int i = 0; i < this.itemName.size(); i++){
			if(this.itemName.get(i) != null)
				str += this.itemName.get(i) + " -\n " + String.valueOf(this.itemCount.get(i)) + "\n";
		}
    	return str;
	}

	public String printBuy(){
		return "Order id: " + this.orderId + "\n" + 
		"Buy id: " + this.buyId + "\n\nBuy content:\n" + 
		this.itemName + " \n- " + this.itemCount +"\n";
		
	}

	@Override
	public String toString(){
		return "Buy { \nBuy ID = "+ this.buyId + 
		"\n" + this.printInform() + "\n}";
	}
}
