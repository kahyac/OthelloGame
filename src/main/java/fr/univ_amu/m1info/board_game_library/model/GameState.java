package fr.univ_amu.m1info.board_game_library.model;

public class GameState {

    private final OthelloBoard boardState;
    private final Piece currentPlayer;

    public GameState(OthelloBoard boardState, Piece currentPlayer) {
        this.boardState = boardState.clone(); // Clonage pour éviter les modifications directes
        this.currentPlayer = currentPlayer;
    }

    public OthelloBoard getBoardState() {
        return boardState;
    }


    public Piece getCurrentPlayer() {
        return currentPlayer;
    }
}

