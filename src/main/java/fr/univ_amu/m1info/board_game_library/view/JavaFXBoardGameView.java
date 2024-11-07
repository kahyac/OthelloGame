package fr.univ_amu.m1info.board_game_library.view;

import fr.univ_amu.m1info.board_game_library.controller.BoardGameController;
import fr.univ_amu.m1info.board_game_library.view.components.Bar;
import fr.univ_amu.m1info.board_game_library.view.components.BoardGridView;
import fr.univ_amu.m1info.board_game_library.model.Color;
import fr.univ_amu.m1info.board_game_library.model.Shape;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import fr.univ_amu.m1info.board_game_library.view.components.BarPlayer;


public class JavaFXBoardGameView implements BoardGameControllableView {
    private final Stage stage;
    private BoardGridView boardGridView;
    private Bar bar;
    private BoardGameController controller;

    private BarPlayer barPlayer;

    public void setController(BoardGameController controller) {
        this.controller = controller;
    }

    public JavaFXBoardGameView(Stage stage) {
        this.stage = stage;
        stage.setOnCloseRequest(_ -> Platform.exit());
        stage.setResizable(true);
    }

    public synchronized void reset() {
        VBox vBox = new VBox();
        bar = new Bar();
        barPlayer= new BarPlayer();
        boardGridView = new BoardGridView();
        vBox.getChildren().add(bar);
        vBox.getChildren().add(barPlayer);
        vBox.getChildren().add(boardGridView);
        Scene scene = new Scene(vBox);
        stage.setScene(scene);
    }

    @Override
    public synchronized void updateLabeledElement(String id, String newText) {
        bar.updateLabel(id, newText);
    }

    @Override
    public synchronized void setCellColor(int row, int column, Color color) {
        boardGridView.setColorSquare(row, column, color);
    }

    @Override
    public synchronized void addShapeAtCell(int row, int column, Shape shape, Color color) {
        boardGridView.addShapeAtSquare(row, column, shape, color);
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

    public BarPlayer getBarPlayer(){ return barPlayer;}

    public void buttonActionOnclick(String id){
        controller.buttonActionOnClick(id);
    }

    public void boardActionOnclick(int row, int column){
        controller.boardActionOnClick(row, column);
    }


}
