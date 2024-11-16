package fr.univ_amu.m1info.board_game_library;

import fr.univ_amu.m1info.board_game_library.graphics.*;
import fr.univ_amu.m1info.board_game_library.graphics.configuration.*;
import fr.univ_amu.m1info.board_game_library.graphics.javafx.board.BoardActionOnHover;
import fr.univ_amu.m1info.board_game_library.model.*;
import java.util.List;

public class HelloApplication {

    private static class HelloController implements BoardGameController, BoardActionOnHover {
        private BoardGameView view;
        private OthelloBoard board;        // Represents the game board
        private Piece currentPlayer;       // Indicates current player (BLACK or WHITE)
        private OthelloMoveValidator moveValidator; // Validates moves
        private PieceFlipper pieceFlipper; // To return captured opponent pieces


        @Override
        public void initializeViewOnStart(BoardGameView view) {
            this.view = view;
            this.board = new OthelloBoard();
            this.currentPlayer = Piece.BLACK;
            this.moveValidator = new OthelloMoveValidator();
            this.pieceFlipper = new PieceFlipper();
            initializeStartingBoard();
        }

        private void initializeStartingBoard() {
            // Placing the central pieces of the Othello game
            view.addShapeAtCell(3, 3, Shape.CIRCLE, Color.WHITE);
            board.placePiece(3, 3, Piece.WHITE);

            view.addShapeAtCell(3, 4, Shape.CIRCLE, Color.BLACK);
            board.placePiece(3, 4, Piece.BLACK);

            view.addShapeAtCell(4, 3, Shape.CIRCLE, Color.BLACK);
            board.placePiece(4, 3, Piece.BLACK);

            view.addShapeAtCell(4, 4, Shape.CIRCLE, Color.WHITE);
            board.placePiece(4, 4, Piece.WHITE);
        }


        @Override
        public void boardActionOnClick(int row, int column) {
            // Check if the box is occupied
            if (board.getPieceAt(row, column) != Piece.EMPTY) {
                System.out.println("La case est déjà occupée !");
                return;
            }
            // Check if the move is valid
            if (moveValidator.isValidMove(board, row, column, currentPlayer)) {
                // Place the pawn
                board.placePiece(row, column, currentPlayer);

                // Turning over the opponent's pawns
                pieceFlipper.flipPieces(board, row, column, currentPlayer);

                // Switch to next player
                togglePlayer();

                // Update the possible moves for the next player
                highlightValidMoves(currentPlayer);
            } else {
                System.out.println("Coup invalide !");
            }
        }


        private void togglePlayer() {
            currentPlayer = (currentPlayer == Piece.BLACK) ? Piece.WHITE : Piece.BLACK;
        }

        private void highlightValidMoves(Piece currentPlayer) {
            for (int row = 0; row < board.getSize(); row++) {
                for (int col = 0; col < board.getSize(); col++) {
                    // Check that the square is empty and that the move is valid
                    if (board.getPieceAt(row, col) == Piece.EMPTY
                            && moveValidator.isValidMove(board, row, col, currentPlayer)) {
                        view.setCellColor(row, col, Color.LIGHTGREEN); // Highlighting valid shots
                    } else {
                        view.setCellColor(row, col, Color.GREEN); // Default color
                    }
                }
            }
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

        @Override
        public boolean validateHover(int row, int column) {
            return moveValidator.isValidMove(board, row, column, currentPlayer);
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
                view.addShapeAtCell(row, column, shape, colorShape);
            }
        }
    }
}