package fr.univ_amu.m1info.board_game_library;

import fr.univ_amu.m1info.board_game_library.model.OthelloBoard;
import fr.univ_amu.m1info.board_game_library.model.Piece;
import fr.univ_amu.m1info.board_game_library.model.PieceFlipper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PieceFlipperTest {

    private OthelloBoard board;
    private PieceFlipper pieceFlipper;

    @BeforeEach
    void setUp() {
        board = new OthelloBoard();
        pieceFlipper = new PieceFlipper();
    }

    @Test
    void testFlipPiecesHorizontal() {
        // Arrange
        board.placePiece(3, 2, Piece.BLACK); // Place une pièce à gauche des pièces adverses
        pieceFlipper.flipPieces(board, 3, 2, Piece.BLACK);

        // Assert: Les pièces entre (3,2) et (3,4) doivent être retournées
        assertEquals(Piece.BLACK, board.getPieceAt(3, 3), "La pièce à (3,3) doit être retournée en BLACK");
        assertEquals(Piece.BLACK, board.getPieceAt(3, 4), "La pièce à (3,4) doit rester BLACK");
    }

    @Test
    void testFlipPiecesVertical() {
        // Arrange
        board.placePiece(2, 3, Piece.BLACK); // Place une pièce au-dessus des pièces adverses
        pieceFlipper.flipPieces(board, 2, 3, Piece.BLACK);

        // Assert: Les pièces entre (2,3) et (4,3) doivent être retournées
        assertEquals(Piece.BLACK, board.getPieceAt(3, 3), "La pièce à (3,3) doit être retournée en BLACK");
        assertEquals(Piece.BLACK, board.getPieceAt(4, 3), "La pièce à (4,3) doit rester BLACK");
    }

    @Test
    void testFlipPiecesDiagonal() {
        // Arrange: Préparer un cas de retournement en diagonale
        board.placePiece(2, 2, Piece.BLACK); // Position finale pour retourner les pièces
        board.placePiece(3, 3, Piece.WHITE); // Pièce de l'adversaire
        board.placePiece(4, 4, Piece.WHITE); // Pièce de l'adversaire
        board.placePiece(5, 5, Piece.BLACK); // Pièce du joueur pour enclencher le retournement

        // Act: Appliquer le retournement
        pieceFlipper.flipPieces(board, 2, 2, Piece.BLACK);

        // Assert: Les pièces adverses entre (3,3) et (4,4) doivent être retournées
        assertEquals(Piece.BLACK, board.getPieceAt(3, 3), "La pièce à (3,3) doit être retournée en BLACK");
        assertEquals(Piece.BLACK, board.getPieceAt(4, 4), "La pièce à (4,4) doit être retournée en BLACK");
        assertEquals(Piece.BLACK, board.getPieceAt(2, 2), "La pièce à (2,2) doit être BLACK");
        assertEquals(Piece.BLACK, board.getPieceAt(5, 5), "La pièce à (5,5) reste BLACK");
    }


    @Test
    void testNoFlipForInvalidMove() {
        // Arrange: Place une pièce où il n'y a aucune pièce adverse à retourner
        board.placePiece(0, 0, Piece.BLACK);
        pieceFlipper.flipPieces(board, 0, 0, Piece.BLACK);

        // Assert: Le plateau ne doit pas changer autour de (0,0)
        assertEquals(Piece.BLACK, board.getPieceAt(0, 0), "La pièce placée doit rester BLACK");
        assertEquals(Piece.EMPTY, board.getPieceAt(1, 0), "Aucune pièce ne doit être retournée à (1,0)");
        assertEquals(Piece.EMPTY, board.getPieceAt(0, 1), "Aucune pièce ne doit être retournée à (0,1)");
    }

    @Test
    void testMultipleFlips() {
        // Arrange: Place plusieurs pièces pour retourner dans différentes directions
        board.placePiece(3, 2, Piece.BLACK);
        board.placePiece(2, 3, Piece.BLACK);
        board.placePiece(4, 4, Piece.BLACK);

        pieceFlipper.flipPieces(board, 3, 2, Piece.BLACK);
        pieceFlipper.flipPieces(board, 2, 3, Piece.BLACK);
        pieceFlipper.flipPieces(board, 4, 4, Piece.BLACK);

        // Assert: Vérifie les retournements multiples
        assertEquals(Piece.BLACK, board.getPieceAt(3, 3), "La pièce à (3,3) doit être retournée en BLACK");
        assertEquals(Piece.BLACK, board.getPieceAt(4, 3), "La pièce à (4,3) doit être retournée en BLACK");
        assertEquals(Piece.BLACK, board.getPieceAt(3, 4), "La pièce à (3,4) doit être BLACK");
    }
}
