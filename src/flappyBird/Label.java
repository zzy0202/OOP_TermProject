package flappyBird;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * @author 朱章涌 <br>
 * 这个类是用于设置一些图标如exit,ready等待.
 */
public class Label {
    /**
     * 这个方法负责实现图标的生成,大小以及挂载位置.
     */
    public void setLabels() {
        FlappyBird_Game.scoreLabel = new Text("0");
        FlappyBird_Game.scoreLabel.setFont(Font.font("Courier", FontWeight.EXTRA_BOLD, 50));
        FlappyBird_Game.scoreLabel.setStroke(Color.BLACK);
        FlappyBird_Game.scoreLabel.setFill(Color.WHITE);
        FlappyBird_Game.scoreLabel.setLayoutX(20);
        FlappyBird_Game.scoreLabel.setLayoutY(40);

        FlappyBird_Game.gameOver = new ImageView(new Image(getClass().getResource("/images/game_over.png").toExternalForm()));
        FlappyBird_Game.gameOver.setFitWidth(178);
        FlappyBird_Game.gameOver.setFitHeight(50);
        FlappyBird_Game.gameOver.setLayoutX(311);
        FlappyBird_Game.gameOver.setLayoutY(275);

        FlappyBird_Game.player1 = new Text("Purple Bird Won!");
        FlappyBird_Game.player1.setLayoutX(311);
        FlappyBird_Game.player1.setLayoutY(275);
        FlappyBird_Game.player1.setFont(Font.font("Courier", FontWeight.EXTRA_BOLD, 25));
        FlappyBird_Game.player1.setFill(Color.WHITE);
        FlappyBird_Game.player1.setStroke(Color.BLACK);

        FlappyBird_Game.player2 = new Text("Yellow Bird Won!");
        FlappyBird_Game.player2.setLayoutX(311);
        FlappyBird_Game.player2.setLayoutY(275);
        FlappyBird_Game.player2.setFont(Font.font("Courier", FontWeight.EXTRA_BOLD, 25));
        FlappyBird_Game.player2.setFill(Color.WHITE);
        FlappyBird_Game.player2.setStroke(Color.BLACK);

        FlappyBird_Game.draw = new Text("Draw!");
        FlappyBird_Game.draw.setLayoutX(375);
        FlappyBird_Game.draw.setLayoutY(350);
        FlappyBird_Game.draw.setFont(Font.font("Courier", FontWeight.EXTRA_BOLD, 25));
        FlappyBird_Game.draw.setFill(Color.WHITE);
        FlappyBird_Game.draw.setStroke(Color.BLACK);

        FlappyBird_Game.ready = new ImageView(new Image(getClass().getResource("/images/ready.png").toExternalForm()));
        FlappyBird_Game.ready.setFitWidth(178);
        FlappyBird_Game.ready.setFitHeight(50);
        FlappyBird_Game.ready.setLayoutX(311);
        FlappyBird_Game.ready.setLayoutY(275);

        FlappyBird_Game.exit = new ImageView(new Image(getClass().getResource("/images/exit.jpg").toExternalForm()));
        FlappyBird_Game.exit.setFitWidth(200);
        FlappyBird_Game.exit.setFitHeight(30);
        FlappyBird_Game.exit.setLayoutX(600);
        FlappyBird_Game.exit.setLayoutY(0);
    }
}
