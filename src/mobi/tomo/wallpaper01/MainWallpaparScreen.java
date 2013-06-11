package mobi.tomo.wallpaper01;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public abstract class MainWallpaparScreen extends Activity implements SensorEventListener{
	//傾きセンサー
	int SENSOR_NAME = Sensor.TYPE_ACCELEROMETER;
	int SENSOR_DELAY = SensorManager.SENSOR_DELAY_NORMAL;

	
}