package fr.univ_amu.m1info.board_game_library.model;

public class GameStateManager {
    private final OthelloBoard board;
    private final MoveValidator moveValidator;
    private final PieceFlipper pieceFlipper;
    private Piece currentPlayer;

    public GameStateManager(OthelloBoard board, MoveValidator moveValidator, PieceFlipper pieceFlipper) {
        this.board = board;
        this.moveValidator = moveValidator;
        this.pieceFlipper = pieceFlipper;
        this.currentPlayer = Piece.BLACK; // Noir commence toujours
    }

    public boolean playMove(int row, int col) {
        if (moveValidator.isValidMove(board, row, col, currentPlayer)) {
            board.placePiece(row, col, currentPlayer);
            pieceFlipper.flipPieces(board, row, col, currentPlayer);
            togglePlayer();
            return true;
        }
        return false;
    }

    private void togglePlayer() {
        currentPlayer = (currentPlayer == Piece.BLACK) ? Piece.WHITE : Piece.BLACK;
    }

    public boolean isGameOver() {
        // Logic to check if the game is over
        return false;
    }
}
