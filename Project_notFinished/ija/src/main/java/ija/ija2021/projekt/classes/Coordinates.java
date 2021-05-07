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
	
	public Coordinates() {
		
	}
	
	public Coordinates(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	public double getX(){
		return this.x;
	}
	public double getY(){
		return this.y;
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	

	public double getDistanceBetweenCoordinates(Coordinates cord){
		return Math.sqrt(Math.pow(this.x - cord.getX(), 2) +  Math.pow(this.y - cord.getY(), 2));
	}
	
	
    @Override
    public boolean equals(Object coordinates){
        Coordinates tmp1 = (Coordinates) coordinates;
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

    @Override
    public int hashCode(){
    	return Objects.hash(x, y);
    }
	
	 @Override
	 public String toString() {
	     return " (" + this.x +
	             ", " + this.y + ")";
	}
}
