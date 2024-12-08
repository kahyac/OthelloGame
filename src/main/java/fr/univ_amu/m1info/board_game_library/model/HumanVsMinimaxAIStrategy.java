package fr.univ_amu.m1info.board_game_library.model;

import fr.univ_amu.m1info.board_game_library.OthelloController;

public class HumanVsMinimaxAIStrategy implements GameModeStrategy {

    @Override
    public void handleMove(Position position, Piece currentPlayer, OthelloController controller) {
        if (currentPlayer == Piece.BLACK) {
            // Coup du joueur humain
            if (!controller.getLogic().isValidMove(position.getRow(), position.getCol(), currentPlayer)) {
                System.out.println("Invalid move!");
                return;
            }

            controller.saveGameState();
            controller.getGameMode().playMove(position, currentPlayer);
            controller.refreshGameState();

            // Vérifie si le jeu est terminé
            if (controller.isGameOver()) {
                controller.determineWinner();
                return;
            }

            // Passe au joueur IA (Blanc)
            controller.switchPlayer();
        }

        if (controller.getCurrentPlayer() == Piece.WHITE) {
            System.out.println("Minimax AI is calculating its move...");

            // Ajoute un délai pour que l'IA joue
            javafx.animation.Timeline delay = new javafx.animation.Timeline(new javafx.animation.KeyFrame(
                    javafx.util.Duration.seconds(1),
                    event -> {
                        // Joue le meilleur coup calculé par l'IA Minimax
                        Position bestMove = controller.getGameMode().getLogic().getBestMoveUsingMinimax(Piece.WHITE, 3);
                        if (bestMove != null) {
                            controller.saveGameState();
                            controller.getGameMode().playMove(bestMove, Piece.WHITE);
                        } else {
                            System.out.println("No valid moves for Minimax AI!");
                        }

                        controller.refreshGameState();

                        // Vérifie si le jeu est terminé
                        if (controller.isGameOver()) {
                            controller.determineWinner();
                            return;
                        }

                        // Passe au joueur humain (Noir)
                        controller.switchPlayer();
                        controller.refreshGameState();
                    }
            ));
            delay.setCycleCount(1);
            delay.play();
        }
    }
}
