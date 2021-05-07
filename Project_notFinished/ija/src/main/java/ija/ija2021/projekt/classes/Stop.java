/**
 * Project: Storage Simulation
 * @author Roman Stepaniuk  <xstepa64>, Viktoryia Bahdanovich <xbahda01>
 * 
 * Class represents all functions for working with Stop
 * Date: 07.05.2021
 */

package ija.ija2021.projekt.classes;

import java.util.ArrayList;

import ija.ija2021.projekt.settings.Drawable;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Shape;

public class Stop implements Drawable {
	private int id;
	private Coordinates coordinates;
    private ArrayList<Shape> gui;
	Shelf shelf;
	

    
	public Stop (int id, Coordinates coordinates, Shelf shelf) {
		this.id = id;
    	this.coordinates = coordinates;
		this.shelf = shelf;
    	setGui();
	}
	
	public Coordinates getCoordinates() {
		return this.coordinates;
	}
	
	public Stop getStop() {
		return this;
	}
	
	public Shelf getSHelf(){
		return this.shelf;
	}
	
	public int getId() {
    	return this.id;
    }
	
//    public void setId(String id) {
//        this.id = id;
//    }
	

	
    public void setGui()
    {
		Ellipse stop = new Ellipse(coordinates.getX(), coordinates.getY(), 4.2f, 4.2f);
		stop.setFill(Color.BLACK);
        gui = new ArrayList<>();
        gui.add(stop);
    }
	
//    @Override
//    public String toString() {
//        return "Stop{" +
//                "id='" + id + '\'' +
//                ", coordinates=" + coordinates +
//                '}';
//    }
	
    

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
