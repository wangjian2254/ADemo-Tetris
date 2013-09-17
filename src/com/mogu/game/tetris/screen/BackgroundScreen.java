package com.mogu.game.tetris.screen;

import com.mogu.game.tetris.config.ConfigTool;

import loon.action.sprite.SpriteBatch;
import loon.core.graphics.opengl.LTexture;
import loon.core.graphics.opengl.LTextures;
import loon.core.timer.GameTime;

public class BackgroundScreen extends GameScreen {

	private LTexture background;

//	private String backgroundName;

	public BackgroundScreen() {
		super.setTransitionOnTime(0f);
		super.setTransitionOffTime(0.5f);
//		this.backgroundName = backgroundName;
	}

	@Override
	public void draw(SpriteBatch batch, GameTime gameTime) {
		batch.draw(this.background, 0f, 0f,
				PoolColor.getColor(1f, 1f, 1f, getTransitionAlpha()));
	}

	@Override
	public void LoadContent() {
		this.background = LTextures.loadTexture(ConfigTool.getConfig().bg_001_light);
	}

	@Override
	public void Update(GameTime gameTime, boolean otherScreenHasFocus,
			boolean coveredByOtherScreen) {
		super.Update(gameTime, otherScreenHasFocus, false);
	}
}