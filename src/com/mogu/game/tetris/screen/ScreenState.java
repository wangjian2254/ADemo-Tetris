package com.mogu.game.tetris.screen;

public enum ScreenState
{
	TransitionOn,
	Active,
	TransitionOff,
	Hidden;

	public int getValue()
	{
		return this.ordinal();
	}

	public static ScreenState forValue(int value)
	{
		return values()[value];
	}
}