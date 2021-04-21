package ija.ija2021.projekt.classes;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Base {
	private Coordinates coordinates;
	private ArrayList <Stop> stopList;
	private ArrayList <Order> orderList;
	private ArrayList <Shelf> shelfList;
	
	public Base(double x, double y) {
		this.coordinates = new Coordinates(x, y);
		this.orderList = new ArrayList <Order>();
		this.shelfList = new ArrayList <Shelf>();
	}
	
	public void addToOrderList(Order order, Semaphore semaphore) {
		this.orderList.add(order);
//		System.out.println("SEMAPHORE WAS RELEASED");
		semaphore.release();
		
	}
	
	
	public void removeFromOrderList(Order order) {
		this.orderList.remove(order);
	}
	
	
	public void addToStopList(Stop stop) {
		this.stopList.add(stop);
	}
	
	public Base getBase() {
		return this;
	}
	
	public Order getOrder() {
//		System.out.println("BASE getOrder");
		return this.orderList.get(0);
	}
	
	public void addShelf(Shelf shelf) {
		this.shelfList.add(shelf);
	}
	
	public ArrayList <Shelf> getShelfList(){
		return this.shelfList;
	}
	
}
