package ija.ija2021.projekt.classes;
import java.time.LocalDate;
import java.util.ArrayList;
//import org.joda.time.DateTime;
//import org.joda.time.LocalDateTime;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;

public class Order {
	private int id;
	private ArrayList <GoodsItem> itemList;
	private ArrayList <Buy> buyList;
	private GoodsItem [] LastBuy;
	private int buyId;
	SimpleDateFormat parser;
	Date startTime;
	String printTime;
	private ArrayList <Integer> allBuyId;

	public Order(int id, String time) {
		this.id = id;
		this.buyId = 1;

		this.buyList = new ArrayList<Buy>();
		this.itemList = new ArrayList<GoodsItem>();
		this.allBuyId = new ArrayList<Integer>();
		this.parser = new SimpleDateFormat("HH:mm:ss");
		try {
			this.startTime = parser.parse(time);


			// 				DATE PRINT  HH:mm:ss
			this.printTime = parser.format(this.startTime);
			// System.out.println(this.printTime + " <-- NOW");  // print the string of time without date

			// 		DATE COMPARISON
			// Date date1 = parser.parse("00:30:00");
			// if(date1.compareTo( this.startTime) > 0){
			// 	System.out.println(date1 + " > " + this.startTime);
			// }

		} catch (ParseException e) {
			System.out.println("INVALID DATE WAS ENTERED!");
		}

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
			// System.out.println("CAME   NULL - " + incomeItem.goods().getName());
			return;
		} else {
			// System.out.println("--------------- ");
			// for(int i = 0; i < 5; i++){
			// 	if (LastBuy[i] != (null)){
			// 		System.out.println("Array ["+i+"]");
			// 		System.out.println("Array ["+i+"] = " + LastBuy[i].goods().getName());
			// 	} else {
			// 		System.out.println("Array ["+i+"] = NULL" );
			// 	}
			// }
			
			// System.out.println("--------------- ");
		}
		for(int i = 0; i < 5; i++){
			if (LastBuy[i] == (null)){
				this.LastBuy[i] = incomeItem;
				// System.out.println("CAME   NOT NULL - " + LastBuy[i].goods().getName());
				return;
			}
		}
		this.allBuyId.add(this.buyId);
		this.buyList.add(new Buy(this.LastBuy, this.id, this.buyId++));
		
		// System.out.println("ADDED TO BUYLIST");
		this.LastBuy = new GoodsItem [5];
		this.LastBuy[0] = incomeItem;
	}

	public void checkFreeLastBuy(){
		if(this.LastBuy[0] != null){
			this.allBuyId.add(this.buyId);
			this.buyList.add(new Buy(this.LastBuy, this.id, this.buyId++));
			// System.out.println("LAST BUY WAS NOT !!!1 FREE -------------------");
			
			// for(int i = 0; i < 5; i++){
			// 	if (LastBuy[i] != (null)){
			// 		System.out.println("Array ["+i+"]");
			// 		System.out.println("Array ["+i+"] = " + LastBuy[i].goods().getName());
			// 	} else {
			// 		System.out.println("Array ["+i+"] = NULL" );
			// 	}
			// }
			
			// System.out.println("LAST --------------- ");
		

		} 
		// else {
		// 	System.out.println("LAST BUY WAS FREE ----------");
		// }
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
		", Start time = " + this.printTime + "\n" +
		this.buyList +  "\n}";
	}

}

