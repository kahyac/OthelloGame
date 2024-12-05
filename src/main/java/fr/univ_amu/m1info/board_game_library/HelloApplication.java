package fr.univ_amu.m1info.board_game_library;

import fr.univ_amu.m1info.board_game_library.graphics.BoardGameController;
import fr.univ_amu.m1info.board_game_library.graphics.BoardGameApplicationLauncher;
import fr.univ_amu.m1info.board_game_library.graphics.JavaFXBoardGameApplicationLauncher;
import fr.univ_amu.m1info.board_game_library.graphics.configuration.BoardGameConfiguration;
import fr.univ_amu.m1info.board_game_library.graphics.configuration.BoardGameDimensions;
import fr.univ_amu.m1info.board_game_library.graphics.configuration.LabeledElementConfiguration;
import fr.univ_amu.m1info.board_game_library.graphics.configuration.LabeledElementKind;

import java.util.List;

public class HelloApplication {

    public static void main(String[] args) {
        // Étape 1 : Configuration du jeu
        BoardGameConfiguration boardGameConfiguration = new BoardGameConfiguration(
                "Othello Game",
                new BoardGameDimensions(8, 8),
                List.of(
                        new LabeledElementConfiguration("1 VS 1", "ButtonToggleTwoPlayers", LabeledElementKind.BUTTON),
                        new LabeledElementConfiguration("IA", "ButtonToggleModeIA", LabeledElementKind.BUTTON),
                        new LabeledElementConfiguration("Initial Text", "Initial Text", LabeledElementKind.TEXT),
                        new LabeledElementConfiguration("C'est au tour de : Noir", "TurnIndicator", LabeledElementKind.TEXT),
                        new LabeledElementConfiguration("Score : Noir 2 - Blanc 2", "ScoreIndicator", LabeledElementKind.TEXT),
                        new LabeledElementConfiguration("Undo", "ButtonUndo", LabeledElementKind.BUTTON),
                        new LabeledElementConfiguration("Redo", "ButtonRedo", LabeledElementKind.BUTTON)
                )
        );

        // Étape 2 : Création du contrôleur
        BoardGameController controller = new HelloController();

        // Étape 3 : Lancement de l'application
        BoardGameApplicationLauncher launcher = JavaFXBoardGameApplicationLauncher.getInstance();
        launcher.launchApplication(boardGameConfiguration, controller);
    }
}
