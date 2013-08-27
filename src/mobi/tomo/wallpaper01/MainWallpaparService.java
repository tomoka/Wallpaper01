/*開発用マスターブランチ
 * 20130717 背景描画色クラス分け
 * 20130808 衝突判定 球を二つ出して重ならない
 * 20130827 衝突判定2 球を二つ出して重ならない
 * */

package mobi.tomo.wallpaper01;

import java.util.Calendar;
import java.util.Random;

import mobi.tomo.wallpaper01.StarObj;
import mobi.tomo.wallpaper01.model.SakuraGravity;

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
	
	//クラス配列の表現
	SakuraGravity sakura[] = new SakuraGravity[2];
	StarObj starObj = new StarObj();
	HitTest hitTest = new HitTest();
	BackgroundColorChange backgroundColorChange = new BackgroundColorChange();	

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
		return new LiveWallpaperEngine();
	}

	class LiveWallpaperEngine extends Engine implements SensorEventListener {
		
		Bitmap snow;
		Bitmap itemTouch;
		float x;
		Canvas canvas = null;
		
		LiveWallpaperEngine() {
			snow = BitmapFactory.decodeResource(getResources() , R.drawable.snow);
			itemTouch  = BitmapFactory.decodeResource(getResources(), R.drawable.star);
			 			
			starObj.init();
			for(int ii = 0;ii<sakura.length;ii++){
				//SakuraGravityクラスの複製
				sakura[ii] = new SakuraGravity();
				sakura[ii].init();
			}
			
		}
		
		/*
		 * @Overrideしない。
		 * デフォルトでBundle savedInstanceStateが引数に入ってくる
		 * 
		 */

		public void onCreate(SurfaceHolder surfaceHolder) {
			super.onCreate(surfaceHolder);

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
		public void onOffsetsChanged(float xOffset, float yOffset, float xStep,float yStep, int xPixels, int yPixels) {

				float xPixelsF = (float)xPixels;
	        	x = (float) (xPixelsF*2.33);
				drawFrame(mTouchX, mTouchY);
		}
		
        @Override
		//タッチイベント呼び出し
        public void onTouchEvent(MotionEvent event) {
			super.onTouchEvent(event);
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

					starObj.start();
					super.onTouchEvent(event);

					break;
				case MotionEvent.ACTION_POINTER_DOWN:
					//Log.i("tag", "Touch PTR Down" + " count=" + count + ", id=" + id);
					break;
				case MotionEvent.ACTION_UP:
					//Log.i("tag", "Touch Up" + " count=" + count + ", id=" + id);
					break;
				case MotionEvent.ACTION_POINTER_UP:
					//Log.i("tag", "Touch PTR Up" + " count=" + count + ", id=" + id);
					break;
				case MotionEvent.ACTION_MOVE:
					//Log.i("tag", "Touch Move" + " count=" + count + ", id=" + id);
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

    		canvas = holder.lockCanvas();
    		backgroundColorChange.drawColorBackground();

            try {
        	    if (canvas != null) {
        	    	canvas.drawColor(Color.argb(backgroundColorChange.alpha, backgroundColorChange.red, backgroundColorChange.green, backgroundColorChange.blue));
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
						
			float sakuraHeight_y = sakura[0].height_y - sakura[1].height_y;
			float sakuraWide_x = sakura[0].wide_x - sakura[1].wide_x;
			
			//更新前の座標
			float pre_sakuraHeight_y0 = sakura[0].height_y;
			float pre_sakuraWide_x0 = sakura[0].wide_x;
			float pre_sakuraHeight_y1 = sakura[1].height_y;
			float pre_sakuraWide_x1 = sakura[1].wide_x;

			float sakuraWide_x_abs = Math.abs(sakuraWide_x);
			float sakuraHeight_y_abs = Math.abs(sakuraHeight_y);

			//座標の更新
			sakura[0].run(canvas,snow, newWidth,newHeight,sensorX,sensorY,sensorZ);
			sakura[1].run(canvas,snow, newWidth,newHeight,sensorX,sensorY,sensorZ);
			
			//ボールのサイズ
			float snowSize = snow.getWidth();

			//重なっているときのみ座標の更新
			//[0]が[1]よりも左の場合はマイナス
			if(sakuraHeight_y_abs < snowSize && sakuraWide_x_abs < snowSize){
				sakuraHeight_y_abs = snowSize - sakuraHeight_y_abs;
				//重なっている時は、座標を更新しない。
				if(sakuraHeight_y < 0){
					sakura[0].height_y = sakura[0].height_y - sakuraHeight_y_abs/2;
					sakura[1].height_y = sakura[1].height_y + sakuraHeight_y_abs/2;
				}else{
					sakura[0].height_y = sakura[0].height_y + sakuraHeight_y_abs/2;
					sakura[1].height_y = sakura[1].height_y - sakuraHeight_y_abs/2;
				}
				
				sakuraWide_x_abs = snowSize - sakuraWide_x_abs;
				//重なっている時は、座標を更新しない。
				if(sakuraWide_x < 0){
					sakura[0].wide_x = sakura[0].wide_x - sakuraWide_x_abs/2;
					sakura[1].wide_x = sakura[1].wide_x + sakuraWide_x_abs/2;
				}else{
					sakura[0].wide_x = sakura[0].wide_x + sakuraWide_x_abs/2;
					sakura[1].wide_x = sakura[1].wide_x - sakuraWide_x_abs/2;
				}
			}

			starObj.run(canvas,itemTouch,mTouchX,mTouchY);

			//for(int iiii = 0;iiii<sakura.length;iiii++){
				//SakuraGravityクラスの複製:描画
				//sakura[iiii].drowSakura(canvas,snow,sakura[iiii].wide_x,sakura[iiii].height_y);
			//}
			sakura[0].drowSakura(canvas,snow,sakura[0].wide_x,sakura[0].height_y);
			sakura[1].drowSakura(canvas,snow,sakura[1].wide_x,sakura[1].height_y);

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

				//Log.d("onSensorChanged", " x: " + sensorX); // 表示フォーマットの指定の終了
				//Log.d("onSensorChanged", " y: " + sensorY); // 表示フォーマットの指定の終了
				//Log.d("onSensorChanged", " z: " + sensorZ); // 表示フォーマットの指定の終了
			}

	}

	}
}

