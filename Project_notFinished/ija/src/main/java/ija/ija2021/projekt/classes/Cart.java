package ija.ija2021.projekt.classes;

import ija.ija2021.projekt.classes.Shelf;
import ija.ija2021.projekt.classes.*;

import java.util.concurrent.Semaphore;
import java.util.ArrayList;

import ija.ija2021.projekt.settings.Drawable;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Shape;

public class Cart extends Thread implements Drawable {
	private int id;
	private String color;
	private boolean isFree;
	private Coordinates coordinates;
	double speed;
	private ArrayList <Stop> stopList;
	private ArrayList <GoodsItem> itemList;
	private ArrayList <GoodsItem> itemListFind;
	private ArrayList <Shelf> shelfList;
	private ArrayList <Shelf> shelfRoute;
	private Order order;
	private Coordinates startCoordinates;

	private ArrayList <Shape> gui;
	
	private Base base;
	Semaphore semaphore;
	public Cart(int id, String color, Base base, /*Semaphore sem,*/ Coordinates coordinates){
		this.id = id;
		this.color = color;
		this.isFree = true;
		this.coordinates = coordinates; 
		this.startCoordinates = coordinates;
		this.speed = 5;

        this.itemList = new ArrayList<GoodsItem>();
        this.itemListFind = new ArrayList<GoodsItem>();
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
	
	public void move(double x, double y) {
		this.coordinates.setX(this.coordinates.getX() + x);
		this.coordinates.setY(this.coordinates.getY() + y);
	}
	
	public void addItemToList(GoodsItem item) {
		this.itemList.add(item);
		this.itemListFind.add(item);
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
			//delete Goods from itemlistFind
			//this.removeOneGoods(goods, this.itemListFind);
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
	
	public void run()
    {
		
//		System.out.println ("RUN START");
	
		try
	      {
			
			while(true) {
			
				semaphore.acquire();
				
		        this.order = base.getOrder();  
		        if (this.order == null)
			        System.out.print("ORDER IS NULL ");
		        this.base.removeFromOrderList(this.order);
		        
		        System.out.print("Cart "+ this.id + " took an order with ");
		        this.order.printInfo();
		        createShelfRoute();
		        
		        System.out.print("CESTA: VYDEJNI MISTO - ");
		        //vypis
		        
		        for (Shelf tmpShelf : this.shelfRoute) {
		        	System.out.print("Regal" + tmpShelf.getShelfId() + " - ");
		        }
		        //
		        System.out.println("CESTA: VYDEJNI MISTO");
		        
//		        System.out.print("Shelf - ");
		        while(this.shelfRoute.isEmpty() == false) {
		        	int i = this.shelfRoute.get(shelfRoute.size() - 1).getShelfId();
//		        	 System.out.print(i + " ");
		        	 this.shelfRoute.remove(shelfRoute.size() - 1);
		        }
	        
			}
	           
	      }
	      catch(InterruptedException e)
	      {
	    	  
	      }
    }

	public void addToStopList(GoodsItem item, int number) {
		// finding shelf with coordinate sub
	}

	public void setGui()
    {
        Ellipse cart = new Ellipse(this.startCoordinates.getX(), this.startCoordinates.getY(), 8, 8);
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
