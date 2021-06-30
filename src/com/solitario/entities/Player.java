package com.solitario.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import com.solitario.graficos.Spritesheet;
import com.solitario.graficos.Ui;
import com.solitario.main.Game;
import com.solitario.word.Camera;
import com.solitario.word.Word;

public class Player extends Entity{
	
	private boolean direita, esquerda, cima, baixo;
	private double velocidade = 2;
	public double mouseX, mouseY;
	
	private int frames = 0, max_frames = 3, indice = 0 , max_indice = 4;
	public int vida = 100;
	public int maxVida = 100;
	public int municao = 0;
	public int maxMunicao = 250;
	private BufferedImage[] spriteDireita;
	private BufferedImage[] spriteEsquerda;
	private BufferedImage[] spriteCima;
	private BufferedImage[] spriteBaixo;
	
	private BufferedImage spriteAtivo = this.getSprite();

	public boolean dano = false;
	public boolean pegouArma = false;
	public boolean atirando = false;
	
	public Player(int x, int y, int width, int heigth, BufferedImage sprite) {
		super(x, y, width, heigth, sprite);
		
		this.spriteDireita = new BufferedImage[6];
		this.spriteEsquerda = new BufferedImage[6];
		this.spriteBaixo = new BufferedImage[6];
		this.spriteCima = new BufferedImage[6];
		
		for(int i =0; i < this.spriteDireita.length;i++) 
			this.spriteDireita[i] = Game.spritesheet.getSprite(96+(32*i), 0, 32, 32);			
		
		for(int i =0; i < this.spriteEsquerda.length;i++) 
			this.spriteEsquerda[i] = Game.spritesheet.getSprite(96+(32*i), 32, 32, 32);
		
		for(int i =0; i < this.spriteBaixo.length;i++)
			this.spriteBaixo[i] = Game.spritesheet.getSprite(96+(32*i), 64, 32, 32);
		
		for(int i =0; i < this.spriteCima.length;i++) 
			this.spriteCima[i] = Game.spritesheet.getSprite(96+(32*i), 96, 32, 32);
		
		
		
	}

	
	public void tick() {
		this.frames++;
		
		if(this.direita && Word.isFree((int)this.getX()+(int)this.getVelocidade(), (int)this.getY())) {
			this.incrementoX(velocidade);
			this.mudaSprite(this.spriteDireita);
				
		}else if(this.esquerda && Word.isFree((int)this.getX()-(int)this.getVelocidade(), (int)this.getY())) {
			this.decrementoX(velocidade);
			this.mudaSprite(this.spriteEsquerda);
		}
		
		if(this.cima && Word.isFree((int)this.getX(), (int)this.getY()-(int)this.getVelocidade())) {
			this.decrementoY(velocidade);
			this.mudaSprite(this.spriteCima);
		}else if(this.baixo && Word.isFree((int)this.getX(), (int)this.getY()+(int)this.getVelocidade())) {
			this.incrementoY(velocidade);
			this.mudaSprite(this.spriteBaixo);
		}
		
		
		Camera.x = Camera.clamp((Word.WIDTH*32)-(Game.LARGURA), 0, (int)this.getX() - (Game.LARGURA/2));
		Camera.y = Camera.clamp((Word.HEIGTH*32)-(Game.ALTURA), 0, (int)this.getY() - (Game.ALTURA/2));
		
		
		if(this.atirando) {
			this.atirando=false;
			if(this.pegouArma && this.municao > 0) {				
				this.municao--;
				this.atirar();
			}
		}
		
		
		
		
		//reseta frame
		if(this.frames == this.max_frames)
			this.frames = 0;
		
		if(Game.jogador.vida < 0) {
			//fim de jogo
			Game.jogador.vida = 0;
			Game.estadoJogo = "GAME_OVER";
			
		}
		
		
	}
	
	public void render(Graphics g) {
		g.drawImage(this.spriteAtivo, (int)this.getX() - Camera.x, (int)this.getY() - Camera.y, null);
		//g.setColor(new Color(255, 0, 0));
		//g.drawRect((int)this.getX() - Camera.x, (int)this.getY() - Camera.y, 32, 32);
	}
	
	public void setDireita(boolean val) {
		this.direita = val;
	}
	
	public void setEsquerda(boolean val) {
		this.esquerda = val;
	}
	
	public void setCima(boolean val) {
		this.cima = val;
	}
	
	public void setBaixo(boolean val) {
		this.baixo = val;
	}
	
	public double getVelocidade() {
		return this.velocidade;
	}
	
	public void mudaSprite(BufferedImage[] buffer) {
		if(this.frames == this.max_frames) {
			this.indice++;
			this.spriteAtivo = buffer[this.indice];
			if(Game.jogador.dano) {
				this.spriteAtivo = buffer[5];
				Game.jogador.dano = false;
			}
			
			if(this.indice == this.max_indice) {
				this.indice = 0;
			}
		}
	}
	
	public void poderAtivo() {
		for(int i =0; i < this.spriteDireita.length;i++) 
			this.spriteDireita[i] = Game.spritesheet.getSprite(96+(32*i), 128, 32, 32);			
		
		for(int i =0; i < this.spriteEsquerda.length;i++) 
			this.spriteEsquerda[i] = Game.spritesheet.getSprite(96+(32*i), 160, 32, 32);
		
		for(int i =0; i < this.spriteBaixo.length;i++)
			this.spriteBaixo[i] = Game.spritesheet.getSprite(96+(32*i), 192, 32, 32);
		
		for(int i =0; i < this.spriteCima.length;i++) 
			this.spriteCima[i] = Game.spritesheet.getSprite(96+(32*i), 224, 32, 32);
	}
	
	public void atirar() {
		double angulo = Math.atan2(this.mouseY - ( this.getY() - Camera.y), this.mouseX - ( this.getX() - Camera.x));
		double cosX = Math.cos(angulo);
		double senY = Math.sin(angulo);
		
		EntityBala bala = new EntityBala((int)(this.getX()+cosX), (int)(this.getY()+senY), 5, 5, null);
		bala.dx = cosX;
		bala.dy = senY;
		Game.balas.add(bala);
	}
	
	
	

}//fim class
