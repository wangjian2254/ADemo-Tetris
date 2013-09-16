package com.mogu.game.tetris.screen;

import android.util.Log;

import com.mogu.game.tetris.config.ConfigTool;

import loon.core.graphics.LImage;
import loon.core.graphics.Screen;
import loon.core.graphics.component.LButton;
import loon.core.graphics.component.LPanel;
import loon.core.graphics.opengl.GLEx;
import loon.core.graphics.opengl.LTexture;
import loon.core.input.LTouch;
import loon.core.timer.LTimerContext;

public class MainMenu extends Screen {
	
	private LButton danren,duoren,daoju,shezhi,chengjiu;
	private LPanel  background;


	@Override
	public void alter(LTimerContext timer) {
		// TODO Auto-generated method stub

	}

	@Override
	public void touchDown(LTouch e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void touchUp(LTouch e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void touchMove(LTouch e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void touchDrag(LTouch e) {
		// TODO Auto-generated method stub

	}


	@Override
	public void draw(GLEx g) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void onLoad() {
		background = new LPanel(0, 0, (int)ConfigTool.getConfig().all_w, (int)ConfigTool.getConfig().all_h);
		background.setBackground(LImage.createImage(ConfigTool.getConfig().bg_001_light).getTexture());
		LImage ba=LImage.createImage(ConfigTool.getConfig().menu_btn_danren1);
		LImage bb=LImage.createImage(ConfigTool.getConfig().menu_btn_danren2);
		LTexture[] btn1={ba.getTexture(),bb.getTexture()};
		danren=new  LButton(btn1, null, ba.getWidth(), ba.getHeight(), ConfigTool.getConfig().main_menu_btn_x, ConfigTool.getConfig().main_menu_btn_y){
			@Override
			public void doClick(){
				Log.d("game_btn", "1");
			}
		};
		LImage b2a=LImage.createImage(ConfigTool.getConfig().menu_btn_duoren1);
		LImage b2b=LImage.createImage(ConfigTool.getConfig().menu_btn_duoren2);
		LTexture[] btn2={b2a.getTexture(),b2b.getTexture()};
		duoren=new  LButton(btn2, null, ba.getWidth(), ba.getHeight(), ConfigTool.getConfig().main_menu_btn_x, ConfigTool.getConfig().main_menu_btn2_y){
			@Override
			public void doClick(){
				Log.d("game_btn", "2");
			}
		};
		LImage b3a=LImage.createImage(ConfigTool.getConfig().menu_btn_daoju1);
		LImage b3b=LImage.createImage(ConfigTool.getConfig().menu_btn_daoju2);
		LTexture[] btn3={b3a.getTexture(),b3b.getTexture()};
		daoju=new  LButton(btn3, null, ba.getWidth(), ba.getHeight(), ConfigTool.getConfig().main_menu_btn_x, ConfigTool.getConfig().main_menu_btn3_y){
			@Override
			public void doClick(){
				Log.d("game_btn", "3");
			}
		};
		LImage b4a=LImage.createImage(ConfigTool.getConfig().menu_btn_shezhi1);
		LImage b4b=LImage.createImage(ConfigTool.getConfig().menu_btn_shezhi2);
		LTexture[] btn4={b4a.getTexture(),b4b.getTexture()};
		shezhi=new  LButton(btn4, null, ba.getWidth(), ba.getHeight(), ConfigTool.getConfig().main_menu_btn_x, ConfigTool.getConfig().main_menu_btn4_y){
			@Override
			public void doClick(){
				Log.d("game_btn", "4");
			}
		};
		LImage b5a=LImage.createImage(ConfigTool.getConfig().menu_btn_chengjiu1);
		LImage b5b=LImage.createImage(ConfigTool.getConfig().menu_btn_chengjiu2);
		LTexture[] btn5={b5a.getTexture(),b5b.getTexture()};
		chengjiu=new  LButton(btn5, null, ba.getWidth(), ba.getHeight(), ConfigTool.getConfig().main_menu_btn_x, ConfigTool.getConfig().main_menu_btn5_y){
			@Override
			public void doClick(){
				Log.d("game_btn", "5");
			}
		};
		//danren,duoren,daoju,shezhi,chengjiu
		add(background);
		add(danren);
		add(duoren);
		add(daoju);
		add(shezhi);
		add(chengjiu);
	}

}
