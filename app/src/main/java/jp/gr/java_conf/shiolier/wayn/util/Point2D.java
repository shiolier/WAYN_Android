package jp.gr.java_conf.shiolier.wayn.util;

public class Point2D {
	private float x;
	private float y;

	public Point2D() {
		x = 0;
		y = 0;
	}

	public Point2D(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
}
