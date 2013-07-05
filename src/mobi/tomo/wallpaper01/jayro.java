package mobi.tomo.wallpaper01;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;

public class jayro extends Activity implements SensorEventListener {
	private SensorManager sensorManager;
	// センサーを指定する
	int SENSOR_NAME = Sensor.TYPE_ACCELEROMETER;
	// センサーの値を取得するタイミングを指定する
	int SENSOR_DELAY = SensorManager.SENSOR_DELAY_NORMAL;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.main);

		// SensorManagerインスタンスを取得
		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
	}

	// Sensorインスタンスの取得とセンサーリスナーの登録の開始
	@Override
	protected void onResume() {
		super.onResume();

		// 加速度センサーを指定してSensorインスタンスを取得
		Sensor sensor = sensorManager.getDefaultSensor(SENSOR_NAME);
		// センサーリスナーに登録
		sensorManager.registerListener(this, sensor, SENSOR_DELAY);
	}

	@Override
	protected void onPause() {
		super.onPause();

		// センサーリスナーを解除
		sensorManager.unregisterListener(this);
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {

		// 精度が変更された時に呼ばれる

	}

	@Override
	public void onSensorChanged(SensorEvent event) {

		// センサータイプが取得したいものか否かを確認
		if (event.sensor.getType() == SENSOR_NAME) {
			// 表示フォーマットの指定の開始
			// X軸Y軸Z軸それぞれの加速度を取得
			Log.d(
				"onSensorChanged", 
				" x: " + String.valueOf(event.values[0])
				+ "\ty: " + String.valueOf(event.values[1]) + 
				"\tz: " + String.valueOf(event.values[2])
			); // 表示フォーマットの指定の終了
		}

	}
}