package fr.univ_amu.m1info.board_game_library.graphics;

import fr.univ_amu.m1info.board_game_library.graphics.configuration.BoardGameConfiguration;

import fr.univ_amu.m1info.board_game_library.graphics.javafx.app.JavaFXBoardGameApplication;
import javafx.application.Application;


/**
 * Singleton class responsible for launching a board game application using JavaFX.
 * It implements the {@link BoardGameApplicationLauncher} interface and manages the configuration, controller,
 * and view initializer for the game.
 */
public class JavaFXBoardGameApplicationLauncher implements BoardGameApplicationLauncher {

    /** The singleton instance of the launcher. */
    private static JavaFXBoardGameApplicationLauncher instance = null;

    /** The configuration of the board game. */
    private BoardGameConfiguration configuration;

    /** The controller that manages game interactions. */
    private BoardGameController controller;

    /** The view initializer to set up the initial game view. */
    private ViewInitializer onStart;

    /** Private constructor to prevent direct instantiation. */
    private JavaFXBoardGameApplicationLauncher() {}

    /**
     * Retrieves the singleton instance of the {@code JavaFXBoardGameApplicationLauncher}.
     * If the instance does not already exist, it is created in a thread-safe manner.
     *
     * @return the singleton instance of the launcher.
     */
    public static JavaFXBoardGameApplicationLauncher getInstance() {
        JavaFXBoardGameApplicationLauncher result = instance;
        if (result != null) {
            return result;
        }
        synchronized(JavaFXBoardGameApplicationLauncher.class) {
            if (instance == null) {
                instance = new JavaFXBoardGameApplicationLauncher();
            }
            return instance;
        }
    }

    /**
     * Retrieves the current board game configuration.
     *
     * @return the {@link BoardGameConfiguration} for the game.
     */
    public BoardGameConfiguration getConfiguration() {
        return configuration;
    }

    /**
     * Retrieves the current controller managing the game.
     *
     * @return the {@link BoardGameController} for the game.
     */
    public BoardGameController getController() {
        return controller;
    }

    /**
     * Retrieves the view initializer that sets up the initial state of the game view.
     *
     * @return the {@link ViewInitializer} for the game.
     */
    public ViewInitializer getOnStart() {
        return onStart;
    }

    /**
     * Launches the JavaFX board game application with the specified configuration, controller, and view initializer.
     * This method sets the internal fields and starts the JavaFX application.
     *
     * @param configuration the configuration of the board game, represented by {@link BoardGameConfiguration}.
     * @param onStart       the view initializer that sets up the game view, implemented by {@link ViewInitializer}.
     * @param controller    the controller that manages game interactions, implemented by {@link BoardGameController}.
     */
    @Override
    public void launchApplication(BoardGameConfiguration configuration,
                                  ViewInitializer onStart, BoardGameController controller) {
        this.configuration = configuration;
        this.controller = controller;
        this.onStart = onStart;
        Application.launch(JavaFXBoardGameApplication.class);
    }
}

