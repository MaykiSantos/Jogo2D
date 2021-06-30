package com.solitario.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.solitario.main.Game;
import com.solitario.word.Camera;

public class EntityArma extends Entity {
	
	private int frame = 0, maxFrame = 40, indice = 0, maxIndice = 1;
	BufferedImage spriteAtivo;
	BufferedImage[] sprites;

	public EntityArma(int x, int y, int width, int heigth, BufferedImage sprite) {
		super(x, y, width, heigth, sprite);
		
		this.sprites = new BufferedImage[2];
		this.sprites[0] = Game.spritesheet.getSprite(288, 160, 32, 32);
		this.sprites[1] = Game.spritesheet.getSprite(288, 192, 32, 32);
	}
	
	public void tick() {

		this.frame++;
		if(this.frame == this.maxFrame) {
			this.spriteAtivo = this.sprites[this.indice];
			this.indice++;
			this.frame = 0;
		}
		//pega arma
		if(this.colidindoComJogador()) {
			int i = Game.entidades.indexOf(this);
			Game.jogador.pegouArma = true;
			Game.entidades.remove(i);
			Game.jogador.poderAtivo();
			System.out.println("pegou arma!!");
		}
		
		if(this.indice > this.maxIndice) {
			this.indice=0;
		}
		
	}
	
	
	public void render(Graphics g) {
		g.drawImage(this.spriteAtivo, (int)this.maskX - Camera.x, (int)this.maskY - Camera.y, null);
		//g.setColor(new Color(255, 0, 0));
		//g.drawRect((int)this.maskX - Camera.x, (int)this.maskY - Camera.y, 32, 32);
	}

}
