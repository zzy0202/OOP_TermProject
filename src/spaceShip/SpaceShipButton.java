package model;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;

/**
 * 这个方法主要是用来设置游戏中的各种按钮，这里利用了extend java自带的Button类
 */
public class SpaceShipButton extends Button {
    private final String FONT_PATH = "/resources/kenvector_future.ttf";
    private final String BUTTON_PRESSED_STYLE = "-fx-background-color: transparent; -fx-background-image: url('/resources/red_button_pressed.png');";
    private final String BUTTON_FREE_STYLE = "-fx-background-color: transparent; -fx-background-image: url('/resources/red_button.png');";

    /**
     * 先设置button的规格
     * @param text
     */
    public SpaceShipButton(String text) {
        this.setText(text);
        this.setButtonFont();
        this.setPrefWidth(190.0D);
        this.setPrefHeight(49.0D);
        this.setStyle("-fx-background-color: transparent; -fx-background-image: url('/resources/red_button.png');");
        this.initializeButtonListeners();
    }

    /**
     * 设置字体的样式和大小
     */
    private void setButtonFont() {
        this.setFont(Font.loadFont(this.getClass().getResourceAsStream("/resources/kenvector_future.ttf"), 23.0D));
    }

    /**
     * 设置按下按钮的效果（这里直接利用图片）
     */
    private void setButtonPressedStyle() {
        this.setStyle("-fx-background-color: transparent; -fx-background-image: url('/resources/red_button_pressed.png');");
        this.setPrefHeight(45.0D);
        this.setLayoutY(this.getLayoutY() + 4.0D);
    }
    /**
     * 设置按钮的效果（这里直接利用图片）
     */
    private void setButtonReleasedStyle() {
        this.setStyle("-fx-background-color: transparent; -fx-background-image: url('/resources/red_button.png');");
        this.setPrefHeight(45.0D);
        this.setLayoutY(this.getLayoutY() - 4.0D);
    }

    /**
     * 设置游戏里的按钮
     */
    private void initializeButtonListeners() {
        this.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    SpaceShipButton.this.setButtonPressedStyle();
                }

            }
        });
        this.setOnMouseReleased(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    SpaceShipButton.this.setButtonReleasedStyle();
                }

            }
        });
        this.setOnMouseEntered(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                SpaceShipButton.this.setEffect(new DropShadow());
            }
        });
        this.setOnMouseExited(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                SpaceShipButton.this.setEffect((Effect)null);
            }
        });
    }
}
