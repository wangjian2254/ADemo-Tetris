package com.mogu.game.tetris.model;

import com.mogu.game.tetris.config.CT;

import loon.core.graphics.opengl.LTexture;
import loon.core.graphics.opengl.LTextures;

public class DaoJuTools {

	public static LTexture zd=LTextures.loadTexture(CT.gC().tools_pic_boom);
	public static LTexture w=LTextures.loadTexture(CT.gC().tools_pic_wu);
	public static LTexture getImageByType(int type){
		LTexture r=null;
		switch (type) {
		case 1:
			r= zd;
			break;

		case 2:
			r= w;
			break;
		}
		return r;
	}
}
