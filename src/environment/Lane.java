package environment;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

import caseSpecial.CaseBonus;
import caseSpecial.Trap;
import graphicalElements.Element;
import waterLine.River;
import util.Case;
import gameCommons.Game;
import waterLine.WoodLog;

public class Lane {
	private Game game;
	private int ord;
	private int speed;
	private ArrayList<Car> cars = new ArrayList<>();
	private boolean leftToRight;
	private double density;
	private int timer;
	private ArrayList<Trap> traps = new ArrayList<>();
	private ArrayList<CaseBonus>caseBonus = new ArrayList<>();
	private ArrayList<River> river = new ArrayList<>();
	private boolean isRiver = false;
	private ArrayList<WoodLog>woodLogs = new ArrayList<>();

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

	public Lane(Game game, int ord, int abscTrap){
		this(game,ord,game.defaultDensity);
		traps.add(new Trap(game,new Case(abscTrap,ord)));
		if(abscTrap <= game.height/2){
			caseBonus.add(new CaseBonus(game,new Case(abscTrap + game.randomGen.nextInt(game.height/2-1)+1,ord)));
		} else
			caseBonus.add(new CaseBonus(game,new Case(abscTrap - game.randomGen.nextInt((game.height/2)-1)+1,ord)));
	}

	public Lane(Game game, int ord, boolean isRiver){
		this.game =game;
		this.ord = ord;
		this.density = game.defaultDensity;
		this.speed = game.randomGen.nextInt(game.minSpeedInTimerLoops) + 1;
		this.leftToRight = game.randomGen.nextBoolean();
		this.isRiver = isRiver;
		for(int i = 0; i < 40 * game.width; ++i) {
			this.moveWoodLog(true);
			mayAddCaWoodLog();
		}
		river.add(new River(game, ord,new Case(0,ord) ));
	}

	public void update() {
		// TODO
		// Toutes les voitures se d�placent d'une case au bout d'un nombre "tic
		// d'horloge" �gal � leur vitesse
		// Notez que cette m�thode est appel�e � chaque tic d'horloge

		// Les voitures doivent etre ajoutes a l interface graphique meme quand
		// elle ne bougent pas

		// A chaque tic d'horloge, une voiture peut �tre ajout�e

		for (Trap p : traps)
			game.getGraphic().add(new Element(p.getPosition().absc, p.getPosition().ord, p.getColor()));

		for (CaseBonus b : caseBonus)
			game.getGraphic().add(new Element(b.getPosition().absc, b.getPosition().ord, b.getColor()));

		for (River r : river)
			for (int i = 0; i < r.getLength(); i++)
				game.getGraphic().add(new Element(0 + i, ord, r.getColor()));

		++this.timer;
		if (this.timer <= this.speed) {
			this.moveCars(false);
			/*if (isRiver)*/ moveWoodLog(false);
		} else {
			this.moveCars(true);
			this.mayAddCar();
			if (isRiver) {
				this.moveWoodLog(true);
				this.mayAddCaWoodLog();
			}
			this.timer = 0;
		}

	}

	// TODO : ajout de methodes
	public boolean isSafe(Case c ){
		if(c.ord != ord) return true;
		for(Car car : cars) {
			if (c.absc >= car.getLeftPosition().absc  && c.absc <= (car.getLeftPosition().absc + car.getLength()-1) )
				return false;
		}
		for(Trap p : traps)
			if(p.getPosition().absc == c.absc && p.getPosition().ord == c.ord)
				return false;

		for(CaseBonus b : caseBonus)
			if(b.getPosition().absc == c.absc && b.getPosition().ord == c.ord){
				game.score++;
				caseBonus.remove(0);
			}

		for(WoodLog w : woodLogs) {
			if (c.absc >= w.getLeftPosition().absc  && c.absc <= (w.getLeftPosition().absc + w.getLength()-1) )
				return true;
		}

		for(River r : river)
			if( (c.absc>= r.getLeftCase().absc && c.ord <= (r.getLeftCase().absc + r.getLength())))
				return false;

		return true;
	}




	//woodLog
	private void moveWoodLog(boolean b) {
		Iterator i = woodLogs.iterator();
		while(i.hasNext()) {
			WoodLog w = (WoodLog)i.next();
			w.move(b);
		}
		this.removeOldWoodLog();
	}

	private void removeOldWoodLog() {
		ArrayList<WoodLog> toBeRemoved = new ArrayList();
		Iterator i = this.woodLogs.iterator();

		WoodLog w;
		while(i.hasNext()) {
			w = (WoodLog) i.next();
			if (leftToRight) {
				if((w.getLeftPosition().absc ) == game.width)
					toBeRemoved.add(w);
			} else
				if((w.getLeftPosition().absc + w.getLength()) == 0)
					toBeRemoved.add(w);
		}

		i = toBeRemoved.iterator();

		while(i.hasNext()) {
			w = (WoodLog) i.next();
			this.woodLogs.remove(w);
		}
	}

	private void mayAddCaWoodLog() {
		if (isRiver) {
			if (game.randomGen.nextDouble() < density) {
				woodLogs.add(new WoodLog(game, getBeforeFirstCase(), leftToRight));
			}
		}
	}

	//car
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
		if (isSafe(getFirstCase()) && isSafe(getBeforeFirstCase()) && !isRiver) {
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
