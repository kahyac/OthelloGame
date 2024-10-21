package fr.univ_amu.m1info.board_game_library.graphics.javafx;

import fr.univ_amu.m1info.board_game_library.graphics.*;
import fr.univ_amu.m1info.board_game_library.graphics.javafx.bar.Bar;
import fr.univ_amu.m1info.board_game_library.graphics.javafx.board.BoardGridView;
import fr.univ_amu.m1info.board_game_library.graphics.view.BoardGameView;
import fr.univ_amu.m1info.board_game_library.graphics.view.Color;
import fr.univ_amu.m1info.board_game_library.graphics.view.Shape;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class JavaFXBoardGameView implements BoardGameView {
    private final Stage stage;
    private BoardGridView boardGridView;
    private Bar bar;
    private BoardGameController controller;

    public void setController(BoardGameController controller) {
        this.controller = controller;
    }

    public JavaFXBoardGameView(Stage stage) {
        this.stage = stage;
        stage.setOnCloseRequest(_ -> Platform.exit());
        stage.setResizable(false);
        stage.sizeToScene();
    }

    public synchronized void reset() {
        VBox vBox = new VBox();
        bar = new Bar();
        boardGridView = new BoardGridView();
        vBox.getChildren().add(bar);
        vBox.getChildren().add(boardGridView);
        Scene scene = new Scene(vBox);
        stage.setScene(scene);
    }

    @Override
    public synchronized void updateLabeledElement(String id, String newText) {
        bar.updateLabel(id, newText);
    }

    @Override
    public synchronized void setColorSquare(int row, int column, Color color) {
        boardGridView.setColorSquare(row, column, color);
    }

    @Override
    public synchronized void addShapeAtSquare(int row, int column, Shape shape, Color color) {
        boardGridView.addShapeAtSquare(row, column, shape, color);
    }

    @Override
    public synchronized void removeShapesAtSquare(int row, int column) {
        boardGridView.removeShapesAtSquare(row, column);
    }

    public BoardGridView getBoardGridView() {
        return boardGridView;
    }

    public Stage getStage() {
        return stage;
    }

    public Bar getBar() {
        return bar;
    }



    public void buttonActionOnclick(String id){
        controller.buttonActionOnClick(id);
    }

    public void boardActionOnclick(int row, int column){
        controller.boardActionOnClick(row, column);
    }


}
