package fr.univ_amu.m1info.board_game_library.model;

// Game mode for two human players
public class HumanVsHumanMode extends GameMode {

    public HumanVsHumanMode(OthelloLogic logic) {
        super(logic);
    }

    @Override
    public void playMove(int row, int col, Piece player) {
        if (!logic.isValidMove(row, col, player)) {
            throw new IllegalArgumentException("Invalid move.");
        }
        logic.flipPieces(row, col, player); // Flip opponent pieces
        logic.getBoard().placePiece(row, col, player); // Place the player's piece
    }
}
