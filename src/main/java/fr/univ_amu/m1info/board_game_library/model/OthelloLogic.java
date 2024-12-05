package fr.univ_amu.m1info.board_game_library.model;

import java.util.ArrayList;
import java.util.List;

public class OthelloLogic {
    protected final OthelloBoard board;

    public OthelloLogic(OthelloBoard board) {
        this.board = board;
    }

    public boolean isValidMove(int row, int col, Piece player) {
        if (board.getPieceAt(row, col) != Piece.EMPTY) {
            return false;
        }
        return hasFlippablePieces(row, col, player);
    }

    private boolean hasFlippablePieces(int row, int col, Piece player) {
        for (int[] direction : DIRECTIONS) {
            if (!findFlippablePieces(row, col, player, direction[0], direction[1]).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private static final int[][] DIRECTIONS = {
            {-1, 0}, {1, 0}, {0, -1}, {0, 1},
            {-1, -1}, {-1, 1}, {1, -1}, {1, 1}
    };

    public List<int[]> findFlippablePieces(int row, int col, Piece player, int dRow, int dCol) {
        List<int[]> flippable = new ArrayList<>();
        int currentRow = row + dRow;
        int currentCol = col + dCol;
        Piece opponent = (player == Piece.BLACK) ? Piece.WHITE : Piece.BLACK;

        while (board.isValidPosition(currentRow, currentCol) && board.getPieceAt(currentRow, currentCol) == opponent) {
            flippable.add(new int[]{currentRow, currentCol});
            currentRow += dRow;
            currentCol += dCol;
        }

        if (board.isValidPosition(currentRow, currentCol) && board.getPieceAt(currentRow, currentCol) == player) {
            return flippable;
        }

        return new ArrayList<>();
    }

    public void updateBoardState(OthelloBoard board) {
        this.board.copyFrom(board);
    }

    public void playMove(int row, int col, Piece player) {
        for (int[] direction : DIRECTIONS) {
            List<int[]> flippable = findFlippablePieces(row, col, player, direction[0], direction[1]);
            for (int[] position : flippable) {
                board.placePiece(position[0], position[1], player);
            }
        }
        board.placePiece(row, col, player);
    }

    public boolean canPlayerPlay(Piece player) {
        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {
                if (isValidMove(row, col, player)) {
                    return true;
                }
            }
        }
        return false;
    }
}
