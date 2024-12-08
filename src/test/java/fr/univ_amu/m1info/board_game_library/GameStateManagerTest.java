package fr.univ_amu.m1info.board_game_library;

import fr.univ_amu.m1info.board_game_library.model.GameState;
import fr.univ_amu.m1info.board_game_library.model.GameStateManager;
import fr.univ_amu.m1info.board_game_library.model.OthelloBoard;
import fr.univ_amu.m1info.board_game_library.model.Piece;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameStateManagerTest {

    private OthelloBoard board;
    private GameStateManager stateManager;

    @BeforeEach
    void setUp() {
        board = new OthelloBoard(); // Réinitialise le plateau à chaque test
        stateManager = new GameStateManager(board);
    }

    @Test
    void testInitializeBoard() {
        OthelloBoard testBoard = new OthelloBoard();

        assertEquals(Piece.WHITE, testBoard.getPieceAt(3, 3), "La pièce à (3,3) doit être WHITE.");
        assertEquals(Piece.BLACK, testBoard.getPieceAt(3, 4), "La pièce à (3,4) doit être BLACK.");
        assertEquals(Piece.BLACK, testBoard.getPieceAt(4, 3), "La pièce à (4,3) doit être BLACK.");
        assertEquals(Piece.WHITE, testBoard.getPieceAt(4, 4), "La pièce à (4,4) doit être WHITE.");
    }



    @Test
    void testSaveStateAndUndo() {
        Piece currentPlayer = Piece.BLACK;

        // Étape 1 : Vérifiez l'état initial
        assertEquals(Piece.WHITE, board.getPieceAt(3, 3), "La pièce à (3,3) doit être WHITE initialement.");

        // Étape 2 : Place une pièce à (3,2) et sauvegarde l'état
        board.placePiece(3, 2, Piece.BLACK);
        stateManager.saveState(currentPlayer);

        // Étape 3 : Modifier la position (3,3)
        board.placePiece(3, 3, Piece.EMPTY);
        assertEquals(Piece.EMPTY, board.getPieceAt(3, 3), "La pièce à (3,3) doit être EMPTY après modification.");

        // Étape 4 : Undo pour revenir à l'état sauvegardé
        GameState previousState = stateManager.undo(currentPlayer);

        // Vérification après undo
        assertNotNull(previousState);
        assertEquals(Piece.WHITE, board.getPieceAt(3, 3), "La pièce à (3,3) doit être restaurée à WHITE.");
        assertEquals(Piece.BLACK, board.getPieceAt(3, 2), "La pièce à (3,2) doit rester BLACK.");
    }








    @Test
    void testRedoAfterUndo() {
        // Arrange: État initial avec une pièce posée
        Piece currentPlayer = Piece.BLACK;
        board.placePiece(3, 2, Piece.BLACK);
        stateManager.saveState(currentPlayer);

        // Modification et sauvegarde d'un nouvel état
        board.placePiece(3, 3, Piece.BLACK);
        stateManager.saveState(currentPlayer);

        // Undo deux fois
        stateManager.undo(currentPlayer);
        stateManager.undo(currentPlayer);

        // Act: Redo pour rétablir l'état
        GameState redoneState = stateManager.redo(currentPlayer);

        // Assert: La pièce à (3,2) est rétablie après redo
        assertNotNull(redoneState);
        assertEquals(Piece.BLACK, board.getPieceAt(3, 2), "La pièce à (3,2) doit être rétablie.");
    }

    @Test
    void testRedoClearedAfterSaveState() {
        // Arrange: Sauvegarde d'un état initial
        Piece currentPlayer = Piece.BLACK;
        board.placePiece(3, 2, Piece.BLACK);
        stateManager.saveState(currentPlayer);

        // Undo pour générer un état dans redoStack
        stateManager.undo(currentPlayer);

        // Act: Nouvelle sauvegarde
        board.placePiece(3, 3, Piece.BLACK);
        stateManager.saveState(currentPlayer);

        // Assert: RedoStack est vidé
        assertNull(stateManager.redo(currentPlayer), "Redo doit être vide après saveState.");
    }

    @Test
    void testUndoWhenStackIsEmpty() {
        // Act: Essayer un undo sans sauvegarde
        GameState result = stateManager.undo(Piece.BLACK);

        // Assert: Aucun état n'est restauré
        assertNull(result, "Undo doit retourner null si aucun état n'est disponible.");
    }

    @Test
    void testRedoWhenStackIsEmpty() {
        // Act: Essayer un redo sans états disponibles
        GameState result = stateManager.redo(Piece.BLACK);

        // Assert: Aucun état n'est restauré
        assertNull(result, "Redo doit retourner null si aucun état n'est disponible.");
    }
}
