package com.solitario.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

import com.solitario.entities.Entity;
import com.solitario.entities.EntityBala;
import com.solitario.entities.EntityInimigo;
import com.solitario.entities.Player;
import com.solitario.graficos.Menu;
import com.solitario.graficos.Som;
import com.solitario.graficos.Sound;
import com.solitario.graficos.Spritesheet;
import com.solitario.graficos.Ui;
import com.solitario.word.Camera;
import com.solitario.word.Word;

public class Game extends Canvas implements Runnable, KeyListener, MouseListener{
	
	private static final long serialVersionUID = 1L;
	private boolean isRunning;
	private Thread thread;
	private JFrame tela;
	
	public static final int LARGURA = 480;
	public static final int ALTURA = 320;
	public static final int ESCALA = 2;
	private BufferedImage image;
	public String nomeMapa;
	public static int levelJogo = 1, levelMax = 4;
	public static String estadoJogo = "MENU"; //"GAME_OVER", "NORMAL", "MENU", "SAIR", "INICIAR", "PAUSAR", "CONTINUAR"
	public boolean reiniciar = false;
	
	public static Spritesheet spritesheet;
	public static List<Entity> entidades;
	public static List<EntityBala> balas;
	public static List<EntityInimigo> inimigos;
	public static Player jogador;
	public static Random random;
	
	public static Word word;
	public static Ui barraVida;
	public static Menu menu;
	
	private String[] saveDescricao = {"Level", "Vida"};
	private int[] saveValores = new int[2];
	
	
	public Game() {
		this.setPreferredSize(new Dimension(Game.LARGURA*Game.ESCALA, Game.ALTURA*Game.ESCALA));
		this.configJanela();
		
		this.addKeyListener(this);//pesquisar eventos
		this.addMouseListener(this);
		Sound.musicBackground.loop();
		
		this.image = new BufferedImage(Game.LARGURA, Game.ALTURA, BufferedImage.TYPE_INT_RGB);
		
		Game.spritesheet = new Spritesheet("/sprites.png");
		
		Game.barraVida = new Ui();
		Game.menu = new Menu();
		Game.random = new Random();
		Game.entidades = new ArrayList<Entity>();
		Game.inimigos = new ArrayList<EntityInimigo>();
		Game.balas = new ArrayList<EntityBala>();
		Game.jogador = new Player(0, 0, 32, 32, Game.spritesheet.getSprite(96, 0, 32, 32));
		Game.word = new Word("/fundo_menu1.png");

		
		Game.entidades.add(jogador);
		
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		game.start();
		
	}
	
	public void configJanela() {
		this.tela = new JFrame("Jogo2D");
		this.tela.add(this);
		this.tela.setResizable(false);
		this.tela.setLocationRelativeTo(null);
		this.tela.pack();
		this.tela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.tela.setVisible(true);
	}
	
	public synchronized void start() {
		this.isRunning = true;
		
		this.thread = new Thread(this);
		this.thread.start();
	}
	
