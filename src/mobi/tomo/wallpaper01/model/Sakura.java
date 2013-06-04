package mobi.tomo.wallpaper01.model;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

public class Sakura {

	public float wide_x;
	public float speed;
	public float height_y;
	public float degree;
	public float addDegree;
	public float defaultSpeed;
	
	//角度
	float rad;
	//座標
	float x1;
	float y1;
	float x2;
	float y2;
	float dx;
	float dy;
	
	float snow_X = x2;
	float snow_Y = y2;

	float elapsedTime =(float) 0.25;
	float gravity =(float) 9.8;
	
	Bitmap snow_R;	
	int ran;
	Random rnd = new Random();
	long old_time = System.currentTimeMillis();
			
	public Sakura() {
		//初期化
		init();
	}
	
	//初期化処理中身
	public void init(){
		speed = rnd.nextInt(10) + 10;
		wide_x = rnd.nextInt(680);
		height_y = rnd.nextInt(300) - 400;
		degree = 0;
		addDegree = rnd.nextInt(30) + 30;
		defaultSpeed = rnd.nextInt(30) + 5;	
	}
	
	public void run(Canvas canvas,Bitmap snow){
		long now_time = System.currentTimeMillis();

			if (height_y > 1000) {
				init();
				}
			
			//回転
			degree = degree + addDegree;
			if (degree >= 360){
				degree = 0;
				}
						
			/* d[i]度の場合のラジアン */
			rad = (float) (Math.PI/180 * degree);
			
			x1 = wide_x;
			y1 = snow_Y;
			
			//d[i]分の角度
			x2 = (float) (x1*Math.cos(rad) + y1*Math.sin(rad));
			y2 = (float) (x1*Math.sin(rad) - y1*Math.cos(rad));

			snow_X = x2;
			snow_Y = y2;

			Matrix matrix = new Matrix();
						
			/* distance 
			 * h = 距離　※書き換えて代入（前回まで進んだ距離→今回進んだ距離）
			 * v*t = 速さ×時間　※初期値（はじめから前回までの時間で進んだ距離）
			 * g*t*t/2 = 加速度x時間x時間÷2　※今回進んだ距離（直線運動の三角形面積）
			 */
			height_y = height_y + defaultSpeed*elapsedTime + gravity*elapsedTime*elapsedTime/2;
			/* velocity
			 * 速さを保存して、次の処理で使う
			 */
			defaultSpeed = defaultSpeed + gravity*elapsedTime;

			matrix.setScale(speed/10,speed/10);
			snow_X = snow_X/2;
			snow_X = snow_X+snow.getWidth()/-2+wide_x;
			snow_Y = snow_Y/2;
			snow_Y = snow_Y + snow.getHeight()/-2+height_y;
			matrix.postRotate(degree,snow_X,snow_Y);
			
			//Log.d("tag", "snow.getWidth()------>"+ snow.getWidth());
			//Log.d("tag", "snow.getHeight()------>"+ snow.getHeight());

			if (snow_R != null) {
				snow_R = null;
			}
			snow_R = Bitmap.createBitmap(snow,0,0,snow.getWidth(),snow.getHeight(),matrix,false);
			//snow_R = snow;
						
			Matrix matrix2 = new Matrix();
			
			matrix2.setTranslate(snow_R.getWidth()/-2+wide_x,snow_R.getHeight()/-2+height_y);

			canvas.drawBitmap(snow_R , matrix2, new Paint());
			long pass_time = now_time - old_time;

			old_time = now_time;
	}
}
