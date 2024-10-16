package fr.univ_amu.m1info.board_game_library.graphics;

public interface BoardGameViewBuilder {
    BoardGameViewBuilder resetView();
    BoardGameViewBuilder setBoardGameSize(int rowCount, int columnCount);
    BoardGameViewBuilder setTitle(String title);
    BoardGameViewBuilder addLabel(String id, String initialText);
    BoardGameViewBuilder addButton(String id, String label);
    BoardGameView getView();
}
