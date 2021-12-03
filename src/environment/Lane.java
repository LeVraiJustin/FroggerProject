package environment;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

import caseSpecial.CaseBonus;
import caseSpecial.Piege;
import graphicalElements.Element;
import util.Case;
import gameCommons.Game;

public class Lane {
	private Game game;
	private int ord;
	private int speed;
	private ArrayList<Car> cars = new ArrayList<>();
	private boolean leftToRight;
	private double density;
	private int timer;
	private ArrayList<Piege>pieges = new ArrayList<>();
	private ArrayList<CaseBonus>caseBonus = new ArrayList<>();

	// TODO : Constructeur(s)
	public Lane(Game game, int ord, double density){
		this.game =game;
		this.ord = ord;
		this.density = density;
		this.speed = game.randomGen.nextInt(game.minSpeedInTimerLoops) + 1;
		this.leftToRight = game.randomGen.nextBoolean();


		for(int i = 0; i < 4 * game.width; ++i) {
			this.moveCars(true);
			this.mayAddCar();
		}

	}

	public Lane(Game game, int ord) {
		this(game, ord, game.defaultDensity);
	}

	public Lane(Game game, int ord, int abscPiege){
		this(game,ord,game.defaultDensity);
		pieges.add(new Piege(game,new Case(abscPiege,ord)));

		if(abscPiege <= game.height/2){
			caseBonus.add(new CaseBonus(game,new Case(abscPiege + game.randomGen.nextInt(game.height/2)+1,ord)));
		}
		else
			caseBonus.add(new CaseBonus(game,new Case(abscPiege - game.randomGen.nextInt(game.height/2)+1,ord)));
	}

	public void update() {

		// TODO

		// Toutes les voitures se d�placent d'une case au bout d'un nombre "tic
		// d'horloge" �gal � leur vitesse
		// Notez que cette m�thode est appel�e � chaque tic d'horloge

		// Les voitures doivent etre ajoutes a l interface graphique meme quand
		// elle ne bougent pas

		// A chaque tic d'horloge, une voiture peut �tre ajout�e

		++this.timer;
		if (this.timer <= this.speed) {
			this.moveCars(false);
		} else {
			this.moveCars(true);
			this.mayAddCar();
			this.timer = 0;
		}

		for(Piege p : pieges)
			game.getGraphic().add(new Element(p.getPosition().absc , p.getPosition().ord, Color.BLACK));

		for(CaseBonus b : caseBonus)
			game.getGraphic().add(new Element(b.getPosition().absc , b.getPosition().ord, Color.ORANGE));

	}

	// TODO : ajout de methodes
	public boolean isSafe(Case c ){
		if(c.ord != ord) return true;
		for(Car car : cars) {
			if (c.absc >= car.getLeftPosition().absc  && c.absc <= (car.getLeftPosition().absc + car.getLength()-1) )
				return false;
		}
		for(Piege p : pieges)
			if(p.getPosition().absc == c.absc && p.getPosition().ord == c.ord)
				return false;

		for(CaseBonus b : caseBonus)
			if(b.getPosition().absc == c.absc && b.getPosition().ord == c.ord){
				game.score++;
				caseBonus.remove(0);

			}




		return true;
	}



	private void moveCars(boolean b) {
		Iterator i = cars.iterator();
		while(i.hasNext()) {
			Car car = (Car)i.next();
			car.move(b);
		}
		this.removeOldCars();
	}


	private void removeOldCars() {
		ArrayList<Car> toBeRemoved = new ArrayList();
		Iterator i = this.cars.iterator();

		Car c;
		while(i.hasNext()) {
			c = (Car)i.next();
			if (leftToRight) {
				if((c.getLeftPosition().absc ) == game.width)
					toBeRemoved.add(c);
			}
			else
				if((c.getLeftPosition().absc + c.getLength()) == 0)
					toBeRemoved.add(c);
		}

		i = toBeRemoved.iterator();

		while(i.hasNext()) {
			c = (Car)i.next();
			this.cars.remove(c);
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
