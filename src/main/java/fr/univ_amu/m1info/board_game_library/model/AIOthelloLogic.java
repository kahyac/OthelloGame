package fr.univ_amu.m1info.board_game_library.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AIOthelloLogic extends OthelloLogic {
    private final Random random;

    public AIOthelloLogic(OthelloBoard board) {
        super(board);
        this.random = new Random();
    }

    public int[] getBestMove(Piece aiPlayer) {
        List<int[]> validMoves = getValidMoves(aiPlayer);
        if (!validMoves.isEmpty()) {
            return validMoves.get(random.nextInt(validMoves.size()));
        }
        return null;
    }

    private List<int[]> getValidMoves(Piece player) {
        List<int[]> validMoves = new ArrayList<>();
        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {
                if (isValidMove(row, col, player)) {
                    validMoves.add(new int[]{row, col});
                }
            }
        }
        return validMoves;
    }
}
