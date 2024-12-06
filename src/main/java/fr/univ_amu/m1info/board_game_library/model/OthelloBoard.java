package fr.univ_amu.m1info.board_game_library.model;

public class OthelloBoard {
    private final int size = 8;
    private final Piece[][] board;

    public OthelloBoard() {
        board = new Piece[size][size];
        initializeBoard();
    }

    public void initializeBoard() {
        // Initialize every case as empty
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                board[row][col] = Piece.EMPTY;
            }
        }

        // Place the 4 initial pawns
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

    public OthelloBoard clone() {
        OthelloBoard clonedBoard = new OthelloBoard();
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                clonedBoard.board[row][col] = this.board[row][col]; // copy every case
            }
        }
        return clonedBoard;
    }

    public void copyFrom(OthelloBoard otherBoard) {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                this.board[row][col] = otherBoard.board[row][col]; // Cases update
            }
        }
    }

    public boolean isValidPosition(int row, int col) {
        return row >= 0 && row < size && col >= 0 && col < size;
    }

    public boolean isFull() {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (board[row][col] == Piece.EMPTY) {
                    return false; // If any cell is empty, the board is not full
                }
            }
        }
        return true; // All cells are occupied
    }
}

