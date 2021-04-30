package ija.ija2021.projekt.classes;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Base {
	private Coordinates coordinates;	
	private ArrayList <Stop> stopList;
	private ArrayList <Order> orderList;
	private ArrayList <Shelf> shelfList;
	private ArrayList<Line> lineList;
	
	private ArrayList<Goods> goodsList;
	private ArrayList<Buy> activeBuyList;
	
	public Base(double x, double y) {
		this.coordinates = new Coordinates(x, y);
		this.orderList = new ArrayList <Order>();
		this.shelfList = new ArrayList <Shelf>();
		this.stopList = new ArrayList <Stop>();
		this.lineList = new ArrayList <Line>();
		this.goodsList = new ArrayList <Goods>();
		this.activeBuyList = new ArrayList<Buy>();
	}

	public void addToGoodsList(String name){
		if(!isInGoodsList(name)){
			this.goodsList.add(new Goods(name));
		}
	}

	public boolean isInGoodsList(String name){
		if (this.goodsList.isEmpty())
			return false;
		else {
			for (Goods goods : this.goodsList){
				if (goods.getName() == name)
					return true;
			}
			return false;
		}
	}

	public Goods getGoods(String name){
		for (Goods goods : this.goodsList){
			if (goods.getName().equals(name))
				return goods;
		}
		System.out.println("ERROR GET GOODS RETURNED NULL!");
		return null;
	}

	public void addToOrderList(Order order/*, Semaphore semaphore*/) {
		this.orderList.add(order);
//		System.out.println("SEMAPHORE WAS RELEASED");
		// semaphore.release();
	}
	

	public void addToActiveBuyList(Buy buy){
		this.activeBuyList.add(buy);
	}

	@JsonIgnore
	public void removeFromOrderList(Order order) {
		this.orderList.remove(order);
	}
	
	
	public void addToStopList(Stop stop) {
		this.stopList.add(stop);
	}
//	
	public Base getBase() {
		return this;
	}
	@JsonIgnore
	public Order getOrder() {
//		System.out.println("BASE getOrder");
		return this.orderList.get(0);
	}
	
	public void addShelf(Shelf shelf) {
		this.shelfList.add(shelf);
	}
	
//	public ArrayList <Shelf> getShelfList(){
//		return this.shelfList;
//	}
	
	
	// ---------------------
    public void addLine(Line line) {
        this.lineList.add(line);
    }
    
    public void setStops(ArrayList<Stop> stopList) {
        this.stopList = stopList;
    }
    @JsonIgnore
    public void setShelves(ArrayList<Shelf> shelves) {
        this.shelfList = shelves;  
    }
    @JsonIgnore
    public ArrayList<Line> getLineList() {
        return this.lineList;
    }
    
    public ArrayList<Stop> getStopList() {
        return this.stopList;
    }
    @JsonIgnore
    public ArrayList<Shelf> getShelfList() {
        return this.shelfList;  
    }
    
	public String linesInfo(){
		String str = "";
		int count = 0;
    	for (Line line : this.lineList){
			count++;
			str += "[" + count + "] " + line + "\n";
		}
    	return str;
	}

	public String goodsTypesInfo(){
		String str = "";
    	for (Goods goods : this.goodsList){
			str += "[ " + goods.getName() + " ] ";
		}
		str += "\n";
    	return str;
	}

    @Override
    public String toString() {
        return "Base {\n" +
                "Base coordinates =" + this.coordinates + 
                "\nBase lines:\n" + this.linesInfo() +
				"\nOrder list:\n " + this.orderList + "\n"+
                "\n}";
    }
    
	
}
