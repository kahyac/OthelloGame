package fr.univ_amu.m1info.board_game_library.model;

public class ScoreCalculator {
    private final OthelloBoard board;

    public ScoreCalculator(OthelloBoard board) {
        this.board = board;
    }

    public int calculateScore(Piece player) {
        int score = 0;
        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {
                if (board.getPieceAt(row, col) == player) {
                    score++;
                }
            }
        }
        return score;
    }
}
