package ija.ija2021.projekt.classes;
import java.util.ArrayList;
public class ProductInform {
    private int goodsAmount;
    private String goodsName;


    public ProductInform(String name, int amount){
        this.goodsAmount = amount;
        this.goodsName = name;
    }
    
    public ProductInform getProductInform(){
        return this;
    }

    public String getName(){
        return this.goodsName;
    }

    public int getAmount(){
        return this.goodsAmount;
    }

    public boolean minusAmount(){
        if(this.goodsAmount >= 0){
            this.goodsAmount -= 1;
            return true;
        }

        return false;
    }

    @Override
    public String toString(){
        return this.goodsName + " : " + this.goodsAmount + "\n";
    }
}
