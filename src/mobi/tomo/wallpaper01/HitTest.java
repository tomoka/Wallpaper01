package mobi.tomo.wallpaper01;

import android.util.Log;

public class HitTest {
	
	public float height_y0;
	public float height_y1;

	public void Judgment(float height_y0, float height_y1, float sakuraHeight_y) {
		// TODO Auto-generated method stub
		Log.d("hittest=========", "ヒットしました！");
		
		height_y0 = height_y0;
		height_y1 = height_y1 - sakuraHeight_y;
		Log.d("hittest2==========", " height_y1: " + height_y1);
		Log.d("hittest2==========", " sakuraHeight_y: " + sakuraHeight_y);
		return;
	};
	public void ItChanges() {
		Log.d("hittest=========★==★==★", "ヒットしました！");

	}

}
