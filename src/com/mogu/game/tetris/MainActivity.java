package com.mogu.game.tetris;

import com.liyu.pluginframe.util.MainDataTool;
import loon.LGame;
import loon.LSetting;
import loon.core.graphics.opengl.LTexture;
import android.view.Display;

import com.mogu.game.tetris.config.CT;
import com.mogu.game.tetris.screen.Tetris;
import com.umeng.analytics.MobclickAgent;


public class MainActivity extends LGame {

	@Override
	public void onGamePaused() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGameResumed() {
		// TODO Auto-generated method stub
		
	}
//	@Override
//	public void onCreate(Bundle icicle) {
////		getWindow().setFormat(PixelFormat.TRANSLUCENT);
////		getWindow().setBackgroundDrawableResource(R.drawable.bg_001_light);
//		super.onCreate(icicle);
//	}

	@Override
	public void onMain() {
		Display disp=getWindowManager().getDefaultDisplay();
		int w=disp.getWidth();
		int h=disp.getHeight();
		CT.init(w, h);
		setDestroy(false);
		LTexture.ALL_LINEAR = true;
		LSetting setting = new LSetting();
		setting.mode=LMode.Fill;
		setting.showMemory=false;
		setting.width = CT.gC().all_w;
		setting.height = CT.gC().all_h;
		setting.showFPS = false;
		setting.fps = 30;
		setting.landscape = false;
		register(setting, Tetris.class);

		
	}
	
	public void onResume(){
    	super.onResume();
    	MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

}
