package fr.atesab.customcursormod.common.handler;

public interface CommonMatrixStack {
	<T> T getHandle();

	void scale(float x, float y, float z);

	default void scale(float factor) {
		scale(factor, factor, factor);
	}

	default void scaleInv(float factor) {
		scale(1 / factor);
	}

	default void scaleInv(float x, float y, float z) {
		scale(1 / x, 1 / y, 1 / z);
	}

	void setIdentity();

	void translate(float x, float y, float z);

	default void translateOposite(float x, float y, float z) {
		translate(-x, -y, -z);
	}
}
