/**
 * Project: Storage Simulation
 * @author Roman Stepaniuk  <xstepa64>, Viktoryia Bahdanovich <xbahda01>
 * 
 * Class represents all functions for working with Cart
 * Date: 07.05.2021
 */

package ija.ija2021.projekt.classes;
import ija.ija2021.projekt.classes.*;
import ija.ija2021.projekt.settings.*;
import java.util.ArrayList;
import java.lang.Math;

import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Shape;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;

public class Cart implements Drawable {
	private int id;
	private String color;
	private boolean isFree;
	private int timeOnStop;
	private Coordinates coordinates;
	private Coordinates previousCoordinates;
	private ArrayList <Stop> stopList;
	private ArrayList <GoodsItem> itemList;
	private ArrayList <GoodsItem> itemListFound;
	private ArrayList <Shelf> shelfList;
	private ArrayList <Shelf> shelfRoute;
	private Order order;
	private Coordinates startCoordinates;

	private boolean isClicked;
	private MainController controller;

	private Buy BuyOrder;
	private ArrayList <Shape> gui;
	
	private Base base;
	public Cart(int id, String color, Base base, Coordinates coordinates, MainController controller){
		this.id = id;
		this.color = color;
		this.isFree = true;
		this.timeOnStop = 0;
		this.coordinates = coordinates; 
		this.startCoordinates = new Coordinates(coordinates.getX(), coordinates.getY());
		this.previousCoordinates = new Coordinates(coordinates.getX(), coordinates.getY());
        this.itemList = new ArrayList<GoodsItem>();
        this.itemListFound = new ArrayList<GoodsItem>();
        this.stopList = new ArrayList<Stop>();
        this.shelfList = new ArrayList<Shelf>(base.getShelfList());
        this.shelfRoute = new ArrayList<Shelf>();

		this.isClicked = false;
        this.controller = controller;
        this.base = base;
	    this.setGui();
		this.clickedOnCart();
	}

	// adds buy to cart
	public void addBuy(Buy buy){
		this.makeBusy();
		this.BuyOrder = buy;
		this.itemList.addAll(buy.getItemList());
	}
	
	// checked if cart staying on base or processing the order
	public boolean isFree() {
		return this.isFree;
	}
	
	// when cart gets to base, make it free
	public void makeFree() {
		this.isFree = true;
	}
	
	// when cart gets an order, make it busy for another orders
	public void makeBusy() {
		this.isFree = false;
	}

	// returns coordinates
	public Coordinates getCoordinates(){
		return this.coordinates;
	}
	
	// cart moving and sets gui with new coordinates
	public void move(double x, double y) {
		this.coordinates.setX(x);
		this.coordinates.setY(y);

		Ellipse cart = (Ellipse) this.getGui().get(0);
		cart.setCenterX(x);
        cart.setCenterY(y);
	}
	
	// add goodsItem to list
	public void addItemToList(GoodsItem item) {
		this.itemList.add(item);
	}
	
	// removes random goodsItem from cart's itemList
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
	
	
	public boolean CompareItemsWithShelf(Shelf shelf) {
		
		for (GoodsItem item : this.itemList){
			for (ProductInform inform : shelf.getProductInform()){
				if(item.goods().getName().equals(inform.getName()) && inform.getAmount() > 0){
					return true;
				}
			}
		}
		return false;
	}
	

