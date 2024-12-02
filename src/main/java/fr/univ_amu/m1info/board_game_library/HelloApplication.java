package fr.univ_amu.m1info.board_game_library;

import fr.univ_amu.m1info.board_game_library.graphics.*;
import fr.univ_amu.m1info.board_game_library.graphics.configuration.*;
import fr.univ_amu.m1info.board_game_library.graphics.javafx.board.BoardActionOnHover;
import fr.univ_amu.m1info.board_game_library.graphics.javafx.board.BoardGridView;
import fr.univ_amu.m1info.board_game_library.graphics.javafx.view.JavaFXBoardGameView;
import fr.univ_amu.m1info.board_game_library.model.*;
import java.util.List;

public class HelloApplication {

    private static class HelloController implements BoardGameController, BoardActionOnHover {
        private BoardGameView view;
        private OthelloBoard board;        // Represents the game board
        private Piece currentPlayer;       // Indicates current player (BLACK or WHITE)
        private OthelloMoveValidator moveValidator; // Validates moves
        private PieceFlipper pieceFlipper; // To return captured opponent pieces
        private boolean playAgainstAI = false; //Check if we are playing with AI


        @Override
        public void initializeViewOnStart(BoardGameView view) {
            this.view = view;
            this.board = new OthelloBoard();
            this.currentPlayer = Piece.BLACK;
            this.moveValidator = new OthelloMoveValidator();
            this.pieceFlipper = new PieceFlipper();

            // Afficher les couleurs initiales
            initializeBoardColors();

            // Placer les pions de départ
            initializeStartingBoard();

            // Log de l'état initial du plateau
            printBoardState();

            // Afficher les coups valides
            highlightValidMoves();

            // Configurer les clics sur le plateau
            BoardGridView gridView = ((JavaFXBoardGameView) view).getBoardGridView();
            gridView.setAction(this::boardActionOnClick);
        }

        private void printBoardState() {
            System.out.println("État initial du plateau :");
            for (int row = 0; row < board.getSize(); row++) {
                for (int col = 0; col < board.getSize(); col++) {
                    System.out.print(board.getPieceAt(row, col) + " ");
                }
                System.out.println();
            }
        }



        private void initializeBoardColors() {
            for (int row = 0; row < board.getSize(); row++) {
                for (int col = 0; col < board.getSize(); col++) {
                    view.setCellColor(row, col, Color.GREEN); // Vert comme couleur de fond
                }
            }
        }



        private void initializeStartingBoard() {
            // Placer les pions de départ
            view.addShapeAtCell(3, 3, Shape.CIRCLE, Color.WHITE);
            board.placePiece(3, 3, Piece.WHITE);

            view.addShapeAtCell(3, 4, Shape.CIRCLE, Color.BLACK);
            board.placePiece(3, 4, Piece.BLACK);

            view.addShapeAtCell(4, 3, Shape.CIRCLE, Color.BLACK);
            board.placePiece(4, 3, Piece.BLACK);

            view.addShapeAtCell(4, 4, Shape.CIRCLE, Color.WHITE);
            board.placePiece(4, 4, Piece.WHITE);

            // Mettre à jour les couleurs des cases
            highlightValidMoves();
        }



        @Override
        public void boardActionOnClick(int row, int column) {
            // Checks if the box is empty
            if (board.getPieceAt(row, column) != Piece.EMPTY) {
                System.out.println("La case est déjà occupée !");
                return;
            }

            // Vérifie si le coup est valide
            if (moveValidator.isValidMove(board, row, column, currentPlayer)) {
                // Place le pion sur la case cliquée (logique et graphique)
                board.placePiece(row, column, currentPlayer);
                view.addShapeAtCell(row, column, Shape.CIRCLE,
                        currentPlayer == Piece.BLACK ? Color.BLACK : Color.WHITE);

                // Retourne les pions adverses
                pieceFlipper.flipPieces(board, row, column, currentPlayer);

                // Mets à jour l'affichage graphique des pions retournés
                updateViewFromBoard();

                // Basculer le joueur
                togglePlayer();

                // Met à jour les coups valides pour le prochain joueur
                highlightValidMoves();
            } else {
                System.out.println("Coup invalide !");
            }
        }


        private void updateViewFromBoard() {
            for (int row = 0; row < board.getSize(); row++) {
                for (int col = 0; col < board.getSize(); col++) {
                    Piece piece = board.getPieceAt(row, col);
                    if (piece == Piece.BLACK) {
                        view.addShapeAtCell(row, col, Shape.CIRCLE, Color.BLACK);
                    } else if (piece == Piece.WHITE) {
                        view.addShapeAtCell(row, col, Shape.CIRCLE, Color.WHITE);
                    } else {
                        view.removeShapesAtCell(row, col); // Supprime les pions si la case est vide
                    }
                }
            }
        }









        private void togglePlayer() {
            currentPlayer = (currentPlayer == Piece.BLACK) ? Piece.WHITE : Piece.BLACK;
        }

        private void highlightValidMoves() {
            System.out.println("Highlighting valid moves for " + currentPlayer);
            for (int row = 0; row < board.getSize(); row++) {
                for (int col = 0; col < board.getSize(); col++) {
                    if (board.getPieceAt(row, col) == Piece.EMPTY
                            && moveValidator.isValidMove(board, row, col, currentPlayer)) {
                        view.setCellColor(row, col, Color.RED); // Rouge pour les coups valides
                        System.out.println("Case valide : (" + row + ", " + col + ")");
                    } else {
                        view.setCellColor(row, col, Color.GREEN); // Vert sinon
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
            return board.getPieceAt(row, column) == Piece.EMPTY
                    && moveValidator.isValidMove(board, row, column, currentPlayer);
        }

        private void startNewGame(boolean playAgainstAI) {
            this.playAgainstAI = playAgainstAI; // Définit le mode Humain vs IA ou Humain vs Humain
            this.board = new OthelloBoard();
            this.currentPlayer = Piece.BLACK; // L'humain commence toujours
            this.moveValidator = new OthelloMoveValidator();
            this.pieceFlipper = new PieceFlipper();

            initializeBoardColors(); // Configure la grille avec des couleurs par défaut
            initializeStartingBoard(); // Place les 4 pions initiaux

            highlightValidMoves(); // Affiche les coups valides pour l'humain
        }




    }

    public static void main(String[] args) {
        BoardGameConfiguration boardGameConfiguration = new BoardGameConfiguration("Hello World",
                new BoardGameDimensions(8, 8),
                List.of(
                        new LabeledElementConfiguration("Change button & label", "ButtonChangeLabel", LabeledElementKind.BUTTON),
                        new LabeledElementConfiguration("Add squares and stars", "ButtonStarSquare", LabeledElementKind.BUTTON),
                        new LabeledElementConfiguration("Add diamonds and circles", "ButtonDiamondCircle", LabeledElementKind.BUTTON),
                        new LabeledElementConfiguration("IA", "ButtonToggleModeIA", LabeledElementKind.BUTTON), // Nouveau bouton
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

}