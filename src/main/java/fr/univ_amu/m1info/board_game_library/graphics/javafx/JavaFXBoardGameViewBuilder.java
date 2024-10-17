package fr.univ_amu.m1info.board_game_library.graphics.javafx;

import fr.univ_amu.m1info.board_game_library.graphics.BoardGameView;
import fr.univ_amu.m1info.board_game_library.graphics.BoardGameViewBuilder;
import javafx.stage.Stage;

public class JavaFXBoardGameViewBuilder implements BoardGameViewBuilder {
    JavaFXBoardGameView boardGameView;

    public JavaFXBoardGameViewBuilder(Stage primaryStage) {
        boardGameView = new JavaFXBoardGameView(primaryStage);
    }

    public BoardGameViewBuilder resetView(){
        boardGameView.reset();
        return this;
    }


    @Override
    public BoardGameViewBuilder setBoardGameDimensions(int rowCount, int columnCount) {
        boardGameView.getBoardGridView().setDimensions(rowCount, columnCount);
        return this;
    }

    @Override
    public BoardGameViewBuilder setTitle(String title) {
        boardGameView.getStage().setTitle(title);
        return this;
    }

    @Override
    public BoardGameViewBuilder addLabel(String id, String initialText) {
        boardGameView.getBar().addLabel(id, initialText);
        return this;
    }

    @Override
    public BoardGameViewBuilder addButton(String id, String label) {
        boardGameView.getBar().addButton(id, label);
        return this;
    }

    @Override
    public BoardGameView getView() {
        return boardGameView;
    }

}
