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
    public void playMove(Position position, Piece currentPlayer) {
        if (currentPlayer == Piece.BLACK) {
            // Coup du joueur humain
            if (!logic.isValidMove(position.getRow(), position.getCol(), currentPlayer)) {
                throw new IllegalArgumentException("Invalid move.");
            }
            // Met à jour le plateau pour le joueur noir
            logic.flipPieces(position.getRow(), position.getCol(), currentPlayer);
            logic.getBoard().placePiece(position.getRow(), position.getCol(), currentPlayer);
        } else if (currentPlayer == Piece.WHITE) {
            // Coup de l'IA
            Position aiMove = getBestMove(Piece.WHITE);
            if (aiMove != null) {
                System.out.println("AI plays at (" + aiMove.getRow() + ", " + aiMove.getCol() + ")");
                logic.flipPieces(aiMove.getRow(), aiMove.getCol(), Piece.WHITE);
                logic.getBoard().placePiece(aiMove.getRow(), aiMove.getCol(), Piece.WHITE);
            } else {
                System.out.println("AI has no valid moves!");
            }
        }
    }

    // Finds the best move for the AI (random valid move)
    private Position getBestMove(Piece aiPlayer) {
        List<Position> validMoves = getValidMoves(aiPlayer);
        return validMoves.isEmpty() ? null : validMoves.get(random.nextInt(validMoves.size()));
    }

    // Retrieves all valid moves for the given player
    private List<Position> getValidMoves(Piece player) {
        List<Position> validMoves = new ArrayList<>();
        OthelloBoard board = logic.getBoard();

        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {
                if (logic.isValidMove(row, col, player)) {
                    validMoves.add(new Position(row, col));
                }
            }
        }
        return validMoves;
    }
}
