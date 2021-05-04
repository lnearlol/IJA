package ija.ija2021.projekt.classes;

import ija.ija2021.projekt.classes.*;

import java.util.concurrent.Semaphore;
import java.util.ArrayList;
import java.lang.Math;

import ija.ija2021.projekt.settings.Drawable;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Shape;

public class Cart extends Thread implements Drawable {
	private int id;
	private String color;
	private boolean isFree;
	private int timeOnStop;
	private Coordinates coordinates;
	private Coordinates previousCoordinates;
	// double speed;
	private ArrayList <Stop> stopList;
	
	private ArrayList <GoodsItem> itemList;
	private ArrayList <GoodsItem> itemListFound;


	private ArrayList <Shelf> shelfList;
	private ArrayList <Shelf> shelfRoute;
	private Order order;
	private Coordinates startCoordinates;

	private Buy BuyOrder;
	private ArrayList <Shape> gui;
	
	private Base base;
	Semaphore semaphore;
	public Cart(int id, String color, Base base, /*Semaphore sem,*/ Coordinates coordinates){
		this.id = id;
		this.color = color;
		this.isFree = true;
		this.timeOnStop = 0;
		this.coordinates = coordinates; 
		this.startCoordinates = new Coordinates(coordinates.getX(), coordinates.getY());
		this.previousCoordinates = new Coordinates(coordinates.getX(), coordinates.getY());
		// this.speed = 5;

        this.itemList = new ArrayList<GoodsItem>();

        this.itemListFound = new ArrayList<GoodsItem>();
        this.stopList = new ArrayList<Stop>();
        this.shelfList = new ArrayList<Shelf>(base.getShelfList());
        this.shelfRoute = new ArrayList<Shelf>();
        
        this.base = base;
	    this.setGui();
    //    this.semaphore = sem;
	}
	
	public boolean isFree() {
		return this.isFree;
	}
	
	public void makeFree() {
		this.isFree = true;
	}
	
	public void makeBusy() {
		this.isFree = false;
	}

	public Coordinates getCoordinates(){
		return this.coordinates;
	}
	
	public void move(double x, double y) {
		this.coordinates.setX(x);
		this.coordinates.setY(y);
		this.setGui();
	}
	
	public void addItemToList(GoodsItem item) {
		this.itemList.add(item);
		this.itemListFound.add(item);
	}
	
	public GoodsItem removeOneItem(Goods goods) {
		GoodsItem tmp = null;
		if(this.itemList.isEmpty())
            return null;

        for (GoodsItem elem : this.itemList){
            if(elem.goods().equals(goods)){
                tmp = elem;
                itemList.remove(elem);
                break;
            }
        }
        return tmp;
	}
	
	public boolean removeOneGoods(Goods goods, ArrayList<GoodsItem> list) {
		if(list.isEmpty())
            return false;

        for (GoodsItem elem : list){
            if(elem.goods().equals(goods)){
                list.remove(elem);
            }
        }
        return true;
	}
	
	public int countGoods(Goods goods, ArrayList<GoodsItem> list) {
		int counterCart = 0;
		for (GoodsItem elem : list) {
			if(elem.goods().equals(goods)) {
				counterCart++;
			}
		}
		return counterCart;
	}
	
	
	public Shelf CompareItemsWithShelf(Shelf shelf, int goodsNumber, Goods goods) {
		
		// WAY TO HOME!11 RETURN HOME  and isFree == false
	
		
		
		if(goodsNumber <= shelf.countGoods(goods)) {
			return shelf;
		}
		
		return null;
	}
	
