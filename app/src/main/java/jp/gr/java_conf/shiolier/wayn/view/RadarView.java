package jp.gr.java_conf.shiolier.wayn.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class RadarView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
	private static final float MY_MARKER_RADIUS = 10;

	private Context context;
	private SurfaceHolder holder;
	private Paint paint;

	private int screenWidth;
	private int screenHeight;

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

		holder = getHolder();
		holder.addCallback(this);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		paint = new Paint();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		screenWidth = width;
		screenHeight = height;

		myDraw();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {

	}

	@Override
	public void run() {
		myDraw();
	}

	private void myDraw() {
		Canvas canvas = holder.lockCanvas();
		if (canvas == null)		return;
		canvas.drawColor(Color.BLACK);

		paint.setColor(Color.WHITE);
		// paint.setAntiAlias(true);
		canvas.drawCircle(screenWidth / 2.0f, screenHeight / 2.0f, MY_MARKER_RADIUS, paint);

		holder.unlockCanvasAndPost(canvas);
	}
}

