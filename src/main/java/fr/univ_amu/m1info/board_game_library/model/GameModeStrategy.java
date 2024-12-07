package fr.univ_amu.m1info.board_game_library.model;

import fr.univ_amu.m1info.board_game_library.OthelloController;

public interface GameModeStrategy {
    void handleMove(Position position, Piece currentPlayer, OthelloController controller);
}
