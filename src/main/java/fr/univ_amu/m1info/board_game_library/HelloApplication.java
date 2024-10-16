package fr.univ_amu.m1info.board_game_library;

import fr.univ_amu.m1info.board_game_library.graphics.BoardGameView;
import fr.univ_amu.m1info.board_game_library.graphics.Color;
import fr.univ_amu.m1info.board_game_library.graphics.Shape;
import fr.univ_amu.m1info.board_game_library.graphics.javafx.JavaFXBoardGameViewBuilder;
import javafx.application.Application;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) {
        final BoardGameView view = new JavaFXBoardGameViewBuilder(stage)
                .resetView()
                .setTitle("Hello World")
                .addLabel("Counter","0")
                .addButton("IncrementButton", "Increment")
                .addButton("SoutButton", "Sout")
                .addButton("removeShapes", "RemoveShapes")
                .setBoardGameSize(8,8)
                .getView();
        view.setButtonAction("IncrementButton", ()-> view.updateLabel("Counter", "ttt"));
        view.setButtonAction("SoutButton", ()-> System.out.println("SoutButton"));
        view.setButtonAction("removeShapes", ()-> view.removeShapesAtSquare(1,1) );
        view.setBoardGameAction((i,j)->System.out.println(i +" " + j));
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                boolean b = (row + column) % 2 == 0;
                Color colorSquare = b ? Color.LIGHTGREEN : Color.DARKRED;
                Color colorShape = b ? Color.BLUE : Color.LIGHTBLUE;
                Shape shape = b ? Shape.SQUARE : Shape.DIAMOND;
                view.setColorSquare(row, column, colorSquare);
                view.addShapeAtSquare(row, column, shape, colorShape);
            }
        }

        view.show();
    }

    public static void main(String[] args) {
        launch();
    }
}