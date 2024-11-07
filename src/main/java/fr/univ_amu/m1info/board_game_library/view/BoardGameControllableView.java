package fr.univ_amu.m1info.board_game_library.view;

import fr.univ_amu.m1info.board_game_library.controller.BoardGameController;


public interface BoardGameControllableView extends BoardGameView {
    void setController(BoardGameController controller);
}
