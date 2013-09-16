package com.mogu.game.tetris.screen;

import loon.core.graphics.LImage;
import loon.core.graphics.device.LGraphics;
import loon.core.graphics.opengl.GLEx;
import loon.core.graphics.opengl.LTexture;

public class TetrisField {
	private Block stoneCurrent;

	private Block stoneNext;

	private int[][] stonePosition;

	private int[][] gameFieldStones;

	private int curLines = 0, curLevel = 1, curPoints = 0, row, col;

	private boolean gameOver = false;

	public TetrisField(int row, int col) {
		int i = 0;
		int j = 0;
		this.row = row;
		this.col = col;
		stonePosition = new int[4][2];
		gameFieldStones = new int[row][col];
		for (i = 0; i < row; i++) {
			for (j = 0; j < col; j++) {
				gameFieldStones[i][j] = 0;
			}
		}
	}

	public int getCol() {
		return col;
	}

	public int getRow() {
		return row;
	}

	public void createCurrentStone(int NextStoneID) {
		stoneCurrent = stoneNext;

		createNextStone(NextStoneID);
		stonePosition = stoneCurrent.getStartPosition();

		if (gameFieldStones[5][4] != 0) {
			gameOver = true;
		}

		setCurrentStonePosition(stonePosition);
	}

	public void createNextStone(int stoneID) {
		stoneNext = new Block(stoneID);
	}

	public void setCurrentStonePosition(int[][] StoneNewPosition) {
		int i;
		for (i = 0; i < 4; i++) {
			gameFieldStones[StoneNewPosition[i][0]][StoneNewPosition[i][1]] = stoneCurrent
					.getBlockID();
		}
	}

	public void setCurrentStonePosition(int[][] StoneNewPosition,
			int[][] StoneOldPosition) {
		int i;
		for (i = 0; i < 4; i++) {
			gameFieldStones[StoneOldPosition[i][0]][StoneOldPosition[i][1]] = 0;
		}
		for (i = 0; i < 4; i++) {
			gameFieldStones[StoneNewPosition[i][0]][StoneNewPosition[i][1]] = stoneCurrent
					.getBlockID();
		}
		stonePosition = StoneNewPosition;
	}

	public boolean incrementPositionY(boolean isThread) {
		int i;
		int[][] StoneNewPosition = new int[4][2];
		int canStart = 0;

		for (i = 0; i < 4; i++) {
			if (stonePosition[i][1] > 3) {
				canStart = 1;
			}
		}
		if (canStart == 1 || isThread) {
			for (i = 0; i < 4; i++) {
				StoneNewPosition[i][1] = stonePosition[i][1] + 1;
				StoneNewPosition[i][0] = stonePosition[i][0];
			}

			if (!hasCollision(StoneNewPosition)) {
				setCurrentStonePosition(StoneNewPosition, stonePosition);
				return true;
			} else {
				setCurrentStonePosition(stonePosition);
				return false;
			}
		}
		return false;
	}

	public void rightPositionX() {
		int i;
		int[][] StoneNewPosition = new int[4][2];
		int canStart = 0;

		for (i = 0; i < 4; i++) {
			if (stonePosition[i][1] > 3) {
				canStart = 1;
			}
		}

		if (canStart == 1) {
			for (i = 0; i < 4; i++) {
				StoneNewPosition[i][1] = stonePosition[i][1];
				StoneNewPosition[i][0] = stonePosition[i][0] + 1;
			}

			if (!hasCollision(StoneNewPosition)) {
				setCurrentStonePosition(StoneNewPosition, stonePosition);
			} else {
				setCurrentStonePosition(stonePosition);
			}
		}
	}

	public void leftPositionX() {
		int i;
		int[][] StoneNewPosition = new int[4][2];
		int canStart = 0;

		for (i = 0; i < 4; i++) {
			if (stonePosition[i][1] > 3) {
				canStart = 1;
			}
		}

		if (canStart == 1) {
			for (i = 0; i < 4; i++) {
				StoneNewPosition[i][1] = stonePosition[i][1];
				StoneNewPosition[i][0] = stonePosition[i][0] - 1;
			}

			if (!hasCollision(StoneNewPosition)) {
				setCurrentStonePosition(StoneNewPosition, stonePosition);
			} else {
				setCurrentStonePosition(stonePosition);
			}
		}
	}

