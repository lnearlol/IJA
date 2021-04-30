//package ija.ija2021.projekt.classes;
//
//import ija.ija2021.projekt.classes.*;
//
//import java.util.ArrayList;
//import java.util.concurrent.Semaphore;
//import java.time.LocalDate;
//
//public class Testovaci_trida extends Thread {
//	private Base base;
//	public ArrayList<Order> orderList;
//	 Order order1, order2, order3;
//	private ArrayList<Shelf> shelfList;
//	
//	public Testovaci_trida() {
////		System.out.println("CREATING EVERYTHING");
//		//--------------------------------------------
//		this.base = new Base(0, 0);
//		Goods zidle = new Goods("Zidle");
//		Goods stul = new Goods("Stul");
//		Goods gauc = new Goods("Gauc");
//		
//		this.orderList = new ArrayList<Order>();
//		this.shelfList = new ArrayList<Shelf>();
//		
//	// GOODS ITEM
//		GoodsItem itm10 = new GoodsItem(zidle, LocalDate.of(2021, 1, 6));
//		GoodsItem itm11 = new GoodsItem(zidle, LocalDate.of(2021, 1, 5));
//	    GoodsItem itm12 = new GoodsItem(zidle, LocalDate.of(2021, 1, 6));
//	    GoodsItem itm13 = new GoodsItem(zidle, LocalDate.of(2021, 1, 5));
//	    GoodsItem itm14 = new GoodsItem(zidle, LocalDate.of(2021, 1, 6));
//	    GoodsItem itm15 = new GoodsItem(zidle, LocalDate.of(2021, 1, 5));
//	    GoodsItem itm16 = new GoodsItem(zidle, LocalDate.of(2021, 1, 6));
//	    GoodsItem itm17 = new GoodsItem(zidle, LocalDate.of(2021, 1, 5));
//	    GoodsItem itm18 = new GoodsItem(zidle, LocalDate.of(2021, 1, 6));
//	    GoodsItem itm19 = new GoodsItem(zidle, LocalDate.of(2021, 1, 5));
//	    
//	    GoodsItem itm20 = new GoodsItem(stul, LocalDate.of(2021, 1, 6));
//		GoodsItem itm21 = new GoodsItem(stul, LocalDate.of(2021, 1, 5));
//	    GoodsItem itm22 = new GoodsItem(stul, LocalDate.of(2021, 1, 6));
//	    GoodsItem itm23 = new GoodsItem(stul, LocalDate.of(2021, 1, 5));
//	    GoodsItem itm24 = new GoodsItem(stul, LocalDate.of(2021, 1, 6));
//	    GoodsItem itm25 = new GoodsItem(stul, LocalDate.of(2021, 1, 5));
//	    GoodsItem itm26 = new GoodsItem(stul, LocalDate.of(2021, 1, 6));
//	    GoodsItem itm27 = new GoodsItem(stul, LocalDate.of(2021, 1, 5));
//	    GoodsItem itm28 = new GoodsItem(stul, LocalDate.of(2021, 1, 6));
//	    GoodsItem itm29 = new GoodsItem(stul, LocalDate.of(2021, 1, 5));
//	    
//	    GoodsItem itm30 = new GoodsItem(gauc, LocalDate.of(2021, 1, 6));
//		GoodsItem itm31 = new GoodsItem(gauc, LocalDate.of(2021, 1, 5));
//	    GoodsItem itm32 = new GoodsItem(gauc, LocalDate.of(2021, 1, 6));
//	    GoodsItem itm33 = new GoodsItem(gauc, LocalDate.of(2021, 1, 5));
//	    GoodsItem itm34 = new GoodsItem(gauc, LocalDate.of(2021, 1, 6));
//	    GoodsItem itm35 = new GoodsItem(gauc, LocalDate.of(2021, 1, 5));
//	    GoodsItem itm36 = new GoodsItem(gauc, LocalDate.of(2021, 1, 6));
//	    GoodsItem itm37 = new GoodsItem(gauc, LocalDate.of(2021, 1, 5));
//	    GoodsItem itm38 = new GoodsItem(gauc, LocalDate.of(2021, 1, 6));
//	    GoodsItem itm39 = new GoodsItem(gauc, LocalDate.of(2021, 1, 5));
//	    
//	// SHELF    
//	    Shelf shelf1 = new Shelf(10, 10, 10, 10, 1);
//	    Shelf shelf2 = new Shelf(5, 5, 5, 5, 2);
//	    Shelf shelf3 = new Shelf(15, 15, 15, 15, 3);
//	    
//	    base.addShelf(shelf1);
//	    base.addShelf(shelf2);
//	    base.addShelf(shelf3);
//	    
//	    shelf1.put(itm10);
//	    shelf1.put(itm11);
//	    shelf1.put(itm12);
//	    shelf1.put(itm13);
//	    shelf1.put(itm14);
//	    shelf1.put(itm15);
//	    
//	    shelf1.put(itm20);
//	    shelf1.put(itm21);
//	    shelf1.put(itm22);
//	    shelf1.put(itm23);
//	    shelf1.put(itm24);
//// -----------
//	    shelf2.put(itm16);
//	    shelf2.put(itm17);
//	    shelf2.put(itm18);
//	    shelf2.put(itm19);
//	    
//	    shelf1.put(itm30);
//	    shelf1.put(itm31);
//	    shelf1.put(itm32);
//	    shelf1.put(itm33);
//	    shelf1.put(itm34);
//// ---------------	    
//	    shelf3.put(itm25);
//	    shelf3.put(itm26);
//	    shelf3.put(itm27);
//	    shelf3.put(itm28);
//	    shelf3.put(itm29);
//	    
//	    shelf3.put(itm35);
//	    shelf3.put(itm36);
//	    shelf3.put(itm37);
//	    shelf3.put(itm38);
//	    shelf3.put(itm39);
//	    
//	    // BUYS
//	    
//	    Buy buy1 = new Buy(zidle, 2);
//	    Buy buy2 = new Buy(gauc, 2);
//	    Buy buy3 = new Buy (stul, 1);
//	    
//	    Buy buy4 = new Buy(zidle, 1);
//	    Buy buy5 = new Buy(gauc, 1);
//	    Buy buy6 = new Buy (stul, 1);
//
//	    Buy buy7 = new Buy(zidle, 5);
//	    
//	    // ORDERS
//	    
//	    this.order1 = new Order();
//	    this.order1.addBuy(buy1);
//	    this.order1.addBuy(buy2);
//	    this.order1.addBuy(buy3);
//	    
//	    this.order2 = new Order();
//	    this.order2.addBuy(buy4);
//	    this.order2.addBuy(buy5);
//	    this.order2.addBuy(buy6);
//	    
//	    this.order3 = new Order();
//	    this.order3.addBuy(buy7);
//		
//		//--------------------------------------------
//	    
////	    System.out.println("Going to go");
//		this.go();
//	}
//	
//	public void go() {
////		System.out.println("New semaphore");
//		Semaphore sem = new Semaphore(0);
////		System.out.println("start cycle");
//	    for(int i=0;i<2;i++) {
//	    	if (i > 0) {
//		        new Cart(i, "red", this.base, sem).start();
////		        System.out.println("cycle "+  i);
//	    	}
//	    	
//	    }
//	    
//	    try {
////	    	System.out.println("ADD TO BASE (ORDER, SEM)");
//		    this.base.addToOrderList(this.order1, sem);
//		    sleep(5000);
////	    	System.out.println("ADD TO BASE 2 (ORDER, SEM)");
//
//		    this.base.addToOrderList(this.order2, sem);
//		    sleep(10000);
////	    	System.out.println("ADD TO BASE 3 (ORDER, SEM)");
//
//		    this.base.addToOrderList(this.order3, sem);
//
//		    
//	    } catch(InterruptedException e) {
//      
//	    	 System.out.println ("PROBLEMS");
//	    }
//      
//	}
//}
