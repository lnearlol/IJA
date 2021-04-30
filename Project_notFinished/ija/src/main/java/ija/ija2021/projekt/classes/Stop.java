package ija.ija2021.projekt.classes;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.util.StdConverter;
import ija.ija2021.projekt.settings.Drawable;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

//@JsonDeserialize(converter = Stop.StopConstructor.class)
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")

public class Stop implements Drawable {
	private int id;
	private Coordinates coordinates;
    @JsonIgnore
    private ArrayList<Shape> gui;
	
//    
//    /**
//     * Empty constructor for deserializing YML
//     */
//    public Stop() {
//    }
    
	public Stop (int id, Coordinates coordinates) {
		this.id = id;
    	this.coordinates = coordinates;
    	setGui();
	}
	
	public Coordinates getCoordinates() {
		return this.coordinates;
	}
	
	public Stop getStop() {
		return this;
	}
	
	
	public int getId() {
    	return this.id;
    }
	
//    public void setId(String id) {
//        this.id = id;
//    }
	

	
    public void setGui()
    {
        gui = new ArrayList<>();
        gui.add(new Circle(coordinates.getX(), coordinates.getY(), 6.5, Color.LIGHTGREEN ));
    }
	
//    @Override
//    public String toString() {
//        return "Stop{" +
//                "id='" + id + '\'' +
//                ", coordinates=" + coordinates +
//                '}';
//    }
	
    
    @JsonIgnore
    @Override
    public ArrayList<Shape> getGui() {
        return gui;
    }
    
//    /**
//     * Constructor that is called when deserializing stop from YML
//     */
//    static class StopConstructor extends StdConverter<Stop, Stop>
//    {
//        @Override
//        public Stop convert(Stop value) {
//            value.setGui();
//            return value;
//        }
//    }
	
}
