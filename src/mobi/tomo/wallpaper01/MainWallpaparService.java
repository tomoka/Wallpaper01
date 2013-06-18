/*重力センサー活用20130618*/
package mobi.tomo.wallpaper01;

import java.util.Calendar;
import java.util.Random;

import mobi.tomo.wallpaper01.model.Sakura;

import android.app.Activity;
import android.content.Context;
import android.hardware.SensorManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.service.wallpaper.WallpaperService;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.WindowManager;


public class MainWallpaparService extends WallpaperService{

	private final Handler mHandler = new Handler();
	
	String nyash_count = null;

	int count = 200;	
	int touchFlag = 0;	
	float mTouchX = -100;
	float mTouchY = -100;
	
	Random rnd = new Random();

	int red = rnd.nextInt(255);
	int green = rnd.nextInt(255);
	int blue = rnd.nextInt(255);
	int redAdd = 1;
	int greenAdd = 1;
	int blueAdd = 1;
	//0~255 100がマックスではありません。
	int alpha = 255;
	
	//クラス配列の表現
	//クラスの生成は１カ所にまとめた方が良い
	Sakura[] sakura = new Sakura[15];
	StarObj StarObj = new StarObj();	

	private SensorManager mSensorManager;
	// センサーを指定する
	int SENSOR_NAME = Sensor.TYPE_ACCELEROMETER;
	// センサーの値を取得するタイミングを指定する
	int SENSOR_DELAY = SensorManager.SENSOR_DELAY_NORMAL;
	//センサー格納
	float sensorX = (float) 1.00;
	float sensorY = (float) 1.00;
	float sensorZ = (float) 1.00;
	
	/** 加速度センサーオブジェクト */
	private Sensor mAccelerometerSensor;

	@Override
	public Engine onCreateEngine() {
		Log.d("step", "onCreateEngine");		
		return new LiveWallpaperEngine();
	}

	class LiveWallpaperEngine extends Engine implements SensorEventListener {
		
		Bitmap snow;
		Bitmap itemTouch;
		float x;
		Canvas canvas = null;
		
		LiveWallpaperEngine() {
			Log.d("step", "LiveWallpaperEngine");

			snow = BitmapFactory.decodeResource(getResources() , R.drawable.snow);
			itemTouch  = BitmapFactory.decodeResource(getResources(), R.drawable.star);

			for(int ii = 0;ii<sakura.length;ii++){
				sakura[ii] = new Sakura();
			}
			
		}
		
		/*
		 * @Overrideしない。
		 * デフォルトでBundle savedInstanceStateが引数に入ってくる
		 * 
		 */

		public void onCreate(SurfaceHolder surfaceHolder) {
			super.onCreate(surfaceHolder);

			Log.d("step", "onCreate(SurfaceHolder surfaceHolder)");
			// SensorManagerインスタンスを取得
			mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
			// マネージャから加速度センサーオブジェクトを取得
			mAccelerometerSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

			//タッチイベント取得
			setTouchEventsEnabled(true);
		}

		private final Runnable animationRunnable = new Runnable() {
            public void run() {
				drawFrame(mTouchX, mTouchY);
            }
        };

		@Override
		public void onVisibilityChanged(boolean visible) {
			if (visible) {
				drawFrame(mTouchX, mTouchY);
			} else {
				mHandler.removeCallbacks(animationRunnable);
			}
		}

		
		@Override
		public void onOffsetsChanged(float xOffset, float yOffset, float xStep,
				float yStep, int xPixels, int yPixels) {

				float xPixelsF = (float)xPixels;
	        	x = (float) (xPixelsF*2.33);
				drawFrame(mTouchX, mTouchY);
		}
		
        @Override
		//タッチイベント呼び出し
        public void onTouchEvent(MotionEvent event) {
			super.onTouchEvent(event);
			Log.d("tag", "onTouchEvent:"+ event.getAction());
			//タッチイベント呼び出し
			//タッチアクションの情報を取得
			int action = event.getAction();
			//タッチ数を取得
			int count = event.getPointerCount();
			int id = (action & MotionEvent.ACTION_POINTER_ID_MASK) >> MotionEvent.ACTION_POINTER_ID_SHIFT;
			
			//イベントごとにログを出力
			switch(action & MotionEvent.ACTION_MASK) {
				case MotionEvent.ACTION_DOWN:
					Log.i("tag", "Touch Down" + " count=" + count + ", id=" + id);
					
					mTouchX = event.getX();
					mTouchY = event.getY();

					StarObj.start();
					super.onTouchEvent(event);

					break;
				case MotionEvent.ACTION_POINTER_DOWN:
					Log.i("tag", "Touch PTR Down" + " count=" + count + ", id=" + id);
					break;
				case MotionEvent.ACTION_UP:
					Log.i("tag", "Touch Up" + " count=" + count + ", id=" + id);
					break;
				case MotionEvent.ACTION_POINTER_UP:
					Log.i("tag", "Touch PTR Up" + " count=" + count + ", id=" + id);
					break;
				case MotionEvent.ACTION_MOVE:
					Log.i("tag", "Touch Move" + " count=" + count + ", id=" + id);
					break;
				}
			
			for(int i=0; i < count; i++) {
				Log.i("tag", " X=" + event.getX(i) + ", Y=" + event.getY(i) + ", id=" + event.getPointerId(i) );
			}
			return;

		}

