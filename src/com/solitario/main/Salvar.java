package com.solitario.main;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;

public class Salvar {


	private static File arquivo = new File("save//save.txt");
	
	public static void salvarJogo(String[] descricao, int[] valor) {
		try{
			if(!Salvar.arquivo.exists()) {
				Salvar.arquivo.createNewFile();
				FileWriter escrever = new FileWriter(Salvar.arquivo, false);
				escrever.close();	
			}
			if(Salvar.arquivo.exists()) {
				FileWriter escrever = new FileWriter(Salvar.arquivo, false);
				String conteudo = "";
				for(int i = 0; i < descricao.length; i++ ) {
					conteudo += descricao[i] +":"+valor[i]+"\n";
				}
				escrever.write(conteudo);
				escrever.close();
			}
		}catch(Error | IOException e){
			e.getMessage();
		}
	}
	
	public static void carregaJogo() {
		
			if(!Salvar.arquivo.exists() || Salvar.arquivo.length() < 1) {
				Game.estadoJogo = "MENU";
			}else {
				try {
					FileReader leitor = new FileReader(Salvar.arquivo);
					char[] cadeia = new char[(int)Salvar.arquivo.length()];
					int cursor = leitor.read(cadeia);
					String conteudo = new String(cadeia);
					String[] itens = conteudo.split("\n");
					
					for(int i=0; i<itens.length; i++) {
						String[] item = itens[i].split(":");
						switch(item[0]) {
						case "Level":
							Game.levelJogo = Integer.parseInt(item[1]);
							Game.redefineJogo("level" + Game.levelJogo);
							break;
						case "Vida":
							Game.jogador.vida = Integer.parseInt(item[1]);
							break;
						}
					}
					Game.estadoJogo = "NORMAL";
					
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		
			
		
	}
	
	
}
