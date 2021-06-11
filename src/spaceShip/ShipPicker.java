package model;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

/**
 * 这个类是用来当要开始游戏时，选择飞船角色的按钮
 */
public class ShipPicker extends VBox {
    private ImageView circleImage;
    private ImageView shipImage;
    private String circleNotChoosen = "/resources/grey_circle.png";
    private String circleChoosen = "/resources/red_choosen.png";
    private SHIP ship;
    private boolean isCircleChoosen;

    public ShipPicker(SHIP ship) {
        this.circleImage = new ImageView(this.circleNotChoosen);
        this.shipImage = new ImageView(ship.getUrl());
        this.ship = ship;
        this.isCircleChoosen = false;
        this.setAlignment(Pos.CENTER);
        this.setSpacing(20.0D);
        this.getChildren().add(this.circleImage);
        this.getChildren().add(this.shipImage);
    }

    public SHIP getShip() {
        return this.ship;
    }

    public boolean getCircleChoosen() {
        return this.isCircleChoosen;
    }

    /**
     * 判断按钮是否选中并设置角色
     * @param isCircleChoosen
     */
    public void setIsCircleChoosen(boolean isCircleChoosen) {
        this.isCircleChoosen = isCircleChoosen;
        String imageToSet = this.isCircleChoosen ? this.circleChoosen : this.circleNotChoosen;
        this.circleImage.setImage(new Image(imageToSet));
    }
}