	public void rotateStone() {
		int[][] StoneNewPosition = new int[4][2];

		StoneNewPosition = stoneCurrent.rotateStone(stonePosition);

		if (!hasCollision(StoneNewPosition)) {
			setCurrentStonePosition(StoneNewPosition, stonePosition);
		} else {
			stoneCurrent.noRoate();
			setCurrentStonePosition(stonePosition);
		}
	}

	public boolean hasCollision(int[][] StoneNewPosition) {
		int i;

		for (i = 0; i < 4; i++) {
			gameFieldStones[stonePosition[i][0]][stonePosition[i][1]] = 0;
		}

		for (i = 0; i < 4; i++) {
			if (StoneNewPosition[i][0] < 0) {
				return true;
			} else if (StoneNewPosition[i][0] == row) {
				return true;
			} else if (StoneNewPosition[i][1] == col) {
				return true;
			} else if (gameFieldStones[StoneNewPosition[i][0]][StoneNewPosition[i][1]] != 0) {
				return true;
			}
		}

		return false;
	}

	public boolean hasLines() {
		int i = 0;
		int j = 0;
		int[] lines = new int[4];
		int Quantity = 0;
		boolean isLine = false;

		for (i = 3; i < col; i++) {
			for (j = 0; j < row; j++) {
				isLine = true;
				if (gameFieldStones[j][i] == 0) {
					isLine = false;
					break;
				}
			}

			if (isLine == true) {
				lines[Quantity] = i;
				Quantity++;
			}
		}

		if (Quantity > 0) {
			int[][] TempGameField = new int[row][col];
			int sum = 0;
			curLines += Quantity;
			curLevel = Math.round(curLines / row) + 1;

			// ���ȼ�Ϊ20
			if (curLevel > 20) {
				curLevel = 20;
			}

			curPoints += Math.pow((row * curLevel), Quantity);

			for (i = col - 1; i > 3; i--) {
				for (j = 0; j < row; j++) {
					isLine = true;
					if (gameFieldStones[j][i] == 0) {
						isLine = false;
						break;
					}
				}
				if (isLine == false) {
					for (j = 0; j < row; j++) {
						TempGameField[j][i + sum] = gameFieldStones[j][i];
					}
				} else {
					sum++;
				}
			}

			for (i = 0; i < 4; i++) {
				for (j = 0; j < row; j++) {
					TempGameField[j][i] = gameFieldStones[j][i];
				}
			}

			gameFieldStones = TempGameField;

			return true;
		} else {
			return false;
		}
	}

	public int[][] getStonePosition() {

		return gameFieldStones;
	}

	public void draw(GLEx g, LTexture[] stones,float x,float y,float w,float h,int b) {
		int nextStone = getNextStone();
		switch (nextStone) {
		case 1:
			g.drawTexture(stones[1], x+(w-b*2)/2, y+(h-b*2)/2);
			g.drawTexture(stones[1], x+(w-b*2)/2+b, y+(h-b*2)/2);
			g.drawTexture(stones[1], x+(w-b*2)/2, y+(h-b*2)/2+b);
			g.drawTexture(stones[1], x+(w-b*2)/2+b, y+(h-b*2)/2+b);
			break;
		case 2:
			g.drawTexture(stones[2],x+(w-b*4)/2, y+(h-b)/2);
			g.drawTexture(stones[2],x+(w-b*4)/2+b, y+(h-b)/2);
			g.drawTexture(stones[2],x+(w-b*4)/2+b+b, y+(h-b)/2);
			g.drawTexture(stones[2],x+(w-b*4)/2+b+b+b, y+(h-b)/2);
			break;
		case 3:
			g.drawTexture(stones[3], x+(w-b*3)/2+b, y+(h-b*2)/2);
			g.drawTexture(stones[3], x+(w-b*3)/2, y+(h-b*2)/2+b);
			g.drawTexture(stones[3], x+(w-b*3)/2+b, y+(h-b*2)/2+b);
			g.drawTexture(stones[3], x+(w-b*3)/2+b+b, y+(h-b*2)/2+b);
			break;
		case 4:
			g.drawTexture(stones[4], x+(w-b*3)/2+b+b, y+(h-b*2)/2);
			g.drawTexture(stones[4], x+(w-b*3)/2, y+(h-b*2)/2+b);
			g.drawTexture(stones[4], x+(w-b*3)/2+b, y+(h-b*2)/2+b);
			g.drawTexture(stones[4], x+(w-b*3)/2+b+b, y+(h-b*2)/2+b);
			break;
		case 5:
			g.drawTexture(stones[5], x+(w-b*3)/2, y+(h-b*2)/2);
			g.drawTexture(stones[5], x+(w-b*3)/2, y+(h-b*2)/2+b);
			g.drawTexture(stones[5], x+(w-b*3)/2+b, y+(h-b*2)/2+b);
			g.drawTexture(stones[5], x+(w-b*3)/2+b+b, y+(h-b*2)/2+b);
			break;
		case 6:
			g.drawTexture(stones[6], x+(w-b*3)/2, y+(h-b*2)/2);
			g.drawTexture(stones[6], x+(w-b*3)/2+b, y+(h-b*2)/2);
			g.drawTexture(stones[6], x+(w-b*3)/2+b, y+(h-b*2)/2+b);
			g.drawTexture(stones[6], x+(w-b*3)/2+b+b, y+(h-b*2)/2+b);
			break;
		case 7:
			g.drawTexture(stones[7], x+(w-b*3)/2+b, y+(h-b*2)/2);
			g.drawTexture(stones[7], x+(w-b*3)/2+b+b, y+(h-b*2)/2);
			g.drawTexture(stones[7], x+(w-b*3)/2, y+(h-b*2)/2+b);
			g.drawTexture(stones[7], x+(w-b*3)/2+b, y+(h-b*2)/2+b);
			break;
		}
	}

