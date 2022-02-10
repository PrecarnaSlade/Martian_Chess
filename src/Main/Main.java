package Main;

import Game.Board;
import Game.Game;
import Game.Player;

public class Main {

    public static void main(String[] args) {
//        Player[] aPlayer = {new Player(false, 0, 1), new Player(false, 0, 2)};
//        Board boardMain = new Board(4);
        Game oGame = new Game(2, 0, 2);
        oGame.Play(4);
    }
}
