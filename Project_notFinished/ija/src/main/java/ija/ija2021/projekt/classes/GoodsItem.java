package ija.ija2021.projekt.classes;


import ija.ija2021.projekt.classes.Goods;
import ija.ija2021.projekt.classes.GoodsItem;
import java.time.LocalDate;

public class GoodsItem {
    private Goods storedGoods;
    private LocalDate localDate;
    public GoodsItem(Goods input_goods, LocalDate date) {
        storedGoods = input_goods;
        localDate = date;
    }
    
    public Goods goods() {
        return this.storedGoods;
    }

    
    public boolean sell() {
        return this.storedGoods.remove(this);
    }

}
