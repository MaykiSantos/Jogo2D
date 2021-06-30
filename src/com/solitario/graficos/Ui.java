package com.solitario.graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import com.solitario.main.Game;

public class Ui {
	
	private int time1 = 0, timeMax = 9, time2 = 0;
	private boolean estadoMensagem = true;
	public boolean voltarMenu = false;
	public boolean pausarJogo = false;
	public static int contPausaJogo = 0;
	
	public void tick() {
		this.time1++;
		if(this.time1 == this.timeMax) {
			this.estadoMensagem = true;
			this.time2++;
			this.time1=0;
		}
		if(time2 == this.timeMax) {
			this.estadoMensagem = false;
			this.time2 = 0;
		}
		if(this.voltarMenu) {
			this.voltarMenu = false;
			Game.redefineJogo("fundo_menu1");
			Game.estadoJogo = "MENU";
		}

	
		if(this.pausarJogo) {
			this.pausarJogo = false;
			
			switch(this.contPausaJogo) {
			case 0:
				Game.estadoJogo = "PAUSAR";				
			break;
			case 1:
				Game.estadoJogo = "NORMAL";
			break;
			}
			this.contPausaJogo++;
			if(this.contPausaJogo > 1)
				this.contPausaJogo=0;
		}
		
	}
	
	public void render(Graphics g) {
		g.setColor(Color.red);
		g.fillRect(10, 10, Game.jogador.maxVida, 10);
		
		g.setColor(Color.green);
		g.fillRect(10, 10, Game.jogador.vida, 10);
		
		g.setColor(Color.white);
		g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 9));
		g.drawString(Game.jogador.vida + "/" + Game.jogador.maxVida, 12, 18);
		
		g.setColor(Color.white);
		g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 10));
		g.drawString("MUNICAO: " + Game.jogador.municao, 12, 30);
		
		if(Game.estadoJogo == "GAME_OVER") {
			g.setColor(new Color(0,0,0,180));
			g.fillRect(0, 0, Game.LARGURA, Game.ALTURA); 
			
			g.setColor(Color.white);
			g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 50));
			g.drawString("Game Over!", 100, 100);
			
			if(this.estadoMensagem) {
				g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
				g.drawString("aperte ENTER para reiniciar", 122, 140);
			}
			g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
			g.drawString("aperte M para voltar ao menu", 165, 180);
			
		}
		if(Game.estadoJogo == "PAUSAR") {
			g.setColor(new Color(0,0,0,180));
			g.fillRect(0, 0, Game.LARGURA, Game.ALTURA); 
			g.setColor(new Color(255,255,255));
			g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 32));
			g.drawString("PAUSE", 177, 140);
			if(this.estadoMensagem) {
				g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
				g.drawString("aperte P para continuar", 165, 180);
			}
		}
		
	}
}
