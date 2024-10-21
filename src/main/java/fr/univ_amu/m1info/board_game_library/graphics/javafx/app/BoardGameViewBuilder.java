package fr.univ_amu.m1info.board_game_library.graphics.javafx.app;

import fr.univ_amu.m1info.board_game_library.graphics.view.BoardGameView;

public interface BoardGameViewBuilder {
    BoardGameViewBuilder resetView();
    BoardGameViewBuilder setBoardGameDimensions(int rowCount, int columnCount);
    BoardGameViewBuilder setTitle(String title);
    BoardGameViewBuilder addLabel(String id, String initialText);
    BoardGameViewBuilder addButton(String id, String label);
    BoardGameView getView();
}
