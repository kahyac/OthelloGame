package fr.univ_amu.m1info.board_game_library.model;

import fr.univ_amu.m1info.board_game_library.HelloController;

public class HumanVsHumanStrategy implements GameModeStrategy {
    @Override
    public void handleMove(Position position, Piece currentPlayer, HelloController controller) {
        if (!controller.getLogic().isValidMove(position.getRow(), position.getCol(), currentPlayer)) {
            System.out.println("Invalid move!");
            return;
        }

        // Sauvegarde l'état et joue le coup
        controller.saveGameState();
        controller.getGameMode().playMove(position, currentPlayer);

        // Vérifie la fin du jeu
        if (controller.isGameOver()) {
            controller.refreshGameState();
            controller.determineWinner();
            return;
        }

        // Change de joueur
        controller.switchPlayer();
        controller.refreshGameState();
    }
}
