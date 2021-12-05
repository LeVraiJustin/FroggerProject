package gameCommons;

import graphicalElements.FroggerGraphic;
import graphicalElements.IFroggerGraphics;
import infinite.EnvironmentInf;
import infinite.FrogInf;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//import givenEnvironment.GivenEnvironment;

public class Main {

	public static void main(String[] args) {

		//Caract�ristiques du jeu
		int width = 64;
		int height = 32;
		int tempo = 100;
		int minSpeedInTimerLoops = 50; // Par défault : 3
		double defaultDensity = 0.1; // Par défault : 0.2
		
		//Cr�ation de l'interface graphique
		IFroggerGraphics graphic = new FroggerGraphic(width, height);
		//Cr�ation de la partie
		Game game = new Game(graphic, width, height, minSpeedInTimerLoops, defaultDensity);
		//Cr�ation et liason de la grenouille
		IFrog frog = new FrogInf(game);
		game.setFrog(frog);
		graphic.setFrog(frog);
		//Cr�ation et liaison de l'environnement
		//IEnvironment env = new Environment(game);
		//IEnvironment env = new GivenEnvironment(game);
		IEnvironment env = new EnvironmentInf(game);
		game.setEnvironment(env);
				
		//Boucle principale : l'environnement s'acturalise tous les tempo milisecondes
		Timer timer = new Timer(tempo, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				game.update();
				graphic.repaint();
			}
		});
		timer.setInitialDelay(0);
		timer.start();
	}
}
