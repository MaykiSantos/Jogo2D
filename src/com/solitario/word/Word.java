package com.solitario.word;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.solitario.entities.*;
import com.solitario.main.Game;

public class Word {
	
	public static Tile[] tiles;
	public static int WIDTH, HEIGTH;
	public static int TILE_SIZE = 32;
	
	
	public Word(String arquivo){
		try {
			BufferedImage imageMap = ImageIO.read(this.getClass().getResource(arquivo));
			this.WIDTH = imageMap.getWidth();
			this.HEIGTH = imageMap.getHeight();
			
			int[] pixels = new int[Word.WIDTH * Word.HEIGTH];
			this.tiles = new Tile[Word.WIDTH * Word.HEIGTH];
			imageMap.getRGB(0, 0, Word.WIDTH, Word.HEIGTH, pixels, 0, Word.WIDTH);
			
			for(int xx =0; xx < Word.WIDTH; xx++) {
				for(int yy=0; yy < Word.HEIGTH; yy++) {
					int pixelAtual = pixels[xx+(yy*Word.WIDTH)];
					this.tiles[xx+(yy*Word.WIDTH)] = new TileChao(xx*32, yy*32, Tile.TILE_CHAO);
					
					if(pixelAtual == 0xFFFFFFFF) {
						//parede
						this.tiles[xx+(yy * Word.WIDTH)] = new TileParede(xx*32, yy*32, Tile.TILE_PAREDE);
						
					}else if(pixelAtual == 0xFF1AFF00) {
						//jogador
						Game.jogador.setX(xx*32);
						Game.jogador.setY(yy*32);
						Game.jogador.setMask(xx*32, yy*32, 12, 12);
								
					}else if(pixelAtual == 0xFFFF0044) {
						//inimigo
						EntityInimigo inimigo = new EntityInimigo(xx*32,yy*32, 32, 32, Entity.EN_INIMIGO);
						inimigo.setMask(xx*32,yy*32, 12, 15);
						Game.entidades.add(inimigo);
						Game.inimigos.add(inimigo);
					}else if(pixelAtual == 0xFFFFED00) {
						//munição
						EntityMunicao municao = new EntityMunicao(xx*32,yy*32, 32, 32, Entity.EN_MUNICAO);
						municao.setMask(xx*32+4,yy*32+17, 18, 20);
						Game.entidades.add(municao);
					}else if(pixelAtual == 0xFF1A00FF) {
						//arma
						EntityArma arma = new EntityArma(xx*32,yy*32, 32, 32, Entity.EN_ARMA);
						Game.entidades.add(arma);
					}else if(pixelAtual == 0xFF550087) {
						//vida
						EntityVida vida = new EntityVida(xx*32,yy*32, 32, 32, Entity.EN_VIDA);
						vida.setMask(xx*32,yy*32, 32, 17);
						Game.entidades.add(vida);
					}else if(pixelAtual == 0xFFFF7200) {
						//parede
						this.tiles[xx+(yy * Word.WIDTH)] = new TileParede(xx*32, yy*32, Tile.TILE_PAREDE_LATERAIS_LIVRES);
					}else if(pixelAtual == 0xFFFF3B14) {
						//parede
						this.tiles[xx+(yy * Word.WIDTH)] = new TileParede(xx*32, yy*32, Tile.TILE_PAREDE_SUPERIORINFERIOR_LIVRES);
					}else if(pixelAtual == 0xFFFF006A) {
						//parede
						this.tiles[xx+(yy * Word.WIDTH)] = new TileParede(xx*32, yy*32, Tile.TILE_PAREDE_CIMA);	
					}else if(pixelAtual == 0xFFFF2B95) {
						//parede
						this.tiles[xx+(yy * Word.WIDTH)] = new TileParede(xx*32, yy*32, Tile.TILE_PAREDE_BAIXO);
					}else if(pixelAtual == 0xFFFF3FC2) {
						//parede
						this.tiles[xx+(yy * Word.WIDTH)] = new TileParede(xx*32, yy*32, Tile.TILE_PAREDE_ESQUERDA);	
					}else if(pixelAtual == 0xFFFF75E1) {
						//parede
						this.tiles[xx+(yy * Word.WIDTH)] = new TileParede(xx*32, yy*32, Tile.TILE_PAREDE_DIREITA);
					}else if(pixelAtual == 0xFFFFA470) {
						//parede
						this.tiles[xx+(yy * Word.WIDTH)] = new TileParede(xx*32, yy*32, Tile.TILE_PAREDE_COMPLETO);
						
					}
					
					
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static boolean isFree(int x, int y) {
		
		int x1 = x/TILE_SIZE;
		int y1 = y/TILE_SIZE;
		
		int x2 = (x+TILE_SIZE-1)/TILE_SIZE;
		int y2 = y/TILE_SIZE;
		
		int x3 = x/TILE_SIZE;
		int y3 = (y+TILE_SIZE-1)/TILE_SIZE;
		
		int x4 = (x+TILE_SIZE-1)/TILE_SIZE;
		int y4 = (y+TILE_SIZE-1)/TILE_SIZE;
		
		return !(tiles[x1 + (y1*Word.WIDTH)] instanceof TileParede ||
				tiles[x2 + (y2*Word.WIDTH)] instanceof TileParede ||
				tiles[x3 + (y3*Word.WIDTH)] instanceof TileParede ||
				tiles[x4 + (y4*Word.WIDTH)] instanceof TileParede);	
	}
	
	
	public void render(Graphics g) {
		
		int xInicial = Camera.x / 32;
		int xFinal = xInicial + (Game.LARGURA / 32);
		
		int yInicial = Camera.y / 32;
		int yFinal = yInicial + (Game.ALTURA / 32);
		
		
		for(int xx =xInicial; xx <= xFinal; xx++) {
			for(int yy=yInicial; yy <= yFinal; yy++) {
				if(xx < 0 || yy < 0 || xx >= Word.HEIGTH || yy >= Word.HEIGTH) {
					continue;
				}
				Tile tile = tiles[xx+(yy*Word.WIDTH)];
				tile.render(g);
			}
		}
		
	}
	

}
