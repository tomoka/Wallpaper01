/*20130903一定の速さで跳ね返り*/
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
	//public float defaultSpeed;
	
	/*加速度*/
	DefaultSpeed defaultSpeed = new DefaultSpeed();

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
	
	/*通常の自由落下加速度の衝突の時にかける数*/
	SpeedStep01 speedStep01 = new SpeedStep01();
	SpeedStep02 speedStep02 = new SpeedStep02();
	
	float num01 = speedStep01.Num01;
	float num02 = speedStep01.Num02;
	
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
		//defaultSpeed = rnd.nextInt(10) + 5;
		defaultSpeed.Y = 1;
		defaultSpeed.X = 1;
		defaultSpeed.stepStatusX = defaultSpeed.stepStatusX.step01;
		defaultSpeed.stepStatusY = defaultSpeed.stepStatusY.step01;
	}
	
	public void run(Canvas canvas,Bitmap snow,float newWidth,float newHeight,float sensorX,float sensorY,float sensorZ){
		long now_time = System.currentTimeMillis();
		nowWidth = newWidth;
		nowHeight = newHeight;
			
			if (wide_x > nowWidth-36 ) {
				//defaultSpeedY = defaultSpeedY*2;
				defaultSpeed.X = defaultSpeed.X*num02;
				wide_x = nowWidth-35;
				}
			if (height_y > nowHeight-36 ) {
				defaultSpeed.Y = defaultSpeed.Y*num02;
				//defaultSpeedX = defaultSpeedX*2;
				height_y = nowHeight-35;
				}
			if (wide_x < 0 ) {
				//defaultSpeedY = defaultSpeedY*2;
				defaultSpeed.X = defaultSpeed.X*num01*-1;
				wide_x = 1;
				}
			if (height_y < 34 ) {
				defaultSpeed.Y = defaultSpeed.Y*num01*-1;
				//defaultSpeedX = defaultSpeedX*2;
				height_y = 35;
				}
			
			if(sensorX < 0){
				gravityX = 2;
			}else{
				gravityX = -2;
			}
			if(sensorY < 0){
				gravityY = -2;
			}else{
				gravityY = 2;
			}
			
			height_y = (float) (height_y + defaultSpeed.Y*elapsedTime + gravityY*elapsedTime*elapsedTime);
			wide_x = (float) (wide_x + defaultSpeed.X*elapsedTime + gravityX*elapsedTime*elapsedTime);

			defaultSpeed.Y = defaultSpeed.Y + gravityY*elapsedTime;
			defaultSpeed.X = defaultSpeed.X + gravityX*elapsedTime;
			
			/*Yの計算*/						
			if(defaultSpeed.Y > 300 || defaultSpeed.Y > -300){
				//defaultSpeed.stepStatusY = defaultSpeed.stepStatusY.step01;
				num01 = speedStep01.Num01;
				
				defaultSpeed.Y = defaultSpeed.Y + gravityY*elapsedTime;
			}else{
				//defaultSpeed.stepStatusY = defaultSpeed.stepStatusY.step02;
				num01 = speedStep02.Num01;
				
				defaultSpeed.Y = defaultSpeed.Y - gravityY*elapsedTime;
			}
			
			/*Xの計算*/						
			if(defaultSpeed.X > 300 || defaultSpeed.X > -300){
				//defaultSpeed.stepStatusX = defaultSpeed.stepStatusX.step01;
				num01 = speedStep02.Num01;
				
				defaultSpeed.X = defaultSpeed.X + gravityX*elapsedTime;
			}else{
				//defaultSpeed.stepStatusX = defaultSpeed.stepStatusX.step02;
				num02 = speedStep01.Num02;
				
				defaultSpeed.X = defaultSpeed.X - gravityX*elapsedTime;
			}
									
    		//Log.i("defaultSpeed", "defaultSpeedY=======" + defaultSpeedY );
    		//Log.i("defaultSpeed", "defaultSpeedX=======" + defaultSpeedX );
						
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
class DefaultSpeed{
	double X;
	double Y;

	// 加速の状態:Xステータスと配列の番号をひも付けできる
	enum StepStatusX {step01, step02};
	StepStatusX stepStatusX;

	// 加速の状態:Yステータスと配列の番号をひも付けできる
	enum StepStatusY {step01, step02};
	StepStatusY stepStatusY;
}
/*加速時*/
class SpeedStep01{
	float Num01 = (float) 1.2;
	float Num02 = (float) -1.2;
	//defaultSpeed.Y = 0;
}
/*減速時*/
class SpeedStep02{
	float Num01 = (float) 0.8;
	float Num02 = (float) -0.8;
	//defaultSpeed.Y = 0;
}


