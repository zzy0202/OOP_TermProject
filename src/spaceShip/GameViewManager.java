package view;

import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.SHIP;
import model.SmallInfoLabel;

/**
 * 这个类主要是游戏界面的实现
 */
public class GameViewManager {
    private AnchorPane gamePane;
    private Scene gameScene;
    private Stage gameStage;
    private static final int GAME_WIDTH = 600;
    private static final int GAME_HEIGHT = 800;
    private Stage menuStage;
    private ImageView ship;
    private boolean isLeftKeyPressed;
    private boolean isRightKeyPressed;
    private AnimationTimer gameTimer;
    private int angle;
    private GridPane gridPane1;
    private GridPane gridPane2;
    private static final String BACKGROUND_IMAGE = "/resources/deep_blue.png";
    private static final String METEOR_BROWN_IMAGE = "/resources/meteor_brown.png";
    private static final String METEOR_GREY_IMAGE = "/resources/meteor_grey.png";
    private ImageView[] brownMeteors;
    private ImageView[] greyMeteors;
    Random randomPositionGenerator;
    private ImageView star;
    private SmallInfoLabel pointsLabel;
    private ImageView[] playerLifes;
    private int playerLife;
    private int points;
    private static final String GOLD_STAR_IMAGE = "/resources/star_gold.png";
    private static final int STAR_RADIUS = 12;
    private static final int SHIP_RADIUS = 27;
    private static final int METEOR_RADIUS = 20;

    public GameViewManager() {
        this.initializeStage();
        this.createKeyListeners();
        this.randomPositionGenerator = new Random();
    }

