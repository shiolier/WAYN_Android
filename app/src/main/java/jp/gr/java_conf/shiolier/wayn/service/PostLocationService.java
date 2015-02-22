package jp.gr.java_conf.shiolier.wayn.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import jp.gr.java_conf.shiolier.wayn.MainActivity;
import jp.gr.java_conf.shiolier.wayn.R;
import jp.gr.java_conf.shiolier.wayn.asynctask.PostLocationAsyncTask;
import jp.gr.java_conf.shiolier.wayn.entity.User;
import jp.gr.java_conf.shiolier.wayn.util.MySharedPref;

public class PostLocationService extends Service {
	private static final int ONGOING_NOTIFICATION = 0x01;

	private int id;
	private String password;
	private LocationManager locationManager;
	private Location currentLocation;
	private NotificationManager notificationManager;
	private LocationListener gpsLocationListener = new MyLocationListener();
	private LocationListener coarseLocationListener = new MyLocationListener();

	public PostLocationService() {
	}

	@Override
	public void onCreate() {
		super.onCreate();

		notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

		locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);

		// GPS
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				1000,	// 最低で何秒毎か (ミリ秒)
				0,		// 最低で何メートル毎か
				gpsLocationListener);
		// Network
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
				1000,
				0,
				coarseLocationListener);

		MySharedPref mySharedPref = new MySharedPref(this);
		id = mySharedPref.getUserId(0);
		password = mySharedPref.getUserPassword("");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// Notificationがタップされた時のIntentを設定(Context, リクエストコード, Intent, Intent発行時のフラグ)
		PendingIntent pendingIntent =
				PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);


		@SuppressWarnings("deprecation")
		// Notification
		Notification notification = new Notification.Builder(this)
		// あらかじめ生成しておいたPendingIntentインスタンスをセット
		.setContentIntent(pendingIntent)
		// 表示アイコン
		.setSmallIcon(R.mipmap.ic_launcher)
		// 起動時に1度だけ(一瞬)表示される
		.setTicker("位置情報送信開始")
		// タイトル
		.setContentTitle("WAYN")
		// 説明的なもの
		.setContentText("位置情報送信中")
		// 表示時刻
		.setWhen(System.currentTimeMillis())
		// 非推奨なのでbuild()を使うのが望ましいがbuild()はAPI16(Android4.1)からなので使用しない
		.getNotification();

		// ユーザーがデバイスデフォルトの通知削除ボタンを押しても削除されないようにする
		notification.flags |= Notification.FLAG_ONGOING_EVENT;

		// Foregroundで実行
		startForeground(ONGOING_NOTIFICATION, notification);

		// 強制終了された後に再起動するようにする
		return START_REDELIVER_INTENT;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		// Notificationを削除
		notificationManager.cancel(ONGOING_NOTIFICATION);

		// 位置情報の取得を停止する
		locationManager.removeUpdates(gpsLocationListener);
		locationManager.removeUpdates(coarseLocationListener);
	}

	@Override
	public IBinder onBind(Intent intent) {
		throw new UnsupportedOperationException("Not yet implemented");
	}

	private void postLocation(Location location) {
		PostLocationAsyncTask asyncTask = new PostLocationAsyncTask(new PostLocationAsyncTask.OnPostExecuteListener() {
			@Override
			public void onPostExecute(User user) {
				if (user.getUpdatedLocationAt() < 0) {
					Log.w("MyLog", "位置情報送信失敗");
				}
			}
		});

		User user = new User();
		user.setId(id);
		user.setPassword(password);
		user.setLatitude(location.getLatitude());
		user.setLongitude(location.getLongitude());
		user.setAltitude(location.getAltitude());
		asyncTask.execute(user);
	}

	private class MyLocationListener implements LocationListener {
		@Override
		public void onLocationChanged(Location location) {
			Log.d("MyLog",
					String.format("onLocationChanged\nprovider:%s\nlatitude:%f\nlongitude:%f\naltitude:%f",
							location.getProvider(),
							location.getLatitude(),
							location.getLongitude(),
							location.getAltitude()));
			if (currentLocation == null) {
				currentLocation = location;
			} else {
				// 更新時間が新しいもの優先
				if (currentLocation.getTime() < location.getTime()) {
					currentLocation = location;
				}
			}
			postLocation(currentLocation);
		}
		@Override
		public void onProviderDisabled(String provider) {
		}
		@Override
		public void onProviderEnabled(String provider) {
		}
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
		}
	}
}
