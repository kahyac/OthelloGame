package fr.univ_amu.m1info.board_game_library.model;

import fr.univ_amu.m1info.board_game_library.OthelloController;

public class HumanVsIAStrategy implements GameModeStrategy {

    @Override
    public void handleMove(Position position, Piece currentPlayer, OthelloController controller) {
        if (currentPlayer == Piece.BLACK) {
            // Coup du joueur humain
            if (!controller.getLogic().isValidMove(position.getRow(), position.getCol(), currentPlayer)) {
                System.out.println("Invalid move!");
                return;
            }

            // Sauvegarde l'état et joue le coup
            controller.saveGameState();
            controller.getGameMode().playMove(position, currentPlayer);

            controller.refreshGameState();
            // Vérifie la fin du jeu
            if (controller.isGameOver()) {
                controller.refreshGameState();
                controller.determineWinner();
                return;
            }

            // Passe au tour de l'IA
            controller.switchPlayer();

            // Laisse l'IA jouer avec un délai
            javafx.animation.Timeline delay = new javafx.animation.Timeline(new javafx.animation.KeyFrame(
                    javafx.util.Duration.seconds(1),
                    event -> {
                        controller.getGameMode().playMove(new Position(-1,-1), controller.getCurrentPlayer());

                        if (controller.isGameOver()) {
                            controller.refreshGameState();
                            controller.determineWinner();
                            return;
                        }

                        // Passe au joueur humain
                        controller.switchPlayer();
                        controller.refreshGameState();
                    }
            ));
            delay.setCycleCount(1);
            delay.play();
        }
    }

}


