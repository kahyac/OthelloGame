package fr.univ_amu.m1info.board_game_library.graphics;

import fr.univ_amu.m1info.board_game_library.graphics.view.BoardGameView;

public interface BoardGameController {
    void boardActionOnClick(int row, int column);
    void buttonActionOnClick(String buttonId);
    void setView(BoardGameView view);
}
