package fr.univ_amu.m1info.board_game_library.model;

public interface MoveValidator {
    boolean isValidMove(OthelloBoard board, int row, int col, Piece currentPlayer);
}
