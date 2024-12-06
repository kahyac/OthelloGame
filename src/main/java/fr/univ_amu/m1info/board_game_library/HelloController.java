package fr.univ_amu.m1info.board_game_library;

import fr.univ_amu.m1info.board_game_library.graphics.*;
import fr.univ_amu.m1info.board_game_library.graphics.javafx.board.BoardActionOnHover;
import fr.univ_amu.m1info.board_game_library.model.*;

import java.util.List;

public class HelloController implements BoardGameController, BoardActionOnHover {
    private GameMode currentMode;
    private GameStateManager gameStateManager;
    private BoardGameView view;
    private Piece currentPlayer = Piece.BLACK;
    private GameModeStrategy gameModeStrategy;

    @Override
    public void initializeViewOnStart(BoardGameView view) {
        this.view = view;
        startNewGame(false); // Start with default mode (Human vs Human)
    }

    /**
     * Initializes a new game with the specified mode (Human vs Human or Human vs AI).
     */
    public void startNewGame(boolean againstAI) {
        OthelloBoard board = new OthelloBoard();
        OthelloLogic logic = new OthelloLogic(board);

        if (againstAI) {
            currentMode = new HumanVsAIMode(logic);
            setGameModeStrategy(new HumanVsIAStrategy()); // Définit la stratégie IA
        } else {
            currentMode = new HumanVsHumanMode(logic);
            setGameModeStrategy(new HumanVsHumanStrategy()); // Définit la stratégie Humain vs Humain
        }

        gameStateManager = new GameStateManager(board);
        currentPlayer = Piece.BLACK;
        refreshGameState();
        updateInitialText(againstAI ? "Mode : Humain vs IA activé" : "Mode : Humain vs Humain activé");
    }

    public void setGameModeStrategy(GameModeStrategy gameModeStrategy) {
        this.gameModeStrategy = gameModeStrategy;
    }

    /**
     * Handles board cell clicks by the user.
     */
    @Override
    public void boardActionOnClick(int row, int col) {
        if (gameModeStrategy == null) {
            throw new IllegalStateException("GameModeStrategy is not set.");
        }

        // Convert row and col into a Position object
        Position position = new Position(row, col);

        // Delegate the move to the strategy
        gameModeStrategy.handleMove(position, currentPlayer, this);
    }

    /**
     * Handles button clicks for various actions like toggling game mode, undo, and redo.
     */
    @Override
    public void buttonActionOnClick(String buttonId) {
        switch (buttonId) {
            case "ButtonToggleTwoPlayers" -> toggleGameMode(false);
            case "ButtonToggleModeIA" -> toggleGameMode(true);
            case "ButtonUndo" -> undoMove();
            case "ButtonRedo" -> redoMove();
            default -> System.out.println("Unknown button action: " + buttonId);
        }
    }

    private void toggleGameMode(boolean againstAI) {
        startNewGame(againstAI);
        updateInitialText(againstAI ? "Mode : Humain vs IA activé" : "Mode : Humain vs Humain activé");
    }

    private void undoMove() {
        GameState previousState = gameStateManager.undo(currentPlayer);
        if (previousState != null) {
            restoreGameState(previousState);
        }
    }

    private void redoMove() {
        GameState nextState = gameStateManager.redo(currentPlayer);
        if (nextState != null) {
            restoreGameState(nextState);
        }
    }

    private void restoreGameState(GameState state) {
        currentMode.getLogic().updateBoardState(state.boardState());
        currentPlayer = state.currentPlayer();
        refreshGameState();
    }

    public void switchPlayer() {
        currentPlayer = (currentPlayer == Piece.BLACK) ? Piece.WHITE : Piece.BLACK;
    }

    public void refreshGameState() {
        updateViewFromBoard();
        highlightValidMoves();
        updateScoreIndicator();
        updateTurnIndicator();
    }

    private void updateViewFromBoard() {
        OthelloBoard board = currentMode.getLogic().getBoard();
        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {
                updateCellShape(new Position(row, col), board.getPieceAt(row, col));
            }
        }
    }

    private void updateCellShape(Position position, Piece piece) {
        int row = position.getRow();
        int col = position.getCol();

        if (piece == Piece.BLACK) {
            view.addShapeAtCell(row, col, Shape.CIRCLE, Color.BLACK);
        } else if (piece == Piece.WHITE) {
            view.addShapeAtCell(row, col, Shape.CIRCLE, Color.WHITE);
        } else {
            view.removeShapesAtCell(row, col);
        }
    }

    private void highlightValidMoves() {
        List<Position> validMoves = currentMode.getLogic().getValidMoves(currentPlayer);

        OthelloBoard board = currentMode.getLogic().getBoard();
        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {
                view.setCellColor(row, col, Color.GREEN); // Default color
            }
        }

        for (Position move : validMoves) {
            view.setCellColor(move.getRow(), move.getCol(), Color.RED); // Highlight valid moves
        }
    }

    private void updateScoreIndicator() {
        OthelloBoard board = currentMode.getLogic().getBoard();
        ScoreCalculator scoreCalculator = new ScoreCalculator(board);
        int blackScore = scoreCalculator.calculateScore(Piece.BLACK);
        int whiteScore = scoreCalculator.calculateScore(Piece.WHITE);
        view.updateLabeledElement("ScoreIndicator", "Score : Noir " + blackScore + " - Blanc " + whiteScore);
    }

    private void updateTurnIndicator() {
        String playerColor = (currentPlayer == Piece.BLACK) ? "Noir" : "Blanc";
        view.updateLabeledElement("TurnIndicator", "C'est au tour de : " + playerColor);
    }

    private void updateInitialText(String text) {
        view.updateLabeledElement("Initial Text", text);
    }

    public boolean isGameOver() {
        return !currentMode.getLogic().canPlayerPlay(Piece.BLACK) &&
                !currentMode.getLogic().canPlayerPlay(Piece.WHITE);
    }

    public void determineWinner() {
        String winner = ScoreCalculator.determineWinner();
        updateInitialText(winner);
    }

    @Override
    public boolean validateHover(int row, int column) {
        return currentMode.getLogic().isValidMove(row, column, currentPlayer);
    }

    public GameMode getGameMode() {
        return currentMode;
    }

    public Piece getCurrentPlayer() {
        return currentPlayer;
    }

    public OthelloLogic getLogic() {
        return currentMode.getLogic();
    }

    public void saveGameState() {
        gameStateManager.saveState(currentPlayer);
    }
}
