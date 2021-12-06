package infinite;

import gameCommons.Game;
import graphicalElements.Element;
import util.Case;

import java.awt.*;

public class CarInf {
    private Game game;
    private Case leftPosition;
    private boolean leftToRight;
    private int length;
    private final Color colorLtR = Color.LIGHT_GRAY;
    private final Color colorRtL = Color.DARK_GRAY;

    // Constructeur //

    public CarInf(Game game, Case leftPosition, boolean leftToRight) {
        this.game = game;
        this.leftPosition = leftPosition;
        this.leftToRight = leftToRight;
        // Taille aléatoire, avec une taille minimum de 1
        this.length = 1 + this.game.randomGen.nextInt(3);

        // Position de départ en fonction de leftToRight
        if (leftToRight) {
            this.leftPosition = new Case(this.leftPosition.absc - this.length, this.leftPosition.ord);
        } else {
            this.leftPosition = new Case(this.leftPosition.absc, this.leftPosition.ord);
        }
    }

    // Getters

    public Case getLeftPosition() {
        return this.leftPosition;
    }

    public int getLength() {
        return this.length;
    }

    // Déplacement de la voiture

    public void move() {

        // Déplacement de gauche à droite
        if (leftToRight) {
            this.leftPosition = new Case(this.leftPosition.absc + 1, this.leftPosition.ord);
        } else {
            // Déplacement de droite à gauche
            this.leftPosition = new Case(this.leftPosition.absc - 1, this.leftPosition.ord);
        }

        this.addToGraphics();
    }

    public void graphicUpdate() {
        this.addToGraphics();
    }

    /* Fourni : addToGraphics() permettant d'ajouter un element graphique correspondant a la voiture*/
    private void addToGraphics() {
        for (int i = 0; i < length; i++) {
            Color color = colorRtL;
            if (this.leftToRight){
                color = colorLtR;
            }
            game.getGraphic()
                    .add(new Element(leftPosition.absc + i, leftPosition.ord, color));
        }
    }
}
