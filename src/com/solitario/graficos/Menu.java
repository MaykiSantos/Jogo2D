package com.solitario.graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.solitario.main.Game;
import com.solitario.word.Camera;
import com.solitario.word.Word;

public class Menu {
	
	private String[] estados = {"INICIAR", "CONTINUAR", "SAIR"};
	private int indice = 0, indiceMax = this.estados.length - 1;
	public boolean cima = false, baixo = false, selecionado = false;
	
	
	public void tick(){
		Camera.x = 0;
		Camera.y = 0;
		
		if(this.baixo) {
			this.baixo = false;
			this.indice++;
			if(this.indice > this.indiceMax) {
				this.indice = 0;
			}
		}
		if(this.cima) {
			this.cima = false;
			this.indice--;
			if(this.indice < 0) {
				this.indice = this.indiceMax;
			}
		}
		if(this.selecionado) {
			this.selecionado = false;
			Game.estadoJogo = this.estados[this.indice];
		}
		
		
		
		
	}
	
	public void render(Graphics g) {
		g.setColor(new Color(0,0,0,100));
		g.fillRect(0, 0, Game.LARGURA, Game.ALTURA);
		
		g.setColor(Color.white);
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 50));
		g.drawString("LABIRINTO", 105, 100);
		g.setFont(new Font(Font.SANS_SERIF, Font.CENTER_BASELINE, 20));
		g.drawString("Iniciar", 100, 180);
		g.drawString("Continuar", 100, 205);
		g.drawString("Sair", 100, 230);
		
		if(this.estados[indice] == "INICIAR") {
			g.drawString(">", 80, 180);
		}else if(this.estados[indice] == "CONTINUAR") {
			g.drawString(">", 80, 205);
		}else if(this.estados[indice] == "SAIR") {
			g.drawString(">", 80, 230);
		}
		
		g.setFont(new Font("arial", Font.CENTER_BASELINE, 12));
		g.drawString("Encontre a flor, colete as munições e elimine todos os inimigos", 65, 290);
	}

}
