package environment;

import gameCommons.Game;
import util.Case;

import java.util.ArrayList;

public class Environment {

    private Game game;
    private ArrayList<Lane> road;

    // Constructeur

    public Environment(Game game) {
        this.game = game;
        this.road = new ArrayList<Lane>();

        // Initialisation de l'environnement
        // Avec l'ajout de chaque Lane

        // Ajout de la premiere Lane
        this.road.add(new Lane(game, 0, 0));

        // Ajout des autres lignes
        for (int i = 1 ; i < game.height - 1 ; i++) {
            this.road.add(new Lane(game, this.game.defaultDensity, i));
        }

        // Ajout de la ligne d'arrivée
        this.road.add(new Lane(game, 0, this.game.height - 1));
    }

    //@Override
    public boolean isSafe(Case c) {
        return this.road.get(c.ord).isSafe(c);
    }

    //@Override
    public boolean isWinningPosition(Case c) {
        return c.ord == game.height - 1;
    }

    //@Override
    public void update() {
        for (int i = 0; i < this.road.size(); i++) {
            this.road.get(i).update();
        }
    }


}
