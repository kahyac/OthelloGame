package fr.univ_amu.m1info.board_game_library.model;

import java.util.Stack;

public class GameStateManager {
    private final OthelloBoard board;
    private final Stack<GameState> undoStack = new Stack<>();
    private final Stack<GameState> redoStack = new Stack<>();

    public GameStateManager(OthelloBoard board) {
        this.board = board;
    }

    public void saveState(Piece currentPlayer) {
        // Saves the current state in the undo stack
        undoStack.push(new GameState(board.clone(), currentPlayer));
        // Empties the recovery stack because a new state has been added
        redoStack.clear();
    }

    public GameState undo(Piece currentPlayer) {
        if (undoStack.isEmpty()) {
            System.out.println("Undo: No states to undo.");
            return null;
        }
        // Save current state before cancelling
        redoStack.push(new GameState(board.clone(), currentPlayer));
        // Restores previous state
        return undoStack.pop();
    }

    public GameState redo(Piece currentPlayer) {
        if (redoStack.isEmpty()) {
            System.out.println("Redo: No states to redo.");
            return null;
        }
        // Save current state before restoring
        undoStack.push(new GameState(board.clone(), currentPlayer));
        // Restores next state
        return redoStack.pop();
    }
}
