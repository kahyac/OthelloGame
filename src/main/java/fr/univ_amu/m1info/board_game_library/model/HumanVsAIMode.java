package fr.univ_amu.m1info.board_game_library.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// Game mode for a human vs AI match
public class HumanVsAIMode extends GameMode {
    private final Random random = new Random();

    public HumanVsAIMode(OthelloLogic logic) {
        super(logic);
    }

    @Override
    public void playMove(int row, int col, Piece currentPlayer) {
        if (currentPlayer == Piece.BLACK) {
            // Coup du joueur humain
            if (!logic.isValidMove(row, col, currentPlayer)) {
                throw new IllegalArgumentException("Invalid move.");
            }
            // Met à jour le plateau pour le joueur noir
            logic.flipPieces(row, col, currentPlayer);
            logic.getBoard().placePiece(row, col, currentPlayer);
        } else if (currentPlayer == Piece.WHITE) {
            // Coup de l'IA (les coordonnées sont ignorées)
            int[] aiMove = getBestMove(Piece.WHITE);
            if (aiMove != null) {
                System.out.println("AI plays at (" + aiMove[0] + ", " + aiMove[1] + ")");
                logic.flipPieces(aiMove[0], aiMove[1], Piece.WHITE);
                logic.getBoard().placePiece(aiMove[0], aiMove[1], Piece.WHITE);
            } else {
                System.out.println("AI has no valid moves!");
            }
        }
    }




    // Finds the best move for the AI (random valid move)
    private int[] getBestMove(Piece aiPlayer) {
        List<int[]> validMoves = getValidMoves(aiPlayer);
        return validMoves.isEmpty() ? null : validMoves.get(random.nextInt(validMoves.size()));
    }

    // Retrieves all valid moves for the given player
    private List<int[]> getValidMoves(Piece player) {
        List<int[]> validMoves = new ArrayList<>();
        OthelloBoard board = logic.getBoard();

        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {
                if (logic.isValidMove(row, col, player)) {
                    validMoves.add(new int[]{row, col});
                }
            }
        }
        return validMoves;
    }
}
