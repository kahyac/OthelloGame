module fr.univ_amu.m1info.board_game_library {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    exports fr.univ_amu.m1info.board_game_library;
    exports fr.univ_amu.m1info.board_game_library.model;
    exports fr.univ_amu.m1info.board_game_library.view;
    exports fr.univ_amu.m1info.board_game_library.view.components;
    exports fr.univ_amu.m1info.board_game_library.controller;
    exports fr.univ_amu.m1info.board_game_library.application;
}