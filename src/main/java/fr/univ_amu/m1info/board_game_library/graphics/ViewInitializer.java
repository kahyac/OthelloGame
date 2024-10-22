package fr.univ_amu.m1info.board_game_library.graphics;

/**
 * Interface responsible for initializing the view of a board game.
 * It defines a method to set up the initial state of the game view.
 */
public interface ViewInitializer {

    /**
     * Initializes the specified game view.
     *
     * @param view the {@link BoardGameView} to be initialized.
     */
    void initialize(BoardGameView view);
}
