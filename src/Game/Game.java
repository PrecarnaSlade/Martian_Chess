package Game;

import Pieces.Piece;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
    int playerNumber;
    int aiNumber;
    int playerTurn;

    Game(int pNumberOfPlayer, int pNumberOfAI) {
        this.playerNumber = pNumberOfPlayer;
        this.aiNumber = pNumberOfAI;
        this.playerTurn = 1;
    }

    public static void OneTurn(Board pBoard, Player[] pPlayer, int pTurn) {
        boolean bPosIsPossible;
        Piece oPiece;
        Player oCurrentPlayer;
        String sMove;
        String[] aMove;
        boolean bGoodStart = false;
        boolean bGoodEnd = false;
        Scanner oScanner = new Scanner(System.in);
        String[][] aMoveBis = new String[2][2];
        int[][] aMoveCoordinate = new int[2][2];

        pBoard.UpdatePossibleMoves(pTurn + 1);
        oCurrentPlayer = pPlayer[pTurn];


        while (!bGoodStart || !bGoodEnd) {
            bGoodStart = false;
            System.out.print("Player " + (pTurn + 1) + " turn !\nEnter your play (xStart-yStart xDest-yDest) then press enter : ");
            sMove = oScanner.nextLine();
            aMove = sMove.split(" ");
            if (aMove.length != 2) {
                continue;
            }
            aMoveBis[0] = aMove[0].split("-");
            aMoveBis[1] = aMove[1].split("-");
            if (aMoveBis[0].length != 2 || aMoveBis[1].length != 2) {
                continue;
            }

            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 2; j++) {
                    aMoveCoordinate[i][j] = Integer.parseInt(aMoveBis[i][j]);
                }
            }

            if (Board.GetOwnerByPos(aMoveCoordinate[0][0], aMoveCoordinate[0][1]) == (pTurn + 1)) {
                bPosIsPossible = false;
                oPiece = pBoard.GetPieceByPos(aMoveCoordinate[0][0], aMoveCoordinate[0][1]);
                if (oPiece == null) {
                    continue;
                }
                bGoodStart = true;
                for (Pos iPos : oPiece.PossiblePos) {
                    if (iPos.x == aMoveCoordinate[1][0] && iPos.y == aMoveCoordinate[1][1]) {
                        bPosIsPossible = true;
                        break;
                    }
                }
                if (bPosIsPossible) {
                    if (pBoard.board[aMoveCoordinate[1][0]][aMoveCoordinate[1][1]] != 0) {
                        oCurrentPlayer.score += pBoard.GetPieceByPos(aMoveCoordinate[1][0], aMoveCoordinate[1][1]).pts;
                        pBoard.DelPiece(aMoveCoordinate[1][0], aMoveCoordinate[1][1]);
                    }
                    oPiece.Move(aMoveCoordinate[1][0], aMoveCoordinate[1][1]);
                    pBoard.board[aMoveCoordinate[0][0]][aMoveCoordinate[0][1]] = 0;
                    pBoard.board[aMoveCoordinate[1][0]][aMoveCoordinate[1][1]] = oPiece.id;
                    bGoodEnd = true;
                }
            }
        }
    }

    private boolean IsFinished(List<Piece> pPieces) {
        return GetPieceMoveByCurrentPlayer(pPieces).size() == 0;
    }

    private void NextTurn() {
        this.playerTurn += 1;
    }

    private List<Piece> GetPieceMoveByCurrentPlayer(List<Piece> pPieceArray) {
        List<Piece> lRtnList = new ArrayList<>();

        for (Piece oPiece : pPieceArray) {
            if (oPiece.owner == this.playerTurn) {
                lRtnList.add(oPiece);
            }
        }
        return lRtnList;
    }
}
