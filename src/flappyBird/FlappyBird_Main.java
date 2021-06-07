package flappyBird;
import main.Main;


import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class FlappyBird_Main extends Application {
    public static boolean GAME_SET=false;
    public static int i;
    public static int birdamount=0;
    Stage give_stage1;
    private Parent createContent() {
        Pane root = new Pane();

        ImageView title = new ImageView(new Image(getClass().getResource("/images/title.png").toExternalForm()));
        title.setFitWidth(178);
        title.setFitHeight(50);
        title.setLayoutX(311);
        title.setLayoutY(200);

        ImageView flappyBird = new ImageView(new Image(getClass().getResource("/images/bird2.png").toExternalForm()));
        flappyBird.setFitWidth(50);
        flappyBird.setFitHeight(45);
        flappyBird.setLayoutX(375);
        flappyBird.setLayoutY(300);

        ImageView background = new ImageView(new Image(getClass().getResource("/images/background.png").toExternalForm()));
        background.setFitWidth(800);
        background.setFitHeight(700);



        Rectangle bg = new Rectangle(800, 700);
        bg.setFill(Color.rgb(78,192,202));



        root.getChildren().addAll(background, title, flappyBird);
        return root;
    }

    public void startGame() throws Exception {
        System.out.println(GAME_SET);
        GAME_SET=false;
        if (!GAME_SET) {
            GAME_SET = true;
            FlappyBird_Game game = new FlappyBird_Game();
            Stage st = new Stage();
            try {
                game.start(give_stage1);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    @Override
    public void start(Stage primaryStage)  {
        Scene scene = new Scene(createContent());
        scene.setOnKeyPressed(e -> {
            try {
                if(e.getCode()==KeyCode.DIGIT1){
                    birdamount=1;
                    startGame();
                }
                else if(e.getCode()==KeyCode.DIGIT2){
                    birdamount=2;
                    startGame();
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        primaryStage.setTitle("Super Smash Flappy Bird!");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image("images//icon.png"));
        primaryStage.show();
        give_stage1=primaryStage;

    }




    public static void main(String[] args) {
        launch(args);
    }
}
