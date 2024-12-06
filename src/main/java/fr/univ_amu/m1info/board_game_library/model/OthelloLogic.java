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


    public List<int[]> findFlippablePieces(int row, int col, Piece player, int dRow, int dCol) {
        List<int[]> flippable = new ArrayList<>();
        int currentRow = row + dRow;
        int currentCol = col + dCol;
        Piece opponent = (player == Piece.BLACK) ? Piece.WHITE : Piece.BLACK;

        System.out.println("Checking direction: (" + dRow + ", " + dCol + ") from (" + row + ", " + col + ")");

        // Traverse the direction
        while (board.isValidPosition(currentRow, currentCol) && board.getPieceAt(currentRow, currentCol) == opponent) {
            flippable.add(new int[]{currentRow, currentCol});
            System.out.println("Found opponent piece at: (" + currentRow + ", " + currentCol + ")");
            currentRow += dRow;
            currentCol += dCol;
        }

        // Check if the direction ends on the player's piece
        if (board.isValidPosition(currentRow, currentCol) && board.getPieceAt(currentRow, currentCol) == player) {
            System.out.println("Direction valid. Ends at: (" + currentRow + ", " + currentCol + ")");
            return flippable;
        }

        System.out.println("Direction invalid.");
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
        for (int[] direction : PieceFlipper.DIRECTIONS) {
            List<int[]> flippable = findFlippablePieces(row, col, currentPlayer, direction[0], direction[1]);
            for (int[] piece : flippable) {
                board.placePiece(piece[0], piece[1], currentPlayer);
                System.out.println("Flipped piece at (" + piece[0] + ", " + piece[1] + ")");
            }
        }
    }


    public OthelloBoard getBoard() {
        return board;
    }
}
