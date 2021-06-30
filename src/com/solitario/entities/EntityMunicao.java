package com.solitario.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.solitario.main.Game;
import com.solitario.word.Camera;

public class EntityMunicao extends Entity{

	public EntityMunicao(int x, int y, int width, int heigth, BufferedImage sprite) {
		super(x, y, width, heigth, sprite);
		
	}
	
	public void tick() {

		//pega munição
		if(this.colidindoComJogador() && Game.jogador.pegouArma) {
			int i = Game.entidades.indexOf(this);
			Game.jogador.municao+=20;
			if(Game.jogador.municao>Game.jogador.maxMunicao)
				Game.jogador.municao = 250;
			Game.entidades.remove(i);
		}
		
	}
	
	
	public void render(Graphics g) {
		g.drawImage(this.sprite, (int)this.maskX - Camera.x, (int)this.maskY - Camera.y, null);
		//g.setColor(new Color(255, 0, 0));
		//g.drawRect((int)this.maskX - Camera.x, (int)this.maskY - Camera.y, this.maskW, this.maskH);
	}

}
