package environment;

import gameCommons.Game;
import gameCommons.IEnvironment;
import util.Case;

import java.util.ArrayList;
import java.util.Iterator;

public class Environment implements IEnvironment {
    private Game game;
    private ArrayList<Lane> road;

    public Environment(Game game) {
        this.game = game;
        road = new ArrayList<Lane>();

        road.add(new Lane(game,0,0.0D));
        for(int i = 1; i < game.height - 1; ++i) {
            this.road.add(new Lane(game, i));
        }
        road.add(new Lane(game,game.height-1,0.0D));
    }


    @Override
    public boolean isSafe(Case c) {
        return road.get(c.ord).isSafe(c);
    }

    @Override
    public boolean isWinningPosition(Case c) {
        return c.ord == game.height-1;
    }

    @Override
    public void update() {
        Iterator var2 = this.road.iterator();

        while(var2.hasNext()) {
            Lane lane = (Lane)var2.next();
            lane.update();
        }
    }

}
