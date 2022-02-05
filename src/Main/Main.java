package Main;

import Game.Board;

public class Main {

    public static void main(String[] args) {
        Board boardMain = new Board(4);
        boardMain.UpdatePossibleMoves();
        boardMain.Display();
    }
}
