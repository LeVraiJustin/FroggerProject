package caseSpecial;

import gameCommons.Game;
import util.Case;

public class CaseBonus {
    private Game game;
    private Case position;

    public CaseBonus(Game game, Case position){
        this.game = game;
        this.position = position;
    }

    public Case getPosition(){
        return position;
    }
}
