package main;
import flappyBird.FlappyBird_Main;
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
import spaceShip.SpaceShip_Main;

/**
 * @author 朱章涌 <br>
 * 这个类是用来生成程序的主界面的
 */
public class Main extends Application {
    /**
     * 用来保存当前的窗口，并赋给下一个游戏类对象，以便能在一个窗口运行所有程序。
     */
    public Stage give_stage;

    /**
     * 这个方法是用来让图形挂载到root上的,利用javafx里的ImageView就能获取src文件里的图片资源了 <br>
     * @return 已经加上图片了的root
     */
    public Parent createContent() {
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

    /**
     * 这个方法是用于设置窗口的一些初始化如设置标题，应用图标 <br>
     * 里面的有一个scene.setOnMouseClicked方法是用于判断鼠标点击位置，进行判断进入哪个游戏。
     * @param primaryStage 作为一个容器,即负责存放scene以作为一个窗口
     */
    @Override
    public void start(Stage primaryStage)  {
        give_stage=primaryStage;
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
        primaryStage.getIcons().add(new Image("/images/Mainicon.png"));
        primaryStage.show();
        give_stage=primaryStage;

    }

    /**
     * 当点击了FlappyBird图标后便会到这个方法，并生成FlappyBird_Main进入另外一个窗口<br>
     * 以GAME_SET作为判断。
     */
    public void startFlappy()  {
        FlappyBird_Main main = new FlappyBird_Main();
        try {
            main.start(give_stage);
        } catch (Exception e1) {
             e1.printStackTrace();
        }
    }

    /**
     * 当点击了SpaceShip图标后便会到这个方法，并生成SpaceShip_Main进入另外一个窗口<br>
     * 以GAME_SET作为判断。
     * @throws Exception 方法调用失败时抛出异常.
     */
    public void startSpace_Ship() throws Exception {
        SpaceShip_Main main= new SpaceShip_Main();
        try {
            main.start(give_stage);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
