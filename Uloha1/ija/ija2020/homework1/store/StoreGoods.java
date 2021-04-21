package ija.ija2020.homework1.store;

import ija.ija2020.homework1.goods.Goods;
import ija.ija2020.homework1.goods.GoodsItem;

import java.time.LocalDate;
import java.util.*;


public class StoreGoods implements Goods {

    private String name;
    
    private ArrayList <GoodsItem> goodsList;

    public StoreGoods(String input) {
        this.name = input;
        this.goodsList = new ArrayList<GoodsItem>();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public boolean addItem(GoodsItem goodsItem) {
        if(goodsItem != null){
            goodsList.add(goodsItem);
            return true;
        } else 
            return false;
    }

    @Override
    public GoodsItem newItem(LocalDate localDate) {
        GoodsItem tmp = new StoreGoodsItem(this, localDate);
        this.addItem(tmp);
        return tmp;
    }

    @Override
    public boolean remove(GoodsItem goodsItem) {
        return this.goodsList.remove(goodsItem);
    }

    @Override
    public boolean empty() {
        return this.goodsList.isEmpty();
    }

    @Override
    public int size() {
        return this.goodsList.size();
    }


    @Override
    public boolean equals(Object goods){
        
        if (this == goods)
            return true;
        else if (goods == null)
            return false;
        else if (goods instanceof StoreGoods){
            StoreGoods tmp = (StoreGoods) goods;
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
