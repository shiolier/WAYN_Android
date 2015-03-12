package jp.gr.java_conf.shiolier.wayn.util;

import android.location.Location;

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

	public static Point2D locationToPoint2D(Location centerLocation, Location userLocation, int meter) {
		float x = (float)(((centerLocation.getLongitude() - userLocation.getLongitude()) * LocationCalc.longitudeOneDegree(centerLocation.getLongitude()) / meter));
		float y = (float)(((centerLocation.getLatitude() - userLocation.getLatitude()) * LocationCalc.LATITUDE_ONE_DEGREE) / meter);

		return new Point2D(x, y);
	}
}
