package com.mogu.game.tetris.sprite;

import java.util.ArrayList;
import java.util.LinkedList;

import com.mogu.game.tetris.config.CT;
import com.mogu.game.tetris.screen.TetrisField;

import loon.action.sprite.Sprite;

public class ZhaDanSprite extends Sprite {

	private LinkedList<int[]> blocks=new LinkedList<int[]>();
	private int[] p=null;
//	private int q=-1;
	private TetrisField tf=null;
	private boolean f=true;
	public ZhaDanSprite(String n,int w,int h){
		super(n,w,h);
	}
	public void setTF(TetrisField tf){
		this.tf=tf;
	}
//	public void init(){
//		this.p=0;
//		this.q=-1;
//	}
	
	public void setBlock(int x,int y){
		int[] i={x,y};
		blocks.add(i);
		this.getAnimation().setCurrentFrameIndex(0);
	}
	public void update(){
		if(blocks.size()==0&&p==null){
			this.setVisible(false);
			return;
		}else{
			this.setVisible(true);
		}
		if(f&&this.getAnimation().getCurrentFrameIndex()==0){
			p=null;
			if(blocks.size()==0){
				return;
			}
			p=blocks.removeFirst();
			if(tf.getStonePositionValue(p,0)){
				return;
			}
			this.setLocation(p[0] * CT.gC().blockSize+CT.gC().g_board_x_m-CT.gC().zhadanXm, p[1]* CT.gC().blockSize+CT.gC().g_board_y_m-CT.gC().zhadanXm);
			f=false;
		}else if(this.getAnimation().getCurrentFrameIndex()>=3&&!tf.getStonePositionValue(p, 0)){
			tf.setStonePositionValue(p[0], p[1], 0);
			f=true;
		}
		this.update(50);
	}
}
