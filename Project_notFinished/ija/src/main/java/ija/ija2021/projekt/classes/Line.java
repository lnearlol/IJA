package ija.ija2021.projekt.classes;

import java.util.ArrayList;

public class Line {

	private int id;
	private Coordinates begin;
	private Coordinates end;
	private ArrayList<Shelf> shelfList;
	
	public Line (double x1, double y1, double x2, double y2, int id) {
		this.begin = new Coordinates(x1, y1);
		this.end = new Coordinates(x2, y2);
		this. shelfList = new ArrayList<Shelf>();
		this.id = id;
	}

	
	
	public void addToShelfList(Shelf shelf) {
		if (shelf != null)
			this.shelfList.add(shelf);
	}
	
	public ArrayList<Shelf> getShelfList() {
		return this.shelfList;
	}
	
	public boolean isOnLine(Coordinates pos) {
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

	@Override
	public String toString() {
	    return "Line{" +
	            "id='" + id + '\'' +
	            ", start coordinates=" + this.begin +
                ", end coordinates=" + this.end + "\n" + 
	            " Shelves: \n" + this.shelvesInfo() + 
	            '}';
	}
}
