package fr.univ_amu.m1info.board_game_library.view.components;

import fr.univ_amu.m1info.board_game_library.model.Players;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class BarPlayer extends HBox {

    public BarPlayer() {
        super();
        setSpacing(10);
        setPrefHeight(30);
        setAlignment(Pos.CENTER);

        // Récupérer les noms des joueurs et les afficher comme informations
        Players players = new Players();

        // Ajouter les informations pour chaque joueur sous forme de label
        addPlayerInfo("Joueur 1", players.getTab()[0]);
        addPlayerInfo("Joueur 2", players.getTab()[1]);
    }

    private void addPlayerInfo(String playerLabel, String playerName) {
        Label label = new Label(playerLabel + ": " + playerName);
        this.getChildren().add(label);
    }
}
