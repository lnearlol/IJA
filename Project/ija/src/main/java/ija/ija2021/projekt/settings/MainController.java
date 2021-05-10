/**
 * Project: Storage Simulation
 * @author Roman Stepaniuk  <xstepa64>, Viktoryia Bahdanovich <xbahda01>
 * 
 * Class that represents the GUI and controls all the action with it
 * Date: 07.05.2021
 */

package ija.ija2021.projekt.settings;
import ija.ija2021.projekt.settings.Drawable;

import ija.ija2021.projekt.classes.*;
import ija.ija2021.projekt.Main;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


import javafx.scene.text.Font; 
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text; 
import javafx.stage.Stage; 

public class MainController {
    @FXML
    private Pane map_base;
    @FXML
    private TextField timeScale;
    @FXML
    private Pane line_base;
    @FXML
    private Label time_label;
    @FXML
    private TextField orderName;
    @FXML
    private TextField orderQuantity;
    @FXML
    private TextField jumpInTime;

    private List<Drawable> elements = new ArrayList<>();
    private Timer timer;
    private double timerScale = 1;
    private LocalTime time = LocalTime.of(7,59,30);
    private DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    private long scaling = 1;
    private Main main;
    private Base base;
    private boolean visible = true;
    

    // Faster/Slowe conditions (activates by click)
    @FXML
    private void onTimeScaleChange() {
        try {
            this.timerScale = Double.parseDouble(timeScale.getText());
            if (timerScale < 1)
            {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Invalid timescale (should be 1 and more)");
                errorAlert.showAndWait();
            }
            if (timerScale > 10)
            {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Invalid timescaling (too big)");
                errorAlert.showAndWait();
            }
            timer.cancel();
            this.startClock();         
        } catch (NumberFormatException e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Invalid timescaling (ERROR)");
            errorAlert.showAndWait();
        }
    }

    // function resets program and run it from start
    @FXML
    private void onResetTime() {
        time = LocalTime.of(7,59,30);
        timer.cancel();
        main.startTime(this);
    }

    // function resets program and run till entered time
    @FXML
    private void onJumpInTime() {
        time = LocalTime.of(7,59,30);
        scaling = 100;
        timer.cancel();
        this.visible = false;
        main.startTime(this);
        
    }


    // function for adding order by click (should be entered Goods name and quantity)
    @FXML
    private void onAddingOrder() {
        String name = orderName.getText();
        String quantity = orderQuantity.getText();
        int id = 1; 
        if(base.getOrderList() != null && !base.getOrderList().isEmpty()){
            id = base.getOrderList().get(base.getOrderList().size() - 1).getId() + 1;
        }

        if(base.getAddedOrderList() != null && !base.getAddedOrderList().isEmpty()){
            int newId = base.getAddedOrderList().get(base.getAddedOrderList().size() - 1).getId() + 1;
            if(id < newId)
                id = newId;
        }

        Order createOrder = new Order(id, time_label.getText());
        try{
            createOrder.addToProductInform(new ProductInform(name, Integer.parseInt(quantity)));
            Goods goods = base.getGoods(name);
            if(goods != null) {
                for (int itemCounter = 0; itemCounter < Integer.parseInt(quantity); itemCounter++){
                    createOrder.addItemToBuy(goods);
                }
            } else {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Invalid goods name");
            errorAlert.showAndWait();
            }
        } catch (Exception e){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Invalid goods name or quantity");
            errorAlert.showAndWait();
        }

        createOrder.checkFreeLastBuy();
        base.addToOrderList(createOrder);
    }
       
    // zoom program by scrolling
    @FXML
    private void doZoom(ScrollEvent scroll) {
        scroll.consume();
        double zoom;
        if (scroll.getDeltaY() > 1) zoom = 1.1;
        else zoom = 0.9;
        map_base.setScaleX(zoom * map_base.getScaleX());
        map_base.setScaleY(zoom * map_base.getScaleY());
        map_base.layout();
    }
    
    /**
	 * set the time lable
	 * @param time actual time
	 */
    @FXML
    public void setLabel (LocalTime time) {
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        time_label.setText(time.format(formatter));
    }
    

