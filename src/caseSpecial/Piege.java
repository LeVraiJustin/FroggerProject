package caseSpecial;

import gameCommons.Game;
import graphicalElements.Element;
import util.Case;

import java.awt.*;

public class Piege {
        private Game game;
        private Case position;
        private final Color color = Color.BLACK;

        public Piege(Game game, Case position){
            this.game = game;
            this.position = position;
            this.addToGraphics();
        }



        public Case getPosition() {
            return position;
        }


        private void addToGraphics() {
            game.getGraphic().add(new Element(position.absc , position.ord, color));
        }


}
