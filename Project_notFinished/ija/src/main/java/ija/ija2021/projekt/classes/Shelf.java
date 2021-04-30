package ija.ija2021.projekt.classes;

import ija.ija2021.projekt.classes.Goods;
import ija.ija2021.projekt.classes.GoodsItem;
import javafx.scene.shape.Shape;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Shelf {

	private int id;
    private ArrayList <GoodsItem> goodsShelf;
    Stop stop;
    Coordinates coordinates;
    @JsonIgnore
    private ArrayList<Shape> gui;
    
    public Shelf(double x, double y, double stopX, double stopY, int id){
    	this.id = id;
        this.goodsShelf = new ArrayList <GoodsItem>();
        this.stop = new Stop(id, new Coordinates(stopX, stopY));

        this.coordinates = new Coordinates(x, y);
    }

    public Stop getStop() {
    	return this.stop;
    }
    
    public int getShelfId() {
    	return this.id;
    }
    
    public Shelf getShelf() {
    	return this;
    }
    
    public void put(GoodsItem goodsItem) {
        goodsShelf.add(goodsItem);
    }

   
    public int countGoods(Goods goods) {
        int counter = 0;
        for (GoodsItem elem : this.goodsShelf){
            if(elem.goods().equals(goods)){
                counter++;
            }
        }
        return counter;
    }

    
    public GoodsItem removeAny(Goods goods) {
        GoodsItem tmp = null;
        if(this.goodsShelf.isEmpty())
            return null;

        for (GoodsItem elem : this.goodsShelf){
            if(elem.goods().equals(goods)){
                tmp = elem;
                goodsShelf.remove(elem);
                break;
            }
        }
        return tmp;
    }

    
    public int size(Goods goods) {
        int counter = 0;
        for (GoodsItem elem : this.goodsShelf){
            if(elem.goods().equals(goods)){
                counter++;
            }
        }
        return counter;
    }
    
    public boolean isEmpty() {
    	return goodsShelf.isEmpty(); 
    }
    
    public String goodsInfo() {
    	String str = "";
    	ArrayList <GoodsItem> dest = new ArrayList <GoodsItem>(this.goodsShelf);
    	while (!dest.isEmpty()) {
    		int counter = 0;
    		Goods currentGoods = dest.get(0).goods();
    		for (GoodsItem elem : this.goodsShelf) {
    			if (elem.goods().equals(currentGoods)) {
    				counter++;
    				dest.remove(elem);
    			}
    		}
    		str += currentGoods.getName() + " : " + String.valueOf(counter) + "; ";
    		counter = 0;
    	}
    	
    	return str;
    }

	@Override
	public String toString() {
	    return "Shelf{" +
	            "id='" + id + '\'' +
	            ", coordinates=" + coordinates +
                ", stop coordinates=" + this.stop.getCoordinates() + 
	            ", goods: " + this.goodsInfo() + 
	            '}';
	}
}