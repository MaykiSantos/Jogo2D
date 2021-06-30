package com.solitario.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.solitario.main.Game;
import com.solitario.word.Camera;
import com.solitario.word.Word;

public class EntityBala extends Entity{
	
	public double velocidade = 4.3;
	public int dano = 20;
	public double dx, dy;
	private int tempo = 0 , maxtempo = 40;

	public EntityBala(int x, int y, int width, int heigth, BufferedImage sprite) {
		super(x, y, width, heigth, sprite);
	}
	
	public void colisaoTiro() {
		Rectangle tiro = new Rectangle(this.maskX - Camera.x, this.maskY - Camera.y, this.maskW, this.maskH);
		
		for(int i =0; i < Game.inimigos.size(); i++) {
			EntityInimigo inimigo = Game.inimigos.get(i);
			Rectangle inimigoBox = new Rectangle((int)inimigo.getX() - Camera.x, (int)inimigo.getY() - Camera.y, inimigo.maskW, inimigo.maskH);
			
			if(tiro.intersects(inimigoBox)) {				
				inimigo.vida-= this.dano;
				Game.balas.remove(this);
			}
			if(!Word.isFree(this.maskX, this.maskY)) {
				Game.balas.remove(this);
			}
		}
		//return false;
		
	}
	
	public void tick() {
		this.tempo++;
		this.maskX+= this.dx*this.velocidade;
		this.maskY+= this.dy*this.velocidade;
		
		this.colisaoTiro();
		System.out.println(Game.inimigos.size());
		
		if(this.tempo == this.maxtempo) {
			Game.balas.remove(this);
			return;
		}
	}
	
	public void render(Graphics g) {
		g.setColor(new Color(255, 0, 0));
		g.fillOval(this.maskX - Camera.x+16, this.maskY - Camera.y+19, this.getWidth(), this.getHeigth());
		
	}

	
}
