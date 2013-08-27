/*重力加速度と連携*/
package mobi.tomo.wallpaper01.model;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.Log;
import android.view.WindowManager;

public class SakuraGravity{
	public float wide_x;
	public float height_y;
	public float speed;
	public float degree;
	public float addDegree;
	public float defaultSpeed;
	public double defaultSpeedY;
	public double defaultSpeedX;
	
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
				//defaultSpeedY = defaultSpeedY*2;
				defaultSpeedX = defaultSpeedX*-1.2;
				wide_x = nowWidth-35;
				}
			if (height_y > nowHeight-35 ) {
				defaultSpeedY = defaultSpeedY*-1.2;
				//defaultSpeedX = defaultSpeedX*2;
				height_y = nowHeight-35;
				}
			if (wide_x < 0 ) {
				//defaultSpeedY = defaultSpeedY*2;
				defaultSpeedX = defaultSpeedX*1.5;
				wide_x = 0;
				}
			if (height_y < 35 ) {
				defaultSpeedY = defaultSpeedY*1.5;
				//defaultSpeedX = defaultSpeedX*2;
				height_y = 35;
				}

			//gravityY = sensorY*2;
			//gravityX = sensorX*-1*2;
			
			if(sensorX < 0){
				gravityX = 1;
			}else{
				gravityX = -1;
			}
			if(sensorY < 0){
				gravityY = -1;
			}else{
				gravityY = 1;
			}
			
			height_y = (float) (height_y + defaultSpeedY*elapsedTime + gravityY*elapsedTime*elapsedTime);
			wide_x = (float) (wide_x + defaultSpeedX*elapsedTime + gravityX*elapsedTime*elapsedTime);

			defaultSpeedY = defaultSpeedY + gravityY*elapsedTime;
			defaultSpeedX = defaultSpeedX + gravityX*elapsedTime;
			
			if(defaultSpeedY > 100){
				defaultSpeedY = 0;
			}
			if(defaultSpeedX > 100){
				defaultSpeedX = 0;
			}
			if(defaultSpeedY < -100){
				defaultSpeedY = 0;
			}
			if(defaultSpeedX < -100){
				defaultSpeedX = 0;
			}
			defaultSpeedY = defaultSpeedY + gravityY*elapsedTime;
			defaultSpeedX = defaultSpeedX + gravityX*elapsedTime;
									
    		Log.i("defaultSpeed", "defaultSpeedY=======" + defaultSpeedY );
    		Log.i("defaultSpeed", "defaultSpeedX=======" + defaultSpeedX );
						
			long pass_time = now_time - old_time;

			old_time = now_time;
	}
	public void drowSakura(Canvas canvas, Bitmap snow, float wide_x2, float height_y2) {
		// TODO Auto-generated method stub
		Matrix matrix = new Matrix();
		
		matrix.setTranslate(wide_x2,height_y2);
		canvas.drawBitmap(snow , matrix, new Paint());
		
	}

}

