package fr.univ_amu.m1info.board_game_library.model;

public class OthelloMoveValidator implements MoveValidator {

    @Override
    public boolean isValidMove(OthelloBoard board, int row, int col, Piece currentPlayer) {
        boolean isValid = board.getPieceAt(row, col) == Piece.EMPTY
                && hasCapturablePieces(board, row, col, currentPlayer);
        System.out.println("isValidMove(" + row + ", " + col + ") = " + isValid);
        return isValid;
    }


    private boolean hasCapturablePieces(OthelloBoard board, int row, int col, Piece currentPlayer) {
        Piece opponent = (currentPlayer == Piece.BLACK) ? Piece.WHITE : Piece.BLACK;

        int[][] directions = {
                {-1, 0}, {1, 0}, {0, -1}, {0, 1},       // Haut, bas, gauche, droite
                {-1, -1}, {-1, 1}, {1, -1}, {1, 1}     // Diagonales
        };

        for (int[] dir : directions) {
            int r = row + dir[0];
            int c = col + dir[1];
            boolean hasOpponentBetween = false;

            System.out.println("Checking direction: (" + dir[0] + ", " + dir[1] + ") for (" + row + ", " + col + ")");
            while (r >= 0 && r < board.getSize() && c >= 0 && c < board.getSize()) {
                if (board.getPieceAt(r, c) == opponent) {
                    hasOpponentBetween = true;
                } else if (board.getPieceAt(r, c) == currentPlayer) {
                    if (hasOpponentBetween) {
                        System.out.println("Valid direction for move at (" + row + ", " + col + ")");
                        return true;
                    }
                    break;
                } else {
                    break;
                }
                r += dir[0];
                c += dir[1];
            }
        }

        return false; // Aucun pion capturable
    }




}
