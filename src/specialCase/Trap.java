package specialCase;

import gameCommons.Game;
import graphicalElements.Element;
import util.Case;

import java.awt.*;

public class Trap {

    private Game game;
    private Case position;
    private final Color color = Color.BLACK;

    public Trap(Game game, Case position){
        this.game = game;
        this.position = position;
        this.addToGraphics();
    }

    public Case getPosition() { return position; }

    public Color getColor(){ return color;}

    private void addToGraphics() {
        game.getGraphic().add(new Element(position.absc , position.ord, color));
    }
}
