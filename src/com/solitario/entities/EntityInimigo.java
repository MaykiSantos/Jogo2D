package com.solitario.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.management.remote.SubjectDelegationPermission;

import com.solitario.main.Game;
import com.solitario.word.Camera;
import com.solitario.word.Word;

import ia.AStar;
import ia.Node;
import ia.Vector2i;

public class EntityInimigo extends Entity{

	private double velocidade = 1.5;
	private int frame=0, maxFrame=15, indice=0, maxIndice=1;
	public int vida = 5;
	private BufferedImage[] spriteInimigo;
	private BufferedImage spriteAtivo = this.getSprite();
	
	
	public EntityInimigo(int x, int y, int width, int heigth, BufferedImage sprite) {
		super(x, y, width, heigth, sprite);
		
		this.spriteInimigo = new BufferedImage[2];
		this.spriteInimigo[0] = Game.spritesheet.getSprite(288, 32, 32, 32);
		this.spriteInimigo[1] = Game.spritesheet.getSprite(288, 64, 32, 32);
	}
	
	private double moveInimigo(double x1,double y1,double x2,double y2) {
		return Math.sqrt(((x1 - x2) * (x1 - x2)) + ((y1 - y2) * (y1 - y2)));
	}
	
	
	public void tick() {
		this.frame++;
		
		if(this.frame == this.maxFrame) {
			this.frame = 0;
			this.spriteAtivo = this.spriteInimigo[this.indice];
			this.indice++;
		}
		
		//mata inimigo
		if(this.vida <= 0){
			Game.entidades.remove(this);
			Game.inimigos.remove(this);
		}
		
		if(!this.colidindoComJogador()) {
		
			
			

			if(Game.random.nextInt(100) < 30 && this.moveInimigo(Game.jogador.getX(), Game.jogador.getY(), this.getX(), this.getY())<400) {
				if((int)this.getX() < (int)Game.jogador.getX() &&
						Word.isFree((int)(this.getX() + this.velocidade), (int)this.getY()) &&
						!this.estaColidindo((int)(this.getX() + this.velocidade), (int)this.getY())) {	
					this.incrementoX(this.velocidade);	
				} else if((int)this.getX() > (int)Game.jogador.getX() &&
						Word.isFree((int)(this.getX() - this.velocidade), (int)this.getY()) &&
						!this.estaColidindo((int)(this.getX() - this.velocidade), (int)this.getY())) {
					this.decrementoX(velocidade);
				}
				
				if((int)this.getY() < (int)Game.jogador.getY() &&
						Word.isFree((int)this.getX(), (int)(this.getY() + this.velocidade)) &&
						!this.estaColidindo((int)this.getX(), (int)(this.getY() + this.velocidade))) {
					this.incrementoY(this.velocidade);
				} else if((int)this.getY() > (int)Game.jogador.getY() &&
						Word.isFree((int)this.getX(), (int)(this.getY() - this.velocidade)) &&
						!this.estaColidindo((int)this.getX(), (int)(this.getY() - this.velocidade))) {
					this.decrementoY(this.velocidade);
				}
			}
		}else {
			if(Game.random.nextInt(100) < 30) {
				Game.jogador.vida--;
				Game.jogador.dano = true;
				
				//System.out.println("vida: "+ Game.jogador.vida);
			}
		}
		
		
		if(this.indice > this.maxIndice) {
			this.indice = 0;
		}
		
	}
	
	
	public void animar() {
		this.frame++;
		
		if(this.frame == this.maxFrame) {
			this.frame = 0;
			this.spriteAtivo = this.spriteInimigo[this.indice];
			this.indice++;
		}
		if(this.indice > this.maxIndice) {
			this.indice = 0;
		}
	}
	
	
	public boolean estaColidindo(int novoX, int novoY) {
		
		for(int i = 0; i < Game.inimigos.size(); i++) {
			EntityInimigo outroInimigo = Game.inimigos.get(i);
			if(outroInimigo == this)
				continue;
			Rectangle recOutroInimigo = new Rectangle((int)outroInimigo.getX() - Camera.x, (int)outroInimigo.getY() - Camera.y, this.maskW, this.maskH);
			Rectangle recEsteInimigo = new Rectangle(novoX - Camera.x, novoY - Camera.y, this.maskW, this.maskH);
			if(recEsteInimigo.intersects(recOutroInimigo)) {
				return true;
			}
		}
		
		return false;
	}
	
	
	public void render(Graphics g) {
		g.drawImage(this.spriteAtivo, (int)this.getX() - Camera.x, (int)this.getY() - Camera.y, null);
		// Visualiza bloco de colizão
		//g.setColor(new Color(255, 0, 0));
		//g.drawRect((int)this.getX() - Camera.x+8, (int)this.getY()+16 - Camera.y, this.maskW, this.maskH);
		
	}

}
