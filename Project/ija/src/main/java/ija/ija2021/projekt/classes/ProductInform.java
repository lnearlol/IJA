/**
 * Project: Storage Simulation
 * @author Roman Stepaniuk  <xstepa64>, Viktoryia Bahdanovich <xbahda01>
 * 
 * Class represents all functions for working with ProductInform
 * Date: 07.05.2021
 */

package ija.ija2021.projekt.classes;
import java.util.ArrayList;
public class ProductInform {
    private int goodsAmount;
    private String goodsName;

    /**
	 * ProductInform class represented information about orders and shelves goods
	 * @param name id of order
     * @param amount time when order should be executed
	 */
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
        if(this.goodsAmount > 0){
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
