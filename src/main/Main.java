package main;

import flappyBird.FlappyBird_Game;
import flappyBird.FlappyBird_Main;
import spaceShip.SpaceShip_Main;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {
    public static boolean GAME_SET;
    public static int i=0;
    Stage give_stage;
    private Parent createContent() {
        Pane root = new Pane();

        ImageView background = new ImageView(new Image(getClass().getResource("/images/mainbg.png").toExternalForm()));
        background.setFitWidth(800);
        background.setFitHeight(700);

        ImageView flappybird = new ImageView(new Image(getClass().getResource("/images/bird2_2.png").toExternalForm()));
        flappybird.setFitWidth(40);
        flappybird.setFitHeight(40);
        flappybird.setLayoutX(50);
        flappybird.setLayoutY(500);

        ImageView spaceship = new ImageView(new Image(getClass().getResource("/images/spaceship.png").toExternalForm()));
        spaceship.setFitWidth(50);
        spaceship.setFitHeight(50);
        spaceship.setLayoutX(50);
        spaceship.setLayoutY(575);

        ImageView flappy_title = new ImageView(new Image(getClass().getResource("/images/title.png").toExternalForm()));
        flappy_title.setFitWidth(150);
        flappy_title.setFitHeight(50);
        flappy_title.setLayoutX(150);
        flappy_title.setLayoutY(500);

        ImageView space_title = new ImageView(new Image(getClass().getResource("/images/spacetitle.png").toExternalForm()));
        space_title.setFitWidth(150);
        space_title.setFitHeight(50);
        space_title.setLayoutX(150);
        space_title.setLayoutY(575);

        Rectangle bg = new Rectangle(800, 700);
        bg.setFill(Color.rgb(78,192,202));

        Text inst = new Text("Click a Gaming icon to have fun!");
        inst.setFont(Font.font("Courier", FontWeight.EXTRA_BOLD, 25));
        inst.setFill(Color.WHITE);
        inst.setStroke(Color.BLACK);
        inst.setLayoutX(50);
        inst.setLayoutY(450);

        root.getChildren().addAll(background,inst,flappybird,spaceship,flappy_title,space_title);
        return root;
    }

    public void startGame() throws Exception {
        if (!GAME_SET) {
            GAME_SET = true;
            FlappyBird_Game game = new FlappyBird_Game();
            Stage st = new Stage();
            try {
                game.start(st);
            } catch (Exception e1) {
                game.start(st);
                e1.printStackTrace();
            }
        }
    }

    @Override
    public void start(Stage primaryStage)  {
        Scene scene = new Scene(createContent());
        scene.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                double posx = mouseEvent.getX();
                double posy = mouseEvent.getY();
                if(posx>=50&&posx<=300&&posy>=500&&posy<=540){
                    startFlappy();
                }
                else if(posx>=50&&posx<=300&&posy>=575&&posy<=625){
                    try {
                        startSpace_Ship();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        primaryStage.setTitle("Gaming Time!");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image("images//Mainicon.png"));
        primaryStage.show();
        give_stage=primaryStage;

    }

    public void startFlappy()  {
        if (!GAME_SET) {
            GAME_SET = true;
            FlappyBird_Main main = new FlappyBird_Main();
            try {
                main.start(give_stage);
            } catch (Exception e1) {
                main.start(give_stage);
                e1.printStackTrace();
            }
        }
    }

    public void startSpace_Ship() throws Exception {
        if (!GAME_SET) {
            GAME_SET = true;
            SpaceShip_Main main = new SpaceShip_Main();
            Stage st = new Stage();
            try {
                main.start(give_stage);
            } catch (Exception e1) {
                main.start(st);
                e1.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
