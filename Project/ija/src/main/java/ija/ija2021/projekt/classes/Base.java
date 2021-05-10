/**
 * Project: Storage Simulation
 * @author Roman Stepaniuk  <xstepa64>, Viktoryia Bahdanovich <xbahda01>
 * 
 * Class represents all functions for working with Base
 * Date: 07.05.2021
 */


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
	private MainController controller;

	private ArrayList<Shape> gui;
	
	/**
	 * @param x1 x coordinate of start of street
	 * @param y1 y coordinate of start of street
	 * @param x2 x coordinate of end of street
	 * @param y2 y coordinate of end of street
	 * @param id id ulice
	 * @param controller MainController
	 * @param base Base
	 */

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

	// get the controller
	public MainController getController(){
		return this.controller;
	}

	/**
	 * adds goods to goodsList
	 * @param name the name of goods
	 */
	public void addToGoodsList(String name){
		if(!isInGoodsList(name)){
			this.goodsList.add(new Goods(name));
		}
	}

	/**
	 * adds cart to cartList
	 * @param cart incomed cart
	 */
	public void addToCartList(Cart cart){
		this.cartList.add(cart);
	}

	/**
	 * controlls if goods exists
	 * @param name incomed goods
	 */
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

	/**
	 * returns goods from goodsList
	 * @param name goods
	 */
	public Goods getGoods(String name){
		for (Goods goods : this.goodsList){
			if (goods.getName().equals(name))
				return goods;
		}
		System.out.println("ERROR GET GOODS RETURNED NULL!");
		return null;
	}

	/**
	 * adds order to orderList
	 * @param order incomed order
	 */
	public void addToOrderList(Order order) {
		this.orderList.add(order);
	}
	
	/**
	 * adds array list of buys to activeBuyList
	 * @param buyList list of buys
	 */
	public void addToActiveBuyList(ArrayList <Buy> buyList){
		this.activeBuyList.addAll(buyList);
	}
	
	/**
	 * adds array list of buys to activeBuyList
	 * @param buyList list of buys
	 */
	public void addToStopList(Stop stop) {
		this.stopList.add(stop);
	}

	/**
	 * adds shelf to the Shelf List
	 * @param buyList list of buys
	 */
	public void addToShelfList(Shelf shelf) {
		this.shelfList.add(shelf);
	}

	/**
	 * function returns base
	 */
	public Base getBase() {
		return this;
	}

	/**
	 * function removes order from orderList
	 * @param order incomed order
	 */
	public void removeOrder(Order order) {
		if(order != null)
			this.orderList.remove(order);
	}
	
	
	/**
	 * function adds shelf to ShelfList
	 * @param shelf incomed shelf
	 */
	public void addShelf(Shelf shelf) {
		this.shelfList.add(shelf);
	}
	
	/**
	 * if street is blocked, function helps to find another way
	 * @param street street which was blocked
	 */
	public void setAlternativeWay(Street street){
		for(Cart cart : this.cartList){			
			if(cart.getShelfRoute() == null || cart.getShelfRoute().isEmpty())
				continue;

				if (!cart.isFree() && !street.isOnStreet(cart.getCoordinates())){
					cart.getShelfRoute().clear();
				cart.ShortestWayShelf();
			}
		}
	}
	
	
	/**
	 * returns start position of street with id "1"
	 */
    public Coordinates getFirstStreetPosition(){
		for(Street firstStreet: this.streetList){
			if(firstStreet.getId() == 1)
				return firstStreet.getStartCoordinates();
		}
		return null;
	}

	
	/**
	 * checks if coordinates is on street
	 * @param cords to compare with street coordinates
	 */
	public Street isOnStreet(Coordinates cords){
		for(Street street: this.streetList){
			if (street.isOnStreet(cords))
				return street;
		}
		return null;
	}

	/**
	 * checks if incomed coordinates wasn't on clicked (blocked) street
	 * @param cords to compare with street coordinates
	 */
	public Street isOnStreetNotClicked(Coordinates cords){
		for(Street street: this.streetList){
			if (street.isOnStreet(cords) && ! street.isClicked())
				return street;
		}
		return null;
	}

	/**
	 * function to compare strings as time labels
	 * @param str1 first time
	 * @param str2 second time
	 */
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

	/**
	 * function to make orders with incoming time
	 * @param time actual time in program
	 */
	public void sendTime(String time){
		
		if(this.orderList.isEmpty() && this.activeBuyList.isEmpty())
			return;

		ArrayList <Order> orderRemove = new ArrayList <Order>();
		for (Order findOrder:this.orderList){
			if(compareTime(findOrder.getTime(), time) < 0 && !findOrder.ifUsed()){
				this.addedOrderList.add(findOrder);
				orderRemove.add(findOrder);
				this.addToActiveBuyList(findOrder.getBuyList());
				findOrder.setUsedTrue();
			}
		} 

		this.orderList.removeAll(orderRemove);
		

		if(!this.activeBuyList.isEmpty() && !this.activeBuyList.equals(null)){
			for(Cart cart : this.cartList){
				if(cart.isFree() && !this.activeBuyList.isEmpty()){
					cart.addBuy(this.activeBuyList.get(0));
					this.activeBuyList.remove(this.activeBuyList.get(0));
					cart.ShortestWayShelf();
				}
			}
		}
	}

	/**
	 * function adds street to the streetList
	 * @param street added street
	 */
    public void addStreet(Street street) {
        this.streetList.add(street);
    }
    
	/**
	 * function sets stop list of base
	 * @param stopList incomed stopList
	 */
    public void setStops(ArrayList<Stop> stopList) {
        this.stopList = stopList;
    }

	/**
	 * function sets the shelfList
	 * @param shelves added shelves
	 */
    public void setShelves(ArrayList<Shelf> shelves) {
        this.shelfList = shelves;  
    }

	/**
	 * function returns list of streets
	 */
    public ArrayList<Street> getStreetList() {
        return this.streetList;
    }
    
	/**
	 * function returns list of stops
	 */
    public ArrayList<Stop> getStopList() {
        return this.stopList;
    }

	/**
	 * function returns list of shelves
	 */
    public ArrayList<Shelf> getShelfList() {
        return this.shelfList;  
    }

	/**
	 * function returns the list of carts
	 */
	public ArrayList<Cart> getCartList() {
        return this.cartList;  
    }
    
	/**
	 * function sets if base was clicked or not
	 * @param set true/false
	 */
	public void setClicked(boolean set){
        this.isClicked = set;
    }

	/**
	 * function returns the list of orders
	 */
	public ArrayList<Order> getOrderList(){
		return this.orderList;
	}

	/**
	 * function returns order list which should be executed
	 */
	public ArrayList<Order> getAddedOrderList(){
		return this.addedOrderList;
	}

	/**
	 * function changes color of graphic object
	 * @param color needed color
	 */
	public void changeColor(String color){
        if (color == "blue")
            gui.get(0).setFill(Color.MEDIUMSLATEBLUE);
        else if (color == "start"){
            gui.get(0).setFill(Color.DODGERBLUE);
        }
    }

	/**
	 * function provides all actions when user clicks on base
	 * (text info + graphic changes)
	 */
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
    }

	/**
	 * function prints information about streets
	 */
	public String streetsInfo(){
		String str = "";
		int count = 0;
    	for (Street street : this.streetList){
			count++;
			str += "[" + count + "] " + street + "\n";
		}
    	return str;
	}

	/**
	 * function makes string to print information about goods
	 */
	public String goodsTypesInfo(){
		String str = "";
    	for (Goods goods : this.goodsList){
			str += "[ " + goods.getName() + " ] ";
		}
		str += "\n";
    	return str;
	}

	/**
	 * function creates a grapical view of object
	 */
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

	/**
	 * function overrides toString to print information about base
	 */
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
