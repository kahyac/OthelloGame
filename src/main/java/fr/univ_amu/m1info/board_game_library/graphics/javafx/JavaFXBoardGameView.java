package fr.univ_amu.m1info.board_game_library.graphics.javafx;

import fr.univ_amu.m1info.board_game_library.graphics.*;
import fr.univ_amu.m1info.board_game_library.graphics.javafx.bar.Bar;
import fr.univ_amu.m1info.board_game_library.graphics.javafx.board.BoardGridView;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class JavaFXBoardGameView implements BoardGameView {
    private Scene scene;
    private final Stage stage;
    private BoardGridView boardGridView;
    private Bar bar;
    private VBox vBox;

    public JavaFXBoardGameView(Stage stage) {
        this.stage = stage;
        stage.setOnCloseRequest(_ -> Platform.exit());
        stage.setResizable(false);
        stage.sizeToScene();
    }

    public void reset() {
        vBox = new VBox();
        bar = new Bar();
        boardGridView = new BoardGridView();
        vBox.getChildren().add(bar);
        vBox.getChildren().add(boardGridView);
        scene = new Scene(vBox);
        stage.setScene(scene);
    }

    @Override
    public void updateLabel(String id, String newText) {
        bar.updateLabel(id, newText);
    }

    @Override
    public void setColorSquare(int row, int column, Color color) {
        boardGridView.setColorSquare(row, column, color);
    }

    @Override
    public void addShapeAtSquare(int row, int column, Shape shape, Color color) {
        boardGridView.addShapeAtSquare(row, column, shape, color);
    }

    @Override
    public void removeShapesAtSquare(int row, int column) {

    }

    public Scene getScene() {
        return scene;
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

    public VBox getVBox() {
        return vBox;
    }

    @Override
    public void show() {
        stage.show();
    }

    @Override
    public void setButtonAction(String id, ButtonActionOnClick buttonActionOnClick) {
        bar.setButtonAction(id, buttonActionOnClick);
    }

    @Override
    public void setBoardGameAction(BoardActionOnClick boardActionOnClick) {
        boardGridView.setAction(boardActionOnClick);
    }
}
