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
            changeCellColors(view, Color.GREEN, Color.GREEN);

            this.view = view;
            int centerRow = 4; // Le plateau 8x8 a ses cases centrales autour des index 3 et 4 (indices commençant à 0)
            int centerCol = 4;

            // Placer les pions au centre du plateau selon les règles d'Othello
            view.addShapeAtCell(centerRow - 1, centerCol - 1, Shape.CIRCLE, Color.WHITE);  // Cellule [3][3] - Blanc
            view.addShapeAtCell(centerRow - 1, centerCol, Shape.CIRCLE, Color.BLACK);      // Cellule [3][4] - Noir
            view.addShapeAtCell(centerRow, centerCol - 1, Shape.CIRCLE, Color.BLACK);      // Cellule [4][3] - Noir
            view.addShapeAtCell(centerRow, centerCol, Shape.CIRCLE, Color.WHITE);
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
                    changeCellColors(view, Color.GREEN, Color.GREEN);
                    this.view = view;
                    int centerRow = 4; // Le plateau 8x8 a ses cases centrales autour des index 3 et 4 (indices commençant à 0)
                    int centerCol = 4;

                    // Placer les pions au centre du plateau selon les règles d'Othello
                    view.addShapeAtCell(centerRow - 1, centerCol - 1, Shape.CIRCLE, Color.WHITE);  // Cellule [3][3] - Blanc
                    view.addShapeAtCell(centerRow - 1, centerCol, Shape.CIRCLE, Color.BLACK);      // Cellule [3][4] - Noir
                    view.addShapeAtCell(centerRow, centerCol - 1, Shape.CIRCLE, Color.BLACK);      // Cellule [4][3] - Noir
                    view.addShapeAtCell(centerRow, centerCol, Shape.CIRCLE, Color.WHITE);                }
                case "ButtonDiamondCircle" -> {
                    changeCellColors(view, Color.GREEN, Color.GREEN);

                    this.view = view;
                    int centerRow = 4; // Le plateau 8x8 a ses cases centrales autour des index 3 et 4 (indices commençant à 0)
                    int centerCol = 4;

                    // Placer les pions au centre du plateau selon les règles d'Othello
                    view.addShapeAtCell(centerRow - 1, centerCol - 1, Shape.CIRCLE, Color.WHITE);  // Cellule [3][3] - Blanc
                    view.addShapeAtCell(centerRow - 1, centerCol, Shape.CIRCLE, Color.BLACK);      // Cellule [3][4] - Noir
                    view.addShapeAtCell(centerRow, centerCol - 1, Shape.CIRCLE, Color.BLACK);      // Cellule [4][3] - Noir
                    view.addShapeAtCell(centerRow, centerCol, Shape.CIRCLE, Color.WHITE);                }
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