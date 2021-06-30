package com.solitario.word;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.solitario.main.Game;

public class Tile {
	
	public static BufferedImage TILE_PAREDE = Game.spritesheet.getSprite(32, 0, 32, 32); //FFFFFF
	
	public static BufferedImage TILE_PAREDE_LATERAIS_LIVRES = Game.spritesheet.getSprite(0, 96, 32, 32); //FF7200
	public static BufferedImage TILE_PAREDE_SUPERIORINFERIOR_LIVRES = Game.spritesheet.getSprite(32, 96, 32, 32); //FF3B14
	public static BufferedImage TILE_PAREDE_CIMA = Game.spritesheet.getSprite(32, 128, 32, 32); //FF006A
	public static BufferedImage TILE_PAREDE_BAIXO = Game.spritesheet.getSprite(0, 128, 32, 32); //FF2B95
	public static BufferedImage TILE_PAREDE_DIREITA = Game.spritesheet.getSprite(64, 128, 32, 32); //FF75E1
	public static BufferedImage TILE_PAREDE_ESQUERDA = Game.spritesheet.getSprite(64, 160, 32, 32); //FF3FC2
	public static BufferedImage TILE_PAREDE_COMPLETO = Game.spritesheet.getSprite(64, 96, 32, 32); //FFA470
	
	public static BufferedImage TILE_CHAO = Game.spritesheet.getSprite(0, 0, 32, 32); //000000
	
	private int x, y;
	private BufferedImage sprite;
	
	public Tile(int x, int y, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;
	}
	
	
	public void render(Graphics g) {
		g.drawImage(sprite, x - Camera.x, y - Camera.y, null);
	}
	

}
