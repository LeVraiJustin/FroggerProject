package frog;

import gameCommons.Game;
import gameCommons.IFrog;
import util.Case;
import util.Direction;

public class Frog implements IFrog {
	
	private Game game;
	private Case position;
	private Direction direction;

	/* Constructeur par défaut */
	public Frog(Game game) {
		this.game = game;
		this.position = new Case(game.width / 2,0);
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

		// Partir en haut //
		if (this.direction == Direction.up && this.position.ord < game.height - 1) {
			this.position = new Case(this.position.absc, this.position.ord + 1);
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
