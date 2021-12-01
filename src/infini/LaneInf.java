package infini;

import gameCommons.Game;
import util.Case;

import java.util.ArrayList;
import java.util.Iterator;

public class LaneInf {
    private Game game;
    private int ord;
    private int speed;
    private ArrayList<CarInf> cars = new ArrayList<>();
    private boolean leftToRight;
    private double density;
    private int timer;

    // TODO : Constructeur(s)
    public LaneInf(Game game, int ord, double density){
        this.game =game;
        this.ord = ord;
        this.density = density;
        this.speed = game.randomGen.nextInt(game.minSpeedInTimerLoops) + 1;
        this.leftToRight = game.randomGen.nextBoolean();

        for(int i = 0; i < 40 * game.width; ++i) {
            this.moveCars(true);
            this.mayAddCar();
        }

    }

    public LaneInf(Game game, int ord) {
        this(game, ord, game.defaultDensity);
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

    }

    // TODO : ajout de methodes
    public boolean isSafe(Case c ){
        if(c.ord != ord) return true;
        for(CarInf car : cars) {
            if (c.absc >= car.getLeftPosition().absc  && c.absc <= (car.getLeftPosition().absc + car.getLength()-1) )
                return false;
        }
        return true;
    }



    private void moveCars(boolean b) {
        Iterator i = cars.iterator();
        while(i.hasNext()) {
            CarInf car = (CarInf)i.next();
            car.move(b);
        }
        this.removeOldCars();
    }


    private void removeOldCars() {
        ArrayList<CarInf> toBeRemoved = new ArrayList();
        Iterator i = this.cars.iterator();

        CarInf c;
        while(i.hasNext()) {
            c = (CarInf)i.next();
            if (leftToRight) {
                if((c.getLeftPosition().absc) == game.width)
                    toBeRemoved.add(c);
            }
            else
            if((c.getLeftPosition().absc + c.getLength())  == 0)
                toBeRemoved.add(c);
        }

        i = toBeRemoved.iterator();

        while(i.hasNext()) {
            c = (CarInf)i.next();
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
                cars.add(new CarInf(game, getBeforeFirstCase(), leftToRight));
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
