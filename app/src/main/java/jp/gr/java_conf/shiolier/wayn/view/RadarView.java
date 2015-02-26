package jp.gr.java_conf.shiolier.wayn.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.ArrayList;
import java.util.Date;

import jp.gr.java_conf.shiolier.wayn.asynctask.GetGroupLocationsAsyncTask;
import jp.gr.java_conf.shiolier.wayn.entity.User;
import jp.gr.java_conf.shiolier.wayn.util.MySharedPref;
import jp.gr.java_conf.shiolier.wayn.util.Point2D;

public class RadarView extends SurfaceView implements SurfaceHolder.Callback, Runnable, View.OnTouchListener {
	private static final float MY_MARKER_RADIUS = 10;

	private int userId;
	private String userPassword;

	private Context context;
	private LocationManager locationManager;
	private SurfaceHolder holder;
	private Paint paint;
	private Location currentLocation;
	private ArrayList<User> userList;
	private Thread thread;
	private Handler handler;
	private ScaleGestureDetector.SimpleOnScaleGestureListener onScaleGestureListener = new MyOnScaleGestureListener();
	private ScaleGestureDetector scaleGestureDetector;

	private int groupId;
	private int screenWidth;
	private int screenHeight;
	private int meter = 100;
	private float scale = 1.0f;
	private long lastUpdateLocationTime;
	private long lastGetDataTime;
	private boolean isDuringGetData = false;

	public RadarView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public RadarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public RadarView(Context context) {
		super(context);
		init(context);
	}

	private void init(Context context) {
		this.context = context;

		locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);

		MySharedPref mySharedPref = new MySharedPref(context);
		userId = mySharedPref.getUserId(0);
		userPassword = mySharedPref.getUserPassword("");
		groupId = mySharedPref.getRadarGroupId(0);

		scaleGestureDetector = new ScaleGestureDetector(context, onScaleGestureListener);

		holder = getHolder();
		holder.addCallback(this);

		setOnTouchListener(this);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		paint = new Paint();

		thread = new Thread(RadarView.this);
		thread.start();

		handler = new Handler();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		screenWidth = width;
		screenHeight = height;

		myDraw();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		thread = null;
	}

	@Override
	public void run() {
		while (thread != null) {
			myDraw();

			if (lastGetDataTime + 500 < System.currentTimeMillis() && !isDuringGetData) {
				handler.post(new Runnable() {
					@Override
					public void run() {
						getData();
					}
				});
			}
			if (lastUpdateLocationTime + 500 < System.currentTimeMillis()) {
				updateLocation();
			}
			/*
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			*/
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		scaleGestureDetector.onTouchEvent(event);
		return true;
	}

	private void myDraw() {
		Canvas canvas = holder.lockCanvas();
		if (canvas == null)		return;
		canvas.drawColor(Color.BLACK);

		paint.setColor(Color.WHITE);
		// paint.setAntiAlias(true);
		canvas.drawCircle(screenWidth / 2.0f, screenHeight / 2.0f, MY_MARKER_RADIUS, paint);

		if (userList != null) {
			paint.setColor(Color.GREEN);
			for (User user : userList) {
				if (user.getUpdatedLocationAt() + 180000 < new Date().getTime() / 1000) {
					// 最終更新から3分以上経っている場合は表示しない
					continue;
				}

				Point2D point = user.getPoint2D(currentLocation, (int)(meter/scale));
				float x = screenWidth / 2.0f + point.getX() * screenWidth;
				float y = screenHeight / 2.0f + point.getY() * screenHeight;
				// canvas.drawCircle(point.getX() * screenWidth, point.getY() * screenHeight, MY_MARKER_RADIUS / 2.0f, paint);
				canvas.drawCircle(x, y, MY_MARKER_RADIUS / 2.0f, paint);
			}
		}

		holder.unlockCanvasAndPost(canvas);
	}

	private void getData() {
		isDuringGetData = true;
		GetGroupLocationsAsyncTask asyncTask = new GetGroupLocationsAsyncTask(groupId, new GetGroupLocationsAsyncTask.OnPostExecuteListener() {
			@Override
			public void onPostExecute(ArrayList<User> userList) {
				RadarView.this.userList = userList;
				isDuringGetData = false;
				lastGetDataTime = System.currentTimeMillis();
			}
		});

		User user = new User();
		user.setId(userId);
		user.setPassword(userPassword);
		asyncTask.execute(user);
	}

	private void updateLocation() {
		Location gpsLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		Location networkLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		if (gpsLocation == null && networkLocation == null) {
			return;
		}else if (gpsLocation == null) {
			currentLocation = networkLocation;
		} else if (networkLocation == null) {
			currentLocation = gpsLocation;
		} else {
			currentLocation = gpsLocation.getTime() > networkLocation.getTime() ? gpsLocation : networkLocation;
		}

		lastUpdateLocationTime = System.currentTimeMillis();
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	private class MyOnScaleGestureListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
		@Override
		public boolean onScale(ScaleGestureDetector detector) {
			scale *= detector.getScaleFactor();
			return true;
		}
	}
}

