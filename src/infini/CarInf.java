package infini;

import gameCommons.Game;
import graphicalElements.Element;
import util.Case;

import java.awt.*;

public class CarInf {
    private Game game;
    private Case leftPosition;
    private boolean leftToRight;
    private int length;
    private final Color colorLtR = Color.BLACK;
    private final Color colorRtL = Color.BLUE;

    // Constructeurs //
    // Constructeur par défault //
    public CarInf(Game game, Case leftPosition, boolean leftToRight) {
        this.game = game;
        this.leftPosition = leftPosition;
        this.leftToRight = leftToRight;

        this.length = game.randomGen.nextInt(3) + 1;

        //this.leftPosition = new Case(leftToRight ? leftPosition.absc - this.length : leftPosition.absc, leftPosition.ord);

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
            game.getGraphic().add(new Element(leftPosition.absc + i, leftPosition.ord-game.score, color));
        }
    }
}
