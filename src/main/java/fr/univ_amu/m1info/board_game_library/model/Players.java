package fr.univ_amu.m1info.board_game_library.model;

public class Players {
    String[] tab; // Correctly declaring the array

    public Players() {
        tab = new String[]{"Abdi", "Farah"}; // Initializing the array properly
    }

    // Optionally, add a getter to access the array outside this class
    public String[] getTab() {
        return tab;
    }
}
