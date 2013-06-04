package mobi.tomo.wallpaper01;

import java.util.Random;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.Log;
import android.view.Display;

public class snowDraw{
	public snowDraw(){
		//public snowDraw(int snowNum){
		//snowNum = snowNum;
	}
	
	public void run(Canvas canvas, Bitmap snow,float snow_X,float snow_Y,float[] c,float[] s,float[] h,float[] d,float[] d2,float[] y,float t,float g,float rad,float x1,float y1,float x2,float y2,float dx,float dy,float [] v) {
		//public void run(Canvas canvas, Bitmap snow,float[] c,float[] s,float[] h,float[] d,float[] d2,float[] y,float t,float g,float rad,float x1,float y1,float x2,float y2,float dx,float dy,int snowNum) {
		// TODO Auto-generated method stub
		Random rnd = new Random();
							
		for (int i = 0; i < 10; i++) {
			//s[i] = 5;
			if (h[i] > 1200) {
				//if (h[i] > Display.getHeight()) {
				h[i] = 0;
				int ran;
				v[i] = rnd.nextInt(100);


				ran = rnd.nextInt(680) + 1;
				c[i] = ran;
				//c[i] = 300;

				ran = rnd.nextInt(10) + 1;
				s[i] = ran;

				ran = rnd.nextInt(20) + 10;
				d2[i] = ran;
				}
			
			//回転
			d[i] = d[i]+d2[i];
			if (d[i] >= 360){
				d[i] = 0;
				}
			

			//雪の画像の大きさを取得
			int snowW = snow.getWidth();
			int snowH = snow.getHeight();
			
			/* d[i]度の場合 */
			rad = (float) (Math.PI/180 * d[i]);
			
			//決め打ち
			//x1 = 0;
			//y1 = 0;
			x1 = c[i];
			y1 = snow_Y;
			
			//d[i]分の角度
			x2 = (float) (x1*Math.cos(rad) + y1*Math.sin(rad));
			y2 = (float) (x1*Math.sin(rad) - y1*Math.cos(rad));

			snow_X = x2;
			snow_Y = y2;

			Matrix matrix = new Matrix();
			
			// distance 
			h[i] = h[i] + v[i]*t + g*t*t/2;
			// velocity
			v[i] = v[i] + g*t;

			matrix.setScale(s[i]/10,s[i]/10);
			matrix.postRotate(d[i],snow_X/2,snow_Y/2);
			Bitmap snow_R = Bitmap.createBitmap(snow,0,0,snow.getWidth(),snow.getHeight(),matrix,false);
							
			Matrix matrix2 = new Matrix();
			
			matrix2.setTranslate(snow_R.getWidth()/-2+c[i],snow_R.getHeight()/-2+h[i]);
			
			//Log.d("tag", "h------>"+ "["+i+"]"+h[i]);
			//Log.d("tag", "c------>"+ "["+i+"]"+c[i]);

			canvas.drawBitmap(snow_R , matrix2, new Paint());
			}
	}
}


