package view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.InfoLabel;
import model.HelpLabel;
import model.SHIP;
import model.ShipPicker;
import model.SpaceShipButton;
import model.SpaceShipSubScene;

public class ViewManager {
    private static final int HEIGHT = 768;
    private static final int WIDTH = 1024;
    private AnchorPane mainPane = new AnchorPane();
    private Scene mainScene;
    private Stage mainStage;
    private static final int MENU_BUTTON_START_X = 100;
    private static final int MENU_BUTTON_START_Y = 150;
    private SpaceShipSubScene helpSubscene;
    private SpaceShipSubScene shipChooserSubscene;
    private SpaceShipSubScene sceneToHide;
    List<SpaceShipButton> menuButtons = new ArrayList();
    List<ShipPicker> shipsList;
    private SHIP choosenShip;

    public ViewManager() {
        this.mainScene = new Scene(this.mainPane, 1024.0D, 768.0D);
        this.mainStage = new Stage();
        this.mainStage.setScene(this.mainScene);
        this.createSubScenes();
        this.CreateButtons();
        this.createBackground();
        this.createLogo();
    }

    private void showSubScene(SpaceShipSubScene subScene) {
        if (this.sceneToHide != null) {
            this.sceneToHide.moveSubScene();
        }

        subScene.moveSubScene();
        this.sceneToHide = subScene;
    }

    private void createSubScenes() {
        this.createHelpSubScene();
        this.createShipChooserSubScene();
    }

    private void createShipChooserSubScene() {
        this.shipChooserSubscene = new SpaceShipSubScene();
        this.mainPane.getChildren().add(this.shipChooserSubscene);
        InfoLabel chooseShipLabel = new InfoLabel("CHOOSE YOUR SHIP");
        chooseShipLabel.setLayoutX(110.0D);
        chooseShipLabel.setLayoutY(25.0D);
        this.shipChooserSubscene.getPane().getChildren().add(chooseShipLabel);
        this.shipChooserSubscene.getPane().getChildren().add(this.createShipsToChoose());
        this.shipChooserSubscene.getPane().getChildren().add(this.createButtonToStart());
    }
    private void createHelpSubScene() {
        this.helpSubscene = new SpaceShipSubScene();
        this.mainPane.getChildren().add(this.helpSubscene);
        InfoLabel help = new InfoLabel("Help");
        HelpLabel rule1 = new HelpLabel("1. Move your arrows on your keyboard to control the SpaceShip\n2. Move your SpaceShip to the star and get your points\n3. Be mind of the stone, it will takes away your life\n4. There are 3 chances per game,you can check it out on the left side, the little SpaceShip represents it");
        rule1.setLayoutX(110.0D);
        rule1.setLayoutY(25.0D);
        help.setLayoutX(110.0D);
        help.setLayoutY(25.0D);
        this.helpSubscene.getPane().getChildren().add(rule1);
        this.helpSubscene.getPane().getChildren().add(help);
    }
    
    private HBox createShipsToChoose() {
        HBox box = new HBox();
        box.setSpacing(60.0D);
        this.shipsList = new ArrayList();
        SHIP[] var5;
        int var4 = (var5 = SHIP.values()).length;

        for(int var3 = 0; var3 < var4; ++var3) {
            SHIP ship = var5[var3];
            final ShipPicker shipToPick = new ShipPicker(ship);
            this.shipsList.add(shipToPick);
            box.getChildren().add(shipToPick);
            shipToPick.setOnMouseClicked(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    Iterator var3 = ViewManager.this.shipsList.iterator();

                    while(var3.hasNext()) {
                        ShipPicker ship = (ShipPicker)var3.next();
                        ship.setIsCircleChoosen(false);
                    }

                    shipToPick.setIsCircleChoosen(true);
                    ViewManager.this.choosenShip = shipToPick.getShip();
                }
            });
        }

        box.setLayoutX(64.0D);
        box.setLayoutY(100.0D);
        return box;
    }

    private Node createButtonToStart() {
        SpaceShipButton startButton = new SpaceShipButton("START");
        startButton.setLayoutX(350.0D);
        startButton.setLayoutY(300.0D);
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                if (ViewManager.this.choosenShip != null) {
                    GameViewManager gameManager = new GameViewManager();
                    gameManager.createNewGame(ViewManager.this.mainStage, ViewManager.this.choosenShip);
                }

            }
        });
        return startButton;
    }

    public Stage getMainStage() {
        return this.mainStage;
    }

    private void AddMenuButtons(SpaceShipButton button) {
        button.setLayoutX(100.0D);
        button.setLayoutY((double)(150 + this.menuButtons.size() * 100));
        this.menuButtons.add(button);
        this.mainPane.getChildren().add(button);
    }

    private void CreateButtons() {
        this.createStartButton();
        this.createHelpButton();
        this.createExitButton();
    }

    private void createStartButton() {
        SpaceShipButton startButton = new SpaceShipButton("PLAY");
        this.AddMenuButtons(startButton);
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                ViewManager.this.showSubScene(ViewManager.this.shipChooserSubscene);
            }
        });
    }

    private void createHelpButton() {
        SpaceShipButton helpButton = new SpaceShipButton("HELP");
        this.AddMenuButtons(helpButton);
        helpButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                ViewManager.this.showSubScene(ViewManager.this.helpSubscene);
            }
        });
    }

    private void createExitButton() {
        SpaceShipButton exitButton = new SpaceShipButton("EXIT");
        this.AddMenuButtons(exitButton);
        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                ViewManager.this.mainStage.close();
            }
        });
    }

    private void createBackground() {
        Image backgroundImage = new Image("/resources/deep_blue.png", 256.0D, 256.0D, false, false);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, (BackgroundSize)null);
        this.mainPane.setBackground(new Background(new BackgroundImage[]{background}));
    }

    private void createLogo() {
        final ImageView logo = new ImageView("/resources/spaceship.png");
        logo.setLayoutX(380.0D);
        logo.setLayoutY(50.0D);
        logo.setOnMouseEntered(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                logo.setEffect(new DropShadow());
            }
        });
        logo.setOnMouseExited(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                logo.setEffect((Effect)null);
            }
        });
        this.mainPane.getChildren().add(logo);
    }
}
