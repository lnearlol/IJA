package ija.ija2021.projekt.classes;

public class Buy {
	private Goods goods;
	private int count;
	
	public Buy(Goods goods, int count) {
		this.goods = goods;
		this.count = count;
	}
	
	public Goods getBuyGoods() {
		return this.goods;
	}
	
	public int getBuyCount() {
		return this.count;
	}
	
	public void printInform() {
		System.out.print(this.goods.getName() + " (" + this.count + ")");
	}
}
