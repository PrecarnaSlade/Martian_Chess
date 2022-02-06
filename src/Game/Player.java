package Game;

import Pieces.Piece;
import java.util.List;

public class Player {
    boolean isAI;
    public int score;
    List<Piece> Pieces;

    public Player(boolean pIsAI, int pScore) {
        this.isAI = pIsAI;
        this.score = pScore;
    }

}
