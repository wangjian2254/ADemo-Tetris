package com.mogu.game.tetris.model;

import com.mogu.game.tetris.screen.TetrisField;

import loon.core.graphics.opengl.GLEx;
import loon.core.graphics.opengl.LTexture;
import loon.core.graphics.opengl.LTextures;
import loon.core.timer.LTimer;

public abstract class DaoJu {

	private float x0,y0;//贴图位置
	private float x1,y1,x2,y2;//贴图位置
	
	private LTexture img;
	private String image;
	private LTimer delay;//持续时间
	private int used;//道具状态
	private int time;//道具显示回合
	private String name;//道具名称
	private int type;//道具类型
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	public abstract  void commit(GLEx g,TetrisField gameField);//道具行为
	public abstract  void commit(GLEx g);//道具行为
	public abstract  void commit(TetrisField gameField);//道具行为
	public LTimer getDelay() {
		return delay;
	}
	public void setDelay(LTimer delay) {
		this.delay = delay;
	}
	public int getUsed() {
		return used;
	}
	public void setUsed(int used) {
		this.used = used;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public float getX0() {
		return x0;
	}
	public void setX0(float x0) {
		this.x0 = x0;
	}
	public float getY0() {
		return y0;
	}
	public void setY0(float y0) {
		this.y0 = y0;
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
	
	public void setPos(float x,float y,int w,int h){
		this.x0=x+(w-this.img.getWidth())/2;
		this.y0=y+(h-this.img.getHeight())/2;
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
