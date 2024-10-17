package fr.univ_amu.m1info.board_game_library.graphics;

public interface BoardGameView {
    void updateLabeledElement(String id, String newText);
    void setColorSquare(int row, int column, Color color);
    void addShapeAtSquare(int row, int column, Shape shape, Color color);
    void removeShapesAtSquare(int row, int column);
    void setButtonAction(String id, ButtonActionOnClick buttonActionOnClick);
    void setBoardGameAction(BoardActionOnClick boardActionOnClick);
    void show();
}
