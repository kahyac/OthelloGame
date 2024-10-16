package fr.univ_amu.m1info.board_game_library.graphics.javafx.bar;

import fr.univ_amu.m1info.board_game_library.graphics.ButtonActionOnClick;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;


import java.util.HashMap;
import java.util.Map;

public class Bar extends HBox {
    private Map<String, Label> labels = new HashMap<>();
    private Map<String, Button> buttons = new HashMap<>();

    public Bar() {
        super();
        setSpacing(10);
        setPrefHeight(30);
        setAlignment(Pos.CENTER);
    }

    public void addLabel(String id, String initialText){
        if(labels.containsKey(id)){
            throw new IllegalArgumentException("Label " + id + " already exists");
        }
        Label label = new Label(initialText);
        label.setAlignment(Pos.BASELINE_CENTER);
        labels.put(id, label);
        this.getChildren().add(label);
    }

    public void setButtonAction(String id, ButtonActionOnClick buttonActionOnClick){
       buttons.get(id).setOnAction(_ -> buttonActionOnClick.onClick());
    }

    public void addButton(String id, String label){
        Button button = new Button(label);
        buttons.put(id, button);
        this.getChildren().add(button);
    }

    public void updateLabel(String id, String newText){
        if(labels.containsKey(id)){
            labels.get(id).setText(newText);
        }
    }
}
