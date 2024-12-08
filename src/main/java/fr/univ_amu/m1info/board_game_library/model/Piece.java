package fr.univ_amu.m1info.board_game_library.model;

public enum Piece {
    EMPTY,
    BLACK,
    WHITE;

    /**
     * Retourne l'adversaire de la pièce actuelle.
     * @return La pièce opposée (BLACK pour WHITE, WHITE pour BLACK).
     *         Retourne EMPTY si la pièce actuelle est EMPTY.
     */
    public Piece opponent() {
        return switch (this) {
            case BLACK -> WHITE;
            case WHITE -> BLACK;
            default -> EMPTY; // Si la pièce est vide
        };
    }
}
