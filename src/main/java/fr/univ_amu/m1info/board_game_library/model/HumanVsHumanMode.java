package fr.univ_amu.m1info.board_game_library.model;

// Game mode for two human players
public class HumanVsHumanMode extends GameMode {

    public HumanVsHumanMode(OthelloLogic logic) {
        super(logic);
    }

    @Override
    public void playMove(Position position, Piece player) {
        if (!logic.isValidMove(position.getRow(), position.getCol(), player)) {
            throw new IllegalArgumentException("Invalid move.");
        }
        logic.flipPieces(position.getRow(), position.getCol(), player); // Flip opponent pieces
        logic.getBoard().placePiece(position.getRow(), position.getCol(), player); // Place the player's piece
    }
}
