package environment;

import caseSpecial.CaseBonus;
import caseSpecial.Trap;
import gameCommons.Game;
import gameCommons.IEnvironment;
import graphicalElements.Element;
import util.Case;
import waterLine.River;

import java.util.ArrayList;
import java.util.Iterator;

public class Environment implements IEnvironment {
    private Game game;
    private ArrayList<Lane> road = new ArrayList<Lane>();
    private ArrayList<CaseBonus>caseBonus = new ArrayList<>();
    private ArrayList<Trap> traps = new ArrayList<>();


    public Environment(Game game) {
        this.game = game;
        road.add(new Lane(game,0,0.0D));

        this.road.add(new Lane(game, 1,true));
        this.road.add(new Lane(game, 2,true));
        this.road.add(new Lane(game, 3,true));
        this.road.add(new Lane(game, 4,true));

        for(int i = 5; i < game.height - 1; i++)
            this.road.add(new Lane(game, i));
/*
        this.road.add(new Lane(game, game.height-5,true));
        this.road.add(new Lane(game, game.height-4,true));
        this.road.add(new Lane(game, game.height-3,true));
        this.road.add(new Lane(game, game.height-2,true));
*/
        for(int i=0; i<=10; i++)
            traps.add(new Trap(game, new Case(game.randomGen.nextInt(game.width), game.randomGen.nextInt(game.height-2)+1)));

        for(int i=0; i<=10; i++)
            caseBonus.add(new CaseBonus (game,new Case(game.randomGen.nextInt(game.width),game.randomGen.nextInt(game.height-2)+1)));

        road.add(new Lane(game,game.height-1,0.0D));

    }


    @Override
    public boolean isSafe(Case c) {
        for(CaseBonus b : caseBonus)
            if(b.canAddScore(c)) {
                game.score++;
                caseBonus.remove(b);
            }
        for(Trap p : traps)
            if(!p.isSafe(c))
                return false;

        return road.get(c.ord).isSafe(c);

    }

    @Override
    public boolean isWinningPosition(Case c) {
        return c.ord == game.height-1;
    }

    @Override
    public void update() {
        Iterator i = this.road.iterator();

        while (i.hasNext()) {
            Lane lane = (Lane) i.next();
            lane.update();
        }
        for (CaseBonus b : caseBonus)
            game.getGraphic().add(new Element(b.getPosition().absc, b.getPosition().ord, b.getColor()));

        for (Trap p : traps)
            game.getGraphic().add(new Element(p.getPosition().absc, p.getPosition().ord, p.getColor()));





    }

}
