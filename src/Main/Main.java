package Main;

import Game.Board;
import Game.Game;
import Game.Player;

public class Main {

    public static void main(String[] args) {
        Player[] aPlayer = {new Player(false, 0), new Player(false, 0)};
        Board boardMain = new Board(4);
//        boardMain.UpdatePossibleMoves();
        boardMain.Display();
        Game.OneTurn(boardMain, aPlayer, 0);
        boardMain.Display();
    }
}