	public Shelf ShortestWayShelf() {
		Shelf shelf = null;
		double currentDistance = Double.POSITIVE_INFINITY;;
		
		double newDistance;

		// if distance < currentDistance and there is enough GoodsItems in current shelf => change current distance and shelf
		for (Shelf tmpShelf : base.getShelfList()) {
			if(this.coordinates.equals(this.startCoordinates)){
				newDistance = base.getFirstStreetPosition().getDistanceBetweenCoordinates(tmpShelf.getStop().getCoordinates());
			} else
				newDistance = this.coordinates.getDistanceBetweenCoordinates(tmpShelf.getStop().getCoordinates());
			if(this.CompareItemsWithShelf(tmpShelf) && ! tmpShelf.getStreet().isClicked() &&   // check if line not blocked
				newDistance < currentDistance) {
					currentDistance = newDistance;
				shelf = tmpShelf;
				// ---------------
			}
		}
		if(shelf == null) {
			System.out.println("ERROR SHELF NOT FOUND");
			for (Shelf tmpShelf : base.getShelfList()) {
				newDistance = this.coordinates.getDistanceBetweenCoordinates(tmpShelf.getStop().getCoordinates());
				if(this.CompareItemsWithShelf(tmpShelf) &&   // check if line not blocked
					newDistance < currentDistance) {
						currentDistance = newDistance;
					shelf = tmpShelf;
					// ---------------
				}
			}
			if(shelf == null)
				return null;
			this.shelfRouteAdd(shelf);
			this.previousCoordinates.setX(-100);
			this.previousCoordinates.setY(-100);
		} else {
			//delete Goods from itemListFound
			this.shelfRouteAdd(shelf);
			this.previousCoordinates.setX(-100);
			this.previousCoordinates.setY(-100);
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

	public ArrayList<Shelf> getShelfRoute(){
		return this.shelfRoute;
	}

	public Coordinates FindShortestWay(Coordinates targetCoordinates){
		Coordinates north = new Coordinates(this.coordinates.getX(), this.coordinates.getY()+1);
		Coordinates south = new Coordinates(this.coordinates.getX(), this.coordinates.getY()-1);
		Coordinates east = new Coordinates(this.coordinates.getX()+1, this.coordinates.getY());
		Coordinates west = new Coordinates(this.coordinates.getX()-1, this.coordinates.getY());
		Double shortest = Double.POSITIVE_INFINITY;
		Coordinates returnCoordinates = this.coordinates;
		double distance;

		if(base.isOnStreet(north) != null && ! north.equals(this.previousCoordinates) && base.isOnStreetNotClicked(north) != null){
			distance = targetCoordinates.getDistanceBetweenCoordinates(north);
			if(distance < shortest){
				shortest = distance;
				returnCoordinates = north;
			}
		}

		if(base.isOnStreet(south) != null && ! south.equals(this.previousCoordinates) && base.isOnStreetNotClicked(south) != null){
			distance = targetCoordinates.getDistanceBetweenCoordinates(south);
			if(distance < shortest){
				shortest = distance;
				returnCoordinates = south;
			}
		}
		if(base.isOnStreet(east) != null && ! east.equals(this.previousCoordinates) && base.isOnStreetNotClicked(east) != null){
			distance = targetCoordinates.getDistanceBetweenCoordinates(east);
			if(distance < shortest){
				shortest = distance;
				returnCoordinates = east;
			}
		}
		if(base.isOnStreet(west) != null && ! west.equals(this.previousCoordinates) && base.isOnStreetNotClicked(west) != null){
			distance = targetCoordinates.getDistanceBetweenCoordinates(west);
			if(distance < shortest){
				shortest = distance;
				returnCoordinates = west;
			}
		}
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
			this.itemListFound.clear();
			this.makeFree();
		} else {
			Coordinates cords = this.FindShortestWay(base.getFirstStreetPosition());
			this.move(cords.getX(), cords.getY());
		}
	}

	public void goToStop(){
		if (this.coordinates.equals(this.startCoordinates)){
			this.move(base.getFirstStreetPosition().getX(), base.getFirstStreetPosition().getY());
		} else {
			Coordinates targetCoords = this.shelfRoute.get(0).getStop().getCoordinates();

			if(this.coordinates.equals(targetCoords) && this.timeOnStop == 0){
				this.stayOnStop();
				if(!this.itemList.isEmpty()){
					this.ShortestWayShelf();
				}
			} else {
				Coordinates cords = this.FindShortestWay(targetCoords);
				if(!this.coordinates.equals(cords)){
					this.previousCoordinates.setX(this.coordinates.getX());
					this.previousCoordinates.setY(this.coordinates.getY());
				}
				this.move(cords.getX(), cords.getY());
			}
		}
	}

	public void stayOnStop(){
		if(this.coordinates.equals(this.shelfRoute.get(0).getStop().getCoordinates())){
			this.timeOnStop += 1;
			if (this.shelfRoute.isEmpty())
				System.out.println("ERROR SHELFROUT IS EMPTY");
			
			ArrayList <GoodsItem> tmp = new ArrayList <GoodsItem>();
			for (GoodsItem item : this.itemList){
				for(ProductInform product : this.shelfRoute.get(0).getProductInform()){
					if(item.goods().getName().equals(product.getName())){
						tmp.add(item);
						this.itemListFound.add(item);
						product.minusAmount();
						
						this.shelfRoute.get(0).removeAny(item.goods());
					}
				}
			}
			this.itemList.removeAll(tmp);

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
		}
	}

	public void setClicked(boolean set){
        this.isClicked = set;
    }

	
	public boolean isClicked(){
        return this.isClicked;
    }


    public void clickedOnCart() {
        Drawable UI = (Drawable) this;
        gui.get(0).setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            @FXML
            public void handle(MouseEvent event) {
                if(!isClicked)
                    isClicked = true;
                else
                    isClicked = false;

                String inform = "Cart ID: " + id + 
				"\n\n" + "Buy info:\n";

				if(BuyOrder == null){
					inform += "no buy\n";
				} else {
					inform += BuyOrder.printBuy() + "\nItems found:\n";
				}

				if(!itemListFound.equals(null) && !itemListFound.isEmpty()){
					for(GoodsItem item : itemListFound){
						inform += item.goods().getName() + " : 1\n";
					}
				} else {
					inform += "no items\n";
				}

				if(!shelfRoute.isEmpty()){
					inform += "\nnext Stop: " + shelfRoute.get(0).getStop().getId() + "\n";
				} else {
					inform += "\nnext Stop: Base";
				}

                Text text = new Text("Cart:\n\n"); 
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


    public void changeColor(String color){
        if (color == "red")
            gui.get(0).setFill(Color.MAROON);
        else if (color == "start"){
            gui.get(0).setFill(Color.rgb(255, 165, 0, 0.75));
        }
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
