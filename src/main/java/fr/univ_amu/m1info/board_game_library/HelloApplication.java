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
                .addButton("ButtonChangeLabel", "Change button & label")
                .addButton("ButtonStarSquare", "Add squares and stars")
                .addButton("ButtonDiamondCircle", "Add diamonds and circles")
                .addLabel("SampleLabel","Initial Text")
                .setBoardGameDimensions(8,8)
                .getView();
        view.setButtonAction("ButtonChangeLabel", ()-> {
            view.updateLabeledElement("SampleLabel", "Updated Text");
            view.updateLabeledElement("ButtonChangeLabel", "Updated Text");
        });
        view.setButtonAction("ButtonStarSquare", ()-> {
            changeSquareColors(view, Color.GREEN, Color.DARKGREEN);
            changeShapes(view, Shape.STAR, Shape.SQUARE);
        });
        view.setButtonAction("ButtonDiamondCircle", ()-> {
            changeSquareColors(view, Color.WHITE, Color.DARKBLUE);
            changeShapes(view, Shape.DIAMOND, Shape.CIRCLE);
        });
        view.setBoardGameAction(view::removeShapesAtSquare);
        changeSquareColors(view, Color.DARKRED, Color.LIGHTGREEN);

        view.show();
    }

    private static void changeSquareColors(BoardGameView view, Color odd, Color even) {
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                boolean isEven = (row + column) % 2 == 0;
                Color colorSquare = isEven ? even  : odd ;
                view.setColorSquare(row, column, colorSquare);
                view.addShapeAtSquare(row, column, Shape.TRIANGLE, Color.BLACK);
            }
        }
    }

    static public void changeShapes(BoardGameView view, Shape odd, Shape even) {
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                boolean isEven = (row + column) % 2 == 0;
                Color colorShape = isEven ? Color.BLUE : Color.LIGHTBLUE;
                Shape shape = isEven ? even : odd;
                view.removeShapesAtSquare(row, column);
                view.addShapeAtSquare(row, column, shape, colorShape);
            }
        }
    }


    public static void main(String[] args) {
        launch();
    }
}