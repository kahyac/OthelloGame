package fr.univ_amu.m1info.board_game_library.graphics.javafx.app;

import fr.univ_amu.m1info.board_game_library.graphics.JavaFXBoardGameApplicationLauncher;
import fr.univ_amu.m1info.board_game_library.graphics.javafx.view.BoardGameConfigurator;
import fr.univ_amu.m1info.board_game_library.graphics.javafx.view.JavaFXBoardGameViewBuilder;
import fr.univ_amu.m1info.board_game_library.graphics.javafx.view.JavaFXBoardGameView;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class JavaFXBoardGameApplication extends Application {
    @Override
    public void start(Stage stage) {
        // Obtenir l'instance de launcher et configuration
        var launcher = JavaFXBoardGameApplicationLauncher.getInstance();
        var configuration = launcher.getConfiguration();
        var controller = launcher.getController();

        // Construire l'interface
        final JavaFXBoardGameViewBuilder viewBuilder = new JavaFXBoardGameViewBuilder(stage);
        new BoardGameConfigurator().configure(viewBuilder, configuration);

        // Obtenir la vue contrôlable
        JavaFXBoardGameView view = (JavaFXBoardGameView) viewBuilder.getView();
        view.setController(controller);
        controller.initializeViewOnStart(view);

        // Configurer la disposition de la scène
        BorderPane root = new BorderPane();

        // Ajouter la barre d'outils en haut
        root.setTop(view.getBar());
        // Cree un Vbox pour les joueur
        /*VBox PlayerContainer= new VBox(view.getBarPlayer());
         PlayerContainer.setPadding(new Insets(80));
         root.setLeft(PlayerContainer); */
        // Créer un VBox pour le plateau de jeu, centré avec des marges
        VBox boardContainer = new VBox(view.getBoardGridView());
        boardContainer.setPadding(new Insets(30)); // marges autour du plateau
        root.setCenter(boardContainer);

        // Créer et afficher la scène
        Scene scene = new Scene(root, 750, 750);
        stage.setTitle("Board Game Application");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

