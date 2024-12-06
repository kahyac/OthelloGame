package fr.univ_amu.m1info.board_game_library.model;

import fr.univ_amu.m1info.board_game_library.HelloController;

public interface GameModeStrategy {
    void handleMove(int row, int col, Piece currentPlayer, HelloController controller);
}
