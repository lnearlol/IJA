package ija.ija2021.projekt.classes;

import java.util.ArrayList;

import javafx.scene.shape.Line;
import javafx.scene.shape.Ellipse;
import ija.ija2021.projekt.settings.Drawable;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public class Street implements Drawable {

	private int id;
	private Coordinates begin;
	private Coordinates end;
	private ArrayList<Shelf> shelfList;
	private ArrayList<Shape> gui;
	
	public Street (double x1, double y1, double x2, double y2, int id) {
		this.begin = new Coordinates(x1, y1);
		this.end = new Coordinates(x2, y2);
		this. shelfList = new ArrayList<Shelf>();
		this.id = id;
		this.setGui();
	}

	
	
	public void addToShelfList(Shelf shelf) {
		if (shelf != null)
			this.shelfList.add(shelf);
	}
	
	public ArrayList<Shelf> getShelfList() {
		return this.shelfList;
	}
	
	public boolean isOnStreet(Coordinates pos) {
		if(this.begin.getX() == this.end.getX() && this.begin.getX() == pos.getX()){
			if (this.begin.getY() < this.end.getY()) {
				if (pos.getY() <= this.end.getY() && pos.getY() >= this.begin.getY()) {
					return true;
				}
			} else {
				if (pos.getY() >= this.end.getY() && pos.getY() <= this.begin.getY()) {
					return true;
				}
			}
		} else if (this.begin.getY() == this.end.getY() && this.begin.getY() == pos.getY()) {
			if (this.begin.getX() < this.end.getX()) {
				if(pos.getX() <= this.end.getX() && pos.getX() >= this.begin.getX()) {
					return true;
				}
			} else {
				if (pos.getX() >= this.end.getX() && pos.getX() <= this.begin.getX()) {
					return true;
				}
			}
		}
		return false;
	}

	public String shelvesInfo(){
		String str = "";
    	for (Shelf shelf : this.shelfList){
			str += shelf + "\n";
		}
    	return str;
	}

	public void setGui()
    {
		Line street = new Line(this.begin.getX(), this.begin.getY(), this.end.getX(), this.end.getY());
        street.setStrokeWidth(2);
        street.setStroke(Color.DODGERBLUE);
		Ellipse start = new Ellipse(this.begin.getX(), this.begin.getY(), 3.5f, 3.5f);
        start.setFill(Color.DODGERBLUE);
		Ellipse end = new Ellipse(this.end.getX(), this.end.getY(), 3.5f, 3.5f);
        end.setFill(Color.DODGERBLUE);
        gui = new ArrayList<>();
        gui.add(street);
		gui.add(start);
		gui.add(end);
    }

    @Override
    public ArrayList<Shape> getGui() {
        return gui;
    }

	@Override
	public String toString() {
	    return "Street{" +
	            "id='" + id + '\'' +
	            ", start coordinates=" + this.begin +
                ", end coordinates=" + this.end + "\n" + 
	            " Shelves: \n" + this.shelvesInfo() + 
	            '}';
	}
}
