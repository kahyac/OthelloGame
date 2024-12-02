package fr.univ_amu.m1info.board_game_library;

import fr.univ_amu.m1info.board_game_library.graphics.*;
import fr.univ_amu.m1info.board_game_library.graphics.configuration.*;
import fr.univ_amu.m1info.board_game_library.graphics.javafx.board.BoardActionOnHover;
import fr.univ_amu.m1info.board_game_library.graphics.javafx.board.BoardGridView;
import fr.univ_amu.m1info.board_game_library.graphics.javafx.view.JavaFXBoardGameView;
import fr.univ_amu.m1info.board_game_library.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HelloApplication {

    private static class HelloController implements BoardGameController, BoardActionOnHover {
        private BoardGameView view;
        private OthelloBoard board;        // Represents the game board
        private Piece currentPlayer;       // Indicates current player (BLACK or WHITE)
        private OthelloMoveValidator moveValidator; // Validates moves
        private PieceFlipper pieceFlipper; // To return captured opponent pieces
        private boolean playAgainstAI = false; //Checks if we are playing with AI


        @Override
        public void initializeViewOnStart(BoardGameView view) {
            this.view = view; // Référence à la vue

            // Appeler la méthode pour démarrer une nouvelle partie
            startNewGame(false); // Par défaut, mode Humain vs Humain

            // Configurer les clics sur le plateau
            BoardGridView gridView = ((JavaFXBoardGameView) view).getBoardGridView();
            gridView.setAction(this::boardActionOnClick); // Associer l'action de clic
        }


        private void startNewGame(boolean playAgainstAI) {
            this.playAgainstAI = playAgainstAI; // Définit le mode (IA ou non)
            this.board = new OthelloBoard(); // Réinitialise le plateau
            this.currentPlayer = Piece.BLACK; // Toujours l'humain qui commence
            this.moveValidator = new OthelloMoveValidator(); // Réinitialise le validateur
            this.pieceFlipper = new PieceFlipper(); // Réinitialise le flipper
            resetView();
            initializeBoardColors(); // Configure les couleurs de la grille
            initializeStartingBoard(); // Place les pions de départ

            highlightValidMoves(); // Affiche les coups valides pour le joueur actuel
        }

        private void resetView() {
            // Efface tous les pions de la vue
            for (int row = 0; row < board.getSize(); row++) {
                for (int col = 0; col < board.getSize(); col++) {
                    view.removeShapesAtCell(row, col); // Supprime les formes graphiques
                }
            }

            // Réinitialise les étiquettes ou autres éléments de l'interface si nécessaire
            view.updateLabeledElement("Initial Text", "Nouvelle partie démarrée !");
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
            // Vérifie si la case est vide
            if (board.getPieceAt(row, column) != Piece.EMPTY) {
                System.out.println("La case est déjà occupée !");
                return;
            }

            // Vérifie si le coup est valide pour le joueur actuel
            if (moveValidator.isValidMove(board, row, column, currentPlayer)) {
                // Place le pion sur la case cliquée (logique et graphique)
                board.placePiece(row, column, currentPlayer);
                view.addShapeAtCell(row, column, Shape.CIRCLE, currentPlayer == Piece.BLACK ? Color.BLACK : Color.WHITE);

                // Retourne les pions adverses
                pieceFlipper.flipPieces(board, row, column, currentPlayer);

                // Mets à jour l'affichage graphique des pions retournés
                updateViewFromBoard();

                // Basculer le joueur
                togglePlayer();

                // Met à jour les coups valides pour le prochain joueur
                highlightValidMoves();

                // Si c'est le mode IA et que c'est maintenant au tour de l'IA
                if (playAgainstAI && currentPlayer == Piece.WHITE) {
                    playAIMove(); // L'IA joue automatiquement
                }
            } else {
                System.out.println("Coup invalide !");
            }
        }


        private List<int[]> getValidMoves(Piece currentPlayer) {
            List<int[]> validMoves = new ArrayList<>();
            for (int row = 0; row < board.getSize(); row++) {
                for (int col = 0; col < board.getSize(); col++) {
                    // Vérifie si la case est vide et si le coup est valide pour le joueur
                    if (board.getPieceAt(row, col) == Piece.EMPTY && moveValidator.isValidMove(board, row, col, currentPlayer)) {
                        validMoves.add(new int[]{row, col}); // Ajoute la position (row, col) à la liste
                    }
                }
            }
            return validMoves;
        }


        private void playAIMove() {
            System.out.println("L'IA joue...");
            List<int[]> validMoves = getValidMoves(currentPlayer);

            if (!validMoves.isEmpty()) {
                // Choisit un coup valide aléatoire
                Random random = new Random();
                int[] move = validMoves.get(random.nextInt(validMoves.size()));

                // Place le pion
                board.placePiece(move[0], move[1], currentPlayer);
                view.addShapeAtCell(move[0], move[1], Shape.CIRCLE, Color.WHITE); // Couleur de l'IA (Blanc)

                // Retourne les pions capturés
                pieceFlipper.flipPieces(board, move[0], move[1], currentPlayer);

                // Mets à jour la vue
                updateViewFromBoard();

                // Bascule au joueur humain
                togglePlayer();

                // Met en évidence les coups valides pour le joueur humain
                highlightValidMoves();
            } else {
                System.out.println("L'IA n'a pas de coups valides.");
                togglePlayer(); // Passe au joueur humain si l'IA n'a pas de coup valide
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
                case "ButtonChangeLabel" -> handleChangeLabelButton();
                case "ButtonStarSquare" -> handleStarSquareButton();
                case "ButtonDiamondCircle" -> handleDiamondCircleButton();
                case "ButtonToggleModeIA" -> handleToggleModeIAButton(); // Nouveau bouton IA
                default -> throw new IllegalStateException("Unexpected button ID: " + buttonId);
            }
        }

        private void handleChangeLabelButton() {
            view.updateLabeledElement("SampleLabel", "Updated Text");
            view.updateLabeledElement("ButtonChangeLabel", "Updated Text");
        }

        private void placeInitialPieces() {
            int centerRow = 4; // Index des cellules centrales
            int centerCol = 4;

            view.addShapeAtCell(centerRow - 1, centerCol - 1, Shape.CIRCLE, Color.WHITE); // [3][3] - Blanc
            view.addShapeAtCell(centerRow - 1, centerCol, Shape.CIRCLE, Color.BLACK);     // [3][4] - Noir
            view.addShapeAtCell(centerRow, centerCol - 1, Shape.CIRCLE, Color.BLACK);     // [4][3] - Noir
            view.addShapeAtCell(centerRow, centerCol, Shape.CIRCLE, Color.WHITE);         // [4][4] - Blanc
        }

        private void handleStarSquareButton() {
            changeCellColors(view, Color.GREEN, Color.GREEN);
            placeInitialPieces(); // Utilise une méthode commune pour placer les pions initiaux
        }

        private void handleDiamondCircleButton() {
            changeCellColors(view, Color.GREEN, Color.GREEN);
            placeInitialPieces();
        }

        private void handleToggleModeIAButton() {
            playAgainstAI = !playAgainstAI; // Alterne le mode
            System.out.println("Mode de jeu basculé : " + (playAgainstAI ? "Humain vs IA" : "Humain vs Humain"));

            // Met à jour l'interface utilisateur
            view.updateLabeledElement("Initial Text", playAgainstAI ? "Mode: Humain vs IA activé" : "Mode: Humain vs Humain activé");

            // Redémarre la partie avec le nouveau mode
            startNewGame(true);
        }


        @Override
        public boolean validateHover(int row, int column) {
            return board.getPieceAt(row, column) == Piece.EMPTY
                    && moveValidator.isValidMove(board, row, column, currentPlayer);
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