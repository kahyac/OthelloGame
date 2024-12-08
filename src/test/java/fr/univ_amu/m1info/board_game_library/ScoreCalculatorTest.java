package fr.univ_amu.m1info.board_game_library;

import fr.univ_amu.m1info.board_game_library.model.OthelloBoard;
import fr.univ_amu.m1info.board_game_library.model.Piece;
import fr.univ_amu.m1info.board_game_library.model.ScoreCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScoreCalculatorTest {
    private OthelloBoard board;
    private ScoreCalculator scoreCalculator;

    @BeforeEach
    void setUp() {
        board = new OthelloBoard();
        scoreCalculator = new ScoreCalculator(board);
    }

    @Test
    void testCalculateScoreInitialBoard() {
        // Test sur le plateau initial
        assertEquals(2, ScoreCalculator.calculateScore(Piece.BLACK), "Score initial pour BLACK doit être 2");
        assertEquals(2, ScoreCalculator.calculateScore(Piece.WHITE), "Score initial pour WHITE doit être 2");
    }

    @Test
    void testCalculateScoreAfterPlacement() {
        // Ajout de pièces supplémentaires
        board.placePiece(2, 3, Piece.BLACK);
        board.placePiece(3, 2, Piece.WHITE);
        board.placePiece(4, 5, Piece.BLACK);

        // Vérification des scores
        assertEquals(4, ScoreCalculator.calculateScore(Piece.BLACK), "Score pour BLACK après placement doit être 4");
        assertEquals(3, ScoreCalculator.calculateScore(Piece.WHITE), "Score pour WHITE après placement doit être 3");
    }

    @Test
    void testDetermineWinnerBlackWins() {
        // Remplissage partiel du plateau
        board.placePiece(2, 3, Piece.BLACK);
        board.placePiece(4, 5, Piece.BLACK);

        String result = ScoreCalculator.determineWinner();
        assertEquals("Noir gagne !", result, "BLACK doit gagner avec un score supérieur");
    }

    @Test
    void testDetermineWinnerWhiteWins() {
        // Remplissage partiel du plateau
        board.placePiece(2, 3, Piece.WHITE);
        board.placePiece(4, 5, Piece.WHITE);

        String result = ScoreCalculator.determineWinner();
        assertEquals("Blanc gagne !", result, "WHITE doit gagner avec un score supérieur");
    }

    @Test
    void testDetermineWinnerTie() {
        // Le plateau est équilibré avec un nombre égal de pièces
        board.placePiece(2, 3, Piece.BLACK);
        board.placePiece(3, 2, Piece.WHITE);

        String result = ScoreCalculator.determineWinner();
        assertEquals("Égalité !", result, "Le résultat doit être une égalité avec des scores égaux");
    }
}
