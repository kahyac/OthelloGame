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
        undoStack.push(new GameState(board.clone(), currentPlayer));
        redoStack.clear(); // Vide la pile redo après une nouvelle action
    }


    public GameState undo(Piece currentPlayer) {
        if (undoStack.isEmpty()) return null;

        redoStack.push(new GameState(board.clone(), currentPlayer)); // Sauvegarde l'état actuel
        GameState previousState = undoStack.pop(); // Récupère l'état précédent
        board.copyFrom(previousState.boardState()); // Applique l'état précédent
        return previousState;
    }




    public GameState redo(Piece currentPlayer) {
        if (redoStack.isEmpty()) {
            System.out.println("Redo: No states to redo.");
            return null;
        }
        // Sauvegarde l'état actuel dans undo avant de restaurer
        undoStack.push(new GameState(board.clone(), currentPlayer));

        // Restaure l'état suivant
        GameState nextState = redoStack.pop();
        board.copyFrom(nextState.boardState()); // Applique l'état restauré au plateau
        return nextState;
    }

}
