package ija.ija2021.projekt.classes;

public class Coordinates {
	private double x;
	private double y;
	
	Coordinates(double x, double y)
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
		this.x = this.x + x;
	}
	
	public void setY(double y) {
		this.y = this.y + y;
	}
	
	public double distanse(double x, double y) {
		return Math.abs(this.x - x) + Math.abs(this.y - y);
	}
	
	
	
}
