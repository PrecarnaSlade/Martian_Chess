package Game;

import Pieces.Piece;
import java.util.List;

public class Player {
    boolean isAI;
    int number;
    int score;
    int globalScore;
    List<Piece> Pieces;

    public Player(boolean pIsAI, int pScore, int pNumber) {
        this.isAI = pIsAI;
        this.score = pScore;
        this.globalScore = 0;
        this.number = pNumber;
    }

    public boolean GetIsAI() {return this.isAI;}

    public int getScore() {
        return this.score;
    }

    public int getGlobalScore() {
        return this.globalScore;
    }

    public List<Piece> getPieces() {
        return this.Pieces;
    }
}
