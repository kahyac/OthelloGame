package fr.univ_amu.m1info.board_game_library;

import fr.univ_amu.m1info.board_game_library.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HumanVsHumanModeTest {
    private OthelloBoard board;
    private OthelloLogic logic;
    private HumanVsHumanMode gameMode;

    @BeforeEach
    void setUp() {
        board = new OthelloBoard();
        logic = new OthelloLogic(board);
        gameMode = new HumanVsHumanMode(logic);
    }

    @Test
    void testValidHumanMove() {
        Position position = new Position(2, 3);
        assertTrue(logic.isValidMove(position.getRow(), position.getCol(), Piece.BLACK));

        gameMode.playMove(position, Piece.BLACK);
        assertEquals(Piece.BLACK, board.getPieceAt(position.getRow(), position.getCol()));
    }
}
