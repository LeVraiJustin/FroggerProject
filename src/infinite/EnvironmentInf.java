package infinite;

import gameCommons.Game;
import gameCommons.IEnvironment;
import util.Case;

import java.util.ArrayList;

public class EnvironmentInf implements IEnvironment {

    private Game game;
    private ArrayList<LaneInf> road;

    // Constructeur

    public EnvironmentInf(Game game) {

        this.game = game;
        this.road = new ArrayList<LaneInf>();

        // Initialisation de l'environnement
        // Avec l'ajout de chaque Lane

        // Ajout de la premiere Lane
        this.road.add(new LaneInf(game, 0.0D, 0));

        // Ajout des autres lignes + des lignes spéciales
        for (int i = 1 ; i < game.height ; i++) {
            if ( i % 3 == 0) {
                this.road.add(new LaneInf(this.game, i, this.game.randomGen.nextInt(this.game.width)));
                /*
            } else if (i % 5 == 0) {
                this.road.add((new LaneInf(this.game, i, true)));
                 */
            } else {
                this.road.add(new LaneInf(game, this.game.defaultDensity, i));
            }
        }

    }

    public boolean isSafe(Case c) {
        return this.road.get(c.ord).isSafe(c);
    }

    // En infini, la partie n'est jamais remportée
    public boolean isWinningPosition(Case c) {
        return false;
    }

    public void update() {

        for (int i = 0; i < this.road.size(); i++) {
            this.road.get(i).update();
        }

    }

    public void addLane() {
        this.road.add(new LaneInf(this.game, this.game.defaultDensity, this.road.size()));
    }

    public void clearLane() {
        for (int i = 0 ; i < this.game.height ; i++) {
            if (i < this.game.frogPosition().ord) {

            }
        }
    }
}