	public Shelf ShortestWayShelf(Goods goods, int goodsCounter) {
		Shelf shelf = null;
		double currentDistance = Double.POSITIVE_INFINITY;;
		
		
		
		//LAST
		// if distance < currentDistance and there is enough GoodsItems in current shelf => change current distance and shelf
		for (Shelf tmpShelf : base.getShelfList()) {
			if(this.CompareItemsWithShelf(tmpShelf, goodsCounter, goods) != null && 
					this.coordinates.distanse(tmpShelf.coordinates.getX(), tmpShelf.coordinates.getY()) < currentDistance) {
				currentDistance = this.coordinates.distanse(tmpShelf.coordinates.getX(), tmpShelf.coordinates.getY());
				shelf = tmpShelf;
				// ---------------
			}
		}
		
		if(shelf == null) {
			System.out.println("ERROR SHELF NOT FOUND");
			return null;
		} else {
			//delete Goods from itemListFound
			//this.removeOneGoods(goods, this.itemListFound);
			this.shelfRouteAdd(shelf);
		}
		
		return shelf;
	}
	
	
	public void shelfRouteAdd(Shelf shelf) {
		this.shelfRoute.add(shelf);
		this.stopList.add(shelf.getStop());
	}
	
	public void shelfRouteRemove(Shelf shelf) {
		this.shelfRoute.remove(shelf);
	}
	
	public boolean createShelfRoute() {
//		int i = 0;
//		
//		while(true) {
//        	Buy buy = this.order.getFirstBuy();
//        	if (buy == null)
//        		break;
//        	Goods findGoods = buy.getBuyGoods();
//        	int goodsCounter = buy.getBuyCount();
////        	System.out.println ("i = "+ i);
//        	this.ShortestWayShelf(findGoods, goodsCounter);
//        	this.order.removeBuy(buy);
//        	// find shell for this buy
//        	i++;
//        }
//		
//		if(this.shelfRoute != null) {
//			return true;
//		}
//		else 
			return false;
	}
	
// 	public void run()
//     {
		
// //		System.out.println ("RUN START");
	
// 		try
// 	      {
			
// 			while(true) {
			
// 				semaphore.acquire();
				
// 		        this.order = base.getOrder();  
// 		        if (this.order == null)
// 			        System.out.print("ORDER IS NULL ");
// 		        this.base.removeFromOrderList(this.order);
		        
// 		        System.out.print("Cart "+ this.id + " took an order with ");
// 		        this.order.printInfo();
// 		        createShelfRoute();
		        
// 		        System.out.print("CESTA: VYDEJNI MISTO - ");
// 		        //vypis
		        
// 		        for (Shelf tmpShelf : this.shelfRoute) {
// 		        	System.out.print("Regal" + tmpShelf.getShelfId() + " - ");
// 		        }
// 		        //
// 		        System.out.println("CESTA: VYDEJNI MISTO");
		        
// //		        System.out.print("Shelf - ");
// 		        while(this.shelfRoute.isEmpty() == false) {
// 		        	int i = this.shelfRoute.get(shelfRoute.size() - 1).getShelfId();
// //		        	 System.out.print(i + " ");
// 		        	 this.shelfRoute.remove(shelfRoute.size() - 1);
// 		        }
	        
// 			}
	           
// 	      }
// 	      catch(InterruptedException e)
// 	      {
	    	  
// 	      }
//     }

	public double getDistanceBetweenCoordinates(Coordinates cord1, Coordinates cord2){
		return Math.sqrt(Math.pow(cord2.getX() - cord1.getX(), 2) +  Math.pow(cord2.getY() - cord1.getY(), 2));
	}

	public Coordinates FindShortestWay(Coordinates targetCoordinates){
		System.out.println("in FindShortest : target = " + targetCoordinates);
		Coordinates north = new Coordinates(this.coordinates.getX(), this.coordinates.getY()+1);
		Coordinates south = new Coordinates(this.coordinates.getX(), this.coordinates.getY()-1);
		Coordinates east = new Coordinates(this.coordinates.getX()+1, this.coordinates.getY());
		Coordinates west = new Coordinates(this.coordinates.getX()-1, this.coordinates.getY());
		Double shortest = Double.POSITIVE_INFINITY;
		Coordinates returnCoordinates = null;
		double distance;

		if(base.isOnStreet(north) && ! north.equals(this.previousCoordinates)){
			distance = this.getDistanceBetweenCoordinates(north, targetCoordinates);
			if(distance < shortest){
				shortest = distance;
				returnCoordinates = north;
				System.out.println("returnCord : NORTH " + north);
			}
		}
		if(base.isOnStreet(south) && ! south.equals(this.previousCoordinates)){
			distance = this.getDistanceBetweenCoordinates(south, targetCoordinates);
			if(distance < shortest){
				shortest = distance;
				returnCoordinates = south;
				System.out.println("returnCord : SOUTH " + south);
			}
		}
		if(base.isOnStreet(east) && ! east.equals(this.previousCoordinates)){
			distance = this.getDistanceBetweenCoordinates(east, targetCoordinates);
			if(distance < shortest){
				shortest = distance;
				returnCoordinates = east;
				System.out.println("returnCord : EAST " + east);
			}
		}
		if(base.isOnStreet(west) && ! west.equals(this.previousCoordinates)){
			distance = this.getDistanceBetweenCoordinates(west, targetCoordinates);
			if(distance < shortest){
				shortest = distance;
				returnCoordinates = west;
				System.out.println("returnCord : WEST " + west);
			}
		}

		System.out.println("RETURNED : " + returnCoordinates);
		return returnCoordinates;
	}

