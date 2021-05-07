/**
 * Project: Storage Simulation
 * @author Roman Stepaniuk  <xstepa64>, Viktoryia Bahdanovich <xbahda01>
 * 
 * Class represents all functions for working with Order
 * Date: 07.05.2021
 */

package ija.ija2021.projekt.classes;
import java.time.LocalDate;
import java.util.ArrayList;
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
	private Boolean used;
	private ArrayList <Integer> allBuyId;
	private ArrayList <ProductInform> productInform;


	public Order(int id, String time) {
		this.id = id;
		this.buyId = 1;
		this.used = false;
		
		this.productInform = new ArrayList <ProductInform>();
		this.buyList = new ArrayList<Buy>();
		this.itemList = new ArrayList<GoodsItem>();
		this.allBuyId = new ArrayList<Integer>();
		this.parser = new SimpleDateFormat("HH:mm:ss");
		try {
			this.startTime = parser.parse(time);

			// 				DATE PRINT  HH:mm:ss
			this.printTime = parser.format(this.startTime);

		} catch (ParseException e) {
			System.out.println("INVALID DATE WAS ENTERED!");
		}

	}
	
	public String getTime(){
		return printTime;
	}

	public int getId(){
		return this.id;
	}

	public void addItemToBuy(Goods goods) {
		GoodsItem incomeItem = new GoodsItem(goods, LocalDate.now());
		this.itemList.add(incomeItem);
		addSlots(incomeItem);
	}

	public void setUsedTrue(){
		this.used = true;
	}

	public boolean ifUsed(){
		if(this.used)
			return true;
		 else
			return false;
	}

	public void addSlots(GoodsItem incomeItem){
		if(this.LastBuy == null){
			this.LastBuy = new GoodsItem [5];
			this.LastBuy[0] = incomeItem;
			return;
		} 
		for(int i = 0; i < 5; i++){
			if (LastBuy[i] == (null)){
				this.LastBuy[i] = incomeItem;
				return;
			}
		}
		this.allBuyId.add(this.buyId);
		this.buyList.add(new Buy(this.LastBuy, this.id, this.buyId++));

		this.LastBuy = new GoodsItem [5];
		this.LastBuy[0] = incomeItem;
	}

	public void checkFreeLastBuy(){
		if(this.LastBuy[0] != null){
			this.allBuyId.add(this.buyId);
			this.buyList.add(new Buy(this.LastBuy, this.id, this.buyId++));
		} 
	}

	public void addToProductInform(ProductInform product){
        this.productInform.add(product);
    }

    public ArrayList<ProductInform> getProductInform(){
        return this.productInform;
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

	public ArrayList<Buy> getBuyList(){
		return this.buyList;
	}
	
	public void removeBuy(Buy buy) {
		this.buyList.remove(buy);
	}

	public String printProducInform(){
		String info = "";
		for (ProductInform product : this.productInform){
			info += product;
		}

		return "Order {\nOrder ID = "+ this.id + ",\n" + 
		"Start time = " + this.printTime + ",\n" +
		info +  "}\n\n";
	}

	@Override
	public String toString(){
		return "Order {\nOrder ID = "+ this.id + 
		", Start time = " + this.printTime + "\n" +
		this.buyList +  "\n}";
	}

}

