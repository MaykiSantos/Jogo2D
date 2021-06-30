package com.solitario.word;

public class Camera {
	
	public static int x;
	public static int y;
	
	public static int clamp(int max, int min, int x) {
		if(x <= min) {
			x = min;
		}
		
		if(x >= max) {
			x = max;
		}
		
		return x;
	}

	
	public void setX(int newX) {
		this.x = newX;
	}
	
	public void setY(int newY) {
		this.y = newY;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	
	
}
