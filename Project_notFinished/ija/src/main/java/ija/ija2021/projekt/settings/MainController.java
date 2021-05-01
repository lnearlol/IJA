
package ija.ija2021.projekt.settings;

import ija.ija2021.projekt.settings.Drawable;

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
    private LocalTime time = LocalTime.of(7,59,30);
    private LocalTime maxTime = LocalTime.of(23,00,00);
    private List<TimeUpdate> updates = new ArrayList<>();
    private DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");


    @FXML
    private void onTimeScaleChange()
    {

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
    	
    }
    
    public void clickOnShelf(){
        
        Text text = new Text("Click on the circle to change its color"); 
      
      //Setting the font of the text 
      text.setFont(Font.font(null, FontWeight.BOLD, 15));     
      
      //Setting the color of the text 
      text.setFill(Color.CRIMSON); 
  
      //setting the position of the text 
      text.setX(150); 
      text.setY(50); 

      line_base.getChildren().addAll(text);

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



    
    
}
