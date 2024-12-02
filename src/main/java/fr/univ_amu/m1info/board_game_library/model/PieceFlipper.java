package fr.univ_amu.m1info.board_game_library.model;

public class PieceFlipper {

    public void flipPieces(OthelloBoard board, int row, int col, Piece currentPlayer) {
        int[][] directions = {
                {-1, 0}, {1, 0}, {0, -1}, {0, 1},       // gauche, haut, bas, droite
                {-1, -1}, {-1, 1}, {1, -1}, {1, 1}     // Diagonales
        };

        System.out.println("Retourner les pions pour le coup à (" + row + ", " + col + ")");
        for (int[] dir : directions) {
            flipInDirection(board, row, col, dir[0], dir[1], currentPlayer);
        }
    }

    private void flipInDirection(OthelloBoard board, int row, int col, int rowDir, int colDir, Piece currentPlayer) {
        Piece opponent = (currentPlayer == Piece.BLACK) ? Piece.WHITE : Piece.BLACK;
        int r = row + rowDir, c = col + colDir;
        boolean hasOpponent = false;

        // Vérifie si la direction contient des pions adverses suivis d'un pion du joueur
        while (r >= 0 && r < board.getSize() && c >= 0 && c < board.getSize()) {
            if (board.getPieceAt(r, c) == opponent) {
                hasOpponent = true;
            } else if (board.getPieceAt(r, c) == currentPlayer) {
                if (hasOpponent) {
                    // Retourne les pions entre (row, col) et (r, c)
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
            board.placePiece(r, c, currentPlayer); // Mettre à jour le plateau logique
            r += rowDir;
            c += colDir;
        }
    }
}
