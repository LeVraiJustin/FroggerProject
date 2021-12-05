package infinite;

import gameCommons.Game;
import gameCommons.IFrog;
import util.Case;
import util.Direction;

public class FrogInf implements IFrog {

    private Game game;
    private Case position;
    private Direction direction;

    /* Constructeur par défaut */
    public FrogInf(Game game) {
        this.game = game;
        // On place la grenouille au centre
        this.position = new Case(game.width / 2,0);
        this.direction = Direction.up;
    }

    public Case getPosition() {
        return this.position;
    }

    public Direction getDirection() {
        return this.direction;
    }

    public void move(Direction key) {

		/* Déplacement de la grenouuille
		   On définit le déplacement de la grenouille */
        this.direction = key;

        // Partir en haut
        // Il n'y a plus de limites
        if (this.direction == Direction.up) {
            this.position = new Case(this.position.absc, this.position.ord + 1);
            // Ajout du score du joueur
            this.game.addScore();
            /*
            // Ajout de nouvelles Lane pour le mode infini
            // Les Lanes seront ajoutées seulement si le frog se déplace vers le haut
            if (this.position.ord == this.game.height / 2) {
                this.game.addLane();
            }
             */
            if (this.position.ord > this.game.height / 2.) {
                this.game.addLane();
                System.out.println("newLANE");

            }

        }
        // Partir à droite //
        if (this.direction == Direction.right && this.position.absc < game.width - 1) {
            this.position = new Case(this.position.absc + 1, this.position.ord);
        }

        // Partir à gauche //
        if (this.direction == Direction.left && this.position.absc > 0) {
            this.position = new Case(this.position.absc - 1, this.position.ord);
        }

        // Partir en bas //
        if (this.direction == Direction.down && this.position.ord > 0) {
            this.position = new Case(this.position.absc, this.position.ord - 1);
        }

        // Update de la partie //
        this.game.update();
    }
}
