package fr.univ_amu.m1info.board_game_library;

import fr.univ_amu.m1info.board_game_library.graphics.*;
import fr.univ_amu.m1info.board_game_library.graphics.javafx.board.BoardActionOnHover;
import fr.univ_amu.m1info.board_game_library.model.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class HelloController implements BoardGameController, BoardActionOnHover {
    private final OthelloBoard board = new OthelloBoard();
    private final OthelloLogic logic = new OthelloLogic(board);
    private final GameStateManager stateManager = new GameStateManager(board);
    private final ScoreCalculator scoreCalculator = new ScoreCalculator(board);
    private final AIOthelloLogic aiLogic = new AIOthelloLogic(board);
    private BoardGameView view;
    private Piece currentPlayer = Piece.BLACK;
    private boolean playAgainstAI = false;

    @Override
    public void initializeViewOnStart(BoardGameView view) {
        this.view = view;
        startNewGame(false);
        updateInitialText("Mode : Humain vs Humain activé");
    }

    public void startNewGame(boolean againstAI) {
        playAgainstAI = againstAI;
        board.initializeBoard();
        currentPlayer = Piece.BLACK;
        resetView();
        refreshGameState();
        updateInitialText(playAgainstAI ? "Mode : Humain vs IA activé" : "Mode : Humain vs Humain activé");
    }

    private void resetView() {
        for (int row = 0; row < board.getSize(); row++)
            for (int col = 0; col < board.getSize(); col++)
                view.removeShapesAtCell(row, col);
    }

    private void refreshGameState() {
        initializeBoardColors();
        updateViewFromBoard();
        highlightValidMoves();
        updateScoreIndicator();
        updateTurnIndicator();
    }

    private void initializeBoardColors() {
        for (int row = 0; row < board.getSize(); row++)
            for (int col = 0; col < board.getSize(); col++)
                view.setCellColor(row, col, Color.GREEN);
    }

    private void updateViewFromBoard() {
        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {
                Piece piece = board.getPieceAt(row, col);
                updateCellShape(row, col, piece);
            }
        }
    }

    private void updateCellShape(int row, int col, Piece piece) {
        if (piece == Piece.BLACK)
            view.addShapeAtCell(row, col, Shape.CIRCLE, Color.BLACK);
        else if (piece == Piece.WHITE)
            view.addShapeAtCell(row, col, Shape.CIRCLE, Color.WHITE);
        else
            view.removeShapesAtCell(row, col);
    }

    private void highlightValidMoves() {
        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {
                view.setCellColor(row, col, logic.isValidMove(row, col, currentPlayer) ? Color.RED : Color.GREEN);
            }
        }
    }

    private void updateTurnIndicator() {
        String playerColor = (currentPlayer == Piece.BLACK) ? "Noir" : "Blanc";
        view.updateLabeledElement("TurnIndicator", "C'est au tour de : " + playerColor);
    }

    private void updateScoreIndicator() {
        int blackScore = scoreCalculator.calculateScore(Piece.BLACK);
        int whiteScore = scoreCalculator.calculateScore(Piece.WHITE);
        view.updateLabeledElement("ScoreIndicator", "Score : Noir " + blackScore + " - Blanc " + whiteScore);
    }

    private void updateInitialText(String text) {
        view.updateLabeledElement("Initial Text", text);
    }

    @Override
    public void boardActionOnClick(int row, int col) {
        if (!logic.isValidMove(row, col, currentPlayer)) return;

        stateManager.saveState(currentPlayer);
        logic.playMove(row, col, currentPlayer);
        switchPlayer();
        refreshGameState();
        if (playAgainstAI && currentPlayer == Piece.WHITE) delayAIMove();
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer == Piece.BLACK) ? Piece.WHITE : Piece.BLACK;
    }

    private void delayAIMove() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> playAIMove()));
        timeline.play();
    }

    private void playAIMove() {
        int[] move = aiLogic.getBestMove(currentPlayer);
        if (move != null) {
            logic.playMove(move[0], move[1], currentPlayer);
            switchPlayer();
            refreshGameState();
        }
    }

    @Override
    public void buttonActionOnClick(String buttonId) {
        switch (buttonId) {
            case "ButtonToggleTwoPlayers" -> startNewGame(false);
            case "ButtonToggleModeIA" -> startNewGame(!playAgainstAI);
            case "ButtonUndo" -> undoMove();
            case "ButtonRedo" -> redoMove();
        }
    }

    private void undoMove() {
        GameState previousState = stateManager.undo(currentPlayer);
        if (previousState != null) restoreGameState(previousState);
    }

    private void redoMove() {
        GameState nextState = stateManager.redo(currentPlayer);
        if (nextState != null) restoreGameState(nextState);
    }

    private void restoreGameState(GameState state) {
        board.copyFrom(state.getBoardState());
        currentPlayer = state.getCurrentPlayer();
        logic.updateBoardState(board);
        refreshGameState();
    }

    @Override
    public boolean validateHover(int row, int col) {
        return logic.isValidMove(row, col, currentPlayer);
    }
}
