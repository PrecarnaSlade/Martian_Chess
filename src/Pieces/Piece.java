package Pieces;

import Game.Board;
import Game.Pos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Piece {
    public int type;
    public int pts;
    public int id;
    public int owner;
    public Pos position;
    public List<Pos> PossiblePos = new ArrayList<>();

    public Piece(int pType, int pPoints, Pos pPosition, int ID, int pOwner) {
        this.type = pType;
        this.pts = pPoints;
        this.position = pPosition;
        this.id = ID;
        this.owner = pOwner;
    }

    public boolean IsQueen() {
        return this.type == 3;
    }
    public boolean IsDrone() {
        return this.type == 2;
    }
    public boolean IsPawn() {
        return this.type == 1;
    }
    public boolean CanMerge(Piece pPiece) {
        if (this.type == 3) {
            return false;
        }
        if (this.type == 1 && pPiece.type != 3) {
            return true;
        }
        return this.type == 2 && pPiece.type == 1;
    }

    public boolean CanMove(Pos pPos) {
        int x = pPos.x;
        int y = pPos.y;

        for (Pos iPos : PossiblePos) {
            if (x == iPos.x && y == iPos.y) {
                return true;
            }
        }
        return false;
    }

    public void Move(Pos pPos) {
        this.owner = Board.GetOwnerByPos(pPos);
        this.position = pPos;

    }

    public void Move(int x, int y) {
        this.position.x = x;
        this.position.y = y;
        this.owner = Board.GetOwnerByPos(this.position);
    }

    public void GetMove(Board pBoard, int pPlayerTurn) {
        if (this.owner != pPlayerTurn) {
            return;
        }
        Pos[] aAllPos;
        int nPieceX = this.position.x;
        int nPieceY = this.position.y;
        int nLength = pBoard.length;
        int[][] aBoardArray = pBoard.board;
        Pos oGhostPos;
        Piece oTargetedPiece;

        if (IsPawn()) {
            int[] aFirstNum = {-1, 1};
            int[] aSecondNum = {-1, -1, 1, 1};
            aAllPos = new Pos[4];

            for (int i = 0; i < 4; i++) {
                oGhostPos = new Pos(nPieceX + aFirstNum[i % 2], nPieceY + aSecondNum[i], pBoard.length);
                aAllPos[i] = oGhostPos;
            }
        } else if (IsDrone()) {
            int nStartingPos;
            int nSumPossibleMoves;
            int nPossibleMovesAdded;
            int nRight = 1;
            int nLeft = 1;
            int nUp = 1;
            int nDown = 1;
            aAllPos = new Pos[0];

            for (int iDelta = 1; iDelta < 3; iDelta++) {
                nSumPossibleMoves = nRight + nLeft + nUp + nDown;
                nPossibleMovesAdded = 0;

                aAllPos = Arrays.copyOf(aAllPos, aAllPos.length + nSumPossibleMoves);
                nStartingPos = aAllPos.length - nSumPossibleMoves;

                if (nRight != 0) {
                    oGhostPos = new Pos(nPieceX + iDelta, nPieceY, pBoard.length);
                    aAllPos[nStartingPos + nPossibleMovesAdded] = oGhostPos;
                    nPossibleMovesAdded += 1;
                    if (!pBoard.IsEmpty(oGhostPos)) {
                        nRight = 0;
                    }
                }
                if (nLeft != 0) {
                    oGhostPos = new Pos(nPieceX - iDelta, nPieceY, pBoard.length);
                    aAllPos[nStartingPos + nPossibleMovesAdded] = oGhostPos;
                    nPossibleMovesAdded += 1;
                    if (!pBoard.IsEmpty(oGhostPos)) {
                        nLeft = 0;
                    }
                }
                if (nUp != 0) {
                    oGhostPos = new Pos(nPieceX, nPieceY - iDelta, pBoard.length);
                    aAllPos[nStartingPos + nPossibleMovesAdded] = oGhostPos;
                    nPossibleMovesAdded += 1;
                    if (!pBoard.IsEmpty(oGhostPos)) {
                        nUp = 0;
                    }
                }
                if (nDown != 0) {
                    oGhostPos = new Pos(nPieceX, nPieceY + iDelta, pBoard.length);
                    aAllPos[nStartingPos + nPossibleMovesAdded] = oGhostPos;
                    if (!pBoard.IsEmpty(oGhostPos)) {
                        nDown = 0;
                    }
                }
            }
        } else if (IsQueen()) {
            int nN = 1;
            int nNE = 1;
            int nE = 1;
            int nSE = 1;
            int nS = 1;
            int nSW = 1;
            int nW = 1;
            int nNW = 1;
            int nStartingPos;
            int nSumPossibleMoves;
            int nPossibleMovesAdded;
            aAllPos = new Pos[0];

            for (int iDelta = 1; iDelta <= nLength - 1; iDelta++) {
                nSumPossibleMoves = nN + nNE + nE + nSE + nS + nSW + nW + nNW;
                nPossibleMovesAdded = 0;

                aAllPos = Arrays.copyOf(aAllPos, aAllPos.length + nSumPossibleMoves);
                nStartingPos = aAllPos.length - nSumPossibleMoves;


                if (nN != 0) {
                    oGhostPos = new Pos(nPieceX, nPieceY - iDelta, pBoard.length);
                    aAllPos[nStartingPos + nPossibleMovesAdded] = oGhostPos;
                    nPossibleMovesAdded += 1;
                    if (!pBoard.IsEmpty(oGhostPos)) {
                        nN = 0;
                    }
                }
                if (nNE != 0) {
                    oGhostPos = new Pos(nPieceX + iDelta, nPieceY - iDelta, pBoard.length);
                    aAllPos[nStartingPos + nPossibleMovesAdded] = oGhostPos;
                    nPossibleMovesAdded += 1;
                    if (!pBoard.IsEmpty(oGhostPos)) {
                        nNE = 0;
                    }
                }
                if (nE != 0) {
                    oGhostPos = new Pos(nPieceX + iDelta, nPieceY, pBoard.length);
                    aAllPos[nStartingPos + nPossibleMovesAdded] = oGhostPos;
                    nPossibleMovesAdded += 1;
                    if (!pBoard.IsEmpty(oGhostPos)) {
                        nE = 0;
                    }
                }
                if (nSE != 0) {
                    oGhostPos = new Pos(nPieceX + iDelta, nPieceY + iDelta, pBoard.length);
                    aAllPos[nStartingPos + nPossibleMovesAdded] = oGhostPos;
                    nPossibleMovesAdded += 1;
                    if (!pBoard.IsEmpty(oGhostPos)) {
                        nSE = 0;
                    }
                }
                if (nS != 0) {
                    oGhostPos = new Pos(nPieceX, nPieceY + iDelta, pBoard.length);
                    aAllPos[nStartingPos + nPossibleMovesAdded] = oGhostPos;
                    nPossibleMovesAdded += 1;
                    if (!pBoard.IsEmpty(oGhostPos)) {
                        nS = 0;
                    }
                }
                if (nSW != 0) {
                    oGhostPos = new Pos(nPieceX - iDelta, nPieceY + iDelta, pBoard.length);
                    aAllPos[nStartingPos + nPossibleMovesAdded] = oGhostPos;
                    nPossibleMovesAdded += 1;
                    if (!pBoard.IsEmpty(oGhostPos)) {
                        nSW = 0;
                    }
                }
                if (nW != 0) {
                    oGhostPos = new Pos(nPieceX - iDelta, nPieceY, pBoard.length);
                    aAllPos[nStartingPos + nPossibleMovesAdded] = oGhostPos;
                    nPossibleMovesAdded += 1;
                    if (!pBoard.IsEmpty(oGhostPos)) {
                        nW = 0;
                    }
                }
                if (nNW != 0) {
                    oGhostPos = new Pos(nPieceX - iDelta, nPieceY - iDelta, pBoard.length);
                    aAllPos[nStartingPos + nPossibleMovesAdded] = oGhostPos;
                    if (!pBoard.IsEmpty(oGhostPos)) {
                        nNW = 0;
                    }
                }
            }

        } else {
            aAllPos = new Pos[0];
        }

        this.PossiblePos = new ArrayList<>();

        for (Pos iPos : aAllPos) {
            if (iPos.IsCorrect(nLength)) {
                oTargetedPiece = pBoard.GetPieceByPos(iPos);
                if (aBoardArray[iPos.x][iPos.y] == 0 || this.CanMerge(oTargetedPiece)) {
                    this.PossiblePos.add(iPos);
                }
            }
        }
    }

    public void Merge(Piece pPiece) {
        int nNewPieceType = this.type + pPiece.type;
        if (nNewPieceType <= 3) {
            this.type = nNewPieceType;
        }
    }
}

