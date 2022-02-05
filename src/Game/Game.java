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

    public void Start(Board pBoard) {
        List<Piece> lUserPieces;
        Scanner oScanner = new Scanner(System.in);
        String sMove;
        String[] aMove;
        String[][] aMoveBis = new String[2][2];
        int[][] aMoveCoordinate = new int[2][2];

        while (!IsFinished(pBoard.Pieces)) {
            lUserPieces = GetPieceMoveByCurrentPlayer(pBoard.Pieces);
            System.out.print("Player " + this.playerTurn + " turn !\nEnter your play (xStart-yStart xDest-yDest) then press enter :");
            sMove = oScanner.nextLine();
            System.out.println("\n");
            aMove = sMove.split(" ");
            assert aMove.length == 2;
            aMoveBis[0] = aMove[0].split("-");
            aMoveBis[1] = aMove[1].split("-");

            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 2; j++) {
                    aMoveCoordinate[i][j] = Integer.parseInt(aMoveBis[i][j]);
                }
            }

            if (Board.GetOwnerByPos(aMoveCoordinate[0][0], aMoveCoordinate[0][1]) == this.playerTurn ) {
                
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