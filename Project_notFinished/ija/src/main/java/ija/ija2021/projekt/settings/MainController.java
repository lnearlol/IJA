package ija.ija2021.projekt.settings;

import ija.ija2021.projekt.settings.Drawable;

import ija.ija2021.projekt.classes.*;
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
    private TextField timeChange;

    private List<Drawable> elements = new ArrayList<>();
    private Timer timer;
    private double timerScale = 1;
    private LocalTime time = LocalTime.of(7,59,30);
    private LocalTime maxTime = LocalTime.of(23,00,00);
    private DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");


    @FXML
    private void onTimeScaleChange() {
        try {
            this.timerScale = Double.parseDouble(timeScale.getText());
            if (timerScale <=0)
            {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Invalid timescale (should be more then 0)");
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
    private void onResetTime()
    {
    
    }
       
    @FXML
    private void onJumpInTime()
    {
    	
    }
    
    @FXML
    private void doZoom(ScrollEvent scrolling)
    {
    	
    }
    
    @FXML
    public void setLabel (LocalTime time)
    {
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        time_label.setText(time.format(formatter));
    }
    

    public void ifSmthClicked(Drawable element, boolean isClicked){
        for (Drawable draw : this.elements) {
            if (draw.equals(element) && isClicked){
                map_base.getChildren().removeAll(draw.getGui());
                Shelf shelf = (Shelf) draw;
                shelf.changeColor("blue");
                map_base.getChildren().addAll(draw.getGui());
            } else if (draw instanceof Shelf) {
                map_base.getChildren().removeAll(draw.getGui());
                Shelf shelf = (Shelf) draw;
                shelf.changeColor("start");
                map_base.getChildren().addAll(draw.getGui());    
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

    
    public void cartsRun(){
        for(Drawable cartIcon : this.elements){
            if(cartIcon instanceof Cart){
                Cart cart = (Cart) cartIcon;
                // map_base.getChildren().removeAll(cartIcon.getGui());
                // System.out.println(cart.getCoordinates());
                // cart.move(10, 0);
                // map_base.getChildren().addAll(cartIcon.getGui());
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
        this.timer = new Timer(false);  
        TimerTask timerTask = new TimerTask() {  
            @Override  
            public void run() {  
                Platform.runLater(()->{setLabel(time);});
                long scaling = 10 * (long) timerScale;
                time = time.plusSeconds(scaling);

                cartsRun();

            };  
        };  
        this.timer.scheduleAtFixedRate(timerTask, 500, 1000);    
    } 
}
