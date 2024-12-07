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

    // Vérifie si un coup est valide
    public boolean isValidMove(int row, int col, Piece player) {
        if (!board.isValidPosition(row, col) || board.getPieceAt(row, col) != Piece.EMPTY) {
            return false; // Case non valide ou occupée
        }
        return hasFlippablePieces(row, col, player); // Vérifie s'il y a des pièces à retourner
    }

    // Détermine s'il y a des pièces à retourner dans au moins une direction
    private boolean hasFlippablePieces(int row, int col, Piece player) {
        for (int[] direction : PieceFlipper.DIRECTIONS) {
            if (!findFlippablePieces(row, col, player, direction[0], direction[1]).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    // Trouve toutes les pièces à retourner dans une direction donnée
    public List<Position> findFlippablePieces(int row, int col, Piece player, int dRow, int dCol) {
        List<Position> flippable = new ArrayList<>();
        int currentRow = row + dRow;
        int currentCol = col + dCol;
        Piece opponent = player.opponent();

        while (board.isValidPosition(currentRow, currentCol) && board.getPieceAt(currentRow, currentCol) == opponent) {
            flippable.add(new Position(currentRow, currentCol));
            currentRow += dRow;
            currentCol += dCol;
        }

        // Vérifie si cette direction se termine sur une pièce du joueur actuel
        if (board.isValidPosition(currentRow, currentCol) && board.getPieceAt(currentRow, currentCol) == player) {
            return flippable;
        }

        return new ArrayList<>(); // Aucune pièce à retourner dans cette direction
    }

    // Retourne toutes les positions valides pour un joueur
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

    // Effectue le retournement des pièces
    public void flipPieces(int row, int col, Piece currentPlayer) {
        for (int[] direction : PieceFlipper.DIRECTIONS) {
            List<Position> flippable = findFlippablePieces(row, col, currentPlayer, direction[0], direction[1]);
            for (Position position : flippable) {
                board.placePiece(position.getRow(), position.getCol(), currentPlayer);
            }
        }
    }

    // Retourne si un joueur peut encore jouer
    public boolean canPlayerPlay(Piece player) {
        return !getValidMoves(player).isEmpty();
    }

    // Met à jour l'état actuel du plateau
    public void updateBoardState(OthelloBoard newBoardState) {
        board.copyFrom(newBoardState);
    }

    // Calcule le meilleur coup en utilisant Minimax
    public Position getBestMoveUsingMinimax(Piece player, int depth) {
        int bestScore = Integer.MIN_VALUE;
        Position bestMove = null;

        List<Position> validMoves = getValidMoves(player);

        for (Position move : validMoves) {
            // Crée une copie du plateau pour simuler le coup
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

    // Implémente l'algorithme Minimax
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
