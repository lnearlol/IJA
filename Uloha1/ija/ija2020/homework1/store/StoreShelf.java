package ija.ija2020.homework1.store;

import ija.ija2020.homework1.goods.Goods;
import ija.ija2020.homework1.goods.GoodsItem;
import ija.ija2020.homework1.goods.GoodsShelf;
import java.util.*;

public class StoreShelf implements GoodsShelf {

    private ArrayList <GoodsItem> goodsShelf;

    public StoreShelf(){
        this.goodsShelf = new ArrayList <GoodsItem>();
    }

    @Override
    public void put(GoodsItem goodsItem) {
        goodsShelf.add(goodsItem);
    }

    @Override
    public boolean containsGoods(Goods goods) {
        boolean contains = false;
        int i = 0;
        for (GoodsItem elem : this.goodsShelf){
            if(elem.goods().equals(goods)){
                contains = true;
                break;
            }
            i++;
        }
        return contains;
    }

    @Override
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

    @Override
    public int size(Goods goods) {
        int counter = 0;
        for (GoodsItem elem : this.goodsShelf){
            if(elem.goods().equals(goods)){
                counter++;
            }
        }
        return counter;
    }


}
