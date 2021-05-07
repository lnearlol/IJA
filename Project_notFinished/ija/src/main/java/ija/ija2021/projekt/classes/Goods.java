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

    public Goods(String input) {
        this.name = input;
        this.goodsList = new ArrayList<GoodsItem>();
    }

   
    public String getName() {
        return this.name;
    }

    
    public boolean addItem(GoodsItem goodsItem) {
        if(goodsItem != null){
            goodsList.add(goodsItem);
            return true;
        } else 
            return false;
    }

    
    public GoodsItem newItem(LocalDate localDate) {
        GoodsItem tmp = new GoodsItem(this, localDate);
        this.addItem(tmp);
        return tmp;
    }

    
    public boolean remove(GoodsItem goodsItem) {
        return this.goodsList.remove(goodsItem);
    }

    
    public boolean empty() {
        return this.goodsList.isEmpty();
    }

    
    public int size() {
        return this.goodsList.size();
    }


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

    @Override
    public int hashCode(){
        int hash = 7;
        hash = 31 * hash * Objects.hashCode(this.name);
        return hash;
    }
}
