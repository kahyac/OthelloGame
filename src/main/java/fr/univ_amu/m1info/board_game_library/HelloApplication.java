package fr.univ_amu.m1info.board_game_library;

import fr.univ_amu.m1info.board_game_library.graphics.*;
import fr.univ_amu.m1info.board_game_library.graphics.configuration.BoardGameConfiguration;
import fr.univ_amu.m1info.board_game_library.graphics.configuration.LabeledElementConfiguration;
import fr.univ_amu.m1info.board_game_library.graphics.configuration.LabeledElementKind;
import fr.univ_amu.m1info.board_game_library.graphics.configuration.BoardGameDimensions;

import java.util.List;

public class HelloApplication {

    private static class HelloController implements BoardGameController {
        private BoardGameView view;

        @Override
        public void initializeViewOnStart(BoardGameView view) {
            changeCellColors(view, Color.GREEN, Color.LIGHTGREEN);
            changeShapes(view, Shape.TRIANGLE, Color.BLACK, Shape.CIRCLE, Color.RED);
            this.view = view;
        }


        @Override
        public void boardActionOnClick(int row, int column) {
            view.removeShapesAtCell(row, column);
        }

        @Override
        public void buttonActionOnClick(String buttonId) {
            switch (buttonId) {
                case "ButtonChangeLabel" -> {
                    view.updateLabeledElement("SampleLabel", "Updated Text");
                    view.updateLabeledElement("ButtonChangeLabel", "Updated Text");
                }
                case "ButtonStarSquare" -> {
                    changeCellColors(view, Color.GREEN, Color.DARKGREEN);
                    changeShapes(view, Shape.STAR, Color.DARKBLUE, Shape.SQUARE, Color.DARKRED);
                }
                case "ButtonDiamondCircle" -> {
                    changeCellColors(view, Color.WHITE, Color.DARKBLUE);
                    changeShapes(view, Shape.DIAMOND, Color.LIGHTBLUE, Shape.CIRCLE, Color.RED);
                }
                default -> throw new IllegalStateException("Unexpected event, button id : " + buttonId);
            }
        }
    }

    public static void main(String[] args) {
        BoardGameConfiguration boardGameConfiguration = new BoardGameConfiguration("Hello World",
                new BoardGameDimensions(8, 8),
                List.of(new LabeledElementConfiguration("Change button & label", "ButtonChangeLabel", LabeledElementKind.BUTTON),
                        new LabeledElementConfiguration("Add squares and stars", "ButtonStarSquare", LabeledElementKind.BUTTON),
                        new LabeledElementConfiguration("Add diamonds and circles", "ButtonDiamondCircle", LabeledElementKind.BUTTON),
                        new LabeledElementConfiguration("Initial Text", "Initial Text", LabeledElementKind.TEXT)
                ));
        BoardGameController controller = new HelloController();
        BoardGameApplicationLauncher launcher = JavaFXBoardGameApplicationLauncher.getInstance();
        launcher.launchApplication(boardGameConfiguration, controller);
    }

    private static void changeCellColors(BoardGameView view, Color oddColor, Color evenColor) {
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                boolean isEven = (row + column) % 2 == 0;
                Color colorSquare = isEven ? evenColor : oddColor;
                view.setCellColor(row, column, colorSquare);
                view.addShapeAtCell(row, column, Shape.TRIANGLE, Color.BLACK);
            }
        }
    }

    private static void changeShapes(BoardGameView view, Shape oddShape, Color oddColor, Shape evenShape, Color evenColor) {
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                boolean isEven = (row + column) % 2 == 0;
                Color colorShape = isEven ? evenColor : oddColor;
                Shape shape = isEven ? evenShape : oddShape;
                view.removeShapesAtCell(row, column);
                view.addShapeAtCell(row, column, shape, colorShape);
            }
        }
    }
}