    /**
	 * function checks which of drawable elements was clicked and change color according instructions
	 * @param element drawable element
     * @param isClicked if object was clicked
	 */
    public void ifSmthClicked(Drawable element, boolean isClicked) {
        if(!visible)
            return;
        for (Drawable draw : this.elements) {
            if (draw.equals(element) && isClicked && draw instanceof Shelf){
                Shelf shelf = (Shelf) draw;
                shelf.changeColor("blue");
            } else if (draw instanceof Shelf) {
                Shelf shelf = (Shelf) draw;
                shelf.changeColor("start");
                shelf.setClicked(false);   
            } else if(draw.equals(element) && isClicked && draw instanceof Base){
                Base base = (Base) draw;
                base.changeColor("blue");
            }  else if (draw instanceof Base){
                Base base = (Base) draw;
                base.changeColor("start");
                base.setClicked(false);
            } else if (draw instanceof Street){
                Street street = (Street) draw;
                if(street.isClicked()){
                    street.changeColor("blue");
                } else {
                    street.changeColor("start");
                }
            } else if (draw.equals(element) && isClicked && draw instanceof Cart){
                Cart cart = (Cart) draw;
                cart.changeColor("red");
                if(cart.getShelfRoute() != null && !cart.getShelfRoute().isEmpty()){
                    cart.getShelfRoute().get(0).changeColor("blue");
                }
            } else if (draw instanceof Cart) {
                Cart cart = (Cart) draw;
                cart.changeColor("start");
                cart.setClicked(false);   
            }
        }
    }


    /**
	 * if user clicks some object function will prepare actual information about it
	 * @param text Object name
     * @param textInfo actual information about object
     * @param element Drawable element
     * @param isClicked if object was clicked or unclicked
	 */
    public void setShelfInform(Text text, Text textInfo, Drawable element, boolean isClicked) {
        if(!visible)
            return;
        this.deleteLeftInfopane();
        this.ifSmthClicked(element, isClicked);

        if(isClicked){
            line_base.getChildren().add(text);
            line_base.getChildren().add(textInfo);
        }
    }

    /**
	 * function deletes all elements from left information panel
	 */
    public void deleteLeftInfopane()
    {
        line_base.getChildren().removeAll(line_base.getChildren());
    }

     /**
	 * adds base to controller
	 * @param base Map base
	 */
    public void addBase(Base base){
        this.base = base;
    }

     /**
	 * adds main class to controller
	 * @param Main main class
	 */
    public void addMain(Main main){
        this.main = main;
    }

    // function for running carts
    @FXML
    public void cartsRun(){
        for(Drawable cartIcon : this.elements){
            if(cartIcon instanceof Cart){
                if(! visible){
                    if(base.compareTime(time_label.getText(), jumpInTime.getText()) >= 0){
                        scaling = 1;
                        this.visible = true;
                        timer.cancel();
                        this.startClock(); 
                    }
                }
                map_base.getChildren().removeAll(cartIcon.getGui());
                Cart cart = (Cart) cartIcon;

                this.base.sendTime(time_label.getText());
                cart.run();
                if( visible)
                    map_base.getChildren().addAll(cartIcon.getGui());
            }
        }
    }

    /**
	 * adds element do scene
	 * @param element drawable element
	 */
    public void addElementToScene(Drawable element)
    {
        map_base.getChildren().addAll(element.getGui());
    }

    // draw elements in middle window
    public void setElements(List<Drawable> elements) {
        map_base.getChildren().removeAll(map_base.getChildren());
        this.elements = elements;
        for (Drawable draw : elements) {
            addElementToScene(draw);
        }
    }


    public void startClock() {
                // creating timer task, timer  
        this.timer = new Timer();  
        TimerTask timerTask = new TimerTask() {  
            @Override  
            public void run() {  
                Platform.runLater(()->{setLabel(time);});
                scaling = (long) timerScale;
                time = time.plusSeconds(scaling);
                
                Platform.runLater(()->{
                cartsRun();});
            };  
        };  
        this.timer.scheduleAtFixedRate(timerTask, 0, (long) 100/scaling);    
    } 
}
