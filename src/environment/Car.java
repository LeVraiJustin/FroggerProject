package environment;

import gameCommons.Game;
import graphicalElements.Element;
import util.Case;

import java.awt.*;

public class Car {
	private Game game;
	private Case leftPosition;
	private boolean leftToRight;
	private int length;
	private final Color colorLtR = Color.RED;
	private final Color colorRtL = Color.BLUE;

	// Constructeurs //
	// Constructeur par défault //
	public Car(Game game, Case leftPosition, boolean leftToRight) {
		this.game = game;
		this.leftPosition = leftPosition;
		this.leftToRight = leftToRight;

		this.length = game.randomGen.nextInt(3) + 1;

		if(leftToRight)
			this.leftPosition = new Case(leftPosition.absc - this.length, leftPosition.ord);
		else
			this.leftPosition = new Case(leftPosition.absc, leftPosition.ord);

	}
	
	//TODO : ajout de methodes
	public Case getLeftPosition() {
		return leftPosition;
	}

	public int getLength() {
		return length;
	}

	public void move(boolean b){
		//Ariane
		if(b){
			if(leftToRight)
				leftPosition = new Case(leftPosition.absc + 1, leftPosition.ord);
			else
				leftPosition = new Case(leftPosition.absc - 1, leftPosition.ord);
		}
		this.addToGraphics();
	}

	/* Fourni : addToGraphics() permettant d'ajouter un element graphique correspondant a la voiture*/
	private void addToGraphics() {
		for (int i = 0; i < length; i++) {
			Color color = colorRtL;
			if (this.leftToRight){
				color = colorLtR;
			}
			game.getGraphic().add(new Element(leftPosition.absc + i, leftPosition.ord, color));
		}
	}



}
