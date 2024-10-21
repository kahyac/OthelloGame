module fr.univ_amu.m1info.board_game_library {
    requires javafx.controls;
    requires javafx.fxml;

    exports fr.univ_amu.m1info.board_game_library.graphics;
    exports fr.univ_amu.m1info.board_game_library;
    exports fr.univ_amu.m1info.board_game_library.graphics.javafx.app;
    exports fr.univ_amu.m1info.board_game_library.graphics.configuration;
    exports fr.univ_amu.m1info.board_game_library.graphics.view;
}