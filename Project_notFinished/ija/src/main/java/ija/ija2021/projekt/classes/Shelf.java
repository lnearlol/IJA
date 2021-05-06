package ija.ija2021.projekt.classes;

import ija.ija2021.projekt.classes.Goods;
import ija.ija2021.projekt.classes.GoodsItem;
import ija.ija2021.projekt.settings.*;
import java.util.*;

import ija.ija2021.projekt.settings.Drawable;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;


import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.scene.text.Font;

//
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;


public class Shelf implements Drawable  {
    @FXML
    private Pane line_base;
	private int id;
    private ArrayList <GoodsItem> goodsShelf;
    private ArrayList <ProductInform> productInform;
    private boolean isClicked = false;
    Stop stop;
    Coordinates coordinates;
    MainController controller;

    private ArrayList<Shape> gui;
    
    
    public Shelf(double x, double y, double stopX, double stopY, int id, MainController controller){
    	this.id = id;
        this.goodsShelf = new ArrayList <GoodsItem>();
        this.productInform = new ArrayList <ProductInform>();
        this.stop = new Stop(id, new Coordinates(stopX, stopY));

        this.controller = controller;
        this.coordinates = new Coordinates(x, y);
        setGui();
        clickedOnShelf();
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
    
    public void addToProductInform(ProductInform product){
        this.productInform.add(product);
    }

    public ArrayList<ProductInform> getProductInform(){
        return this.productInform;
    }

    // void printProductInform(){
        
    // }

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

        for(ProductInform product : this.productInform){
            if(goods.getName() == product.getName()){
                if (!product.minusAmount()){
                    Rectangle shelf = new Rectangle(this.coordinates.getX(), this.coordinates.getY(), 26.6, 26.6);
                    shelf.setFill(Color.BLACK);     
                    gui = new ArrayList<>();
                    gui.add(shelf);
                }
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



    public void setClicked(boolean set){
        this.isClicked = set;
    }



    public void clickedOnShelf() {
        // ArrayList<Drawable> UI = new ArrayList<>();
        // UI.add(this);
        Drawable UI = (Drawable) this;
        gui.get(0).setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            @FXML
            public void handle(MouseEvent event) {
                System.out.println("CLICKED! = " + productInform);
                if(!isClicked)
                    isClicked = true;
                else
                    isClicked = false;

                String inform = "";
                for (ProductInform element : productInform){
                    inform = inform + element + "\n";
                }

                Text text = new Text("Shelf " + id + ":\n\n"); 
                Text textInfo = new Text(inform);

                //Setting the color of the text 
                textInfo.setFill(Color.CRIMSON); 
                text.setFill(Color.CRIMSON); 
            
                //setting the position of the text 
                text.setX(60); 
                text.setY(60); 
                textInfo.setX(40); 
                textInfo.setY(100); 
                text.setStyle("-fx-font: 22 arial;");
                text.setStyle("-fx-font: 18 arial;");

                controller.setShelfInform(text, textInfo, UI, isClicked);
            }
        });
        // gui.get(0).setFill(Color.CRIMSON);
    }


    public void changeColor(String color){
        if (color == "blue")
            gui.get(0).setFill(Color.BLUE);
        else if (color == "start"){
            gui.get(0).setFill(Color.CRIMSON);
        }
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



    public void setGui()
    {
        Rectangle shelf = new Rectangle(this.coordinates.getX(), this.coordinates.getY(), 26.6, 26.6);
        shelf.setFill(Color.CRIMSON);     
        gui = new ArrayList<>();
        gui.add(shelf);
    }

    @Override
    public ArrayList<Shape> getGui() {
        return gui;
    }

	@Override
	public String toString() {
	    return "Shelf{" +
	            "id='" + id + '\'' +
	            ", coordinates=" + coordinates +
                ", stop coordinates=" + this.stop.getCoordinates() + 
	            "\n goods: \n" + this.goodsInfo() + 
	            '}';
	}
}