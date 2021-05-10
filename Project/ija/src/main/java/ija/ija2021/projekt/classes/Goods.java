/**
 * Project: Storage Simulation
 * @author Roman Stepaniuk  <xstepa64>, Viktoryia Bahdanovich <xbahda01>
 * 
 * Class represents all functions for working with Goods
 * Date: 07.05.2021
 */

package ija.ija2021.projekt.classes;

import ija.ija2021.projekt.classes.GoodsItem;

import java.time.LocalDate;
import java.util.*;


public class Goods {

    private String name;
    
    private ArrayList <GoodsItem> goodsList;

    /**
	 * Goods class represented type of goods
	 * @param input type of goods
	 */
    public Goods(String input) {
        this.name = input;
        this.goodsList = new ArrayList<GoodsItem>();
    }

    /**
	 * function returns type of goods
	 */
    public String getName() {
        return this.name;
    }

    /**
	 * function adds item to list of goods
	 * @param goodsItem goods item
	 */
    public boolean addItem(GoodsItem goodsItem) {
        if(goodsItem != null){
            goodsList.add(goodsItem);
            return true;
        } else 
            return false;
    }

    /**
	 * function creates goods item with this type of goods
	 * @param goodsItem goods item
	 */
    public GoodsItem newItem(LocalDate localDate) {
        GoodsItem tmp = new GoodsItem(this, localDate);
        this.addItem(tmp);
        return tmp;
    }

    /**
	 * function removes item from list of goods
	 * @param goodsItem goods item
	 */
    public boolean remove(GoodsItem goodsItem) {
        return this.goodsList.remove(goodsItem);
    }

    /**
	 * function checks if the list of goods is empty
	 */
    public boolean empty() {
        return this.goodsList.isEmpty();
    }

    /**
	 * function calculates items of this goods type
	 */
    public int size() {
        return this.goodsList.size();
    }


    /**
	 * overrided function for better evaluating equality of two goods
	 * @param goods goods
	 */
    @Override
    public boolean equals(Object goods){
        
        if (this == goods)
            return true;
        else if (goods == null)
            return false;
        else if (goods instanceof Goods){
            Goods tmp = (Goods) goods;
            if(tmp.getName().toString() == this.getName().toString()){
                return true;
            }
        }
        return false;
    }

    /**
	 * overrided function for better evaluating equality of two goods
	 */
    @Override
    public int hashCode(){
        int hash = 7;
        hash = 31 * hash * Objects.hashCode(this.name);
        return hash;
    }
}
