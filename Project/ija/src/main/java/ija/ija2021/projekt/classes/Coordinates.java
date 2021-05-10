/**
 * Project: Storage Simulation
 * @author Roman Stepaniuk  <xstepa64>, Viktoryia Bahdanovich <xbahda01>
 * 
 * Class represents all functions for working with Coordinates
 * Date: 07.05.2021
 */

package ija.ija2021.projekt.classes;
import java.lang.Math;
import java.util.Objects;

public class Coordinates {
	private double x;
	private double y;
	
	/**
	 * Coordinates class represented coordinates on the map
	 * @param x x coordinate
	 * @param y y coordinate
	 */
	public Coordinates(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	/**
	 * function returns x coordinate
	 */
	public double getX(){
		return this.x;
	}

	/**
	 * function returns y coordinate
	 */
	public double getY(){
		return this.y;
	}
	
	/**
	 * function sets x coordinate
	 * @param x settled coordinate
	 */
	public void setX(double x) {
		this.x = x;
	}
	
	/**
	 * function sets y coordinate
	 * @param y settled coordinate
	 */
	public void setY(double y) {
		this.y = y;
	}
	
	/**
	 * function calculates distance between two coordinates
	 * @param cord target coordinate
	 */
	public double getDistanceBetweenCoordinates(Coordinates cord){
		return Math.sqrt(Math.pow(this.x - cord.getX(), 2) +  Math.pow(this.y - cord.getY(), 2));
	}
	
	/**
	 * overrided function for better evaluating equality of two coordinates
	 * @param coordinates coordinates
	 */
    @Override
    public boolean equals(Object coordinates){
        if (this == coordinates)
            return true;
        else if (coordinates == null)
            return false;
        else if (coordinates instanceof Coordinates){
            Coordinates tmp = (Coordinates) coordinates;
            if(Math.round(tmp.getX()) == Math.round(this.x) && Math.round(tmp.getY()) == Math.round(this.y)){
                return true;
            }
        }
        return false;
    }

	/**
	 * overrided function for better evaluating equality of two coordinates
	 */
    @Override
    public int hashCode(){
    	return Objects.hash(x, y);
    }
	
	/**
	 * function overrides toString to print information about coordinates
	 */
	 @Override
	 public String toString() {
	     return " (" + this.x +
	             ", " + this.y + ")";
	}
}
