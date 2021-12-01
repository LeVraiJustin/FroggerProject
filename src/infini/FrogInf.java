package infini;

import gameCommons.Game;
import gameCommons.IFrog;
import graphicalElements.Element;
import util.Case;
import util.Direction;

import java.awt.*;

public class FrogInf implements IFrog {
    private Game game;
    private Case position;
    private Direction direction;

    /* Constructeur par défaut */
    public FrogInf(Game game) {
        this.game = game;
        this.position = new Case(game.width / 2,1);
        this.direction = Direction.up;
    }

    @Override
    public Case getPosition() {
        return this.position;
    }

    @Override
    public Direction getDirection() {
        return this.direction;
    }

    @Override
    public void move(Direction key) {

		/* Déplacement de la grenouuille
		   On définit le déplacement de la grenouille */
        this.direction = key;

        //Partir en haut
        if (key == Direction.up) {
            this.position = new Case(this.position.absc, this.position.ord + 1);
            ++this.game.score;
            if (this.game.score > this.game.maxScore) {
                this.game.maxScore = this.game.score;
                this.game.addLane();
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
        if (this.direction == Direction.down && this.position.ord > 1) {
            this.position = new Case(this.position.absc, this.position.ord - 1);
            --game.score;
        }

        // Update de la partie //
        this.game.getGraphic().add(new Element(this.position.absc, 1, Color.GREEN));
        this.game.testWin();
        this.game.testLose();
        System.out.println(this.position.absc + " " + this.position.ord + " score : " + this.game.score);
    }
}
