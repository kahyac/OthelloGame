package fr.univ_amu.m1info.board_game_library.model;

public class PieceFlipper {

    // Directions statiques communes pour les déplacements dans le plateau
    public static final int[][] DIRECTIONS = {
            {-1, 0}, {1, 0}, {0, -1}, {0, 1},
            {-1, -1}, {-1, 1}, {1, -1}, {1, 1}
    };

    public void flipPieces(OthelloBoard board, int row, int col, Piece currentPlayer) {
        for (int[] dir : DIRECTIONS) {
            flipInDirection(board, row, col, dir[0], dir[1], currentPlayer);
        }
    }

    private void flipInDirection(OthelloBoard board, int row, int col, int rowDir, int colDir, Piece currentPlayer) {
        Piece opponent = (currentPlayer == Piece.BLACK) ? Piece.WHITE : Piece.BLACK;
        int r = row + rowDir, c = col + colDir;
        boolean hasOpponent = false;

        while (board.isValidPosition(r, c)) {
            Piece piece = board.getPieceAt(r, c);
            if (piece == opponent) {
                hasOpponent = true;
            } else if (piece == currentPlayer) {
                if (hasOpponent) {
                    flipBetween(board, row, col, r, c, rowDir, colDir, currentPlayer);
                }
                break;
            } else {
                break;
            }
            r += rowDir;
            c += colDir;
        }
    }

    private void flipBetween(OthelloBoard board, int startRow, int startCol, int endRow, int endCol, int rowDir, int colDir, Piece currentPlayer) {
        int r = startRow + rowDir, c = startCol + colDir;
        while (r != endRow || c != endCol) {
            board.placePiece(r, c, currentPlayer);
            r += rowDir;
            c += colDir;
        }
    }
}
