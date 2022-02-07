package Game;

import Pieces.Piece;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
    int playerNumber;
    int aiNumber;
    int playerTurn;
    int round;
    int maxRound;
    Board oBoard;
    Player[] players;

    Game(int pNumberOfPlayer, int pNumberOfAI, int pMaxRound) {
        this.playerNumber = pNumberOfPlayer;
        this.aiNumber = pNumberOfAI;
        this.maxRound = pMaxRound;
        this.round = 1;
    }

    public void Start(int pLength) {
        this.oBoard = new Board(pLength);
        this.playerTurn = 1;
        this.players = new Player[pLength % 2];

        int nPlayerAdded = 0;
        boolean bIsAI;

        for (int i = 1; i <= pLength % 2; i++) {
            if (nPlayerAdded != this.playerNumber) {
                bIsAI = false;
            } else {
                bIsAI = true;
            }
            this.players[i - 1] = new Player(bIsAI, 0, i);
        }
    }

    public void End(Player[] pPlayers) {
        for (Player iPlayer : pPlayers) {
            iPlayer.globalScore += iPlayer.getScore();
        }
        this.playerTurn = -1;
        this.oBoard = null;
        this.round += 1;
        if (this.round > this.maxRound) {
            DisplayScore(true, pPlayers);
            System.exit(0);
        } else {
            DisplayScore(false, pPlayers);
        }
    }

    public void DisplayScore(boolean pGlobal, Player[] pPlayers) {
        String str = "";
        for (Player iPlayer : pPlayers) {
            str += "Player " + iPlayer.number + " score : ";
            if (pGlobal) {
                str += iPlayer.getGlobalScore() + "\n";
            } else {
                str += iPlayer.getScore() + "\n";
            }
        }
        System.out.println(str);
    }

    public static void OneTurn(Board pBoard, Player[] pPlayer, int pTurn) { // turn needs to be turned into Base 0 (it is in base 1)
        boolean bPosIsPossible;
        Piece oPiece;
        Piece oTargetPiece;
        Player oCurrentPlayer;
        String sMove;
        String[] aMove;
        boolean bGoodStart = false;
        boolean bGoodEnd = false;
        Scanner oScanner = new Scanner(System.in);
        String[][] aMoveBis = new String[2][2];
        int[][] aMoveCoordinate = new int[2][2];

        oCurrentPlayer = pPlayer[pTurn];
        pBoard.UpdatePossibleMoves(oCurrentPlayer.number);

        while (!bGoodStart || !bGoodEnd) {
            bGoodStart = false;
            System.out.print("Player " + (oCurrentPlayer.number) + " turn !\nEnter your play (xStart-yStart xDest-yDest) then press enter : ");
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

            if (Board.GetOwnerByPos(aMoveCoordinate[0][0], aMoveCoordinate[0][1]) == (oCurrentPlayer.number)) {
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
                        oTargetPiece = pBoard.GetPieceByPos(aMoveCoordinate[1][0], aMoveCoordinate[1][1]);
                         if (oTargetPiece.owner != oCurrentPlayer.number) {
                             oCurrentPlayer.score += oTargetPiece.pts;
                         } else {
                             oPiece.Merge(oTargetPiece);
                         }
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
