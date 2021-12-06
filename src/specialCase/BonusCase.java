package specialCase;

import gameCommons.Game;
import util.Case;

import java.awt.*;

public class BonusCase {

    private Game game;
    private Case position;
    private final Color color = Color.YELLOW;


    public BonusCase(Game game, Case position){
        this.game = game;
        this.position = position;
    }

    public Case getPosition(){ return position; }

    public Color getColor(){ return color; }
}
