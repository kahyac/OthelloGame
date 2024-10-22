package fr.univ_amu.m1info.board_game_library.graphics;

import fr.univ_amu.m1info.board_game_library.graphics.configuration.BoardGameConfiguration;

import fr.univ_amu.m1info.board_game_library.graphics.javafx.app.JavaFXBoardGameApplication;
import javafx.application.Application;


public class JavaFXBoardGameApplicationLauncher implements BoardGameApplicationLauncher{
    private static JavaFXBoardGameApplicationLauncher instance = null;
    private BoardGameConfiguration configuration;
    private BoardGameController controller;
    private ViewInitializer onStart;


    private JavaFXBoardGameApplicationLauncher(){}

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

    public BoardGameConfiguration getConfiguration() {
        return configuration;
    }

    public BoardGameController getController() {
        return controller;
    }

    public ViewInitializer getOnStart() {
        return onStart;
    }

    @Override
    public void launchApplication(BoardGameConfiguration configuration,
                                  BoardGameController controller,
                                  ViewInitializer onStart) {
        this.configuration = configuration;
        this.controller = controller;
        this.onStart = onStart;
        Application.launch(JavaFXBoardGameApplication.class);
    }
}
