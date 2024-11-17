package fr.univ_amu.m1info.board_game_library.model;

public class OthelloBoard {
    private final int size = 8;
    private final Piece[][] board;

    public OthelloBoard() {
        board = new Piece[size][size];
        initializeBoard();
    }

    private void initializeBoard() {
        // Initialise toutes les cases comme vides
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                board[row][col] = Piece.EMPTY;
            }
        }

        // Place les 4 pions centraux
        board[3][3] = Piece.WHITE;
        board[3][4] = Piece.BLACK;
        board[4][3] = Piece.BLACK;
        board[4][4] = Piece.WHITE;
    }

    public Piece getPieceAt(int row, int col) {
        return board[row][col];
    }

    public void placePiece(int row, int col, Piece piece) {
        board[row][col] = piece;
    }

    public int getSize() {
        return size;
    }
}