	public int getNextStone() {
		if (stoneNext == null) {
			stoneNext = new Block((int) Math.round(Math.random() * 6) + 1);
		}
		return stoneNext.getBlockID();
	}

	public int getLines() {
		return curLines;
	}

	public int getLevel() {
		return curLevel;
	}

	public int getPoints() {
		return curPoints;
	}

	public boolean isGameOver() {
		return gameOver;
	}

	class Block {

		private int blockID;

		private int[][] startPosition = new int[4][2];

		private int rotatePosition = 0;

		public Block(int blockID) {
			setBlockID(blockID);
			createStone();
		}

		private void createStone() {
			switch (blockID) {
			case 1:
				startPosition[0][0] = 4;
				startPosition[0][1] = 2;
				startPosition[1][0] = 5;
				startPosition[1][1] = 2;
				startPosition[2][0] = 4;
				startPosition[2][1] = 3;
				startPosition[3][0] = 5;
				startPosition[3][1] = 3;
				break;
			case 2:
				startPosition[0][0] = 5;
				startPosition[0][1] = 0;
				startPosition[1][0] = 5;
				startPosition[1][1] = 1;
				startPosition[2][0] = 5;
				startPosition[2][1] = 2;
				startPosition[3][0] = 5;
				startPosition[3][1] = 3;
				break;
			case 3:
				startPosition[0][0] = 5;
				startPosition[0][1] = 2;
				startPosition[1][0] = 4;
				startPosition[1][1] = 3;
				startPosition[2][0] = 5;
				startPosition[2][1] = 3;
				startPosition[3][0] = 6;
				startPosition[3][1] = 3;
				break;
			case 4:
				startPosition[0][0] = 4;
				startPosition[0][1] = 1;
				startPosition[1][0] = 4;
				startPosition[1][1] = 2;
				startPosition[2][0] = 4;
				startPosition[2][1] = 3;
				startPosition[3][0] = 5;
				startPosition[3][1] = 3;
				break;
			case 5:
				startPosition[0][0] = 5;
				startPosition[0][1] = 1;
				startPosition[1][0] = 5;
				startPosition[1][1] = 2;
				startPosition[2][0] = 5;
				startPosition[2][1] = 3;
				startPosition[3][0] = 4;
				startPosition[3][1] = 3;
				break;
			case 6:
				startPosition[0][0] = 5;
				startPosition[0][1] = 1;
				startPosition[1][0] = 4;
				startPosition[1][1] = 2;
				startPosition[2][0] = 5;
				startPosition[2][1] = 2;
				startPosition[3][0] = 4;
				startPosition[3][1] = 3;
				break;
			case 7:
				startPosition[0][0] = 4;
				startPosition[0][1] = 1;
				startPosition[1][0] = 4;
				startPosition[1][1] = 2;
				startPosition[2][0] = 5;
				startPosition[2][1] = 2;
				startPosition[3][0] = 5;
				startPosition[3][1] = 3;
				break;
			}
		}

