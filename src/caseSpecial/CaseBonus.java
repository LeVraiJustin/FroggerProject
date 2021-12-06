package caseSpecial;

import gameCommons.Game;
import util.Case;

import java.awt.*;

public class CaseBonus {
    private Game game;
    private Case position;
    private final Color color = Color.YELLOW;

    public CaseBonus(Game game, Case position){
        this.game = game;
        this.position = position;
    }

    public Case getPosition(){
        return position;
    }

    public Color getColor(){
        return color;
    }

    public boolean canAddScore(Case c) {
        if(c.ord != position.ord ) return false;
        if (position.absc == c.absc && position.ord == c.ord) {
            return true;
        }
        return  false;
    }

}