		public void onSurfaceCreated(SurfaceHolder holder) {
			super.onSurfaceCreated(holder);
			Log.d("step", "onSurfaceCreated(SurfaceHolder holder)");
			Log.i("mSensorManager", " mSensorManager=" + mSensorManager );
			
			mSensorManager.registerListener((SensorEventListener) this, mAccelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
		}
		
		//public void onSurfaceChenge(SurfaceHolder holder){ 画面がきり変わるライフサイクルがある }
		
		public void onSurfaceDestroyed(SurfaceHolder holder){
			super.onSurfaceDestroyed(holder);
			Log.d("step", "onSurfaceDestroyed(SurfaceHolder holder)");
			mSensorManager.unregisterListener((SensorListener) this);
		}


		void drawFrame(float mTouchX,float mTouchY) {

            final SurfaceHolder holder = getSurfaceHolder();
            
			// ウィンドウマネージャのインスタンス取得
			WindowManager wm = (WindowManager)getSystemService(WINDOW_SERVICE);
			// ディスプレイのインスタンス生成
			Display disp = wm.getDefaultDisplay();
			
			float newWidth = disp.getWidth();
			float newHeight = disp.getHeight();

    		Log.i("WindowSize", "newWidth=======" + newWidth );
    		Log.i("WindowSize", "newHeight=======" + newHeight );

    		canvas = holder.lockCanvas();
            try {
                if (canvas != null) {
                	red = red + redAdd;
                	green = green + greenAdd;
                	blue = blue + blueAdd;
                	if(red > 255){
                		redAdd = -1 * (rnd.nextInt(5) + 1);
                		red = 255;
               	}else if(red < 0){
            		redAdd = 1 * (rnd.nextInt(5) + 1);
                		red = 0;
                	}
                	if(green > 255){
                		greenAdd = -1 * (rnd.nextInt(5) + 1);
                		green = 255;
               	}else if(green < 0){
            		greenAdd = 1 * (rnd.nextInt(5) + 1);
                		green = 0;
                	}
                	if(blue > 255){
                		blueAdd = -1 * (rnd.nextInt(5) + 1);
                		blue = 255;
                	}else if(blue < 0){
                		blueAdd = 1 * (rnd.nextInt(5) + 1);
                		blue = 0;
                	}
            		Log.i("color", "red=======" + red );
            		Log.i("color", "green=======" + green );
            		Log.i("color", "blue=======" + blue );

                	canvas.drawColor(Color.argb(alpha, red, green, blue));
    				animate(canvas, mTouchX, mTouchY,newWidth,newHeight);
                }
            } finally {
                if (canvas != null) holder.unlockCanvasAndPost(canvas);
            }


		}
		private synchronized void animate(Canvas canvas,float mTouchX,float mTouchY,float newWidth,float newHeight) {
			drawClock(canvas, mTouchX, mTouchY,newWidth,newHeight);
			mHandler.removeCallbacks(animationRunnable);
			mHandler.postDelayed(animationRunnable,25);
		};

		private void drawClock(Canvas canvas,float mTouchX,float mTouchY,float newWidth,float newHeight) {

			for(int ii = 0;ii<sakura.length;ii++){
				sakura[ii].run(canvas,snow,newWidth,newHeight,sensorX,sensorY,sensorZ);
			}
			
			if(mTouchX >=0 && mTouchY >=0){
				StarObj.run(canvas,itemTouch,mTouchX,mTouchY);

			}
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
				//Log.d("onSensorChanged", " x: " + String.valueOf(event.values[0]) + "\ty: " + String.valueOf(event.values[1]) + "\tz: " + String.valueOf(event.values[2])); // 表示フォーマットの指定の終了
				sensorX = event.values[0];
				sensorY = event.values[1];
				sensorZ = event.values[2];
				Log.d("onSensorChanged", " x: " + sensorX); // 表示フォーマットの指定の終了
			}

	}

	}
}

