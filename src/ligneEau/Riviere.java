package ligneEau;

import gameCommons.Game;

public class Riviere {
    private Game game;
    private int premiereLane;
    private int largeur;

    public Riviere(Game game, int premiereLane, int largeur){
        this.game = game;
        this.premiereLane = premiereLane;
        this.largeur = largeur;
    }
}
