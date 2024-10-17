package fr.univ_amu.m1info.board_game_library.graphics.app;

import fr.univ_amu.m1info.board_game_library.graphics.BoardGameView;
import fr.univ_amu.m1info.board_game_library.graphics.BoardGameViewBuilder;
import fr.univ_amu.m1info.board_game_library.graphics.javafx.JavaFXBoardGameViewBuilder;
import javafx.application.Application;
import javafx.stage.Stage;
import java.util.function.Consumer;

public class BoardGameApplication extends Application {
    private static Consumer<BoardGameViewBuilder> viewBuilderConfigurator;
    private static Consumer<BoardGameView> viewConfigurator;

    @Override
    public void start(Stage stage) {
        final JavaFXBoardGameViewBuilder viewBuilder = new JavaFXBoardGameViewBuilder(stage);
        viewBuilderConfigurator.accept(viewBuilder);
        BoardGameView view = viewBuilder.getView();
        BoardGameApplication.viewConfigurator.accept(view);
        view.show();
    }

    public static void start(Consumer<BoardGameViewBuilder> viewBuilderConfigurator,
                             Consumer<BoardGameView> viewConfigurator) {
        BoardGameApplication.viewBuilderConfigurator = viewBuilderConfigurator;
        BoardGameApplication.viewConfigurator = viewConfigurator;
        launch();
    }
}
