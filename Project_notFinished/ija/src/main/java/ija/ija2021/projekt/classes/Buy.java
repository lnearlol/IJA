package ija.ija2021.projekt.classes;
import java.util.*;
import java.util.ArrayList;

public class Buy {
	int orderId;
	int buyId;
	int returned;
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
		System.out.println("********************** ");
			for(int i = 0; i < 5; i++){
				if (buy[i] != (null)){
					System.out.println("Array ["+i+"] = " + buy[i].goods().getName());
				} else {
					System.out.println("Array ["+i+"] = NULL" );
				}
			}
			
			System.out.println("********************** ");
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
					System.out.println("it's j  " + j);
					System.out.println("itemname  " + this.itemName.get(j));
					System.out.println("item.goods  " + item.goods().getName());
					if(this.itemName.get(j).equals(item.goods().getName())){
						// this.itemCount.add(j, itemCount.get(j)+1);

						System.out.println(": bylo :");
						for (int k = 0; k < this.itemName.size(); k++){
							System.out.println(this.itemName.get(k) + " # " + String.valueOf(this.itemCount.get(k)));
						}

						this.itemCount.set(j, itemCount.get(j)+1);
						System.out.println("IM HEREEEEEEEEEEEEE " + j);



						System.out.println(": stalo :");
						for (int k = 0; k < this.itemName.size(); k++){
							System.out.println(this.itemName.get(k) + " && " + String.valueOf(this.itemCount.get(k)));
						}
						found = true;
						break;
					}
				}
				if (!found){
					this.itemName.add(item.goods().getName());
					this.itemCount.add(1);

					System.out.println(": stalo 2:");
						for (int k = 0; k < this.itemName.size(); k++){
							System.out.println(this.itemName.get(k) + " && " + String.valueOf(this.itemCount.get(k)));
						}
				}
			} else {
				System.out.println("itemname  was EMPTY " + item.goods().getName());
				itemCount.add(1);
				itemName.add(item.goods().getName());

				System.out.println(": stalo EMPTY :");
						for (int k = 0; k < this.itemName.size(); k++){
							System.out.println(this.itemName.get(k) + " && " + String.valueOf(this.itemCount.get(k)));
						}
			}
		}
	}
	
	// public Goods getBuyGoods() {
	// 	return this.goods;
	// }
	
	// public int getBuyCount() {
	// 	return this.count;
	// }
	
	public String printInform() {
		String str = "";
    	for (int i = 0; i < this.itemName.size(); i++){
			if(this.itemName.get(i) != null)
				str += this.itemName.get(i) + " - " + String.valueOf(this.itemCount.get(i)) + "\n";
		}
    	return str;
	}

	@Override
	public String toString(){
		return "Buy { \nBuy ID = "+ this.buyId + 
		"\n" + this.printInform() + "\n}";
	}
}
