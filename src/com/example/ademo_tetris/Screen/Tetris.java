package com.example.ademo_tetris.Screen;



import com.example.ademo_tetris.Config;

import loon.action.sprite.SpriteBatch;
import loon.action.sprite.painting.Drawable;
import loon.action.sprite.painting.DrawableScreen;
import loon.action.sprite.painting.DrawableState;
import loon.core.LSystem;
import loon.core.graphics.LColor;
import loon.core.graphics.LImage;
import loon.core.graphics.Screen;
import loon.core.graphics.opengl.GLEx;
import loon.core.graphics.opengl.LTexture;
import loon.core.input.LInputFactory;
import loon.core.input.LKey;
import loon.core.input.LTouch;
import loon.core.timer.GameTime;
import loon.core.timer.LTimer;
import loon.core.timer.LTimerContext;




public class Tetris extends Screen {
	

	private int curLevel = 1, blockSize1 = 38,blockSize=Config.getW(38),blockSizeMin1=20,blockSizeMin=Config.getW(20);

	private boolean gameStart=false;

//	private final static LColor emptyColor = new LColor(120, 120, 190,90),
//			gridColor = new LColor(255, 255, 255,25);

	private LTimer delay;

	private TetrisField gameField;

	private LTexture[] stones = new LTexture[9];
	private LTexture[] stonesMin = new LTexture[9];

	private LTexture  background,styleImage,brand;

	public int xm=Config.getW(106+26);
	public int ym=Config.getH(74+26);
	public float positionX;

	public float positionY;
	public boolean move=false;
	public int movepoint=(int)Config.suoxiao_width/10;
	
	public Tetris() {
		// 背景图
		background = LImage.createImage("assets/bg_001_light.png").scaledInstance(Config.getW(640), Config.getH(960)).getTexture();
		brand = LImage.createImage("assets/game_board.png").scaledInstance(Config.getW(432), Config.getH(812)).getTexture();
		// 提示背景
		styleImage = LImage.createImage("assets/game_btn_tools1.png").scaledInstance(Config.getW(124), Config.getH(124)).getTexture();
		LImage b=LImage.createImage("assets/pic_fangkuai_highlight.png");
		LImage b2=LImage.createImage("assets/pic_fangkuai_med.png");
		// 俄罗斯方块小图
//		LImage[] blocks = LImage.createImage(8, blockSize, blockSize, true);
		for (int i = 0; i < 8; i++) {
			stones[i + 1] = b.getSubImage(i*blockSize1, 0, blockSize1, blockSize1).scaledInstance(blockSize, blockSize).getTexture();
			stonesMin[i + 1] = b2.getSubImage(i*blockSizeMin1, 0, blockSizeMin1, blockSizeMin1).scaledInstance(blockSizeMin, blockSizeMin).getTexture();
		}
		delay = new LTimer(100);
		initialize();
	}

	public void initialize() {
		if(gameStart){
			return;
		}
		gameStart = true;
		// 默认游戏区域（网格）大小为横10,竖24
		gameField = new TetrisField(10, 20);
		gameField.createNextStone(((int) Math.round(Math.random() * 6) + 1));
		gameField.createCurrentStone(((int) Math.round(Math.random() * 6) + 1));
	}
	
	public void alter(LTimerContext timer) {
		// 自动计时
		if (delay.action(timer.getTimeSinceLastUpdate())) {
			// 已初始化并且非游戏失败
			if (gameStart && !gameField.isGameOver()) {
				if (!gameField.incrementPositionY(true)) {
					gameField.createCurrentStone(((int) Math.round(Math
							.random() * 6) + 1));
					if (gameField.hasLines()) {
						curLevel = gameField.getLevel();
					}
				}
				delay.setDelay(300 / curLevel);
			}
		}
	}

	

	

