package Game;

import java.util.ArrayList;
import java.util.List;
import Pieces.Piece;

public class Board {
    public int length;
    public int[][] board;
    public List<Piece> Pieces = new ArrayList<>();


    public Board(int pLength) {
        if (pLength == 4 || pLength == 8) {
            this.length = pLength;
            this.board = new int[pLength][8];

            this.board[0][0] = 3;
            this.board[0][1] = 3;
            this.board[0][2] = 2;
            this.board[1][0] = 3;
            this.board[1][1] = 2;
            this.board[1][2] = 1;
            this.board[2][0] = 2;
            this.board[2][1] = 1;
            this.board[2][2] = 1;


            if (pLength == 8) {
                this.board = AxialSymmetry(this.board);
            } else {
                this.board[1][5] = 1;
                this.board[2][5] = 1;
                this.board[3][5] = 2;
                this.board[1][6] = 1;
                this.board[2][6] = 2;
                this.board[3][6] = 3;
                this.board[1][7] = 2;
                this.board[2][7] = 3;
                this.board[3][7] = 3;
            }

            Piece oPiece;
            Pos oPos;
            int nCurrentCell;
            int id = 1;
            int nOwner;
            for (int i = 0; i < pLength; i++) {
                for (int j = 0; j < 8; j++) {
                    nCurrentCell = this.board[i][j];
                    if (nCurrentCell != 0) {
                        oPos = new Pos(i, j, pLength);
                        if (j < 4 && i < 4) {
                            nOwner = 1;
                        } else if (j > 4 && i < 4) {
                            nOwner = 2;
                        } else if (j < 4 && i > 4) {
                            nOwner = 3;
                        } else {
                            nOwner = 4;
                        }
                        oPiece = new Piece(nCurrentCell, nCurrentCell, oPos, id, nOwner);
                        this.board[i][j] = id;
                        id += 1;
                        assert this.Pieces != null;
                        this.Pieces.add(oPiece);
                    }
                }
            }
        }
    }

    private int[][] AxialSymmetry(int[][] pArray) {
        int xDelta;
        int yDelta;
        int nCurrentCell;
        int nMax = 3;

        for (int i = 0; i < nMax; i++) {
            for (int j = 0; j < nMax; j++) {
                nCurrentCell = pArray[i][j];
                xDelta = this.length - 1 - i;
                yDelta = this.length - 1 - j;
                if (true) {
                    pArray[i][yDelta] = nCurrentCell;
                }
                if (true) {
                    pArray[xDelta][j] = nCurrentCell;
                }
                if (true && true) {
                    pArray[xDelta][yDelta] = nCurrentCell;
                }
            }
        }

        return pArray;
    }

    public void Display() {
        String sBoard = "";

        for (int i = 0; i < this.length; i++) {
            sBoard += " ———";
            if (i == 3) {
                sBoard += " ";
            }
        }
        sBoard += "\n";

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < this.length; j++) {
                sBoard += "| " + this.board[j][i] + " ";
                if (j == 3 && this.length > 4) {
                    sBoard += "|";
                }
            }

            sBoard += "|\n";

            if (i == 3) {
                for (int k = 0; k < this.length; k++) {
                    sBoard += " ———";
                    if (k == 3) {
                        sBoard += " ";
                    }
                }
                sBoard += "\n";
                for (int l = 0; l < this.length; l++) {
                    sBoard += " ———";
                    if (l == 3) {
                        sBoard += " ";
                    }
                }
                sBoard += "\n";
            }
        }
        for (int i = 0; i < this.length; i++) {
            sBoard += " ———";
            if (i == 3) {
                sBoard += " ";
            }
        }
        System.out.println(sBoard);
    }

    public boolean IsEmpty(Pos pPos) {
        int x = pPos.x;
        int y = pPos.y;
        if (!pPos.IsCorrect(this.length)) {
            return true;
        }
        return this.board[x][y] == 0;
    }

    public Piece GetPieceByPos(Pos pPos) {
        int xPiece;
        int yPiece;
        int x = pPos.x;
        int y = pPos.y;

        for (Piece piece : this.Pieces) {
            xPiece = piece.position.x;
            yPiece = piece.position.y;
            if (xPiece == x && yPiece == y) {
                return piece;
            }
        }
        return null;
    }

    public Piece GetPieceByPos(int x, int y) {
        int xPiece;
        int yPiece;

        for (Piece piece : this.Pieces) {
            xPiece = piece.position.x;
            yPiece = piece.position.y;
            if (xPiece == x && yPiece == y) {
                return piece;
            }
        }
        return null;
    }

    public static int GetOwnerByPos(Pos pPos) {
        int x = pPos.x;
        int y = pPos.y;
        if (y < 4 && x < 4) {
            return  1;
        } else if (y > 4 && x < 4) {
            return 2;
        } else if (y < 4 && x > 4) {
            return 3;
        } else {
            return 4;
        }
    }

    public static int GetOwnerByPos(int x, int y) {
        if (y < 4 && x < 4) {
            return  1;
        } else if (y > 4 && x < 4) {
            return 2;
        } else if (y < 4 && x > 4) {
            return 3;
        } else {
            return 4;
        }
    }

    public void UpdatePossibleMoves(int pPlayerTurn) {
        for (Piece piece : this.Pieces) {
            piece.GetMove(this, pPlayerTurn);
        }
    }

    public void DelPiece(int x, int y) {
        int nPieceID = GetPieceByPos(x, y).id;
        for (int i = 0; i < this.Pieces.size(); i++) {
            if (this.Pieces.get(i).id == nPieceID) {
                this.Pieces.remove(i);
                return;
            }
        }
    }

    public void DelPiece(Pos pPos) {
        int nPieceID = GetPieceByPos(pPos).id;
        for (int i = 0; i < this.Pieces.size(); i++) {
            if (this.Pieces.get(i).id == nPieceID) {
                this.Pieces.remove(i);
                return;
            }
        }
    }

    public void DelPiece(int pID) {
        for (int i = 0; i < this.Pieces.size(); i++) {
            if (this.Pieces.get(i).id == pID) {
                this.Pieces.remove(i);
                return;
            }
        }
    }
}
