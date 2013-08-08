package mobi.tomo.wallpaper01;

import java.util.Random;

import mobi.tomo.wallpaper01.StarObj.DrawStatus;

public class BackgroundColorChange {
	Random rnd = new Random();

	int red = rnd.nextInt(255);
	int green = rnd.nextInt(255);
	int blue = rnd.nextInt(255);
	int redAdd = 1;
	int greenAdd = 1;
	int blueAdd = 1;
	//0~255 100がマックスではありません。
	int alpha = 255;
    	
    	public void drawColorBackground() {
    		
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
    			//Log.i("color", "red=======" + red );
    			//Log.i("color", "green=======" + green );
    			//Log.i("color", "blue=======" + blue );
    	    	return;
    	}    	
}
