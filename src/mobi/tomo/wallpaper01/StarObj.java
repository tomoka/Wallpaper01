package mobi.tomo.wallpaper01;

import java.lang.reflect.Array;

import mobi.tomo.wallpaper01.model.Sakura;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.Log;

/*
 * 星がはじけるオブジェクトの試作
 * タップした場所に星を表示
 * */
public class StarObj {
	private static final int DRAW_TIME = 1000;
	Star[] star = new Star[9];


	Matrix matrix3 = new Matrix();
	Paint paint = new Paint();
	
	int degree[] = new int[star.length];
	
	int alphaNum = 255;
	float ScaleSize= 0;



	// 描画状態の定義:ステータスと配列の番号をひも付けできる
	enum DrawStatus {start, drawStep01, drawStep02, end};
	
	// 変数宣言
	long old_time;
	long passed_time;
	DrawStatus draw_status;
	
	public void init() {
		old_time = System.currentTimeMillis();
		draw_status = DrawStatus.end;
	}
	
	public void start() {
		draw_status = DrawStatus.start;
		ScaleSize = 0;
		alphaNum = 255;
		for(int i = 0;i<star.length;i++){
			star[i] = new Star();
			degree[i] = 30*i;
		}

		Log.i("tag", "☆☆☆☆☆☆☆☆☆☆draw_status------------>" + draw_status );
	}
	
	public void end() {
		draw_status = DrawStatus.end;
	}
	
	public void drawStep01() {
		draw_status = DrawStatus.drawStep01;
	}
	public void drawStep02() {
		draw_status = DrawStatus.drawStep02;
	}
	
	public void update() {
		if (draw_status.equals(DrawStatus.start)) {
			old_time = System.currentTimeMillis();
			passed_time = 0;
			Log.i("tag", "☆☆draw_status------------>" + draw_status );
			Log.i("tag", "☆☆passed_time------------>" + passed_time );
			drawStep01();
		}
		else if (draw_status.equals(DrawStatus.drawStep01)||draw_status.equals(DrawStatus.drawStep02)) {
			//時間の更新
			passed_time = System.currentTimeMillis() - old_time;
		}
		else if (draw_status.equals(DrawStatus.end)) {
			//何もしない
			alphaNum = 255;
			ScaleSize= 0;
		}
		
		if (passed_time > DRAW_TIME) {
			//end();
		}
	}
	
	public DrawStatus getStatus() {
		return draw_status;
	}

	public void run(Canvas canvas,Bitmap itemTouch,float mTouchX,float mTouchY) {
		update();
		Log.i("tag", "☆draw_status------------>" + draw_status );
		int starX = (int) mTouchX - itemTouch.getWidth()/2;
		int starY = (int) mTouchY - itemTouch.getHeight()/2;
		Log.i("drawStep", "☆☆☆starX-------->" + starX );
		Log.i("drawStep", "☆☆☆xbitemTouch.getWidth()-------->" + itemTouch.getWidth() );

		
		//Step01　分裂☆をふやす
		if (draw_status.equals(DrawStatus.drawStep01)) {
				
				for(int i = 0;i<star.length;i++){
					//star[i].x = starX - itemTouch.getWidth()/2;
					//star[i].y = starY - itemTouch.getHeight()/2;
					star[i].x = starX;
					star[i].y = starY;
					matrix3.setTranslate(star[i].x, star[i].y);

					canvas.drawBitmap(itemTouch, matrix3,paint);
				}
				drawStep02();

		}
		//Step02　拡大してひろがる
		if (draw_status.equals(DrawStatus.drawStep02)) {
			Log.i("drawStep", "☆☆drawStep------------>" + draw_status );
			Log.i("drawStep", "☆☆alphaNum------------>" + alphaNum );
			Log.i("drawStep", "☆☆ScaleSize------------>" + ScaleSize );
			Log.i("drawStep", "☆☆passed_time------------>" + passed_time );
			//スケールサイズを大きく
			ScaleSize++;
			
				for(int i = 0;i<star.length;i++){
					int radius = (int) (5*ScaleSize);
					degree[i] = 30*i;
					star[i].x = (int) ((starX) + (Math.cos(degree[i])*(radius+itemTouch.getWidth()*ScaleSize*2)));
					star[i].y = (int) ((starY) + (Math.sin(degree[i])*(radius+itemTouch.getHeight()*ScaleSize*2)));
					
					//star[i].x = (int) (200 + (Math.cos(degree[i])*(radius)));
					//star[i].y = (int) (200 + (Math.sin(degree[i])*(radius)));

					matrix3.setTranslate(star[i].x, star[i].y);
					matrix3.preScale(ScaleSize, ScaleSize);
					Log.i("drawStep", "☆☆zahyou------------>" + star[i].x+"-------"+ star[i].y );
					Log.i("drawStep", "☆☆itemTouch.getWidth()-------->" + itemTouch.getWidth() );
					Log.i("drawStep", "☆☆ScaleSize-------->" + ScaleSize );
					Log.i("drawStep", "☆☆itemTouch.getWidth()*ScaleSize/2-------->" + itemTouch.getWidth()*ScaleSize/2 );
					Log.i("drawStep", "☆☆ScaleSize:alphaNum-------->" + ScaleSize +":"+ alphaNum );
					
					paint.setAlpha(alphaNum);
					canvas.drawBitmap(itemTouch, matrix3,paint);
				}
				if(alphaNum <= 0){
					end();
				}else{
					drawStep02();
					alphaNum = (int) (alphaNum - (alphaNum * 0.4));
					ScaleSize = (int) (ScaleSize + (ScaleSize * 0.3333));
				}
		}
	}
}
class Star{
	int x;
	int y;
	}
