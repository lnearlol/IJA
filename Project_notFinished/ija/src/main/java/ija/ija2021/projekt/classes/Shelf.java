package ija.ija2021.projekt.classes;

import ija.ija2021.projekt.classes.Goods;
import ija.ija2021.projekt.classes.GoodsItem;
import java.util.*;

public class Shelf {

	private int id;
    private ArrayList <GoodsItem> goodsShelf;
    Stop stop;
    Coordinates coordinates;

    public Shelf(double x, double y, double stopX, double stopY, int id){
    	this.id = id;
        this.goodsShelf = new ArrayList <GoodsItem>();
        stop = new Stop(id, stopX, stopY);
        coordinates = new Coordinates(x, y);
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


}