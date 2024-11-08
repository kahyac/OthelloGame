package fr.univ_amu.m1info.board_game_library.controller;

import fr.univ_amu.m1info.board_game_library.view.BoardGameView;
import fr.univ_amu.m1info.board_game_library.model.Color;
import fr.univ_amu.m1info.board_game_library.model.Shape;

public class OthelloController implements BoardGameController {
    private BoardGameView view;
    private boolean[][] occupiedCells = new boolean[8][8]; // To keep track of occupied cells

    @Override
    public void initializeViewOnStart(BoardGameView view) {
        changeCellColors(view, Color.GREEN, Color.GREEN);
        this.view = view;
        int centerRow = 4;
        int centerCol = 4;


        // Place the initial Othello pieces and mark cells as occupied
        view.addShapeAtCell(centerRow - 1, centerCol - 1, Shape.CIRCLE, Color.WHITE);
        occupiedCells[centerRow - 1][centerCol - 1] = true;

        view.addShapeAtCell(centerRow - 1, centerCol, Shape.CIRCLE, Color.BLACK);
        occupiedCells[centerRow - 1][centerCol] = true;

        view.addShapeAtCell(centerRow, centerCol - 1, Shape.CIRCLE, Color.BLACK);
        occupiedCells[centerRow][centerCol - 1] = true;

        view.addShapeAtCell(centerRow, centerCol, Shape.CIRCLE, Color.WHITE);
        occupiedCells[centerRow][centerCol] = true;
    }

    @Override
    public void boardActionOnClick(int row, int column) {
        if (!occupiedCells[row][column]) {
            view.addShapeAtCell(row, column, Shape.CIRCLE, Color.WHITE);
            occupiedCells[row][column] = true;
        } else {
            System.out.println("Cell [" + row + "][" + column + "] is already occupied!");
        }
    }

    @Override
    public void buttonActionOnClick(String buttonId) {
        switch (buttonId) {
            case "ButtonChangeLabel" -> {
                view.updateLabeledElement("SampleLabel", "Updated Text");
                view.updateLabeledElement("ButtonChangeLabel", "Updated Text");
            }
            case "ButtonStarSquare", "ButtonDiamondCircle" -> resetBoardCenter(view);
            default -> throw new IllegalStateException("Unexpected event, button id : " + buttonId);
        }
    }

    private void resetBoardCenter(BoardGameView view) {
        changeCellColors(view, Color.GREEN, Color.GREEN);
        int centerRow = 4;
        int centerCol = 4;
        view.addShapeAtCell(centerRow - 1, centerCol - 1, Shape.CIRCLE, Color.WHITE);
        view.addShapeAtCell(centerRow - 1, centerCol, Shape.CIRCLE, Color.BLACK);
        view.addShapeAtCell(centerRow, centerCol - 1, Shape.CIRCLE, Color.BLACK);
        view.addShapeAtCell(centerRow, centerCol, Shape.CIRCLE, Color.WHITE);
    }

    private void changeCellColors(BoardGameView view, Color oddColor, Color evenColor) {
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                boolean isEven = (row + column) % 2 == 0;
                Color colorSquare = isEven ? evenColor : oddColor;
                view.setCellColor(row, column, colorSquare);
            }
        }
    }
}
