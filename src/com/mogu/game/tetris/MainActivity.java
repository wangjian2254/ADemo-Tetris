package com.mogu.game.tetris;

import loon.LGame;
import loon.LSetting;
import android.view.Display;

import com.mogu.game.tetris.config.ConfigTool;
import com.mogu.game.tetris.screen.MainMenu;
import com.mogu.game.tetris.screen.Tetris;


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
		ConfigTool.init(w, h);
		
		LSetting setting = new LSetting();
		setting.mode=LMode.Fill;
		setting.showMemory=true;
		setting.width = w;
		setting.height = h;
		setting.showFPS = true;
		setting.fps = 10;
		setting.landscape = false;
		register(setting, MainMenu.class);
		
	}

}
