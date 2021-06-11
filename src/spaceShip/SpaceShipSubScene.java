package model;

import javafx.animation.TranslateTransition;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.util.Duration;

/**
 * 这个主要是用来设置弹窗的规格
 */
public class SpaceShipSubScene extends SubScene {
    private static final String FONT_PATH = "/resources/kenvector_future.ttf";
    private static final String BACKGROUND_IMAGE = "/resources/red_panel.png";
    private boolean isHidden;

    /**
     * 设置弹窗的大小以及导入图片
     */
    public SpaceShipSubScene() {
        super(new AnchorPane(), 600.0D, 400.0D);
        this.prefWidth(600.0D);
        this.prefHeight(400.0D);
        BackgroundImage image = new BackgroundImage(new Image("/resources/red_panel.png", 600.0D, 400.0D, false, true), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, (BackgroundSize)null);
        AnchorPane root2 = (AnchorPane)this.getRoot();
        root2.setBackground(new Background(new BackgroundImage[]{image}));
        this.isHidden = true;
        this.setLayoutX(1024.0D);
        this.setLayoutY(180.0D);
    }

    /**
     * 设置弹窗弹出来的效果
     */
    public void moveSubScene() {
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(0.3D));
        transition.setNode(this);
        if (this.isHidden) {
            transition.setToX(-676.0D);
            this.isHidden = false;
        } else {
            transition.setToX(0.0D);
            this.isHidden = true;
        }

        transition.play();
    }

    public AnchorPane getPane() {
        return (AnchorPane)this.getRoot();
    }
}

