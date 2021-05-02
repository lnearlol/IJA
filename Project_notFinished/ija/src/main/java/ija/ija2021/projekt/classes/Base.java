package ija.ija2021.projekt.classes;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;


import ija.ija2021.projekt.settings.Drawable;
import ija.ija2021.projekt.settings.MainController;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;


public class Base implements Drawable {
	private Coordinates coordinates;	
	private ArrayList<Stop> stopList;
	private ArrayList<Order> orderList;
	private ArrayList<Shelf> shelfList;
	private ArrayList<Street> streetList;
	private ArrayList<Cart> cartList;
	private ArrayList<Goods> goodsList;
	private ArrayList<Buy> activeBuyList;
	MainController controller;

	private ArrayList<Shape> gui;
	
	public Base(double x, double y, MainController controller) {
		this.coordinates = new Coordinates(x, y);
		this.orderList = new ArrayList <Order>();
		this.shelfList = new ArrayList <Shelf>();
		this.stopList = new ArrayList <Stop>();
		this.streetList = new ArrayList <Street>();
		this.goodsList = new ArrayList <Goods>();
		this.activeBuyList = new ArrayList<Buy>();
		this.cartList = new ArrayList<Cart>();
		this.controller = controller;

		this.setGui();
	}

	public MainController getController(){
		return this.controller;
	}

	public void addToGoodsList(String name){
		if(!isInGoodsList(name)){
			this.goodsList.add(new Goods(name));
		}
	}

	public void addToCartList(Cart cart){
		this.cartList.add(cart);
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


	public void removeFromOrderList(Order order) {
		this.orderList.remove(order);
	}
	
	
	public void addToStopList(Stop stop) {
		this.stopList.add(stop);
	}

	public void addToShelfList(Shelf shelf) {
		this.shelfList.add(shelf);
	}
//	
	public Base getBase() {
		return this;
	}

	public Order getOrder() {
		return this.orderList.get(0);
	}
	
	public void addShelf(Shelf shelf) {
		this.shelfList.add(shelf);
	}
	
//	public ArrayList <Shelf> getShelfList(){
//		return this.shelfList;
//	}
	
	
	// ---------------------
    public void addStreet(Street street) {
        this.streetList.add(street);
    }
    
    public void setStops(ArrayList<Stop> stopList) {
        this.stopList = stopList;
    }

    public void setShelves(ArrayList<Shelf> shelves) {
        this.shelfList = shelves;  
    }

    public ArrayList<Street> getStreetList() {
        return this.streetList;
    }
    
    public ArrayList<Stop> getStopList() {
        return this.stopList;
    }

    public ArrayList<Shelf> getShelfList() {
        return this.shelfList;  
    }

	public ArrayList<Cart> getCartList() {
        return this.cartList;  
    }
    
	public String streetsInfo(){
		String str = "";
		int count = 0;
    	for (Street street : this.streetList){
			count++;
			str += "[" + count + "] " + street + "\n";
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


	public void setGui()
    {
        Rectangle base = new Rectangle(this.coordinates.getX(), this.coordinates.getY(), 66.5, 132.0);
    	base.setFill(Color.DODGERBLUE);   
        gui = new ArrayList<>();
        gui.add(base);
    }

    @Override
    public ArrayList<Shape> getGui() {
        return gui;
    }

    @Override
    public String toString() {
        return "Base {\n" +
                "Base coordinates =" + this.coordinates + 
                "\nBase streets:\n" + this.streetsInfo() +
				"\nOrder list:\n " + this.orderList + "\n"+
				"Cart list:\n" + this.cartList +
                "\n}";
    }
    
	
}
