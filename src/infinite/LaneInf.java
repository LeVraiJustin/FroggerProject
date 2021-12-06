package infinite;

import gameCommons.Game;
import util.Case;
import WaterLine.*;
import specialCase.*;
import graphicalElements.*;

import java.util.ArrayList;

public class LaneInf {

    private Game game;
    private int ord;
    private int speed;
    private ArrayList<CarInf> cars = new ArrayList<>();
    private boolean leftToRight;
    private double density;
    private int tic;

    // Partie 4 CASES BONUS
    private ArrayList<Trap> traps = new ArrayList<>();
    private ArrayList<BonusCase>caseBonus = new ArrayList<>();
    private ArrayList<River> river = new ArrayList<>();
    private boolean isRiver = false;
    private ArrayList<WoodLog> woodLogs = new ArrayList<>();


    // Constructeur
    public LaneInf(Game game, double density, int ord) {
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

    // Constructeur avec les cases bonus
    public LaneInf(Game game, int ord, int abscTrap){

        this.game = game;
        this.ord = ord;

        traps.add(new Trap(game,new Case(abscTrap,ord)));

        if(abscTrap <= game.height/2) {
            caseBonus.add(new BonusCase(game,new Case(abscTrap + game.randomGen.nextInt(game.height/2),ord)));
        } else {
            caseBonus.add(new BonusCase(game, new Case(abscTrap - game.randomGen.nextInt((game.height / 2)) + 1, ord)));
        }
    }

    // Constructeur avec riviere
    public LaneInf(Game game, int ord, boolean isRiver){

        this.game =game;
        this.ord = ord;
        this.density = game.defaultDensity;
        this.speed = game.randomGen.nextInt(game.minSpeedInTimerLoops) + 1;
        this.leftToRight = game.randomGen.nextBoolean();
        this.isRiver = isRiver;

        for(int i = 0; i < game.width; ++i) {
            this.moveWoodLog();
            mayAddCaWoodLog();
        }

        river.add(new River(game, ord,new Case(0,ord) ));
    }

    public void update() {

        // Toutes les voitures se d�placent d'une case au bout d'un nombre "tic
        // d'horloge" �gal � leur vitesse
        // Notez que cette m�thode est appel�e � chaque tic d'horloge

        // Les voitures doivent etre ajoutes a l interface graphique meme quand
        // elle ne bougent pas

        // A chaque tic d'horloge, une voiture peut �tre ajout�e

        // Pour les cases spéciales


        for (Trap p : traps) {
            game.getGraphic().add(new Element(p.getPosition().absc, p.getPosition().ord, p.getColor()));
        }

        for (BonusCase b : caseBonus) {
            game.getGraphic().add(new Element(b.getPosition().absc, b.getPosition().ord, b.getColor()));
        }

        for (River r : river) {
            for (int i = 0; i < r.getLength(); i++) {
                game.getGraphic().add(new Element(0 + i, ord, r.getColor()));
            }
        }

        tic++;
        for (int i = 0; i < this.cars.size(); i++) {
            this.cars.get(i).graphicUpdate();
        }

        if (this.tic == this.speed) {
            this.moveLane();
            this.mayAddCar();

            if (isRiver) {

            }

            this.tic = 0;
        }

    }

    public boolean isSafe(Case c) {

        for (CarInf car : cars) {
            // Pour l'ensemble des véhicules de la Lane
            // On vérifie si une voiture est présente à l'endroit c
            if (c.absc >= car.getLeftPosition().absc && c.absc <= (car.getLeftPosition().absc + car.getLength() - 1)) {
                return false;
            }
        }
        for (Trap p : traps) {
            if (p.getPosition().absc == c.absc && p.getPosition().ord == c.ord) {
                return false;
            }
        }

        for (BonusCase b : caseBonus) {
            if(b.getPosition().absc == c.absc && b.getPosition().ord == c.ord) {
                this.game.addScore();
                caseBonus.remove(0);
            }
        }

        for(WoodLog w : woodLogs) {
            if (c.absc >= w.getLeftPosition().absc  && c.absc <= (w.getLeftPosition().absc + w.getLength()-1) ) {
                return true;
            }
        }

        for(River r : river) {
            if ((c.absc >= r.getLeftCase().absc && c.ord <= (r.getLeftCase().absc + r.getLength()))) {
                return false;
            }
        }

        return true;
    }

    private void moveWoodLog() {

        for (int i = 0 ; i < this.woodLogs.size() ; i++) {
            this.woodLogs.get(i).move();
        }

    }

    private void clearWoodlog() {

        for (int i = 0; i < this.woodLogs.size(); i++) {
            if (leftToRight) {
                if (this.woodLogs.get(i).getLeftPosition().absc == game.width) {
                    this.woodLogs.remove(this.woodLogs.get(i));
                }
            } else {
                if (this.woodLogs.get(i).getLeftPosition().absc + this.woodLogs.get(i).getLength() == 0) {
                    this.woodLogs.remove(this.woodLogs.get(i));
                }
            }
        }

    }

    private void mayAddCaWoodLog() {

        if (isRiver) {
            if (game.randomGen.nextDouble() < density) {
                woodLogs.add(new WoodLog(game, getBeforeFirstCase(), leftToRight));
            }
        }

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
