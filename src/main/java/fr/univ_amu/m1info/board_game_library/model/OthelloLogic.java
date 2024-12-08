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
        if (!board.isValidPosition(row, col) || board.getPieceAt(row, col) != Piece.EMPTY) {
            return false;
        }
        return hasFlippablePieces(row, col, player);
    }

    private boolean hasFlippablePieces(int row, int col, Piece player) {
        for (int[] direction : PieceFlipper.DIRECTIONS) {
            if (!flipper.findFlippablePieces(board, row, col, player, direction[0], direction[1]).isEmpty()) {
                return true;
            }
        }
        return false;
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

    public void flipPieces(int row, int col, Piece currentPlayer) {
        flipper.flipPieces(board, row, col, currentPlayer);
    }

    public boolean canPlayerPlay(Piece player) {
        return !getValidMoves(player).isEmpty();
    }

    public void updateBoardState(OthelloBoard newBoardState) {
        board.copyFrom(newBoardState);
    }

    public Position getBestMoveUsingMinimax(Piece player, int depth) {
        int bestScore = Integer.MIN_VALUE;
        Position bestMove = null;

        List<Position> validMoves = getValidMoves(player);

        for (Position move : validMoves) {
            OthelloBoard boardCopy = board.clone();
            OthelloLogic logicCopy = new OthelloLogic(boardCopy);
            logicCopy.flipPieces(move.getRow(), move.getCol(), player);
            boardCopy.placePiece(move.getRow(), move.getCol(), player);

            int score = minimax(logicCopy, depth - 1, false, player.opponent());
            if (score > bestScore) {
                bestScore = score;
                bestMove = move;
            }
        }

        return bestMove;
    }

    // Implementes Minimax Algorithm
    private int minimax(OthelloLogic logic, int depth, boolean isMaximizing, Piece currentPlayer) {
        if (depth == 0 || !logic.canPlayerPlay(Piece.BLACK) && !logic.canPlayerPlay(Piece.WHITE)) {
            return evaluateBoard(logic.getBoard(), currentPlayer);
        }

        List<Position> validMoves = logic.getValidMoves(currentPlayer);

        if (isMaximizing) {
            int maxEval = Integer.MIN_VALUE;
            for (Position move : validMoves) {
                OthelloBoard boardCopy = logic.getBoard().clone();
                OthelloLogic logicCopy = new OthelloLogic(boardCopy);
                logicCopy.flipPieces(move.getRow(), move.getCol(), currentPlayer);
                boardCopy.placePiece(move.getRow(), move.getCol(), currentPlayer);
                int eval = minimax(logicCopy, depth - 1, false, currentPlayer.opponent());
                maxEval = Math.max(maxEval, eval);
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (Position move : validMoves) {
                OthelloBoard boardCopy = logic.getBoard().clone();
                OthelloLogic logicCopy = new OthelloLogic(boardCopy);
                logicCopy.flipPieces(move.getRow(), move.getCol(), currentPlayer);
                boardCopy.placePiece(move.getRow(), move.getCol(), currentPlayer);
                int eval = minimax(logicCopy, depth - 1, true, currentPlayer.opponent());
                minEval = Math.min(minEval, eval);
            }
            return minEval;
        }
    }

    // Évalue le plateau pour calculer un score
    private int evaluateBoard(OthelloBoard board, Piece player) {
        int playerScore = 0;
        int opponentScore = 0;

        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {
                Piece piece = board.getPieceAt(row, col);
                if (piece == player) playerScore++;
                else if (piece == player.opponent()) opponentScore++;
            }
        }
        return playerScore - opponentScore;
    }

    public OthelloBoard getBoard() {
        return board;
    }
}
