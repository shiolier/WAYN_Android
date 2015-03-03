package jp.gr.java_conf.shiolier.wayn.util;

import android.location.Location;

public class LocationCalc {
	/** 地球の半径		単位:m(メートル) */
	// Wikipediaより: http://ja.wikipedia.org/wiki/%E5%9C%B0%E7%90%83%E5%8D%8A%E5%BE%84
	public static final int EARTH_RADIUS = 6378137;
	/** 地球の円周		単位:m(メートル) */
	public static final double EARTH_CIRCUMFERENCE = 2 * Math.PI * EARTH_RADIUS;

	/** 緯度1度分の距離	単位:m(メートル) */
	public static final double LATITUDE_ONE_DEGREE = EARTH_CIRCUMFERENCE / 360;
	/** 1メートルあたりの緯度 */
	public static final double LATITUDE_ONE_METER = 360 / EARTH_CIRCUMFERENCE;

	/**
	 * @param latitude
	 * 		緯度
	 * @return
	 * 		緯度で切断したときに断面にできる円の円周
	 */
	public static double sectionCircumference(double latitude) {
		// 緯度で切断した時の切断面の半径
		double sectionRadius = EARTH_RADIUS * Math.cos(Math.toRadians(latitude));
		// 切断面の円周
		double sectionCircumference = 2 * Math.PI * sectionRadius;

		return sectionCircumference;
	}

	/**
	 * @param latitude
	 * 		緯度
	 * @return
	 * 		1メートルあたりの経度
	 */
	public static double longitudeOneMeter(double latitude) {
		double sectionCircumference = sectionCircumference(latitude);
		double oneMeterLongitude = 360 / sectionCircumference;

		return oneMeterLongitude;
	}

	/**
	 * @param latitude
	 * 		緯度
	 * @return
	 * 		経度1度あたりの距離(メートル)
	 */
	public static double longitudeOneDegree(double latitude) {
		double sectionCircumference = sectionCircumference(latitude);
		double oneDegreeLongitudeMeter = sectionCircumference / 360;

		return oneDegreeLongitudeMeter;
	}
}