		public int[][] rotateStone(int[][] currentPosition) {
			int[][] newPosition = new int[4][2];
			int x, y;
			switch (blockID) {
			case 2:
				if (rotatePosition == 0) {
					x = currentPosition[3][0];
					y = currentPosition[3][1];
					newPosition[0][0] = x - 3;
					newPosition[0][1] = y;
					newPosition[1][0] = x - 2;
					newPosition[1][1] = y;
					newPosition[2][0] = x - 1;
					newPosition[2][1] = y;
					newPosition[3][0] = x;
					newPosition[3][1] = y;
					rotatePosition = 1;
				} else {
					x = currentPosition[3][0];
					y = currentPosition[3][1];
					newPosition[0][0] = x;
					newPosition[0][1] = y - 3;
					newPosition[1][0] = x;
					newPosition[1][1] = y - 2;
					newPosition[2][0] = x;
					newPosition[2][1] = y - 1;
					newPosition[3][0] = x;
					newPosition[3][1] = y;
					rotatePosition = 0;
				}
				return newPosition;
			case 3:
				if (rotatePosition == 0) {
					x = currentPosition[2][0];
					y = currentPosition[2][1];
					newPosition[0][0] = x;
					newPosition[0][1] = y - 1;
					newPosition[1][0] = x;
					newPosition[1][1] = y + 1;
					newPosition[2][0] = x;
					newPosition[2][1] = y;
					newPosition[3][0] = x + 1;
					newPosition[3][1] = y;
					rotatePosition = 1;
				} else if (rotatePosition == 1) {
					x = currentPosition[2][0];
					y = currentPosition[2][1];
					newPosition[0][0] = x;
					newPosition[0][1] = y + 1;
					newPosition[1][0] = x - 1;
					newPosition[1][1] = y;
					newPosition[2][0] = x;
					newPosition[2][1] = y;
					newPosition[3][0] = x + 1;
					newPosition[3][1] = y;
					rotatePosition = 2;
				} else if (rotatePosition == 2) {
					x = currentPosition[2][0];
					y = currentPosition[2][1];
					newPosition[0][0] = x;
					newPosition[0][1] = y - 1;
					newPosition[1][0] = x - 1;
					newPosition[1][1] = y;
					newPosition[2][0] = x;
					newPosition[2][1] = y;
					newPosition[3][0] = x;
					newPosition[3][1] = y + 1;
					rotatePosition = 3;
				} else if (rotatePosition == 3) {
					x = currentPosition[2][0];
					y = currentPosition[2][1];
					newPosition[0][0] = x - 1;
					newPosition[0][1] = y;
					newPosition[1][0] = x + 1;
					newPosition[1][1] = y;
					newPosition[2][0] = x;
					newPosition[2][1] = y;
					newPosition[3][0] = x;
					newPosition[3][1] = y - 1;
					rotatePosition = 0;
				}
				return newPosition;
			case 4:
				if (rotatePosition == 0) {
					x = currentPosition[2][0];
					y = currentPosition[2][1];
					newPosition[0][0] = x;
					newPosition[0][1] = y;
					newPosition[1][0] = x + 1;
					newPosition[1][1] = y;
					newPosition[2][0] = x + 2;
					newPosition[2][1] = y;
					newPosition[3][0] = x + 2;
					newPosition[3][1] = y - 1;
					rotatePosition = 1;
				} else if (rotatePosition == 1) {
					x = currentPosition[2][0];
					y = currentPosition[2][1];
					newPosition[0][0] = x - 1;
					newPosition[0][1] = y - 2;
					newPosition[1][0] = x;
					newPosition[1][1] = y - 2;
					newPosition[2][0] = x;
					newPosition[2][1] = y - 1;
					newPosition[3][0] = x;
					newPosition[3][1] = y;
					rotatePosition = 2;
				} else if (rotatePosition == 2) {
					x = currentPosition[1][0];
					y = currentPosition[1][1];
					newPosition[0][0] = x - 2;
					newPosition[0][1] = y + 1;
					newPosition[1][0] = x - 2;
					newPosition[1][1] = y;
					newPosition[2][0] = x - 1;
					newPosition[2][1] = y;
					newPosition[3][0] = x;
					newPosition[3][1] = y;
					rotatePosition = 3;
				} else if (rotatePosition == 3) {
					x = currentPosition[1][0];
					y = currentPosition[1][1];
					newPosition[0][0] = x;
					newPosition[0][1] = y;
					newPosition[1][0] = x;
					newPosition[1][1] = y + 1;
					newPosition[2][0] = x;
					newPosition[2][1] = y + 2;
					newPosition[3][0] = x + 1;
					newPosition[3][1] = y + 2;
					rotatePosition = 0;
				}
				return newPosition;
			case 5:
				if (rotatePosition == 0) {
					x = currentPosition[1][0];
					y = currentPosition[1][1];
					newPosition[0][0] = x - 2;
					newPosition[0][1] = y - 1;
					newPosition[1][0] = x - 1;
					newPosition[1][1] = y - 1;
					newPosition[2][0] = x;
					newPosition[2][1] = y - 1;
					newPosition[3][0] = x;
					newPosition[3][1] = y;
					rotatePosition = 1;
				} else if (rotatePosition == 1) {
					x = currentPosition[0][0];
					y = currentPosition[0][1];
					newPosition[0][0] = x;
					newPosition[0][1] = y;
					newPosition[1][0] = x + 1;
					newPosition[1][1] = y;
					newPosition[2][0] = x;
					newPosition[2][1] = y + 1;
					newPosition[3][0] = x;
					newPosition[3][1] = y + 2;
					rotatePosition = 2;
				} else if (rotatePosition == 2) {
					x = currentPosition[2][0];
					y = currentPosition[2][1];
					newPosition[0][0] = x;
					newPosition[0][1] = y;
					newPosition[1][0] = x;
					newPosition[1][1] = y + 1;
					newPosition[2][0] = x + 1;
					newPosition[2][1] = y + 1;
					newPosition[3][0] = x + 2;
					newPosition[3][1] = y + 1;
					rotatePosition = 3;
				} else if (rotatePosition == 3) {
					x = currentPosition[2][0];
					y = currentPosition[2][1];
					newPosition[0][0] = x;
					newPosition[0][1] = y;
					newPosition[1][0] = x + 1;
					newPosition[1][1] = y;
					newPosition[2][0] = x + 1;
					newPosition[2][1] = y - 1;
					newPosition[3][0] = x + 1;
					newPosition[3][1] = y - 2;
					rotatePosition = 0;
				}
				return newPosition;
			case 6:
				if (rotatePosition == 0) {
					x = currentPosition[2][0];
					y = currentPosition[2][1];
					newPosition[0][0] = x - 1;
					newPosition[0][1] = y;
					newPosition[1][0] = x;
					newPosition[1][1] = y + 1;
					newPosition[2][0] = x;
					newPosition[2][1] = y;
					newPosition[3][0] = x + 1;
					newPosition[3][1] = y + 1;
					rotatePosition = 1;
				} else {
					x = currentPosition[2][0];
					y = currentPosition[2][1];

					newPosition[0][0] = x;
					newPosition[0][1] = y - 1;
					newPosition[1][0] = x - 1;
					newPosition[1][1] = y;
					newPosition[2][0] = x;
					newPosition[2][1] = y;
					newPosition[3][0] = x - 1;
					newPosition[3][1] = y + 1;
					rotatePosition = 0;
				}
				return newPosition;
			case 7:
				if (rotatePosition == 0) {
					x = currentPosition[2][0];
					y = currentPosition[2][1];

					newPosition[0][0] = x - 1;
					newPosition[0][1] = y - 1;
					newPosition[1][0] = x - 1;
					newPosition[1][1] = y;
					newPosition[2][0] = x;
					newPosition[2][1] = y;
					newPosition[3][0] = x;
					newPosition[3][1] = y + 1;
					rotatePosition = 1;
				} else {
					x = currentPosition[2][0];
					y = currentPosition[2][1];

					newPosition[0][0] = x + 1;
					newPosition[0][1] = y;
					newPosition[1][0] = x - 1;
					newPosition[1][1] = y + 1;
					newPosition[2][0] = x;
					newPosition[2][1] = y;
					newPosition[3][0] = x;
					newPosition[3][1] = y + 1;
					rotatePosition = 0;
				}
				return newPosition;
			default:
				return currentPosition;

			}

		}

		public void noRoate() {
			rotatePosition--;
			if (rotatePosition == -1) {
				rotatePosition = 3;
			}
		}

		public int[][] getStartPosition() {
			return startPosition;
		}

		public void setBlockID(int blockID) {
			this.blockID = blockID;
		}

		public int getBlockID() {
			return blockID;
		}
	}

}