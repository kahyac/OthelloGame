package fr.univ_amu.m1info.board_game_library.application;

import fr.univ_amu.m1info.board_game_library.controller.BoardGameController;
import fr.univ_amu.m1info.board_game_library.model.BoardGameConfiguration;

/**
 * Interface responsible for launching a board game application.
 * It defines a method to initialize and start the game with the provided configuration,
 * controller, and view initializer.
 */
public interface BoardGameApplicationLauncher {

    /**
     * Launches the board game application with the specified configuration and controller.
     *
     * @param configuration the configuration of the board game, represented by {@link BoardGameConfiguration}.
     * @param controller    the controller that manages game interactions, implemented by {@link BoardGameController}.
     */
    void launchApplication(BoardGameConfiguration configuration,
                           BoardGameController controller);
}