	public boolean ifShouldReturn(){
		if(this.shelfRoute.isEmpty())
			return true;
		 else 
			return false;
	}

	public void goToBase(){
		if(this.coordinates.equals(base.getFirstStreetPosition())){
			this.move(this.startCoordinates.getX(), this.startCoordinates.getY());
			this.makeFree();
		} else {
			Coordinates cords = this.FindShortestWay(base.getFirstStreetPosition());
			this.move(cords.getX(), cords.getY());
		}
	}

	public void goToStop(){
		System.out.println("goToStop");
		if (this.coordinates.equals(this.startCoordinates)){
			System.out.println("goToStop if");
			this.move(base.getFirstStreetPosition().getX(), base.getFirstStreetPosition().getY());
			System.out.println("if move result: " + this.coordinates);
		} else {
			System.out.println("goToStop else");
			// System.out.println("WAY:\n"+this.shelfRoute+"\n_____________________________________________");
			Coordinates targetCoords = this.shelfRoute.get(0).getStop().getCoordinates();

			if(this.coordinates.equals(targetCoords) && this.timeOnStop == 0){
				System.out.println("else Stopped: stoptimer =  " + this.timeOnStop);
				this.stayOnStop();
			} else {
				Coordinates cords = this.FindShortestWay(targetCoords);
				this.previousCoordinates.setX(this.coordinates.getX());
				this.previousCoordinates.setY(this.coordinates.getY());
				this.move(cords.getX(), cords.getY());
				System.out.println("else move result: " + this.coordinates);
			}
		}
	}

	public void stayOnStop(){
		if(this.coordinates.equals(this.shelfRoute.get(0).getStop().getCoordinates())){
			this.timeOnStop += 1;
			if (this.shelfRoute.isEmpty())
				System.out.println("ERROR SHELFROUT IS EMPTY");
			this.shelfRoute.remove(0);
		}
	}

	public void run(){
		if (this.isFree){
			return;
		} else if (this.ifShouldReturn() && this.timeOnStop == 0)
			this.goToBase();
		else if (this.timeOnStop == 0)
			this.goToStop();
		else {
			if(this.timeOnStop < 100)
				this.timeOnStop += 1;
			else if(this.timeOnStop >= 100){
				this.timeOnStop = 0;
				this.previousCoordinates.setX(-1);
				this.previousCoordinates.setY(-1);
			}
				System.out.println("Got else");
		}
	}

	public void addToStopList(GoodsItem item, int number) {
		// finding shelf with coordinate sub
	}

	public void setGui()
    {
        Ellipse cart = new Ellipse(this.coordinates.getX(), this.coordinates.getY(), 8, 8);
		cart.setFill(Color.rgb(255, 165, 0, 0.75));     
        gui = new ArrayList<>();
        gui.add(cart);
    }

    @Override
    public ArrayList<Shape> getGui() {
        return gui;
    }

	@Override
	public String toString(){
		return "Cart: {\n" + 
		"Cart ID: " + this.id + "\n" +
		"Cart Coordinates = " + this.startCoordinates
		+ "\n}\n";
	}
	
}
