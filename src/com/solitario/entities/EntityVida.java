package com.solitario.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.solitario.main.Game;
import com.solitario.word.Camera;

public class EntityVida extends Entity{

	public EntityVida(int x, int y, int width, int heigth, BufferedImage sprite) {
		super(x, y, width, heigth, sprite);
	}
	
	public void tick() {
		
		//pega vida
		if(this.colidindoComJogador()) {
			int i = Game.entidades.indexOf(this);
			Game.jogador.vida+=10;
			if(Game.jogador.vida>Game.jogador.maxVida)
				Game.jogador.vida = 100;
			Game.entidades.remove(i);
		}
		
	}
	
	
	
	public void render(Graphics g) {
		g.drawImage(this.sprite, (int)this.getX() - Camera.x, (int)this.getY() - Camera.y + this.maskH, null);
		//g.setColor(new Color(255, 0, 0));
		//g.drawRect((int)this.getX() - Camera.x, (int)this.getY() - Camera.y + this.maskH, this.maskW, this.maskH);
	}

}
