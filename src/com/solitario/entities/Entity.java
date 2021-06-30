package com.solitario.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.solitario.main.Game;
import com.solitario.word.Camera;

public class Entity {
	
	public static BufferedImage EN_VIDA = Game.spritesheet.getSprite(288, 96, 32, 32);
	public static BufferedImage EN_ARMA = Game.spritesheet.getSprite(288, 192, 32, 32);
	public static BufferedImage EN_MUNICAO = Game.spritesheet.getSprite(288, 128, 32, 32);
	public static BufferedImage EN_INIMIGO = Game.spritesheet.getSprite(288, 32, 32, 32);
	
	private double x;
	private double y;
	private int width;
	private int heigth;
	
	protected int maskX, maskY, maskW, maskH;
	
	protected BufferedImage sprite;
	
	
	public Entity(int x, int y, int width, int heigth, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.heigth = heigth;
		this.sprite = sprite;
		
		this.maskX = x;
		this.maskY = y;
		this.maskW = width;
		this.maskH = heigth;
	}
	
	public void setMask(int newMaskX, int newMaskY, int newMaskW, int newMaskH) {
		this.maskX = newMaskX;
		this.maskY = newMaskY;
		this.maskW = newMaskW;
		this.maskH = newMaskH;
	}
	
	public boolean colidindoComJogador() {
		Rectangle objeto = new Rectangle((int)this.getX() - Camera.x, (int)this.getY() - Camera.y, this.maskW, 32);
		Rectangle jogador = new Rectangle((int)Game.jogador.getX() - Camera.x, (int)Game.jogador.getY() - Camera.y, Game.jogador.maskW, Game.jogador.maskH);
		
		return objeto.intersects(jogador);
	}
	
	public void tick() {}
	
	public void render(Graphics g) {
		g.drawImage(this.sprite, (int)this.x - Camera.x, (int)this.y - Camera.y, null);
	}
	
	public BufferedImage getSprite() {
		return this.sprite;
	}
	
	public double getX() {
		return this.x;
	}
	
	public double getY() {
		return this.y;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeigth() {
		return this.heigth;
	}
	
	public void setX(double newX) {
		this.x = newX;
	}
	
	public void setY(double newY) {
		this.y = newY;
	}
	
	public void incrementoX(double newX) {
		this.x += newX;
	}
	
	public void decrementoX(double newX) {
		this.x -= newX;
	}
	
	public void incrementoY(double newX) {
		this.y += newX;
	}
	
	public void decrementoY(double newY) {
		this.y -= newY;
	}
	
	
	
	
	
	

}
