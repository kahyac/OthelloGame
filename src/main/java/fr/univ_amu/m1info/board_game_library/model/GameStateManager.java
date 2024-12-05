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
        // Sauvegarde l'état actuel dans la pile d'annulation
        undoStack.push(new GameState(board.clone(), currentPlayer));
        // Vide la pile de rétablissement car un nouvel état a été ajouté
        redoStack.clear();
    }

    public GameState undo(Piece currentPlayer) {
        if (undoStack.isEmpty()) {
            System.out.println("Undo: No states to undo.");
            return null;
        }
        // Sauvegarde l'état actuel avant d'annuler
        redoStack.push(new GameState(board.clone(), currentPlayer));
        // Restaure l'état précédent
        return undoStack.pop();
    }

    public GameState redo(Piece currentPlayer) {
        if (redoStack.isEmpty()) {
            System.out.println("Redo: No states to redo.");
            return null;
        }
        // Sauvegarde l'état actuel avant de rétablir
        undoStack.push(new GameState(board.clone(), currentPlayer));
        // Restaure l'état suivant
        return redoStack.pop();
    }
}
