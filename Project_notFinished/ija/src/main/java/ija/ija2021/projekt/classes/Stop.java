package ija.ija2021.projekt.classes;



public class Stop extends Thread {
	private Coordinates coordinates;
	private volatile int id;
	
	public Stop (int id, double x, double y) {
		this.id = id;
    	coordinates = new Coordinates(x, y);
	}
	
	public Stop getStop() {
		return this;
	}
	
	
	public int getStopId() {
    	return this.id;
    }
	
	
	public void SleepForSecond() {
		try {
			System.out.println("Vozik " + 1 + " vybira zbozi na " + id + " zastavce");
			sleep(2000);
			System.out.println("Vozik " + 1 + " UZ VYBRAL zbozi na " + id + " zastavce");
		} catch (InterruptedException e) {
			System.out.println("ERROR");
		}
		
		
	}
}
