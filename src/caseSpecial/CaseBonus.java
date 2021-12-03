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
}
