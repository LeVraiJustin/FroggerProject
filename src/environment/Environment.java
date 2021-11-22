package environment;

import gameCommons.IEnvironment;
import util.Case;

public class Environment implements IEnvironment {
    @Override
    public boolean isSafe(Case c) {
        return false;
    }

    @Override
    public boolean isWinningPosition(Case c) {
        return false;
    }

    @Override
    public void update() {

    }

    //TODO

}
