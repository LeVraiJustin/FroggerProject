package environment;

import gameCommons.Game;
import util.Case;

import java.util.ArrayList;

public class Lane {

	private Game game;
	private int ord;
	private int speed;
	private ArrayList<Car> cars = new ArrayList<>();
	private boolean leftToRight;
	private double density;
	private int tic;

	public Lane(Game game, double density, int ord) {
		this.game = game;
		this.density = density;
		this.ord = ord;
		// leftToRight aléatoire
		this.leftToRight = this.game.randomGen.nextBoolean();
		// Vitesse aléatoire + Vitesse minimale
		this.speed = this.game.randomGen.nextInt(this.game.minSpeedInTimerLoops) + 1;
		this.tic = 0;
		for (int i = 0 ; i < this.game.width ; i++) {
			this.moveLane();
			this.mayAddCar();
		}
	}

	public void update() {

		// Toutes les voitures se d�placent d'une case au bout d'un nombre "tic
		// d'horloge" �gal � leur vitesse
		// Notez que cette m�thode est appel�e � chaque tic d'horloge

		// Les voitures doivent etre ajoutes a l interface graphique meme quand
		// elle ne bougent pas

		// A chaque tic d'horloge, une voiture peut �tre ajout�e

		tic++;
		for (int i = 0; i < this.cars.size(); i++) {
			this.cars.get(i).graphicUpdate();
		}

		if (this.tic == this.speed) {
			this.moveLane();
			this.mayAddCar();
			this.tic = 0;
		}

	}

	public boolean isSafe(Case c) {

		for (Car car : cars) {
			// Pour l'ensemble des véhicules de la Lane
			// On vérifie si une voiture est présente à l'endroit c
			if (c.absc >= car.getLeftPosition().absc && c.absc <= (car.getLeftPosition().absc + car.getLength() - 1)) {
				return false;
			}
		}
		return true;
	}

	public void moveLane() {

		for (int i = 0; i < this.cars.size(); i++) {
			this.cars.get(i).move();
		}

		// On enlève les voitures qui ont atteint l'autre côté
		this.clear();
	}

	/** Enlève les voitures qui ont atteints l'autre côté
	 */
	public void clear() {

		for (int i = 0; i < this.cars.size(); i++) {
			if (leftToRight) {
				if (this.cars.get(i).getLeftPosition().absc == game.width) {
					this.cars.remove(this.cars.get(i));
				}
			} else {
				if (this.cars.get(i).getLeftPosition().absc + this.cars.get(i).getLength() == 0) {
					this.cars.remove(this.cars.get(i));
				}
			}
		}

	}

	/*
	 * Fourni : mayAddCar(), getFirstCase() et getBeforeFirstCase() 
	 */

	/**
	 * Ajoute une voiture au d�but de la voie avec probabilit� �gale � la
	 * densit�, si la premi�re case de la voie est vide
	 */
	private void mayAddCar() {
		if (isSafe(getFirstCase()) && isSafe(getBeforeFirstCase())) {
			if (game.randomGen.nextDouble() < density) {
				cars.add(new Car(game, getBeforeFirstCase(), leftToRight));
			}
		}
	}

	private Case getFirstCase() {
		if (leftToRight) {
			return new Case(0, ord);
		} else
			return new Case(game.width - 1, ord);
	}

	private Case getBeforeFirstCase() {
		if (leftToRight) {
			return new Case(-1, ord);
		} else
			return new Case(game.width, ord);
	}

}
