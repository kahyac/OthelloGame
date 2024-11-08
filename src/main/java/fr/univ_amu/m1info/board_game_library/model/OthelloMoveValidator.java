package fr.univ_amu.m1info.board_game_library.model;

public class OthelloMoveValidator implements MoveValidator {

    @Override
    public boolean isValidMove(OthelloBoard board, int row, int col, Piece currentPlayer) {
        if (board.getPieceAt(row, col) != Piece.EMPTY) {
            return false;
        }
        // Logic to check if placing a piece here would capture any opponent pieces
        return hasCapturablePieces(board, row, col, currentPlayer);
    }

    private boolean hasCapturablePieces(OthelloBoard board, int row, int col, Piece currentPlayer) {
        // Check all directions for possible captures
        // Return true if any pieces can be captured
        return true;
    }
}
