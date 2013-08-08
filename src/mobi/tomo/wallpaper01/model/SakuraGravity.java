/*重力加速度と連携*/
package mobi.tomo.wallpaper01.model;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.Log;
import android.view.WindowManager;

public class SakuraGravity {
	public float wide_x;
	public float speed;
	public float height_y;
	public float degree;
	public float addDegree;
	public float defaultSpeed;
	public float defaultSpeedY;
	public float defaultSpeedX;
	
	//角度
	float rad;
	//座標
	float x1;
	float y1;
	float x2;
	float y2;
	float x3=0;
	float y3=0;
	float dx;
	float dy;
	
	float snow_X = x2;
	float snow_Y = y2;

	float elapsedTime =(float) 0.25;
	/*通常の自由落下加速度*/
	float gravity =(float) 9.8;
	float gravityX =(float) 9.8;
	float gravityY =(float) 9.8;
	
	float nowWidth;
	float nowHeight;
	
	float nowWide_x;
	float nowHeight_y;
		
	Bitmap snow_R;	
	int ran;
	Random rnd = new Random();
	long old_time = System.currentTimeMillis();

	public SakuraGravity() {
		//初期化
		init();
	}
	
	private WindowManager getWindowManager() {
		// TODO Auto-generated method stub
		return null;
	}

	//初期化処理中身
	public void init(){
		speed = rnd.nextInt(10) + 10;
		wide_x = rnd.nextInt(300)+10;
		height_y = rnd.nextInt(300)+10;
		degree = 0;
		addDegree = rnd.nextInt(30) + 30;
		defaultSpeed = rnd.nextInt(10) + 5;
		defaultSpeedY = 1;
		defaultSpeedX = 1;
	}
	
	public void run(Canvas canvas,Bitmap snow,float newWidth,float newHeight,float sensorX,float sensorY,float sensorZ){
		long now_time = System.currentTimeMillis();
		nowWidth = newWidth;
		nowHeight = newHeight;

			
			if (wide_x > nowWidth-35 ) {
				defaultSpeedY = 0;
				defaultSpeedX = 0;
				wide_x = nowWidth-35;
				}
			if (height_y > nowHeight-35 ) {
				defaultSpeedY = 0;
				defaultSpeedX = 0;
				height_y = nowHeight-35;
				}
			if (wide_x < 0 ) {
				defaultSpeedY = 0;
				defaultSpeedX = 0;
				wide_x = 0;
				}
			if (height_y < 35 ) {
				defaultSpeedY = 0;
				defaultSpeedX = 0;
				height_y = 35;
				}

			gravityY = sensorY*2;
			gravityX = sensorX*-1*2;
			
			//height_y = height_y + defaultSpeedY*elapsedTime + gravityY*elapsedTime*elapsedTime/2;
			//wide_x = wide_x + defaultSpeedX*elapsedTime + gravityX*elapsedTime*elapsedTime/2;
			height_y = height_y + defaultSpeedY*elapsedTime + gravityY*elapsedTime*elapsedTime;
			wide_x = wide_x + defaultSpeedX*elapsedTime + gravityX*elapsedTime*elapsedTime;

			defaultSpeedY = defaultSpeedY + gravityY*elapsedTime;
			defaultSpeedX = defaultSpeedX + gravityX*elapsedTime;
			
			//float abs_scale = (float) Math.round(sensorZ/2);
			float abs_scale = (float) sensorZ/4;
				/*if(abs_scale < 0){
					  abs_scale = (float) Math.abs(abs_scale);
				}else{
					  abs_scale = 1;
				}*/
			abs_scale = (float) Math.abs(abs_scale);
			if(abs_scale < 1){
				  abs_scale = (float) (1);
			}

			abs_scale = (float) (1);
						
    		Log.i("snow", "height_y=======" + height_y );
    		Log.i("snow", "wide_x=======" + wide_x );
    		Log.i("snow", "abs_scale=======" + abs_scale );
    		Log.i("snow", "nowWidth=======" + nowWidth );
    		Log.i("snow", "nowHeight=======" + nowHeight );
						
			long pass_time = now_time - old_time;

			old_time = now_time;
	}
	public void drowSakura(Canvas canvas,Bitmap snow, float wide_x2, float height_y2) {
		// TODO Auto-generated method stub
		
		Matrix matrix = new Matrix();
				
		//matrix.setScale(abs_scale,abs_scale);
		//matrix.postTranslate(wide_x,height_y);
		matrix.setTranslate(wide_x,height_y);
		canvas.drawBitmap(snow , matrix, new Paint());
		//canvas.drawBitmap(snow , matrix2, new Paint());
	}

}

