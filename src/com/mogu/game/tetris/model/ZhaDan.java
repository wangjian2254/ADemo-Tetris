package com.mogu.game.tetris.model;

import com.mogu.game.tetris.config.CT;

import loon.core.graphics.opengl.GLEx;

public class ZhaDan extends DaoJu {

	public ZhaDan(){
		this.setType(1);
		this.setName("炸弹");
		this.setTime(2);
		this.setUsed(0);
		this.setImage(CT.gC().tools_pic_boom);
	}
	@Override
	public void commit(GLEx g) {
		// TODO Auto-generated method stub

	}

}
