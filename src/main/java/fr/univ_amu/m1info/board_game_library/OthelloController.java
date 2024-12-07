package fr.univ_amu.m1info.board_game_library;

import fr.univ_amu.m1info.board_game_library.graphics.*;
import fr.univ_amu.m1info.board_game_library.graphics.javafx.board.BoardActionOnHover;
import fr.univ_amu.m1info.board_game_library.model.*;

import java.util.List;

public class OthelloController implements BoardGameController, BoardActionOnHover {
    private GameMode currentMode;
    private GameStateManager gameStateManager;
    private BoardGameView view;
    private Piece currentPlayer = Piece.BLACK;
    private GameModeStrategy gameModeStrategy;

    @Override
    public void initializeViewOnStart(BoardGameView view) {
        this.view = view;
        startNewGame(GameModeType.HUMAN_VS_HUMAN); // Mode par défaut
    }

    /**
     * Initialise une nouvelle partie avec un type de jeu spécifique.
     */
    public void startNewGame(GameModeType modeType) {
        OthelloBoard board = new OthelloBoard();
        OthelloLogic logic = new OthelloLogic(board);

        // Configuration du mode de jeu et de la stratégie
        switch (modeType) {
            case HUMAN_VS_HUMAN -> {
                currentMode = new HumanVsHumanMode(logic);
                gameModeStrategy = new HumanVsHumanStrategy();
            }
            case HUMAN_VS_RANDOM_AI -> {
                currentMode = new HumanVsAIMode(logic);
                gameModeStrategy = new HumanVsAIStrategy();
            }
            case HUMAN_VS_MINIMAX_AI -> {
                currentMode = new HumanVsMinimaxAIMode(logic);
                gameModeStrategy = new HumanVsMinimaxAIStrategy();
            }
            default -> throw new IllegalArgumentException("Mode de jeu inconnu !");
        }

        gameStateManager = new GameStateManager(board);
        currentPlayer = Piece.BLACK;
        refreshGameState();

        // Mise à jour du texte initial
        updateInitialText(switch (modeType) {
            case HUMAN_VS_HUMAN -> "Mode : Humain vs Humain activé";
            case HUMAN_VS_RANDOM_AI -> "Mode : Humain vs IA (Facile) activé";
            case HUMAN_VS_MINIMAX_AI -> "Mode : Humain vs IA (Difficile) activé";
        });
    }

    @Override
    public void boardActionOnClick(int row, int col) {
        if (gameModeStrategy == null) {
            System.err.println("Erreur : Aucune stratégie de jeu définie !");
            return;
        }

        // Convertit les coordonnées en objet Position et délègue à la stratégie
        Position position = new Position(row, col);
        gameModeStrategy.handleMove(position, currentPlayer, this);
    }

    @Override
    public void buttonActionOnClick(String buttonId) {
        switch (buttonId) {
            case "ButtonToggleTwoPlayers" -> toggleGameMode(GameModeType.HUMAN_VS_HUMAN);
            case "ButtonToggleModeIA" -> toggleGameMode(GameModeType.HUMAN_VS_RANDOM_AI); // Correspond à "IA Facile"
            case "ButtonToggleModeMinimax" -> toggleGameMode(GameModeType.HUMAN_VS_MINIMAX_AI); // Correspond à "IA Difficile"
            case "ButtonUndo" -> undoMove();
            case "ButtonRedo" -> redoMove();
            default -> System.out.println("Action inconnue : " + buttonId);
        }
    }


    private void toggleGameMode(GameModeType modeType) {
        startNewGame(modeType);
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

        // Réinitialisation des couleurs
        for (int row = 0; row < currentMode.getLogic().getBoard().getSize(); row++) {
            for (int col = 0; col < currentMode.getLogic().getBoard().getSize(); col++) {
                view.setCellColor(row, col, Color.GREEN);
            }
        }

        // Mise en évidence des coups valides
        for (Position move : validMoves) {
            view.setCellColor(move.getRow(), move.getCol(), Color.RED);
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
