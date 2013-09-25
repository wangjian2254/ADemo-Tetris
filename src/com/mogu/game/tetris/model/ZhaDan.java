package com.mogu.game.tetris.model;

import android.util.Log;

import com.mogu.game.tetris.config.CT;
import com.mogu.game.tetris.screen.TetrisField;
import com.mogu.game.tetris.sprite.ZhaDanSprite;

import loon.core.graphics.opengl.GLEx;
import loon.core.graphics.opengl.LTexture;
import loon.core.graphics.opengl.LTextures;

public class ZhaDan extends DaoJu {

	private String image;
	private LTexture img;
	public ZhaDan(){
		this.setType(1);
		this.setName("炸弹");
		this.setTime(2);
		this.setUsed(0);
//		this.setImage(CT.gC().tools_pic_boom);
	}
	@Override
	public void commit(TetrisField gameField) {
		
		
	}
	@Override
	public void commit(GLEx g) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void commit(GLEx g, TetrisField gameField) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void commit(TetrisField gameField, ZhaDanSprite z) {
				int x=0, y=0,j=0;
				boolean flag=false;
				boolean f=false;
				// 默认游戏区域（网格）大小为横10,竖24
				int[][] currentStone = gameField.getStoneCurrent();
				int[][] arrayStones = gameField.getAllStonePosition();
				for (y=0; y <gameField.getCol(); y++) {
					for (x=0; x <gameField.getRow(); x++) {
						
						if (arrayStones[x][y] != 0) {
							f=false;
							for(j=0;j<4;j++){
								if(currentStone[j][0]==x&&currentStone[j][1]==y){
									f=true;
									break;
								}
							}
							if(!f){
								flag=true;
							}
							if(flag){
								break;
							}
							
						}
					}
					if(flag){
						break;
					}
				}
				if(flag){
					int m,k;
					for (m = x-1; m <=x+1; m++) {
						for (k = y-1;k<= y+1;k++) {
							
							if (m>=0&&k>=0&&m<gameField.getRow()&&k<gameField.getCol()&&arrayStones[m][k] != 0) {
								f=false;
								for(j=0;j<4;j++){
									if(currentStone[j][0]==m&&currentStone[j][1]==k){
										f=true;
										
									}
								}
								if(!f){
//									gameField.setStonePositionValue(m,k,0);
									z.setBlock(m, k);
									Log.e("block", m+","+k);
								}
								
							}
						}
					}
				}
				
				this.setUsed(2);
	}
	
	

}
