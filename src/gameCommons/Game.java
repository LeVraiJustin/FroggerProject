package gameCommons;

import java.awt.Color;
import java.util.Random;

import graphicalElements.Element;
import graphicalElements.IFroggerGraphics;

public class Game {
	private int nbTime = 1;
	long startTime = System.currentTimeMillis();


	public final Random randomGen = new Random();

	// Caracteristique de la partie
	public final int width;
	public final int height;
	public final int minSpeedInTimerLoops;
	public final double defaultDensity;
	public int score = 0;

	// Lien aux objets utilis�s
	private IEnvironment environment;
	private IFrog frog;
	private IFrog frog2;
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
	public void setFrog2(IFrog frog2) {
		this.frog2 = frog2;
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
		if (!this.environment.isSafe((frog.getPosition())) || !this.environment.isSafe((frog2.getPosition())))
		{
			if(nbTime < 2){
				nbTime=2;
				long endTime = System.currentTimeMillis();
				long t = (endTime-startTime)/1000;
				this.graphic.endGameScreen("Vous avez perdu !" + " Score : " + score +  " Temps : " + t + "s");
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
		if (this.environment.isWinningPosition((frog.getPosition())) || this.environment.isWinningPosition((frog2.getPosition())))
		{
			if(nbTime < 2){
				nbTime=2;
				long endTime = System.currentTimeMillis();
				long t = (endTime-startTime)/1000;
				this.graphic.endGameScreen("Vous avez gagné !" +  " Score : " + score +   " Temps : " + t + "s");
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
		this.graphic.add(new Element(frog.getPosition(), Color.GREEN));
		this.graphic.add(new Element(frog2.getPosition(), Color.GREEN));
		testLose();
		testWin();
	}

}
