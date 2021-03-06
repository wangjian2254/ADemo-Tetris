package com.mogu.game.tetris.screen;


import loon.LSetting;
import loon.core.LSystem;
import loon.core.graphics.Screen;
import loon.core.graphics.component.LButton;
import loon.core.graphics.opengl.GLEx;
import loon.core.graphics.opengl.LTexture;
import loon.core.graphics.opengl.LTextures;
import loon.core.input.LTouch;
import loon.core.timer.LTimerContext;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.mogu.game.tetris.MainMenuActivity;
import com.mogu.game.tetris.config.CT;

public class MainMenu extends Screen {
	
	private LButton danren,duoren,daoju,shezhi,chengjiu,bangzhu,yuezhan;
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
	}
	
	
	
	@Override
	public void onLoad() {
		LTexture b=new LTexture(CT.gC().menu_btn_danren1);
		LTexture[] btn1={b,LTextures.loadTexture(CT.gC().menu_btn_danren2)};
		danren=new  LButton(btn1, null, btn1[0].getWidth(), btn1[0].getHeight(), CT.gC().main_menu_btn_x, CT.gC().main_menu_btn_y){
			@Override
			public void doClick(){
				replaceScreen(new Tetris(), MoveMethod.FROM_RIGHT);
			}
		};
		LTexture[] btn2={new LTexture(CT.gC().menu_btn_duoren1),new LTexture(CT.gC().menu_btn_duoren2)};
		duoren=new  LButton(btn2, null, btn1[0].getWidth(), btn1[0].getHeight(), CT.gC().main_menu_btn_x, CT.gC().main_menu_btn2_y){
			@Override
			public void doClick(){
				Log.d("game_btn", "2");
			}
		};
		LTexture[] btn3={new LTexture(CT.gC().menu_btn_daoju1),new LTexture(CT.gC().menu_btn_daoju2)};
		daoju=new  LButton(btn3, null, btn1[0].getWidth(), btn1[0].getHeight(), CT.gC().main_menu_btn_x, CT.gC().main_menu_btn3_y){
			@Override
			public void doClick(){
				Log.d("game_btn", "3");
			}
		};
		LTexture[] btn4={new LTexture(CT.gC().menu_btn_shezhi1),new LTexture(CT.gC().menu_btn_shezhi2)};
		shezhi=new  LButton(btn4, null, btn1[0].getWidth(), btn1[0].getHeight(), CT.gC().main_menu_btn_x, CT.gC().main_menu_btn4_y){
			@Override
			public void doClick(){
				Log.d("game_btn", "4");
			}
		};
		LTexture[] btn5={new LTexture(CT.gC().menu_btn_chengjiu1),new LTexture(CT.gC().menu_btn_chengjiu2)};
		chengjiu=new  LButton(btn5, null, btn1[0].getWidth(), btn1[0].getHeight(), CT.gC().main_menu_btn_x, CT.gC().main_menu_btn5_y){
			@Override
			public void doClick(){
				Intent mainIntent = new Intent(LSystem.screenActivity,MainMenuActivity.class);
		    	Bundle extras=new Bundle();
		    	mainIntent.putExtras(extras);
		    	LSystem.screenActivity.startActivity(mainIntent); 
			}
		};
		LTexture[] btn6={new LTexture(CT.gC().topbar_btn_bangzhu1),new LTexture(CT.gC().topbar_btn_bangzhu2)};
		bangzhu=new  LButton(btn6, null, btn6[0].getWidth(), btn6[0].getHeight(), CT.gC().topbar_btn_bangzhu_x, CT.gC().topbar_btn_bangzhu_y){
			@Override
			public void doClick(){
				replaceScreen(new Help(MainMenu.this), MoveMethod.FROM_RIGHT);
			}
		};
		LTexture[] btn7={new LTexture(CT.gC().topbar_btn_yuezhan1),new LTexture(CT.gC().topbar_btn_yuezhan2)};
		yuezhan=new  LButton(btn7, null, btn6[0].getWidth(), btn6[0].getHeight(), CT.gC().topbar_btn_yuezhan_x, CT.gC().topbar_btn_yuezhan_y){
			@Override
			public void doClick(){
				Log.d("game_btn", "7");
			}
		};
		//danren,duoren,daoju,shezhi,chengjiu
//		add(background);
		setBackground(CT.gC().bg_001_light);
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
