package fr.atesab.customcursormod.common.utils;

public class MathHelper {
	private MathHelper() {
	}

	public static double clamp(double v, double min, double max) {
		return Math.min(max, Math.max(min, v));
	}
	public static int clamp(int v, int min, int max) {
		return Math.min(max, Math.max(min, v));
	}
	public static float clamp(float v, float min, float max) {
		return Math.min(max, Math.max(min, v));
	}
}
