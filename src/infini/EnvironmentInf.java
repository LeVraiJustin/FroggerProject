package infini;


import gameCommons.Game;
import gameCommons.IEnvironment;
import util.Case;

import java.util.ArrayList;
import java.util.Iterator;

public class EnvironmentInf implements IEnvironment {
    private Game game;
    private ArrayList<LaneInf> road;

    public EnvironmentInf(Game game) {
        this.game = game;
        road = new ArrayList<LaneInf>();

        road.add(new LaneInf(game,0,0.0D));
        road.add(new LaneInf(game,1,0.0D));
        for(int i = 2; i < game.height; ++i) {
            this.road.add(new LaneInf(game, i));
        }
    }


    @Override
    public boolean isSafe(Case c) {
        return road.get(c.ord).isSafe(c);
    }

    @Override
    public boolean isWinningPosition(Case c) {
        return false;
    }

    @Override
    public void update() {
        Iterator var2 = this.road.iterator();

        while(var2.hasNext()) {
            LaneInf lane = (LaneInf)var2.next();
            lane.update();
        }
    }

    public void addLane(){
        road.add(new LaneInf(game, road.size()));
    }
}
