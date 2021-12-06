package waterLine;

import gameCommons.Game;
import graphicalElements.Element;
import util.Case;

import java.awt.*;


public class River {

    private Game game;
    private int firstLine;
    private Case leftCase;
    private int length;
    private final Color color = Color.blue;


    public River(Game game, int firstLine, Case leftCase){
        this.game = game;
        this.firstLine = firstLine;
        this.leftCase = leftCase;
        this.length = game.height;
    }

    public Case getLeftCase() {
        return leftCase;
    }

    public int getLength() {
        return length;
    }

    public Color getColor(){return color;}


}
