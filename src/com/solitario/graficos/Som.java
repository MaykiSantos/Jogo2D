package com.solitario.graficos;


import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.SwingUtilities;


public class Som{
	

	
	public Som(String arquivo) {		
		//Clip clip;

		try {

        Clip oClip = AudioSystem.getClip();

		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}


}
