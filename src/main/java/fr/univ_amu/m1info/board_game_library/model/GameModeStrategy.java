package fr.univ_amu.m1info.board_game_library.model;

import fr.univ_amu.m1info.board_game_library.HelloController;

public interface GameModeStrategy {
    void handleMove(Position position, Piece currentPlayer, HelloController controller);
}
