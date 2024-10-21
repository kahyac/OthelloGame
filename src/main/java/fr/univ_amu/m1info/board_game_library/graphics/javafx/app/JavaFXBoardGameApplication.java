package fr.univ_amu.m1info.board_game_library.graphics.javafx.app;

import fr.univ_amu.m1info.board_game_library.graphics.*;
import fr.univ_amu.m1info.board_game_library.graphics.configuration.BoardGameConfiguration;
import fr.univ_amu.m1info.board_game_library.graphics.javafx.JavaFXBoardGameViewBuilder;
import fr.univ_amu.m1info.board_game_library.graphics.view.BoardGameView;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.function.Consumer;

public class JavaFXBoardGameApplication extends Application {
    private static BoardGameConfiguration configuration;
    private static BoardGameController controller;
    private static Consumer<BoardGameView> onStart;

    @Override
    public void start(Stage stage) {
        final JavaFXBoardGameViewBuilder viewBuilder = new JavaFXBoardGameViewBuilder(stage);
        new BoardGameConfigurator().configure(viewBuilder, configuration);
        BoardGameView view = viewBuilder.getView();
        view.setController(controller);
        controller.setView(view);
        onStart.accept(view);
        stage.show();
    }

    public static void start(BoardGameConfiguration configuration,
                             BoardGameController controller,
                             Consumer<BoardGameView> onStart
                             ) {
        JavaFXBoardGameApplication.configuration = configuration;
        JavaFXBoardGameApplication.controller = controller;
        JavaFXBoardGameApplication.onStart = onStart;
        launch();
    }

}
