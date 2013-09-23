package com.mogu.game.tetris.model;

import java.util.HashMap;
import java.util.Map;

import android.util.Log;

import com.mogu.game.tetris.config.CT;
import com.mogu.game.tetris.screen.TetrisField;

import loon.core.graphics.opengl.GLEx;
import loon.core.graphics.opengl.LTexture;
import loon.core.graphics.opengl.LTextures;

public class MiWu extends DaoJu {

	private String image;
	private LTexture img;
	public MiWu(){
		this.setType(2);
		this.setName("迷雾");
		this.setTime(4);
		this.setUsed(0);
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
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
		this.img=LTextures.loadTexture(this.image);
	}
	public LTexture getImg() {
		return img;
	}
	public void setImg(LTexture i){
		this.img=i;
	}
	
	public void setPos(float x,float y,int w,int h){
		this.x0=x+(w-this.getImg().getWidth())/2;
		this.y0=y+(h-this.getImg().getHeight())/2;
		this.x1=x;
		this.y1=y;
		this.x2=x+w;
		this.y2=y+h;
	}
	
	public boolean isClick(float x,float y){
		if(x>x1&&x<x2&&y>y1&&y<y2){
			return true;
		}else{
			return false;
		}
	}

}
