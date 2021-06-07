package spaceShip;

import flappyBird.FlappyBird_Game;
import javafx.scene.input.KeyEvent;
import main.Main;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SpaceShip_Main extends Application {
    Stage give_stage2;
    public Parent setContent(){
        Pane root = new Pane();

        ImageView background = new ImageView(new Image(getClass().getResource("/images/spacebg.png").toExternalForm()));
        background.setFitWidth(800);
        background.setFitHeight(700);

        ImageView spaceship = new ImageView(new Image(getClass().getResource("/images/spaceship.png").toExternalForm()));
        spaceship.setFitWidth(75);
        spaceship.setFitHeight(75);
        spaceship.setLayoutX(362.5);
        spaceship.setLayoutY(350);

        ImageView title = new ImageView(new Image(getClass().getResource("/images/spacetitle.png").toExternalForm()));
        title.setFitHeight(75);
        title.setFitWidth(300);
        title.setLayoutX(250);
        title.setLayoutY(225);

        ImageView exit = new ImageView(new Image(getClass().getResource("/images/exit.jpg").toExternalForm()));
        exit.setFitWidth(200);
        exit.setFitHeight(30);
        exit.setLayoutX(0);
        exit.setLayoutY(0);

        ImageView battle = new ImageView(new Image(getClass().getResource("/images/battle.png").toExternalForm()));
        battle.setFitWidth(150);
        battle.setFitHeight(30);
        battle.setLayoutX(325);
        battle.setLayoutY(475);
        root.getChildren().addAll(background,spaceship,title,exit,battle);
        return root;
    }
    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(setContent());
        scene.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                double posx = mouseEvent.getX();
                double posy = mouseEvent.getY();
                if(posx>=0&&posx<=200&&posy>=0&&posy<=30){
                    exitSpaceShip();
                }
                else if(posx>=325&&posx<=475&&posy>=475&&posy<=510){
                    try {
                        startgame();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        stage.getIcons().add(new Image("images/spaceship.png"));
        stage.setTitle("Welcome to the SPACE!");
        stage.setScene(scene);
        stage.show();
        give_stage2=stage;

    }


    public void startgame() throws Exception {
        SpaceShip_Game game = new SpaceShip_Game();
        game.start(give_stage2);
    }

    private void exitSpaceShip() {
        Main exit = new Main();
        exit.start(give_stage2);
        Main.GAME_SET=false;
    }
}
