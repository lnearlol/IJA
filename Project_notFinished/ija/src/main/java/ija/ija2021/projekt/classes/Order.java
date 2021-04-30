package ija.ija2021.projekt.classes;
import java.time.LocalDate;
import java.util.ArrayList;

public class Order {
	private int id;
	private ArrayList <GoodsItem> itemList;
	private ArrayList <Buy> buyList;
	private GoodsItem [] LastBuy;
	private int buyId;


	public Order(int id) {
		this.id = id;
		this.buyId = 1;
		this.buyList = new ArrayList<Buy>();
		this.itemList = new ArrayList<GoodsItem>();
	}
	
	public void addItemToBuy(Goods goods) {
		GoodsItem incomeItem = new GoodsItem(goods, LocalDate.now());
		this.itemList.add(incomeItem);
		addSlots(incomeItem);

	}

	public void addSlots(GoodsItem incomeItem){
		if(this.LastBuy == null){
			this.LastBuy = new GoodsItem [5];
			this.LastBuy[0] = incomeItem;
			System.out.println("CAME   NULL - " + incomeItem.goods().getName());
			return;
		} else {
			System.out.println("--------------- ");
			for(int i = 0; i < 5; i++){
				if (LastBuy[i] != (null)){
					System.out.println("Array ["+i+"]");
					System.out.println("Array ["+i+"] = " + LastBuy[i].goods().getName());
				} else {
					System.out.println("Array ["+i+"] = NULL" );
				}
			}
			
			System.out.println("--------------- ");
		}
		for(int i = 0; i < 5; i++){
			if (LastBuy[i] == (null)){
				this.LastBuy[i] = incomeItem;
				System.out.println("CAME   NOT NULL - " + LastBuy[i].goods().getName());
				return;
			}
		}
		this.buyList.add(new Buy(this.LastBuy, this.id, this.buyId++));
		System.out.println("ADDED TO BUYLIST");
		this.LastBuy = new GoodsItem [5];
		this.LastBuy[0] = incomeItem;
	}

	public void checkFreeLastBuy(){
		if(this.LastBuy[0] != null){
			this.buyList.add(new Buy(this.LastBuy, this.id, this.buyId++));
			System.out.println("LAST BUY WAS NOT !!!1 FREE -------------------");
			
			for(int i = 0; i < 5; i++){
				if (LastBuy[i] != (null)){
					System.out.println("Array ["+i+"]");
					System.out.println("Array ["+i+"] = " + LastBuy[i].goods().getName());
				} else {
					System.out.println("Array ["+i+"] = NULL" );
				}
			}
			
			System.out.println("LAST --------------- ");
		

		} else {
			System.out.println("LAST BUY WAS FREE ----------");
		}
	}
	
	public void printInfo(){
		
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

	@Override
	public String toString(){
		return "Order {\nOrder ID = "+ this.id + 
		this.buyList +  "\n}";
	}

}

