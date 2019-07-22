package com.appikot.games.snakelikesapricot;

public class GlobalValue
{
	public final String namespace = "com.appikot.games.snakelikesapricot";
	public int highScore = 0;

	public void reset()
	{
//		userData = new UserData();
		highScore = 0;
	}

	public static GlobalValue self()
	{
		if (instance == null)
		{
			instance = new GlobalValue();
		}
		return instance;
	}
	
	private GlobalValue() 
	{
	}

	private static GlobalValue instance;
}
