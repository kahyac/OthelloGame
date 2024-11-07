package fr.univ_amu.m1info.board_game_library.view;

import fr.univ_amu.m1info.board_game_library.model.BoardGameConfiguration;
import fr.univ_amu.m1info.board_game_library.model.LabeledElementConfiguration;

public class BoardGameConfigurator {
    public void configure(BoardGameViewBuilder boardGameViewBuilder,
                          BoardGameConfiguration boardGameConfiguration) {
        boardGameViewBuilder = boardGameViewBuilder
                .resetView()
                .setTitle(boardGameConfiguration.title())
                .setBoardGameDimensions(boardGameConfiguration.dimensions().rowCount(),
                        boardGameConfiguration.dimensions().columnCount());
        for (LabeledElementConfiguration elementConfiguration : boardGameConfiguration.labeledElementConfigurations()) {
            switch (elementConfiguration.kind()) {
                case BUTTON -> boardGameViewBuilder = boardGameViewBuilder.addButton(elementConfiguration.id(), elementConfiguration.label());
                case TEXT -> boardGameViewBuilder = boardGameViewBuilder.addLabel(elementConfiguration.id(), elementConfiguration.label());
            }
        }
    }
}
