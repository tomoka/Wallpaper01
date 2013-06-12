package mobi.tomo.wallpaper01;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;

public abstract class MainWallpaparScreen extends Activity implements SensorEventListener{
 
    class SensorAdapter implements SensorEventListener {
 
        private SensorManager manager;

		@Override
		public void onAccuracyChanged(Sensor arg0, int arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSensorChanged(SensorEvent event) {
			// TODO Auto-generated method stub
			
		}
        
		//傾きセンサー
		int SENSOR_NAME = Sensor.TYPE_ACCELEROMETER;
		int SENSOR_DELAY = SensorManager.SENSOR_DELAY_NORMAL;

    }
  
        @Override
        public void onAccuracyChanged(Sensor arg0, int arg1) {
        }
 
        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
				Log.i("event", "Touch Down" + " count=" + event.values[2]);
				Log.i("event", "Touch Down" + " count=" + event.values[1]);
				Log.i("event", "Touch Down" + " count=" + event.values[0]);
            }
        }
 
    
} 