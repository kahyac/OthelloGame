package fr.univ_amu.m1info.board_game_library.model;

// Abstract base class for different game modes
public abstract class GameMode {
    protected final OthelloLogic logic;

    public GameMode(OthelloLogic logic) {
        if (logic == null) {
            throw new IllegalArgumentException("OthelloLogic cannot be null.");
        }
        this.logic = logic;
    }

    public OthelloLogic getLogic() {
        return logic;
    }

    // Abstract method for executing a move, implemented by specific game modes
    public abstract void playMove(Position position, Piece player);
}
