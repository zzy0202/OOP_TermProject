package model;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.text.Font;

public class InfoLabel extends Label {
    public static final String FONT_PATH = "/resources/kenvector_future.ttf";
    public static final String BACKGROUND_IMAGE = "/resources/red_small_panel.png";

    public InfoLabel(String text) {
        this.setPrefWidth(380.0D);
        this.setPrefHeight(49.0D);
        this.setText(text);
        this.setWrapText(true);
        this.setLabelFont();
        this.setAlignment(Pos.CENTER);
        BackgroundImage backgroundImage = new BackgroundImage(new Image("/resources/red_small_panel.png", 380.0D, 49.0D, false, true), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, (BackgroundSize)null);
        this.setBackground(new Background(new BackgroundImage[]{backgroundImage}));
    }

    private void setLabelFont() {
        this.setFont(Font.loadFont(this.getClass().getResourceAsStream("/resources/kenvector_future.ttf"), 23.0D));
    }
}
