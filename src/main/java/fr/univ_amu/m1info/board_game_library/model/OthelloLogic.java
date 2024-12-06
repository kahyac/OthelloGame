package fr.univ_amu.m1info.board_game_library.model;

import java.util.ArrayList;
import java.util.List;

public class OthelloLogic {
    private final OthelloBoard board;
    private final PieceFlipper flipper;

    public OthelloLogic(OthelloBoard board) {
        this.board = board;
        this.flipper = new PieceFlipper();
    }

    public boolean isValidMove(int row, int col, Piece player) {
        if (board.getPieceAt(row, col) != Piece.EMPTY) return false; // Case déjà occupée
        return hasFlippablePieces(row, col, player); // Vérifie s'il y a des pions à retourner
    }

    private boolean hasFlippablePieces(int row, int col, Piece player) {
        for (int[] direction : PieceFlipper.DIRECTIONS) {
            if (!findFlippablePieces(row, col, player, direction[0], direction[1]).isEmpty()) {
                return true; // Une direction retourne des pions
            }
        }
        return false;
    }

    public List<Position> findFlippablePieces(int row, int col, Piece player, int dRow, int dCol) {
        List<Position> flippable = new ArrayList<>();
        int currentRow = row + dRow;
        int currentCol = col + dCol;
        Piece opponent = (player == Piece.BLACK) ? Piece.WHITE : Piece.BLACK;

        while (board.isValidPosition(currentRow, currentCol) && board.getPieceAt(currentRow, currentCol) == opponent) {
            flippable.add(new Position(currentRow, currentCol));
            currentRow += dRow;
            currentCol += dCol;
        }

        if (board.isValidPosition(currentRow, currentCol) && board.getPieceAt(currentRow, currentCol) == player) {
            return flippable;
        }

        return new ArrayList<>();
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

    public void updateBoardState(OthelloBoard newBoardState) {
        board.copyFrom(newBoardState);
    }

    public void flipPieces(int row, int col, Piece currentPlayer) {
        System.out.println("Flipping pieces for move at (" + row + ", " + col + ")");
        flipper.flipPieces(board, row, col, currentPlayer);
    }

    public List<Position> getValidMoves(Piece player) {
        List<Position> validMoves = new ArrayList<>();
        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {
                if (isValidMove(row, col, player)) {
                    validMoves.add(new Position(row, col));
                }
            }
        }
        return validMoves;
    }

    public OthelloBoard getBoard() {
        return board;
    }
}
