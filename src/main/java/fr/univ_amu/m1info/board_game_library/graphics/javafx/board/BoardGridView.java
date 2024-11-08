package fr.univ_amu.m1info.board_game_library.graphics.javafx.board;

import fr.univ_amu.m1info.board_game_library.graphics.Color;
import fr.univ_amu.m1info.board_game_library.graphics.Shape;
import fr.univ_amu.m1info.board_game_library.graphics.javafx.color.JavaFXColorMapper;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class BoardGridView extends GridPane {
    private final static int BASE_SQUARE_SIZE = 65;
    private SquareView[][] squareViews;
    private int rowCount;
    private int columnCount;
    private BoardActionOnClick boardActionOnClick;

    public BoardGridView() {
    }

    // Nouvelle méthode pour ajouter les labels de numérotation
    public void addGridLabels() {
        // Ajout des labels de colonnes (A, B, C, ...) au-dessus des cases du plateau
        for (int column = 0; column < columnCount; column++) {
            char columnLabel = (char) ('A' + column); // Conversion de l'index en lettre
            Label label = new Label(String.valueOf(columnLabel));
            label.setAlignment(Pos.CENTER);
            StackPane stackPane = new StackPane(label);
            stackPane.setMinSize(BASE_SQUARE_SIZE, BASE_SQUARE_SIZE);
            this.add(stackPane, column + 1, 0); // Ligne 0, décalage de colonne de +1
        }

        // Ajout des labels de lignes (1, 2, 3, ...) à gauche des cases du plateau
        for (int row = 0; row < rowCount; row++) {
            Label label = new Label(String.valueOf(row + 1));
            label.setAlignment(Pos.CENTER);
            StackPane stackPane = new StackPane(label);
            stackPane.setMinSize(BASE_SQUARE_SIZE, BASE_SQUARE_SIZE);
            this.add(stackPane, 0, row + 1); // Colonne 0, décalage de ligne de +1
        }
    }

    // Modification de setDimensions pour inclure les labels
    public void setDimensions(int rowCount, int columnCount) {
        squareViews = new SquareView[rowCount][columnCount];
        this.rowCount = rowCount;
        this.columnCount = columnCount;

        // Appel de la méthode pour ajouter les labels de numérotation
        addGridLabels();

        // Ajout des cases du plateau
        for (int row = 0; row < rowCount; row++) {
            for (int column = 0; column < columnCount; column++) {
                addSquareView(row, column);
            }
        }

        setActionOnSquares();
    }

    private void addSquareView(int row, int column) {
        squareViews[row][column] = new SquareView(column, row, BASE_SQUARE_SIZE);
        this.add(squareViews[row][column], column + 1, row + 1); // Décalage de +1 pour chaque case du plateau
    }

    public void setColorSquare(int row, int column, Color color) {
        squareViews[row][column].setColor(JavaFXColorMapper.getJavaFXColor(color));
    }

    public void setAction(BoardActionOnClick boardActionOnClick) {
        this.boardActionOnClick = boardActionOnClick;
        setActionOnSquares();
    }

    public void setActionOnSquares() {
        for (int row = 0; row < rowCount; row++) {
            for (int column = 0; column < columnCount; column++) {
                squareViews[row][column].setAction(boardActionOnClick);
            }
        }
    }

    public void addShapeAtSquare(int row, int column, Shape shape, Color color) {
        squareViews[row][column].addShape(shape, color);
    }

    public void removeShapesAtSquare(int row, int column) {
        squareViews[row][column].removeShapes();
    }
}
