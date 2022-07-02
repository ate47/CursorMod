package fr.atesab.customcursormod.common.cursor;

import fr.atesab.customcursormod.common.utils.MathHelper;

public class CursorClick {
	private static final float ANIMATION_TIME = 2f;
	private static final int IMAGE_COUNT = 3;
	private float time;
	private final double posX;
	private final double posY;

	public CursorClick(double posX, double posY) {
		this.time = ANIMATION_TIME;
		this.posX = posX;
		this.posY = posY;
	}

	public void descreaseTime(float delta) {
		this.time -= delta;
	}

	public double getPosX() {
		return posX;
	}

	public double getPosY() {
		return posY;
	}

	public float getTime() {
		return time;
	}

	public int getImage() {
		return IMAGE_COUNT - MathHelper.clamp((int) (time * IMAGE_COUNT / ANIMATION_TIME), 1, IMAGE_COUNT);
	}
}
