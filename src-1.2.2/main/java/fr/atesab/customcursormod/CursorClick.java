package fr.atesab.customcursormod;

public class CursorClick {
	private int time;
	private double posX;
	private double posY;
	
	public CursorClick(int time, double posX, double posY) {
		this.time = time;
		this.posX = posX;
		this.posY = posY;
	}
	public void descreaseTime() {
		this.time--;
	}
	public double getPosX() {
		return posX;
	}
	public double getPosY() {
		return posY;
	}
	public int getTime() {
		return time;
	}
}
