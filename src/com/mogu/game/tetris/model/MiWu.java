package com.mogu.game.tetris.model;

import loon.core.graphics.opengl.GLEx;
import loon.core.graphics.opengl.LTexture;

import com.mogu.game.tetris.config.CT;
import com.mogu.game.tetris.screen.TetrisField;

public class MiWu extends DaoJu {

	public MiWu(){
		this.setType(2);
		this.setName("迷雾");
		this.setTime(4);
		this.setUsed(0);
//		this.setImage(CT.gC().tools_pic_wu);
	}
	@Override
	public void commit(TetrisField gameField) {
		this.setTime(this.getTime()-1);
		if(this.getTime()<0){
			this.setUsed(2);
		}
		
		
	}
	@Override
	public void commit(GLEx g) {
		g.drawTexture(this.getImg(), CT.gC().g_board_x_m, CT.gC().g_board_y_m);
		
		
	}
	
	@Override
	public void commit(GLEx g, TetrisField gameField) {
		// TODO Auto-generated method stub
		
	}

}
