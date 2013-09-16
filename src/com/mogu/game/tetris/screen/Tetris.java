package com.mogu.game.tetris.screen;




import loon.core.graphics.LColor;
import loon.core.graphics.Screen;
import loon.core.graphics.opengl.GLEx;
import loon.core.graphics.opengl.LTexture;
import loon.core.input.LTouch;
import loon.core.timer.LTimer;
import loon.core.timer.LTimerContext;

import com.mogu.game.tetris.config.ConfigTool;




public class Tetris extends Screen {
	

	private int curLevel = 1;

	private boolean gameStart=false;

//	private final static LColor emptyColor = new LColor(120, 120, 190,90),
//			gridColor = new LColor(255, 255, 255,25);

	private LTimer delay;

	private TetrisField gameField;

	private LTexture[] stones = new LTexture[9];
	private LTexture[] stonesMin = new LTexture[9];

	private LTexture  styleImage,brand;

//	public int xm=Config.getW(106+26);
//	public int ym=Config.getH(74+26);
	public float positionX;

	public float positionY;
	public boolean move=false;
	
	public int jiemian=0;
	
	public MainMenu mainMenu=null;
	
	public Tetris() {
		
		
//		showJieMian();
	}
	
	public void showJieMian(){
		switch (jiemian) {
		case 0:
			if(mainMenu==null){
				mainMenu=new MainMenu();
			}
			setScreen(mainMenu);
			
			break;

		case 1:
			setScreen(this);
			break;
		}
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
			}else if(gameField!=null&&gameField.isGameOver()){
				if(jiemian==0){
					replaceScreen(new MainMenu(), MoveMethod.FROM_LEFT);
//					setScreen(new MainMenu());
					jiemian=1;
				}
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
			
//		g.drawTexture(background, 0,0);
		g.drawTexture(brand, ConfigTool.getConfig().g_board_x,ConfigTool.getConfig().g_board_y);
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
						
						g.drawTexture(stones[arrayStones[x][y]], x * ConfigTool.getConfig().blockSize+ConfigTool.getConfig().g_board_x_m, y * ConfigTool.getConfig().blockSize+ConfigTool.getConfig().g_board_y_m);
					}
				}
			}
			if (gameField.isGameOver()) {
				g.setColor(LColor.white);
				g.drawString("GAME OVER", 120, 160);
				return;
			}
			g.drawTexture(styleImage, ConfigTool.getConfig().g_btn_tool0_x, ConfigTool.getConfig().g_btn_tool0_y);
			// 绘制游戏方块
			gameField.draw(g, stonesMin,ConfigTool.getConfig().g_btn_tool0_x,ConfigTool.getConfig().g_btn_tool0_y,styleImage.getWidth(),styleImage.getHeight(),ConfigTool.getConfig().blockSizeMin);
			
			g.setColor(LColor.white);
			g.drawString("等级:" + Integer.toString(gameField.getLevel()), ConfigTool.getConfig().all_w-80,
					220);
			g.drawString("消层:" + Integer.toString(gameField.getLines()), ConfigTool.getConfig().all_w-80,
					260);
			g.drawString("得分:" + Integer.toString(gameField.getPoints()), ConfigTool.getConfig().all_w-80,
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
		if(Math.abs(e.getX()-positionX)>ConfigTool.getConfig().movepoint){
//			int num=(int)(Math.abs(e.getX()-positionX)-ConfigTool.getConfig().movepoint)/ConfigTool.getConfig().movepoint;
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

	@Override
	public void onLoad() {
		// 背景图
//		background = new LTexture(ConfigTool.getConfig().bg_001_light);
		brand = new LTexture(ConfigTool.getConfig().game_board);
		// 提示背景
		styleImage = new LTexture(ConfigTool.getConfig().game_btn_tools1);
		LTexture b=new LTexture(ConfigTool.getConfig().pic_fangkuai_highlight);
		LTexture b2=new LTexture(ConfigTool.getConfig().pic_fangkuai_med);
		// 俄罗斯方块小图
//				LImage[] blocks = new LTexture(8, blockSize, blockSize, true);
		for (int i = 0; i < 8; i++) {
			stones[i + 1] = b.getSubTexture(i*ConfigTool.getConfig().blockSize, 0, ConfigTool.getConfig().blockSize, ConfigTool.getConfig().blockSize);
			stonesMin[i + 1] = b2.getSubTexture(i*ConfigTool.getConfig().blockSizeMin, 0, ConfigTool.getConfig().blockSizeMin, ConfigTool.getConfig().blockSizeMin);
		}
		delay = new LTimer(100);
		setBackground(ConfigTool.getConfig().bg_001_light);
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