	/**
	 * 点击键盘
	 */
//	public void onKey(KeyEvent e) {
//		int key = e.getKeyCode();
//	if (gameStart) {
//		// 转换方向
//		if (key == KeyEvent.VK_UP) {
//			gameField.rotateStone();
//			// 向右
//		} else if (key == KeyEvent.VK_RIGHT) {
//			gameField.rightPositionX();
//			// 向左
//		} else if (key == KeyEvent.VK_LEFT) {
//			gameField.leftPositionX();
//			// 加速向下
//		} else if (key == KeyEvent.VK_DOWN) {
//			gameField.incrementPositionY(true);
//			// 重启游戏
//		} else if (key == KeyEvent.VK_ESCAPE) {
//			if (gameField != null && gameField.isGameOver()) {
//				initialize();
//			}
//		}
//	} else {
//		// 开始游戏
//		if (key == KeyEvent.VK_ENTER) {
//			initialize();
//		}
//	}
//
//	}

    

	@Override
	public void draw(GLEx g) {
		if (isOnLoadComplete()) {

		g.drawTexture(background, 0,0);
		g.drawTexture(brand, Config.getW(106),Config.getH(74));
		// TODO Auto-generated method stub
		if (gameStart) {
			
			int x, y;
			// 默认游戏区域（网格）大小为横10,竖24
			int[][] arrayStones = gameField.getStonePosition();
//			g.setColor(emptyColor);
//			g.fillRect(0,0,gameField.getRow() * blockSize, gameField.getCol() * blockSize);
			for (x = 0; x < gameField.getRow(); x++) {
				for (y = 0; y < gameField.getCol(); y++) {
					
//					g.setColor(gridColor);
//					g.drawRect(x * blockSize, y * blockSize, blockSize, blockSize);
					if (arrayStones[x][y] != 0) {
						
						g.drawTexture(stones[arrayStones[x][y]], x * blockSize+xm, y * blockSize+ym);
					}
				}
			}
			if (gameField.isGameOver()) {
				g.setColor(LColor.white);
				g.drawString("GAME OVER", 120, 160);
				return;
			}
			g.drawTexture(styleImage, Config.getW(517), Config.getH(84));
			// 绘制游戏方块
			gameField.draw(g, stonesMin,Config.getW(517),Config.getH(84),styleImage.getWidth(),styleImage.getHeight(),blockSizeMin);
			
			g.setColor(LColor.white);
			g.drawString("等级:" + Integer.toString(gameField.getLevel()), Config.suoxiao_width-80,
					220);
			g.drawString("消层:" + Integer.toString(gameField.getLines()), Config.suoxiao_width-80,
					260);
			g.drawString("得分:" + Integer.toString(gameField.getPoints()), Config.suoxiao_width-80,
					300);
		} else {
			g.setColor(LColor.white);
			g.drawString("GAME START", 110, 160);
			g.drawString("请按下 [ENTER]", 105, 200);
		}
		}
	}

	@Override
	public void touchDown(LTouch e) {
		// TODO Auto-generated method stub
		positionX=e.getX();
		positionY=e.getY();
		move=false;
	}

	@Override
	public void touchUp(LTouch e) {
		// TODO Auto-generated method stub
		if(gameStart&&!gameField.isGameOver()){
			
			if(!move){
				gameField.rotateStone();
			}
			
		}else{
			
			initialize();
		}
		positionX=0;
		positionY=0;
	}

	@Override
	public void touchMove(LTouch e) {
		move=true;
		// TODO Auto-generated method stub
		if(Math.abs(e.getX()-positionX)>movepoint){
			int num=(int)(Math.abs(e.getX()-positionX)-movepoint)/movepoint;
//			for(int i=0;i<num;i++){
				if(e.getX()-positionX>0){
					
					gameField.rightPositionX();
				}else{
					gameField.leftPositionX();
				}
//			}
				positionX=e.getX();
				positionY=e.getY();
		}
		
	}

	@Override
	public void touchDrag(LTouch e) {
		// TODO Auto-generated method stub
		
	}

	

	

//	public static void main(String[] args) {
//		GameScene frame = new GameScene("����˹����", 320, 480);
//		Deploy deploy = frame.getDeploy();
//		deploy.setScreen(new Main());
//		deploy.setShowFPS(true);
//		deploy.setLogo(false);
//		deploy.setFPS(100);
//		deploy.mainLoop();
//		frame.showFrame();
//	}
}
