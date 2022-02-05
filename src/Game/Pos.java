package Game;

public class Pos {
    public int x;
    public int y;
    public Pos(int x, int y) {
        if ( x >= 0 && x < 4) {
            this.x = x;
        } else {
            this.x = -1;
        }
        if ( y >= 0 && y < 4) {
            this.y = y;
        } else {
            this.y = -1;
        }
    }

    public boolean IsCorrect(int pLength) {
        return this.x >= 0 && this.x < pLength && this.y >= 0 && this.y < pLength;
    }
}
