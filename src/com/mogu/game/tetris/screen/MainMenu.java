package com.mogu.game.tetris.screen;


import loon.core.graphics.LColor;
import loon.core.graphics.LImage;
import loon.core.graphics.Screen;
import loon.core.graphics.component.LButton;
import loon.core.graphics.opengl.GL;
import loon.core.graphics.opengl.GLEx;
import loon.core.graphics.opengl.LTexture;
import loon.core.graphics.opengl.LTexture.Format;
import loon.core.graphics.opengl.LTextures;
import loon.core.input.LTouch;
import loon.core.timer.LTimerContext;
import android.graphics.Bitmap;
import android.util.Log;

import com.mogu.game.tetris.config.ConfigTool;

public class MainMenu extends Screen {
	
	private LButton danren,duoren,daoju,shezhi,chengjiu,bangzhu,yuezhan;
	private LTexture lt;
//	private LPanel  background;

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
	public void draw(GLEx g) {
		// TODO Auto-generated method stub
		g.setBlendMode(GL.MODE_SPEED);
		g.drawTexture(lt, 210, 30);
	}
	
	
	
	@Override
	public void onLoad() {
//		LImage.createImage(fileName)
		lt = new LTexture(LImage.createImage("assets/metalayer.png"));
//		background = new LPanel(0, 0, (int)ConfigTool.getConfig().all_w, (int)ConfigTool.getConfig().all_h);
//		background.setBackground(LImage.createImage(ConfigTool.getConfig().bg_001_light).getTexture());
		LTexture b=new LTexture(ConfigTool.getConfig().menu_btn_danren1);
		LTexture[] btn1={b,LTextures.loadTexture(ConfigTool.getConfig().menu_btn_danren2)};
		danren=new  LButton(btn1, null, btn1[0].getWidth(), btn1[0].getHeight(), ConfigTool.getConfig().main_menu_btn_x, ConfigTool.getConfig().main_menu_btn_y){
			@Override
			public void doClick(){
				replaceScreen(new Tetris(), MoveMethod.FROM_RIGHT);
			}
		};
		LTexture[] btn2={new LTexture(ConfigTool.getConfig().menu_btn_duoren1),new LTexture(ConfigTool.getConfig().menu_btn_duoren2)};
		duoren=new  LButton(btn2, null, btn1[0].getWidth(), btn1[0].getHeight(), ConfigTool.getConfig().main_menu_btn_x, ConfigTool.getConfig().main_menu_btn2_y){
			@Override
			public void doClick(){
				Log.d("game_btn", "2");
			}
		};
		LTexture[] btn3={new LTexture(ConfigTool.getConfig().menu_btn_daoju1),new LTexture(ConfigTool.getConfig().menu_btn_daoju2)};
		daoju=new  LButton(btn3, null, btn1[0].getWidth(), btn1[0].getHeight(), ConfigTool.getConfig().main_menu_btn_x, ConfigTool.getConfig().main_menu_btn3_y){
			@Override
			public void doClick(){
				Log.d("game_btn", "3");
			}
		};
		LTexture[] btn4={new LTexture(ConfigTool.getConfig().menu_btn_shezhi1),new LTexture(ConfigTool.getConfig().menu_btn_shezhi2)};
		shezhi=new  LButton(btn4, null, btn1[0].getWidth(), btn1[0].getHeight(), ConfigTool.getConfig().main_menu_btn_x, ConfigTool.getConfig().main_menu_btn4_y){
			@Override
			public void doClick(){
				Log.d("game_btn", "4");
			}
		};
		LTexture[] btn5={new LTexture(ConfigTool.getConfig().menu_btn_chengjiu1),new LTexture(ConfigTool.getConfig().menu_btn_chengjiu2)};
		chengjiu=new  LButton(btn5, null, btn1[0].getWidth(), btn1[0].getHeight(), ConfigTool.getConfig().main_menu_btn_x, ConfigTool.getConfig().main_menu_btn5_y){
			@Override
			public void doClick(){
				Log.d("game_btn", "5");
			}
		};
		LTexture[] btn6={new LTexture(ConfigTool.getConfig().topbar_btn_bangzhu1),new LTexture(ConfigTool.getConfig().topbar_btn_bangzhu2)};
		bangzhu=new  LButton(btn6, null, btn6[0].getWidth(), btn6[0].getHeight(), ConfigTool.getConfig().topbar_btn_bangzhu_x, ConfigTool.getConfig().topbar_btn_bangzhu_y){
			@Override
			public void doClick(){
				Log.d("game_btn", "6");
			}
		};
		LTexture[] btn7={new LTexture(ConfigTool.getConfig().topbar_btn_yuezhan1),new LTexture(ConfigTool.getConfig().topbar_btn_yuezhan2)};
		yuezhan=new  LButton(btn7, null, btn6[0].getWidth(), btn6[0].getHeight(), ConfigTool.getConfig().topbar_btn_yuezhan_x, ConfigTool.getConfig().topbar_btn_yuezhan_y){
			@Override
			public void doClick(){
				Log.d("game_btn", "7");
			}
		};
		//danren,duoren,daoju,shezhi,chengjiu
//		add(background);
		setBackground(ConfigTool.getConfig().bg_001_light);
		add(danren);
		add(duoren);
		add(daoju);
		add(shezhi);
		add(chengjiu);
		add(bangzhu);
		add(yuezhan);
	}

	@Override
	public void touchDrag(LTouch e) {
		// TODO Auto-generated method stub
		
	}

}
