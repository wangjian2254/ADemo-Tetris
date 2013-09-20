package com.mogu.game.tetris.screen;


import loon.core.graphics.LColor;
import loon.core.graphics.LFont;
import loon.core.graphics.Screen;
import loon.core.graphics.component.LButton;
import loon.core.graphics.component.LMessage;
import loon.core.graphics.opengl.GLEx;
import loon.core.graphics.opengl.LTexture;
import loon.core.graphics.opengl.LTextures;
import loon.core.input.LTouch;
import loon.core.timer.LTimerContext;
import android.util.Log;

import com.mogu.game.tetris.config.CT;

public class Help extends Screen {
	
	private LButton fanhui;
//	private LPanel  background;
	private LTexture block;
	private Screen f;
	private LMessage msg;
	private String helpStr="游戏帮助";
	private String helpContent="俄罗斯方块的基本规则是移动、旋转和摆放。游戏自动输出各种方块，使之排列成完整的一行或多行并且消除，得分。由于上手简单、老少皆宜，只是一个简单的俄罗斯方块游戏，相信你不会畏惧吧！也相信你迫不及待了，那就让我们一起比一比谁的分数高！";
	public Help(Screen from){
		this.f=from;
	}
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
		g.drawTexture(block, CT.gC().h_b_x, CT.gC().h_b_y);
		
		LFont old = g.getFont();
		LFont font=LFont.getFont(CT.gC().h_c_h0/2);
		g.setFont(font);
		g.setColor(LColor.blue);
		g.drawString(
				helpStr,
				CT.gC().h_c_x + (CT.gC().h_c_w - font.stringWidth(helpStr)) / 2,
				CT.gC().h_c_y + (CT.gC().h_c_h0 - font.getLineHeight()) / 2
						+ font.getLineHeight());
		g.setFont(old);
		g.resetColor();
		
	}
	
	
	
	@Override
	public void onLoad() {
		
		LTexture[] btn7={LTextures.loadTexture(CT.gC().topbar_btn_fanhui1),LTextures.loadTexture(CT.gC().topbar_btn_fanhui2)};
		fanhui=new  LButton(btn7, null, btn7[0].getWidth(), btn7[0].getHeight(), CT.gC().topbar_btn_bangzhu_x, CT.gC().topbar_btn_yuezhan_y){
			@Override
			public void doClick(){
				replaceScreen(f, MoveMethod.OUT_RIGHT);
			}
		};
		block=LTextures.loadTexture(CT.gC().help_block);
		
		msg=new LMessage(CT.gC().h_c_w, CT.gC().h_c_h);
		msg.setLocation(CT.gC().h_c_x, CT.gC().h_c_y2);
		msg.setFontColor(LColor.blue);
		msg.setMessageFont(LFont.getFont(CT.gC().h_c_h0/3));
		msg.setMessage(helpContent, true);
		msg.setMessageLength(19);
		setBackground(CT.gC().bg_001_light);
		add(fanhui);
		add(msg);
		
	}

	@Override
	public void touchDrag(LTouch e) {
		// TODO Auto-generated method stub
		
	}

}
