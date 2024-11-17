package fr.univ_amu.m1info.board_game_library.graphics.javafx.board;

import fr.univ_amu.m1info.board_game_library.graphics.Shape;
import fr.univ_amu.m1info.board_game_library.graphics.javafx.color.JavaFXColorMapper;
import javafx.scene.Group;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class SquareView extends StackPane {
    private final int column;
    private final int row;
    private final Rectangle squareBackground;
    private final Group shapes = new Group();
    private final int squareSize;

    private Color originalColor; // Save original color
    private BoardActionOnHover hoverHandler; // reference to validate Hover


    public SquareView(int column, int row, int squareSize) {
        this.column = column;
        this.row = row;
        this.squareSize = squareSize;
        setWidth(squareSize);
        setHeight(squareSize);
        this.squareBackground = new Rectangle(squareSize, squareSize);
        this.getChildren().add(squareBackground);
        this.getChildren().add(shapes);
        squareBackground.setStroke(Color.BLACK);
        setColor(Color.WHITE);
        // Save current color as default
        originalColor = Color.WHITE;

        // Define hover events
        setOnMouseEntered(e -> handleMouseEnter());
        setOnMouseExited(e -> handleMouseExit());
    }


    public void handleMouseEnter() {
        if (hoverHandler != null && hoverHandler.validateHover(row, column)) {
            squareBackground.setFill(Color.RED); // Indiquer que la case est valide
        }
    }

    public void handleMouseExit() {
        squareBackground.setFill(originalColor); // Restaurer la couleur d'origine
    }


    public void setHoverHandler(BoardActionOnHover hoverHandler) {
        this.hoverHandler = hoverHandler;
    }


    public void setColor(Color backgroundColor) {
        squareBackground.setFill(backgroundColor);
        originalColor = backgroundColor; // Mettre à jour la couleur d'origine
    }

    public void setAction(BoardActionOnClick positionHandler) {
        this.setOnMouseClicked(_ -> positionHandler.onClick(this.row, this.column));
    }

    void addShape(Shape shape, fr.univ_amu.m1info.board_game_library.graphics.Color color){
        javafx.scene.shape.Shape shapeFX = ShapeFactory.makeShape(shape, squareSize);
        shapeFX.setFill(JavaFXColorMapper.getJavaFXColor(color));
        shapes.getChildren().add(shapeFX);
    }

    public void removeShapes() {
        shapes.getChildren().clear(); // Supprime tous les éléments graphiques de cette case
    }

}
