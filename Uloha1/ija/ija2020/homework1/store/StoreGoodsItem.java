package ija.ija2020.homework1.store;

import ija.ija2020.homework1.goods.Goods;
import ija.ija2020.homework1.goods.GoodsItem;
import java.time.LocalDate;

public class StoreGoodsItem implements GoodsItem {
    private Goods storedGoods;
    private LocalDate localDate;
    public StoreGoodsItem(Goods input_goods, LocalDate date) {
        storedGoods = input_goods;
        localDate = date;
    }
    @Override
    public Goods goods() {
        return this.storedGoods;
    }

    @Override
    public boolean sell() {
        return this.storedGoods.remove(this);
    }
}
