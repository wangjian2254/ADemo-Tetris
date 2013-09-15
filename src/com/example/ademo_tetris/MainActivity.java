package com.example.ademo_tetris;

import loon.LGame;
import loon.LSetting;
import android.view.Display;

import com.example.ademo_tetris.Screen.Tetris;


public class MainActivity extends LGame {

	@Override
	public void onGamePaused() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGameResumed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMain() {
		Display disp=getWindowManager().getDefaultDisplay();
		int w=disp.getWidth();
		int h=disp.getHeight();
		Config.suoxiao_width=w;
		Config.suoxiao_height=h;
		Config.suoxiao_x=(float)(w/640.0);
		Config.suoxiao_y=(float)(h/960.0);
		LSetting setting = new LSetting();
		setting.mode=LMode.Fill;
		setting.showMemory=true;
		setting.width = w;
		setting.height = h;
		setting.showFPS = true;
		setting.fps = 10;
		setting.landscape = false;
		register(setting, Tetris.class);
		
	}

}
