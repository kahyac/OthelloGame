package fr.univ_amu.m1info.board_game_library;

import fr.univ_amu.m1info.board_game_library.model.OthelloBoard;
import fr.univ_amu.m1info.board_game_library.model.Piece;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OthelloBoardTest {

    private OthelloBoard board;

    @BeforeEach
    void setUp() {
        board = new OthelloBoard();
    }

    @Test
    void testBoardInitialization() {
        assertEquals(8, board.getSize(), "La taille du plateau doit être 8x8");

        // Vérifie que les 4 pièces initiales sont correctement placées
        assertEquals(Piece.WHITE, board.getPieceAt(3, 3), "La pièce à (3, 3) doit être WHITE");
        assertEquals(Piece.BLACK, board.getPieceAt(3, 4), "La pièce à (3, 4) doit être BLACK");
        assertEquals(Piece.BLACK, board.getPieceAt(4, 3), "La pièce à (4, 3) doit être BLACK");
        assertEquals(Piece.WHITE, board.getPieceAt(4, 4), "La pièce à (4, 4) doit être WHITE");

        // Vérifie que toutes les autres cases sont vides
        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {
                if (!((row == 3 && col == 3) || (row == 3 && col == 4) ||
                        (row == 4 && col == 3) || (row == 4 && col == 4))) {
                    assertEquals(Piece.EMPTY, board.getPieceAt(row, col),
                            "Toutes les cases non initiales doivent être EMPTY");
                }
            }
        }
    }

    @Test
    void testPlacePiece() {
        board.placePiece(2, 3, Piece.BLACK);
        assertEquals(Piece.BLACK, board.getPieceAt(2, 3), "La pièce à (2, 3) doit être BLACK après placement");

        board.placePiece(5, 5, Piece.WHITE);
        assertEquals(Piece.WHITE, board.getPieceAt(5, 5), "La pièce à (5, 5) doit être WHITE après placement");
    }

    @Test
    void testIsValidPosition() {
        assertTrue(board.isValidPosition(0, 0), "(0, 0) est une position valide");
        assertTrue(board.isValidPosition(7, 7), "(7, 7) est une position valide");

        assertFalse(board.isValidPosition(-1, 0), "(-1, 0) n'est pas une position valide");
        assertFalse(board.isValidPosition(0, 8), "(0, 8) n'est pas une position valide");
        assertFalse(board.isValidPosition(8, 8), "(8, 8) n'est pas une position valide");
    }

    @Test
    void testCloneBoard() {
        board.placePiece(2, 2, Piece.BLACK);
        OthelloBoard clonedBoard = board.clone();

        // Vérifie que le clone est identique mais indépendant
        assertEquals(board.getPieceAt(2, 2), clonedBoard.getPieceAt(2, 2), "Le clone doit avoir la même pièce à (2, 2)");
        clonedBoard.placePiece(2, 2, Piece.WHITE);
        assertNotEquals(board.getPieceAt(2, 2), clonedBoard.getPieceAt(2, 2),
                "Modifier le clone ne doit pas affecter l'original");
    }

    @Test
    void testCopyFrom() {
        OthelloBoard otherBoard = new OthelloBoard();
        otherBoard.placePiece(3, 5, Piece.BLACK);

        board.copyFrom(otherBoard);

        assertEquals(Piece.BLACK, board.getPieceAt(3, 5), "La pièce copiée à (3, 5) doit être BLACK");
    }

    @Test
    void testIsFull() {
        // Remplit tout le plateau avec des pièces
        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {
                board.placePiece(row, col, Piece.BLACK);
            }
        }

        assertTrue(board.isFull(), "Le plateau devrait être plein");

        // Vide une case
        board.placePiece(4, 4, Piece.EMPTY);

        assertFalse(board.isFull(), "Le plateau ne doit pas être plein si une case est vide");
    }

    @Test
    void testCloneIndependence() {
        board.placePiece(3, 3, Piece.BLACK);
        OthelloBoard clonedBoard = board.clone();

        // Modifie le clone
        clonedBoard.placePiece(3, 3, Piece.EMPTY);

        // Vérifie que l'original reste inchangé
        assertEquals(Piece.BLACK, board.getPieceAt(3, 3), "L'original ne doit pas être affecté par les modifications du clone.");
        assertEquals(Piece.EMPTY, clonedBoard.getPieceAt(3, 3), "Le clone doit être modifiable indépendamment.");
    }

}