	public synchronized void stop() {
		try {
			this.thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void redefineJogo(String nomeMapa) {

		Game.spritesheet = new Spritesheet("/sprites.png");
		Game.barraVida = new Ui();
		
		Game.random = new Random();
		Game.entidades = new ArrayList<Entity>();
		Game.inimigos = new ArrayList<EntityInimigo>();
		Game.jogador = new Player(0, 0, 32, 32, Game.spritesheet.getSprite(96, 0, 32, 32));
		Game.word = new Word("/"+nomeMapa+".png");
		
		Game.entidades.add(Game.jogador);
		return;
	}
	
	public void tick() {
		//logica durante menu
		if(Game.estadoJogo == "MENU") {
			Game.menu.tick();
			for(int i = 0; i<Game.inimigos.size(); i++) {
				EntityInimigo inimigo = Game.inimigos.get(i);
				inimigo.animar();
			}
		}
		if(Game.estadoJogo == "INICIAR") {
			Game.levelJogo = 1;
			Game.redefineJogo("level" + Game.levelJogo);
			this.saveValores[0] = Game.levelJogo;
			this.saveValores[1] = Game.jogador.vida;
			Salvar.salvarJogo(this.saveDescricao, this.saveValores);
			Game.estadoJogo = "NORMAL";
		}
		if(Game.estadoJogo == "SAIR") {
			System.exit(0);
		}
		if(Game.estadoJogo == "CONTINUAR") {
			Salvar.carregaJogo();
		}
				
		//tela game over
		if(Game.estadoJogo == "GAME_OVER" || Game.estadoJogo == "NORMAL" || Game.estadoJogo == "PAUSAR") {
			Game.barraVida.tick();
		}
		
		//reinicia jogo
		if(this.reiniciar) {
			this.reiniciar = false;
			if(Game.estadoJogo == "GAME_OVER") {
				//Game.levelJogo = 1;
				//Game.redefineJogo("level" + Game.levelJogo);
				Salvar.carregaJogo();
				
			}
		}
		
		//logica durante o jogo
		if(Game.estadoJogo == "NORMAL" && !(Game.estadoJogo == "PAUSAR")) {
			
			//caso o jogador mate todos os inimigos
			if(Game.inimigos.size() == 0) {
				//muda mapa do game
				Game.levelJogo ++;
				if(Game.levelJogo > Game.levelMax) {
					Game.levelJogo = 1;
				}
				
				this.saveValores[1] = Game.jogador.vida;
				Game.redefineJogo("level"+Game.levelJogo);
				Game.jogador.vida = this.saveValores[1];
				this.saveValores[0] = Game.levelJogo;
				Salvar.salvarJogo(this.saveDescricao, this.saveValores);
				
			}
			
			for(int i = 0; i<Game.entidades.size(); i++) {
				Entity entidade = Game.entidades.get(i);
				entidade.tick();
			}
			
			for(int i = 0 ; i<Game.balas.size(); i++) {
				EntityBala b = Game.balas.get(i);
				b.tick();
			}
			
			
			
		}
		
		
		
	}
	
	
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = this.image.getGraphics();
		g.setColor(new Color(0, 255, 255));
		g.fillRect(0, 0, Game.LARGURA, Game.ALTURA);
		requestFocus();
		/*Bloco de rederização do jogo*/
		
		Game.word.render(g);
		
		//render durante o jogo
		if(Game.estadoJogo == "NORMAL" || Game.estadoJogo == "PAUSAR") {
			for(int i = 0; i<Game.entidades.size(); i++) {
				Entity entidade = Game.entidades.get(i);
				entidade.render(g);
			}
			
			for(int i = 0 ; i<Game.balas.size(); i++) {
				EntityBala b = Game.balas.get(i);
				b.render(g);
			}
			
		}
		Game.barraVida.render(g);
		
		//render durante menu
		if(Game.estadoJogo == "MENU") {
			Game.menu.render(g);
			for(int i = 0; i<Game.inimigos.size(); i++) {
				Entity inimigo = Game.inimigos.get(i);
				inimigo.render(g);
			}
		}
		
		
		/***/
		
		g.dispose();
		g = bs.getDrawGraphics();
		g.drawImage(this.image, 0, 0, Game.LARGURA*Game.ESCALA, Game.ALTURA*Game.ESCALA, null);
		
		/*BLOCO DE TESTES*/
		//Teste.renderTeseGrafico(g);
		/***/
		bs.show();
	}


	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000/amountOfTicks;
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		
		while(isRunning) {
			
			long now = System.nanoTime();
			delta+= (now - lastTime) / ns;
			lastTime = now;
			
			if(delta >= 1) {
				this.tick(); //Metodos Principal
				this.render(); //Metodo Principal
				frames++;
				delta--;
			}
			
			if(System.currentTimeMillis() - timer >= 1000) {
				
				System.out.println("FPS: " + frames);
				frames = 0;
				timer+= 1000;
			}
		}
		
		this.stop();
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_UP ||
				e.getKeyCode() == KeyEvent.VK_W) {
			Game.jogador.setCima(true);
			
			if(Game.estadoJogo == "MENU")
				Game.menu.cima = true;
			
		}else if(e.getKeyCode() == KeyEvent.VK_DOWN ||
				e.getKeyCode() == KeyEvent.VK_S) {
			Game.jogador.setBaixo(true);
			
			if(Game.estadoJogo == "MENU")
				Game.menu.baixo = true;
			
		}
		
		if(e.getKeyCode() == KeyEvent.VK_RIGHT ||
				e.getKeyCode() == KeyEvent.VK_D) {
			Game.jogador.setDireita(true);
		}else if(e.getKeyCode() == KeyEvent.VK_LEFT ||
				e.getKeyCode() == KeyEvent.VK_A) {
			Game.jogador.setEsquerda(true);
		}
		
		if(e.getKeyCode() == KeyEvent.VK_B)
			Game.jogador.atirando = true;
		
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			if(Game.estadoJogo == "GAME_OVER") {
				this.reiniciar = true;
			}
			if(Game.estadoJogo == "MENU") {
				Game.menu.selecionado = true;
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_M && Game.estadoJogo == "GAME_OVER")
			Game.barraVida.voltarMenu = true;
		
		if(e.getKeyCode() == KeyEvent.VK_P && (Game.estadoJogo == "NORMAL" || Game.estadoJogo == "PAUSAR"))
			Game.barraVida.pausarJogo = true;
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		if(e.getKeyCode() == KeyEvent.VK_UP ||
				e.getKeyCode() == KeyEvent.VK_W) {
			Game.jogador.setCima(false);
		}else if(e.getKeyCode() == KeyEvent.VK_DOWN ||
				e.getKeyCode() == KeyEvent.VK_S) {
			Game.jogador.setBaixo(false);
		}
		
		if(e.getKeyCode() == KeyEvent.VK_RIGHT ||
				e.getKeyCode() == KeyEvent.VK_D) {
			Game.jogador.setDireita(false);
		}else if(e.getKeyCode() == KeyEvent.VK_LEFT ||
				e.getKeyCode() == KeyEvent.VK_A) {
			Game.jogador.setEsquerda(false);
		}
		
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void mouseClicked(MouseEvent e) {
		Game.jogador.mouseX = e.getX()/Game.ESCALA;
		Game.jogador.mouseY = e.getY()/Game.ESCALA;
		Game.jogador.atirando = true;
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	
	
}

