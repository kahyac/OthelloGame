package fr.univ_amu.m1info.board_game_library.model;

public class GameStateManager {
    private final OthelloBoard board;
    private final MoveValidator moveValidator;
    private final PieceFlipper pieceFlipper;
    private Piece currentPlayer;

    public GameStateManager(OthelloBoard board, MoveValidator moveValidator, PieceFlipper pieceFlipper) {
        this.board = board;
        this.moveValidator = moveValidator;
        this.pieceFlipper = pieceFlipper;
        this.currentPlayer = Piece.BLACK; // Noir commence toujours
    }

    public boolean playMove(int row, int col) {
        System.out.println("Tentative de coup en (" + row + ", " + col + ") pour " +
                (currentPlayer == Piece.BLACK ? "Noir" : "Blanc"));

        if (moveValidator.isValidMove(board, row, col, currentPlayer)) {
            board.placePiece(row, col, currentPlayer);
            pieceFlipper.flipPieces(board, row, col, currentPlayer);
            togglePlayer();
            return true;
        }
        return false;
    }


    private void togglePlayer() {
        currentPlayer = (currentPlayer == Piece.BLACK) ? Piece.WHITE : Piece.BLACK;
    }

    public boolean isGameOver() {
        boolean boardFull = true;

        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {
                if (board.getPieceAt(row, col) == Piece.EMPTY) {
                    boardFull = false;
                }
            }
        }

        boolean noMovesForBlack = !canPlayerPlay(Piece.BLACK);
        boolean noMovesForWhite = !canPlayerPlay(Piece.WHITE);

        if (boardFull || (noMovesForBlack && noMovesForWhite)) {
            System.out.println("Fin de partie détectée.");
            return true;
        }
        return false;
    }


    private boolean canPlayerPlay(Piece player) {
        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {
                if (board.getPieceAt(row, col) == Piece.EMPTY &&
                        moveValidator.isValidMove(board, row, col, player)) {
                    return true;
                }
            }
        }
        return false;
    }


    public String handleEndOfGame() {
        int blackScore = calculateScore(Piece.BLACK);
        int whiteScore = calculateScore(Piece.WHITE);

        String winner;
        if (blackScore > whiteScore) {
            winner = "Le joueur Noir a gagné ! 🎉";
        } else if (whiteScore > blackScore) {
            winner = "Le joueur Blanc a gagné ! 🎉";
        } else {
            winner = "Égalité ! 🤝";
        }

        System.out.println("Fin de la partie !");
        System.out.println("Score Noir : " + blackScore);
        System.out.println("Score Blanc : " + whiteScore);
        System.out.println(winner);

        return "Score Noir : " + blackScore + " | Score Blanc : " + whiteScore + "\n" + winner;
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
