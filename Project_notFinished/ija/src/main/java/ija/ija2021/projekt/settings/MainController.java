package ija.ija2021.projekt.settings;

import ija.ija2021.projekt.settings.Drawable;

import ija.ija2021.projekt.classes.*;
import ija.ija2021.projekt.Main;
// import ija.ija2021.projekt.classes.*;

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


//
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

    private List<Drawable> elements = new ArrayList<>();
    private Timer timer;
    private double timerScale = 1;
    private LocalTime time = LocalTime.of(7,59,30);
    private LocalTime maxTime = LocalTime.of(23,00,00);
    private DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    private long scaling = 1;
    private Main main;
    private Base base;

    @FXML
    private void onTimeScaleChange() {
        try {
            this.timerScale = Double.parseDouble(timeScale.getText());
            if (timerScale < 1)
            {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Invalid timescale (should be 1 and more)");
                errorAlert.showAndWait();
            }
            if (timerScale > 40)
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


    
    @FXML
    private void onResetTime() {
        time = LocalTime.of(7,59,30);
        timer.cancel();
        main.startTime(this);
    }

    @FXML
    private void onAddingOrder() {
        String name = orderName.getText();
        String quantity = orderQuantity.getText();
        int id = 1; 
        if(base.getOrderList() != null && !base.getOrderList().isEmpty()){
            id = base.getOrderList().get(base.getOrderList().size() - 1).getId() + 1;
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
            Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Invalid goods name");
            errorAlert.showAndWait();
        }

        createOrder.checkFreeLastBuy();
        base.addToOrderList(createOrder);
    }
       
    
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
    
    @FXML
    public void setLabel (LocalTime time) {
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        time_label.setText(time.format(formatter));
    }
    

    public void ifSmthClicked(Drawable element, boolean isClicked) {
        for (Drawable draw : this.elements) {
            if (draw.equals(element) && isClicked && draw instanceof Shelf){
                // map_base.getChildren().removeAll(draw.getGui());
                Shelf shelf = (Shelf) draw;
                shelf.changeColor("blue");
                // map_base.getChildren().addAll(draw.getGui());
            } else if (draw instanceof Shelf) {
                // map_base.getChildren().removeAll(draw.getGui());
                Shelf shelf = (Shelf) draw;
                shelf.changeColor("start");
                shelf.setClicked(false);
                // map_base.getChildren().addAll(draw.getGui());    
            } else if(draw.equals(element) && isClicked && draw instanceof Base){
                // map_base.getChildren().removeAll(draw.getGui());
                Base base = (Base) draw;
                base.changeColor("blue");
                // map_base.getChildren().addAll(draw.getGui());
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
                if(cart.getShelfRoute() != null){
                    cart.getShelfRoute().get(0).changeColor("blue");
                }
            } else if (draw instanceof Cart) {
                Cart cart = (Cart) draw;
                cart.changeColor("start");
                cart.setClicked(false);   
            }
        }
    }


    public void setShelfInform(Text text, Text textInfo, Drawable element, boolean isClicked) {
        this.deleteLeftInfopane();
        this.ifSmthClicked(element, isClicked);

        if(isClicked){
            line_base.getChildren().add(text);
            line_base.getChildren().add(textInfo);
        }
        // addElementToScene(this.changed);
    }



    public void deleteLeftInfopane()
    {
        
        // map_base.getChildren().remove(this.changed);
        line_base.getChildren().removeAll(line_base.getChildren());
        
    }

    public void addBase(Base base){
        this.base = base;
    }

    public void addMain(Main main){
        this.main = main;
    }

    // double direction = 1;
    @FXML
    public void cartsRun(){
        for(Drawable cartIcon : this.elements){
            if(cartIcon instanceof Cart){
                map_base.getChildren().removeAll(cartIcon.getGui());
                Cart cart = (Cart) cartIcon;


                System.out.println("TIME = " + time_label.getText());
                
                // System.out.println(cart.getCoordinates());
                
                // if(cart.getCoordinates().getX() > 300)
                //     direction = -1;
                // else if (cart.getCoordinates().getX() < 100)
                //     direction = 1;
                
                this.base.sendTime(time_label.getText());

                // if(compareTime(time_label.getText(), "08:00:34") > 0){
                //     ArrayList<Shelf> shelves = base.getShelfList();
                //     ArrayList<Cart> carts = base.getCartList();
                //     if(carts.get(0).ifShouldReturn()){
                //         for(Shelf shelf : shelves){
                //             carts.get(0).shelfRouteAdd(shelf);
                //         }
                //         carts.get(0).makeBusy();
                //         System.out.println("TIME IS UP END");
                //     }
                // }


                // if(compareTime(time_label.getText(), "08:06:34") > 0){
                //     ArrayList<Shelf> shelves = base.getShelfList();
                //     ArrayList<Cart> carts = base.getCartList();
                //     if(carts.get(1).ifShouldReturn()){
                //         for(Shelf shelf : shelves){
                //             carts.get(1).shelfRouteAdd(shelf);
                //         }
                //         carts.get(1).makeBusy();
                //         System.out.println("TIME IS UP END");
                //     }
                    
                // }

                // if(compareTime(time_label.getText(), "08:15:34") > 0){
                //     ArrayList<Shelf> shelves = base.getShelfList();
                //     ArrayList<Cart> carts = base.getCartList();
                //     if(carts.get(2).ifShouldReturn()){
                //         for(Shelf shelf : shelves){
                //             carts.get(2).shelfRouteAdd(shelf);
                //         }
                //         carts.get(2).makeBusy();
                //         System.out.println("TIME IS UP END");
                //     }
                    
                // }

                cart.run();
                map_base.getChildren().addAll(cartIcon.getGui());
            }
        }
    }



    public void addElementToScene(Drawable element)
    {
        map_base.getChildren().addAll(element.getGui());
    }


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
                scaling = 2 * (long) timerScale;
                time = time.plusSeconds(scaling);
                
                Platform.runLater(()->{
                cartsRun();});
            };  
        };  
        this.timer.scheduleAtFixedRate(timerTask, 0, (long) 100/scaling);    
    } 
}
