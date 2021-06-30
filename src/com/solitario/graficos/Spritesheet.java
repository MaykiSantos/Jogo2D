package com.solitario.graficos;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Spritesheet {
	
	private BufferedImage spritesheet;
	
	public Spritesheet(String arquivo) {
		
		try {			
			this.spritesheet = ImageIO.read(getClass().getResource(arquivo));
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public BufferedImage getSprite(int x, int y, int width, int heigth) {
		return this.spritesheet.getSubimage(x, y, width, heigth);
	}
	
}
	
