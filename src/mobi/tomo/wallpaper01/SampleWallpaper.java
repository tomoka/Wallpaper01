package mobi.tomo.wallpaper01;

import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.service.wallpaper.WallpaperService;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

public class SampleWallpaper extends WallpaperService {
    private final Handler mHandler = new Handler();
 
    @Override
    public void onCreate() {
        super.onCreate();
    }
 
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
 
    @Override
    public Engine onCreateEngine() {
        return new SampleEngine();
    }
 
    class SampleEngine extends Engine {
 
        private boolean visible;
 
        private final Runnable mDraw = new Runnable() {
            public void run() {
                drawFrame();
            }
        };
 
        public SampleEngine() {
 
        }
 
        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
        }
 
        @Override
        public void onDestroy() {
            super.onDestroy();
            mHandler.removeCallbacks(mDraw);
        }
 
        @Override
        public void onVisibilityChanged(boolean visible) {
            this.visible = visible;
            if (visible) {
                drawFrame();
            } else {
                mHandler.removeCallbacks(mDraw);
            }
        }
 
        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format,int width, int height) {
            super.onSurfaceChanged(holder, format, width, height);
            drawFrame();
        }
 
        @Override
        public void onSurfaceCreated(SurfaceHolder holder) {
            super.onSurfaceCreated(holder);
        }
 
        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
            mHandler.removeCallbacks(mDraw);
        }
 
        @Override
        public void onOffsetsChanged(float xOffset, float yOffset, float xStep, float yStep, int xPixels, int yPixels) {
        	Log.d("tag",
					String.format(
							"onOffsetsChanged:{xOffset:%f, yOffset:%f, xStep:%f, yStep:%f, xPixels:%d, yPixels:%d}",
							xOffset, yOffset,xStep,yStep,xPixels,yPixels));
        	drawFrame();
        }
 
        @Override
        public void onTouchEvent(MotionEvent event) {
            super.onTouchEvent(event);
        }
 
        void drawFrame() {
            final SurfaceHolder holder = getSurfaceHolder();
 
            Canvas c = null;
            c = holder.lockCanvas();
 
            /*
            *
            *   描画処理
            *
            */
 
            holder.unlockCanvasAndPost(c);
 
 
            mHandler.removeCallbacks(mDraw);
            if (visible) mHandler.postDelayed(mDraw, 25);
 
        }
 
    }
}