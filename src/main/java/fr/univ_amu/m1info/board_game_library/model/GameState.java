package fr.univ_amu.m1info.board_game_library.model;

public record GameState(OthelloBoard boardState, Piece currentPlayer) {

    public GameState(OthelloBoard boardState, Piece currentPlayer) {
        this.boardState = boardState.clone(); // Cloning to avoid direct modifications
        this.currentPlayer = currentPlayer;
    }
}

