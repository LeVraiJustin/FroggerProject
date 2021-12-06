package gameCommons;

import graphicalElements.Element;
import graphicalElements.IFroggerGraphics;
import util.Case;

import java.awt.*;
import java.util.Random;

public class Game {

	// Initialisation du timer
	private int nbTimer = 1;
	long startTime = System.currentTimeMillis();

	public final Random randomGen = new Random();

	// Caracteristique de la partie
	public final int width;
	public final int height;
	public final int minSpeedInTimerLoops;
	public final double defaultDensity;
	public int playerScore = 0;

	// Lien aux objets utilis�s
	private IEnvironment environment;
	private IFrog frog;
	private IFroggerGraphics graphic;

	/**
	 * 
	 * @param graphic
	 *            l'interface graphique
	 * @param width
	 *            largeur en cases
	 * @param height
	 *            hauteur en cases
	 * @param minSpeedInTimerLoop
	 *            Vitesse minimale, en nombre de tour de timer avant d�placement
	 * @param defaultDensity
	 *            densite de voiture utilisee par defaut pour les routes
	 */
	public Game(IFroggerGraphics graphic, int width, int height, int minSpeedInTimerLoop, double defaultDensity) {
		super();
		this.graphic = graphic;
		this.width = width;
		this.height = height;
		this.minSpeedInTimerLoops = minSpeedInTimerLoop;
		this.defaultDensity = defaultDensity;
	}

	/**
	 * Lie l'objet frog � la partie
	 * 
	 * @param frog
	 */
	public void setFrog(IFrog frog) {
		this.frog = frog;
	}

	/**
	 * Lie l'objet environment a la partie
	 * 
	 * @param environment
	 */
	public void setEnvironment(IEnvironment environment) {
		this.environment = environment;
	}

	/**
	 * 
	 * @return l'interface graphique
	 */
	public IFroggerGraphics getGraphic() {
		return graphic;
	}

	/**
	 * Teste si la partie est perdue et lance un �cran de fin appropri� si tel
	 * est le cas
	 * 
	 * @return true si le partie est perdue
	 */
	public boolean testLose() {
		if (!this.environment.isSafe((frog.getPosition())))
		{
			if (nbTimer < 2) {
				nbTimer++;
				long endTime = System.currentTimeMillis();
				long t = (endTime-startTime) / 1000;
				this.graphic.endGameScreen("Vous avez perdu !" + " Score : " + this.playerScore + ", Temps : " + t + " s ");
			}
			return true;
		}
		return false;
	}

	/**
	 * Teste si la partie est gagnee et lance un �cran de fin appropri� si tel
	 * est le cas
	 * 
	 * @return true si la partie est gagn�e
	 */
	public boolean testWin() {
		if (this.environment.isWinningPosition((frog.getPosition())))
		{
			if (nbTimer < 2) {
				nbTimer++;
				long endTime = System.currentTimeMillis();
				long t = (endTime-startTime) / 1000;
				this.graphic.endGameScreen("Vous avez gagné !" + " Score : " + this.playerScore + ", Temps : " + t + " s ");
			}
			return true;
		}
		return false;
	}

	/**
	 * Actualise l'environnement, affiche la grenouille et verifie la fin de
	 * partie.
	 */
	public void update() {
		graphic.clear();
		environment.update();
		this.graphic.add(new Element(this.frog.getPosition(), Color.GREEN));

		testLose();
		testWin();
	}

	public void addScore() {
		this.playerScore++;
	}

	public void addLane() {
		this.environment.addLane();
	}

	public Case frogPosition() {
		return this.frog.getPosition();
	}
}
