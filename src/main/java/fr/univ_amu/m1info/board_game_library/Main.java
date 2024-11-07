package fr.univ_amu.m1info.board_game_library;

import fr.univ_amu.m1info.board_game_library.application.BoardGameApplicationLauncher;
import fr.univ_amu.m1info.board_game_library.application.JavaFXBoardGameApplicationLauncher;
import fr.univ_amu.m1info.board_game_library.controller.BoardGameController;
import fr.univ_amu.m1info.board_game_library.controller.OthelloController;
import fr.univ_amu.m1info.board_game_library.model.BoardGameConfiguration;
import fr.univ_amu.m1info.board_game_library.model.BoardGameDimensions;
import fr.univ_amu.m1info.board_game_library.model.LabeledElementConfiguration;
import fr.univ_amu.m1info.board_game_library.model.LabeledElementKind;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        BoardGameConfiguration boardGameConfiguration = new BoardGameConfiguration(
                "Othello Game",
                new BoardGameDimensions(8, 8),
                List.of(
                        new LabeledElementConfiguration("Change button & label", "ButtonChangeLabel", LabeledElementKind.BUTTON),
                        new LabeledElementConfiguration("Add squares and stars", "ButtonStarSquare", LabeledElementKind.BUTTON),
                        new LabeledElementConfiguration("Add diamonds and circles", "ButtonDiamondCircle", LabeledElementKind.BUTTON),
                        new LabeledElementConfiguration("Initial Text", "Initial Text", LabeledElementKind.TEXT)
                )
        );
        BoardGameController controller = new OthelloController();
        BoardGameApplicationLauncher launcher = JavaFXBoardGameApplicationLauncher.getInstance();
        launcher.launchApplication(boardGameConfiguration, controller);
    }
}
