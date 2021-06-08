package model;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.text.Font;

public class SmallInfoLabel extends Label {
    private static final String FONT_PATH = "/resources/kenvector_future.ttf";

    public SmallInfoLabel(String text) {
        this.setPrefWidth(130.0D);
        this.setPrefHeight(50.0D);
        BackgroundImage backgroundImage = new BackgroundImage(new Image("/resources/red_info_label.png", 130.0D, 50.0D, false, false), BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, (BackgroundSize)null);
        this.setBackground(new Background(new BackgroundImage[]{backgroundImage}));
        this.setAlignment(Pos.CENTER_LEFT);
        this.setPadding(new Insets(10.0D, 10.0D, 10.0D, 10.0D));
        this.setLabelFont();
        this.setText(text);
    }

    private void setLabelFont() {
        this.setFont(Font.loadFont(this.getClass().getResourceAsStream("/resources/kenvector_future.ttf"), 15.0D));
    }
}
