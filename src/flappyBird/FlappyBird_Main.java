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

/**
 * @author 朱章涌
 * 这个类主要作为游戏FlappyBird的主界面.
 */
public class FlappyBird_Main extends Application {
    /**
     * 用来判断是否要进行游戏开始的布尔变量
     */
    public static boolean GAME_SET=false;
    /**
     * 但玩家在这个界面选择了1时,赋值1进入单人模式,2则进入双人模式
     */
    public static int birdamount=0;
    /**
     *  当我们开始游戏时,可以把这个Stage传给FlappyBird_Game类里,这样就能进行一个游戏回退了.
     */
    Stage give_stage1;

    /**
     * 这个方法主要是获得图片以及对图片的属性进行初始化.
     * @return 一个已经将图片挂载了的Pane实例.
     */
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
        flappyBird.setLayoutX(325);
        flappyBird.setLayoutY(300);

        ImageView flappyBird2 = new ImageView(new Image(getClass().getResource("/images/bird2_2.png").toExternalForm()));
        flappyBird2.setFitWidth(50);
        flappyBird2.setFitHeight(45);
        flappyBird2.setLayoutX(400);
        flappyBird2.setLayoutY(300);

        ImageView background = new ImageView(new Image(getClass().getResource("/images/background.png").toExternalForm()));
        background.setFitWidth(800);
        background.setFitHeight(700);

        ImageView exit = new ImageView(new Image(getClass().getResource("/images/exit.jpg").toExternalForm()));
        exit.setFitWidth(200);
        exit.setFitHeight(30);
        exit.setLayoutX(0);
        exit.setLayoutY(0);

        Rectangle bg = new Rectangle(800, 700);
        bg.setFill(Color.rgb(78,192,202));

        Text inst = new Text("Press 1 for Single Player \n    2 for Double Player");
        inst.setFont(Font.font("Courier", FontWeight.EXTRA_BOLD, 25));
        inst.setFill(Color.WHITE);
        inst.setStroke(Color.BLACK);
        inst.setLayoutX(245);
        inst.setLayoutY(400);

        Text guide = new Text("Press Spacebar to control yellow bird,W to control purple bird");
        guide.setFont(Font.font("Courier", FontWeight.EXTRA_BOLD, 20));
        guide.setFill(Color.WHITE);
        guide.setStroke(Color.BLACK);
        guide.setLayoutX(100);
        guide.setLayoutY(500);

        root.getChildren().addAll(background, title, flappyBird,flappyBird2,guide,exit,inst);
        return root;
    }

    /**
     * 当输入了1或2时,调用这个方法开始游戏.
     * @throws Exception
     */
    public void startGame() throws Exception {
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

    /**
     * Javafx提供的start方法,在这里主要对窗口进行初始化和进行输出.并且在里面设置了监听器,当输入1或2时进入游戏.
     * @param primaryStage Stage对象的实例.
     */
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
        scene.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                double posx = mouseEvent.getX();
                double posy = mouseEvent.getY();
                if(posx>=0&&posx<=200&&posy>=0&&posy<=30){
                    exitFlappy();
                }
            }
        });
        primaryStage.setTitle("Super Smash Flappy Bird!");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image("images/icon.png"));
        primaryStage.show();
        give_stage1=primaryStage;

    }

    /**
     * 当点击'exitgame'图标时，退出游戏.
     */
    private void exitFlappy() {
        Main exit = new Main();
        exit.start(give_stage1);
    }


}
