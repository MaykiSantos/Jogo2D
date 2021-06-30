package com.solitario.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Teste {
	
	
	public static void renderTeseGrafico(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		int x1 = 100;
		int y1 = 100;
		int x2 = 200;
		int y2 = 200;
		g.setColor(new Color(0,0,0));
		g.fillRect(0, 0, Game.LARGURA, Game.ALTURA);
		g.setColor(new Color(255,0,0));
		g.drawRect(x1, y1, 3, 3);
		g.drawRect(x2, y2, 3, 3);
		double valor = Math.sqrt((x2-x1)*(x2-x1) + (y2-y1)*(y2-y1));
		//g.drawRect((int)valor, (int)valor, 3, 3);
		System.out.println(valor);
	}

}
