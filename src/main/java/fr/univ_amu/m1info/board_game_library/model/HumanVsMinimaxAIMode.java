package fr.univ_amu.m1info.board_game_library.model;

import java.util.List;

public class HumanVsMinimaxAIMode extends GameMode {

    public HumanVsMinimaxAIMode(OthelloLogic logic) {
        super(logic);
    }

    @Override
    public void playMove(Position position, Piece currentPlayer) {
        if (currentPlayer == Piece.BLACK) {
            // Coup du joueur humain
            if (!logic.isValidMove(position.getRow(), position.getCol(), currentPlayer)) {
                throw new IllegalArgumentException("Invalid move.");
            }
            logic.flipPieces(position.getRow(), position.getCol(), currentPlayer);
            logic.getBoard().placePiece(position.getRow(), position.getCol(), currentPlayer);
        } else if (currentPlayer == Piece.WHITE) {
            // Coup de l'IA utilisant Minimax
            Position bestMove = findBestMove(currentPlayer, 3); // Profondeur = 3
            if (bestMove != null) {
                logic.flipPieces(bestMove.getRow(), bestMove.getCol(), currentPlayer);
                logic.getBoard().placePiece(bestMove.getRow(), bestMove.getCol(), currentPlayer);
                System.out.println("Minimax AI plays at (" + bestMove.getRow() + ", " + bestMove.getCol() + ")");
            } else {
                System.out.println("Minimax AI has no valid moves!");
            }
        }
    }

    private Position findBestMove(Piece aiPlayer, int depth) {
        int bestScore = Integer.MIN_VALUE;
        Position bestMove = null;

        List<Position> validMoves = logic.getValidMoves(aiPlayer);

        for (Position move : validMoves) {
            // Clone le plateau pour simuler
            OthelloBoard boardCopy = logic.getBoard().clone();
            OthelloLogic logicCopy = new OthelloLogic(boardCopy);
            logicCopy.flipPieces(move.getRow(), move.getCol(), aiPlayer);
            boardCopy.placePiece(move.getRow(), move.getCol(), aiPlayer);

            int score = minimax(logicCopy, depth - 1, false, aiPlayer.opponent());

            if (score > bestScore) {
                bestScore = score;
                bestMove = move;
            }
        }
        return bestMove;
    }

    private int minimax(OthelloLogic logic, int depth, boolean isMaximizing, Piece player) {
        if (depth == 0 || !logic.canPlayerPlay(player) && !logic.canPlayerPlay(player.opponent())) {
            return evaluateBoard(logic.getBoard(), player);
        }

        List<Position> validMoves = logic.getValidMoves(player);
        if (isMaximizing) {
            int maxEval = Integer.MIN_VALUE;
            for (Position move : validMoves) {
                OthelloBoard boardCopy = logic.getBoard().clone();
                OthelloLogic logicCopy = new OthelloLogic(boardCopy);
                logicCopy.flipPieces(move.getRow(), move.getCol(), player);

                int eval = minimax(logicCopy, depth - 1, false, player.opponent());
                maxEval = Math.max(maxEval, eval);
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (Position move : validMoves) {
                OthelloBoard boardCopy = logic.getBoard().clone();
                OthelloLogic logicCopy = new OthelloLogic(boardCopy);
                logicCopy.flipPieces(move.getRow(), move.getCol(), player.opponent());

                int eval = minimax(logicCopy, depth - 1, true, player);
                minEval = Math.min(minEval, eval);
            }
            return minEval;
        }
    }

    private int evaluateBoard(OthelloBoard board, Piece player) {
        int score = 0;
        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {
                Piece piece = board.getPieceAt(row, col);
                if (piece == player) score++;
                else if (piece == player.opponent()) score--;
            }
        }
        return score;
    }
}