    /**
     * 获取玩家用键盘左右键对飞船进行操作
     */
    private void createKeyListeners() {
        this.gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.LEFT) {
                    GameViewManager.this.isLeftKeyPressed = true;
                } else if (event.getCode() == KeyCode.RIGHT) {
                    GameViewManager.this.isRightKeyPressed = true;
                }

            }
        });
        this.gameScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.LEFT) {
                    GameViewManager.this.isLeftKeyPressed = false;
                } else if (event.getCode() == KeyCode.RIGHT) {
                    GameViewManager.this.isRightKeyPressed = false;
                }

            }
        });
    }

    /**
     * 初始化游戏界面
     */
    private void initializeStage() {
        this.gamePane = new AnchorPane();
        this.gameScene = new Scene(this.gamePane, 600.0D, 800.0D);
        this.gameStage = new Stage();
        this.gameStage.setScene(this.gameScene);
    }

    /**
     * 开始新游戏时，传入玩家所选择的飞船
     * @param menuStage
     * @param choosenShip
     */
    public void createNewGame(Stage menuStage, SHIP choosenShip) {
        this.menuStage = menuStage;
        this.menuStage.hide();
        this.createBackground();
        this.createShip(choosenShip);
        this.createGameElements(choosenShip);
        this.createGameLoop();
        this.gameStage.show();
    }

    /**
     * 创建初始化游戏界面里的元素包括飞船游戏时要碰到的星星以及掉落的陨石并且记录玩家的游戏剩余次数
     * @param choosenShip
     */
    private void createGameElements(SHIP choosenShip) {
        this.playerLife = 2;
        this.star = new ImageView("/resources/star_gold.png");
        this.setNewElementPosition(this.star);
        this.gamePane.getChildren().add(this.star);
        this.pointsLabel = new SmallInfoLabel("POINTS : 00");
        this.pointsLabel.setLayoutX(460.0D);
        this.pointsLabel.setLayoutY(20.0D);
        this.gamePane.getChildren().add(this.pointsLabel);
        this.playerLifes = new ImageView[3];

        int i;
        for(i = 0; i < this.playerLifes.length; ++i) {
            this.playerLifes[i] = new ImageView(choosenShip.getUrlLife());
            this.playerLifes[i].setLayoutX((double)(455 + i * 50));
            this.playerLifes[i].setLayoutY(80.0D);
            this.gamePane.getChildren().add(this.playerLifes[i]);
        }

        this.brownMeteors = new ImageView[3];

        for(i = 0; i < this.brownMeteors.length; ++i) {
            this.brownMeteors[i] = new ImageView("/resources/meteor_brown.png");
            this.setNewElementPosition(this.brownMeteors[i]);
            this.gamePane.getChildren().add(this.brownMeteors[i]);
        }

        this.greyMeteors = new ImageView[3];

        for(i = 0; i < this.greyMeteors.length; ++i) {
            this.greyMeteors[i] = new ImageView("/resources/meteor_grey.png");
            this.setNewElementPosition(this.greyMeteors[i]);
            this.gamePane.getChildren().add(this.greyMeteors[i]);
        }

    }

    /**
     * 设置游戏元素的移动
     */
    private void moveGameElements() {
        this.star.setLayoutY(this.star.getLayoutY() + 5.0D);

        int i;
        for(i = 0; i < this.brownMeteors.length; ++i) {
            this.brownMeteors[i].setLayoutY(this.brownMeteors[i].getLayoutY() + 7.0D);
            this.brownMeteors[i].setRotate(this.brownMeteors[i].getRotate() + 4.0D);
        }

        for(i = 0; i < this.greyMeteors.length; ++i) {
            this.greyMeteors[i].setLayoutY(this.greyMeteors[i].getLayoutY() + 7.0D);
            this.greyMeteors[i].setRotate(this.greyMeteors[i].getRotate() + 4.0D);
        }

    }

    /**
     * 判断飞船是否有碰到这些元素（用来以后判断飞船是否有“吃”到星星，或者是否被陨石砸中了）
     */
    private void checkIfElementAreBehindTheShipAndRelocated() {
        if (this.star.getLayoutY() > 1200.0D) {
            this.setNewElementPosition(this.star);
        }

        int i;
        for(i = 0; i < this.brownMeteors.length; ++i) {
            if (this.brownMeteors[i].getLayoutY() > 900.0D) {
                this.setNewElementPosition(this.brownMeteors[i]);
            }
        }

        for(i = 0; i < this.greyMeteors.length; ++i) {
            if (this.greyMeteors[i].getLayoutY() > 900.0D) {
                this.setNewElementPosition(this.greyMeteors[i]);
            }
        }

    }

    /**
     * 设置出现的新元素
     * @param image
     */
    private void setNewElementPosition(ImageView image) {
        image.setLayoutX((double)this.randomPositionGenerator.nextInt(370));
        image.setLayoutY((double)(-this.randomPositionGenerator.nextInt(3200) + 600));
    }

    /**
     * 根据玩家选择的飞船角色创建
     * @param choosenShip
     */
    private void createShip(SHIP choosenShip) {
        this.ship = new ImageView(choosenShip.getUrl());
        this.ship.setLayoutX(300.0D);
        this.ship.setLayoutY(710.0D);
        this.gamePane.getChildren().add(this.ship);
    }

    /**
     * 游戏开始之前的设置
     */
    private void createGameLoop() {
        this.gameTimer = new AnimationTimer() {
            public void handle(long now) {
                GameViewManager.this.moveBackground();
                GameViewManager.this.moveGameElements();
                GameViewManager.this.checkIfElementAreBehindTheShipAndRelocated();
                GameViewManager.this.checkIfElementsCollide();
                GameViewManager.this.moveShip();
            }
        };
        this.gameTimer.start();
    }

    /**
     * 设置飞船的移动
     */
    private void moveShip() {
        if (this.isLeftKeyPressed && !this.isRightKeyPressed) {
            if (this.angle > -30) {
                this.angle -= 5;
            }

            this.ship.setRotate((double)this.angle);
            if (this.ship.getLayoutX() > -20.0D) {
                this.ship.setLayoutX(this.ship.getLayoutX() - 3.0D);
            }
        }

        if (this.isRightKeyPressed && !this.isLeftKeyPressed) {
            if (this.angle < 30) {
                this.angle += 5;
            }

            this.ship.setRotate((double)this.angle);
            if (this.ship.getLayoutX() < 522.0D) {
                this.ship.setLayoutX(this.ship.getLayoutX() + 3.0D);
            }
        }

        if (!this.isLeftKeyPressed && !this.isRightKeyPressed) {
            if (this.angle < 0) {
                this.angle += 5;
            } else if (this.angle > 0) {
                this.angle -= 5;
            }

            this.ship.setRotate((double)this.angle);
        }

        if (this.isLeftKeyPressed && this.isRightKeyPressed) {
            if (this.angle < 0) {
                this.angle += 5;
            } else if (this.angle > 0) {
                this.angle -= 5;
            }

            this.ship.setRotate((double)this.angle);
        }

    }

    /**
     * 创建游戏的背景
     */
    private void createBackground() {
        this.gridPane1 = new GridPane();
        this.gridPane2 = new GridPane();

        for(int i = 0; i < 12; ++i) {
            ImageView backgroundImage1 = new ImageView("/resources/deep_blue.png");
            ImageView backgroundImage2 = new ImageView("/resources/deep_blue.png");
            GridPane.setConstraints(backgroundImage1, i % 3, i / 3);
            GridPane.setConstraints(backgroundImage2, i % 3, i / 3);
            this.gridPane1.getChildren().add(backgroundImage1);
            this.gridPane2.getChildren().add(backgroundImage2);
        }

        this.gridPane2.setLayoutY(-1024.0D);
        this.gamePane.getChildren().addAll(new Node[]{this.gridPane1, this.gridPane2});
    }

    /**
     * 背景的移动
     */
    private void moveBackground() {
        this.gridPane1.setLayoutY(this.gridPane1.getLayoutY() + 0.5D);
        this.gridPane2.setLayoutY(this.gridPane2.getLayoutY() + 0.5D);
        if (this.gridPane1.getLayoutY() >= 1024.0D) {
            this.gridPane1.setLayoutY(-1024.0D);
        }

        if (this.gridPane2.getLayoutY() >= 1024.0D) {
            this.gridPane2.setLayoutY(-1024.0D);
        }

    }

    /**
     * 用来判断飞船是否被砸中，如果是则玩家剩余游戏次数-1，如果还没完全耗光游戏次数将会有新的元素继续出现
     */
    private void checkIfElementsCollide() {
        if (39.0D > this.calculateDistance(this.ship.getLayoutX() + 49.0D, this.star.getLayoutX() + 15.0D, this.ship.getLayoutY() + 37.0D, this.star.getLayoutY() + 15.0D)) {
            this.setNewElementPosition(this.star);
            ++this.points;
            String textToSet = "POINTS : ";
            if (this.points < 10) {
                textToSet = textToSet + "0";
            }

            this.pointsLabel.setText(textToSet + this.points);
        }

        int i;
        for(i = 0; i < this.brownMeteors.length; ++i) {
            if (47.0D > this.calculateDistance(this.ship.getLayoutX() + 49.0D, this.brownMeteors[i].getLayoutX() + 20.0D, this.ship.getLayoutY() + 37.0D, this.brownMeteors[i].getLayoutY() + 20.0D)) {
                this.removeLife();
                this.setNewElementPosition(this.brownMeteors[i]);
            }
        }

        for(i = 0; i < this.greyMeteors.length; ++i) {
            if (47.0D > this.calculateDistance(this.ship.getLayoutX() + 49.0D, this.greyMeteors[i].getLayoutX() + 20.0D, this.ship.getLayoutY() + 37.0D, this.greyMeteors[i].getLayoutY() + 20.0D)) {
                this.removeLife();
                this.setNewElementPosition(this.greyMeteors[i]);
            }
        }

    }

    /**
     * 移除玩家剩余游戏次数，如果三次次数都耗光了将会退出游戏
     */
    private void removeLife() {
        this.gamePane.getChildren().remove(this.playerLifes[this.playerLife]);
        --this.playerLife;
        if (this.playerLife < 0) {
            this.gameStage.close();
            this.gameTimer.stop();
            this.menuStage.show();
        }

    }

    /**
     * 计算距离
     * @param x1
     * @param x2
     * @param y1
     * @param y2
     * @return
     */
    private double calculateDistance(double x1, double x2, double y1, double y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2.0D) + Math.pow(y1 - y2, 2.0D));
    }
}
