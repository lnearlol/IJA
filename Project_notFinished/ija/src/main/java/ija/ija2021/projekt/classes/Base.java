package ija.ija2021.projekt.classes;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import ija.ija2021.projekt.classes.*;
import ija.ija2021.projekt.settings.Drawable;
import ija.ija2021.projekt.settings.MainController;
import javafx.scene.shape.Rectangle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.scene.text.Font;

public class Base implements Drawable {
	private Coordinates coordinates;	
	private ArrayList<Stop> stopList;
	private ArrayList<Order> orderList;
	private ArrayList<Shelf> shelfList;
	private ArrayList<Street> streetList;
	private ArrayList<Cart> cartList;
	private ArrayList<Goods> goodsList;
	private ArrayList<Order> addedOrderList;
	private ArrayList<Buy> activeBuyList;

	private boolean isClicked;
	MainController controller;

	private ArrayList<Shape> gui;
	
	public Base(double x, double y, MainController controller) {
		this.coordinates = new Coordinates(x, y);

		this.orderList = new ArrayList <Order>();
		this.addedOrderList = new ArrayList <Order>();
		this.shelfList = new ArrayList <Shelf>();
		this.stopList = new ArrayList <Stop>();
		this.streetList = new ArrayList <Street>();
		this.goodsList = new ArrayList <Goods>();
		this.activeBuyList = new ArrayList<Buy>();
		this.cartList = new ArrayList<Cart>();
		this.controller = controller;
		this.isClicked = false;

		this.setGui();
		this.clickedOnBase();
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
	

	public void addToActiveBuyList(ArrayList <Buy> buyList){
		this.activeBuyList.addAll(buyList);
	}


	// public void removeFromOrderList(Order order) {
	// 	this.orderList.remove(order);
	// }
	
	
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

	// public Order getOrder(Order order) {
	// 	if (order != null)
	// 		return this.orderList.get(order);
	// 	else 
	// 		return null;
	// }

	public void removeOrder(Order order) {
		if(order != null)
			this.orderList.remove(order);
	}
	
	public void addShelf(Shelf shelf) {
		this.shelfList.add(shelf);
	}
	
//	public ArrayList <Shelf> getShelfList(){
//		return this.shelfList;
//	}

	public void setAlternativeWay(Street street){
		for(Cart cart : this.cartList){
			System.out.println("?????????????????????????????????????\n?????????????????????????????????????\n?????????????????????????????????????\n?????????????????????????????????????\n?????????????????????????????????????\n?????????????????????????????????????\n?????????????????????????????????????\n?????????????????????????????????????\n?????????????????????????????????????\n");
			
			if(cart.getShelfRoute() == null || cart.getShelfRoute().isEmpty())
				continue;

			// for (Street street : this.streetList){
			if (!cart.isFree() /*&& cart.getShelfRoute().get(0).getStreet().equals(street)*/ && !street.isOnStreet(cart.getCoordinates())){
				System.out.println("##############################################\n##############################################\n##############################################\n##############################################\n##############################################\n##############################################\n##############################################\n##############################################\n##############################################\n");
				cart.getShelfRoute().clear();
				cart.ShortestWayShelf();
			}
		// }
		}
	}
	

    public Coordinates getFirstStreetPosition(){
		for(Street firstStreet: this.streetList){
			if(firstStreet.getId() == 1)
				return firstStreet.getStartCoordinates();
		}
		return null;
	}

	public Street isOnStreet(Coordinates cords){
		for(Street street: this.streetList){
			if (street.isOnStreet(cords))
				return street;
		}
		return null;
	}

	public int compareTime(String str1, String str2){
        int time1, time2;
        for(int i = 0; i < 9; i+=3){
            time1 = Integer.parseInt(str1.substring(i, i+2));
            time2 = Integer.parseInt(str2.substring(i, i+2));
            if (time1 > time2)
                return 1;
            else if (time1 < time2)
                return -1;
        }
        return 0;
    }

	public void sendTime(String time){
		// System.out.println("im here");
		// System.out.println(this.orderList);
		if(this.orderList.isEmpty() && this.activeBuyList.isEmpty())
			return;

		ArrayList <Order> orderRemove = new ArrayList <Order>();
		for (Order findOrder:this.orderList){
			if(compareTime(findOrder.getTime(), time) < 0 && !findOrder.ifUsed()){
				System.out.println("Check orders: "+findOrder.getId());
				this.addedOrderList.add(findOrder);
				orderRemove.add(findOrder);
				this.addToActiveBuyList(findOrder.getBuyList());
				findOrder.setUsedTrue();
			}
		} 

		// System.out.println("ACTIVE BUYLIST: " + this.activeBuyList);
		this.orderList.removeAll(orderRemove);
		

		if(!this.activeBuyList.isEmpty()){
			System.out.println("Check buy list");
			for(Cart cart : this.cartList){
				if(cart.isFree()){
					cart.addBuy(this.activeBuyList.get(0));
					this.activeBuyList.remove(0);
					cart.ShortestWayShelf();
				}
			}
		}

		// System.out.println("carts run");
		// for(Cart cart : this.cartList){
		// 	cart.run();
		// }
	}

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
    
	public void setClicked(boolean set){
        this.isClicked = set;
    }

	public ArrayList<Order> getOrderList(){
		return this.orderList;
	}

	public void changeColor(String color){
        if (color == "blue")
            gui.get(0).setFill(Color.MEDIUMSLATEBLUE);
        else if (color == "start"){
            gui.get(0).setFill(Color.DODGERBLUE);
        }
    }

	public void clickedOnBase() {
        Drawable UI = (Drawable) this;
        gui.get(0).setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            @FXML
            public void handle(MouseEvent event) {
                if(!isClicked)
                    isClicked = true;
                else
                    isClicked = false;

				String	inform = "Zpracovane:\n\n";
				if(addedOrderList.isEmpty())
					inform += "prazdny\n";
				else {
					for (Order order : addedOrderList){
						inform += order.printProducInform();
					}
				}

                inform += "\nNe zpracovane:\n\n";
				if(orderList.isEmpty())
					inform += "prazdny\n";
				else {
					for (Order order : orderList){
						inform += order.printProducInform();
					}
				}

                Text text = new Text("Base "+ ":\n\n"); 
                Text textInfo = new Text(inform);

                //Setting the color of the text 
                textInfo.setFill(Color.CRIMSON); 
                text.setFill(Color.CRIMSON); 
            
                //setting the position of the text 
                text.setX(60); 
                text.setY(60); 
                textInfo.setX(40); 
                textInfo.setY(100); 
                text.setStyle("-fx-font: 22 arial;");
                text.setStyle("-fx-font: 18 arial;");
				
                controller.setShelfInform(text, textInfo, UI, isClicked);
            }
        });
        // gui.get(0).setFill(Color.CRIMSON);
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
