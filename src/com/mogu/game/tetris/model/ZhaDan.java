package com.mogu.game.tetris.model;

import com.mogu.game.tetris.config.CT;
import com.mogu.game.tetris.screen.TetrisField;

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
	public void commit(GLEx g,TetrisField gameField) {
		// TODO Auto-generated method stub
		int x=0, y=0;
		boolean flag=false;
		// 默认游戏区域（网格）大小为横10,竖24
		int[][] arrayStones = gameField.getStonePosition();
		for (; x <gameField.getRow(); x++) {
			for (; y <gameField.getCol(); y++) {
				
				if (arrayStones[x][y] != 0) {
					flag=true;
					break;
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
						arrayStones[m][k]=0;
					}
				}
				if(flag){
					break;
				}
			}
		}
		
		this.setUsed(2);
		
	}

}
