package flappyBird;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Random;

public class Background {
    public static ImageView setBackground() {
        Random random = new Random();
        String filePath;
        int bg = random.nextInt(1);
        if(bg==0){
            filePath="/images/background.png";
        }
        else{
            filePath="/images/background_night.png";
        }
        ImageView imageView = new ImageView(new Image(Background.class.getResource(filePath).toExternalForm()));
        imageView.setFitWidth(FlappyBird_Game.APP_WIDTH);
        imageView.setFitHeight(FlappyBird_Game.APP_HEIGHT);
        return imageView;
    }
}
