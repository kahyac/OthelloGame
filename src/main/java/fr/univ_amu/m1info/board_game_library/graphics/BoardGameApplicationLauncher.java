package fr.univ_amu.m1info.board_game_library.graphics;

import fr.univ_amu.m1info.board_game_library.graphics.configuration.BoardGameConfiguration;

/**
 * Interface responsible for launching a board game application.
 * It defines a method to initialize and start the game with the provided configuration,
 * controller, and view initializer.
 */
public interface BoardGameApplicationLauncher {

    /**
     * Launches the board game application with the specified configuration, controller, and startup logic.
     *
     * @param configuration the configuration of the board game, represented by {@link BoardGameConfiguration}.
     * @param onStart       the logic for initializing the game view, implemented by {@link ViewInitializer}.
     * @param controller    the controller that manages game interactions, implemented by {@link BoardGameController}.
     */
    void launchApplication(BoardGameConfiguration configuration,
                           ViewInitializer onStart, BoardGameController controller);
}
