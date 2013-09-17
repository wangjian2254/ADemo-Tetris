package com.mogu.game.tetris.screen;

public enum Difficulty
{
	Easy,
	Medium,
	Hard;

	public int getValue()
	{
		return this.ordinal();
	}

	public static Difficulty forValue(int value)
	{
		return values()[value];
	}
}