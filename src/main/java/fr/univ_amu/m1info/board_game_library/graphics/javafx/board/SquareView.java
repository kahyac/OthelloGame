package fr.univ_amu.m1info.board_game_library.graphics.javafx.board;

import fr.univ_amu.m1info.board_game_library.graphics.BoardActionOnClick;
import fr.univ_amu.m1info.board_game_library.graphics.Shape;
import fr.univ_amu.m1info.board_game_library.graphics.javafx.JavaFXColorMapper;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

public class SquareView extends StackPane {
    private final int column;
    private final int row;
    private final Rectangle squareBackground;
    private final Group shapes = new Group();
    private int pixelSize;


    public SquareView(int column, int row, int pixelSize) {
        this.column = column;
        this.row = row;
        this.pixelSize = pixelSize;
        setWidth(pixelSize);
        setHeight(pixelSize);
        this.squareBackground = new Rectangle(pixelSize, pixelSize);
        this.getChildren().add(squareBackground);
        this.getChildren().add(shapes);
        squareBackground.setStroke(Color.BLACK);
        setColor(Color.WHITE);
    }

    public void setColor(Color backgroundColor) {
        squareBackground.setFill(backgroundColor);
    }

    public void setAction(BoardActionOnClick positionHandler) {
        this.setOnMouseClicked(_ -> positionHandler.onClick(this.row, this.column));
    }

    void addShape(Shape shape, fr.univ_amu.m1info.board_game_library.graphics.Color color){
        javafx.scene.shape.Shape shapeFX = switch (shape){
            case CIRCLE -> new Circle((3./8) * pixelSize);
            case SQUARE -> new Rectangle( (3./4) * pixelSize, (3./4) *pixelSize);
            case DIAMOND -> {
                Rectangle rectangle = new Rectangle( (3./(4 * Math.sqrt(2))) * pixelSize, (3./(4 * Math.sqrt(2))) *pixelSize);
                rectangle.getTransforms().add(new Rotate(45,0,0));
                yield rectangle;
            }
        };
        shapeFX.setFill(JavaFXColorMapper.getJavaFXColor(color));
        shapes.getChildren().add(shapeFX);
    }



}
