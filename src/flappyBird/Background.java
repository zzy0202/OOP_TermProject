package flappyBird;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.Random;

/**
 * @author 朱章涌 <br>
 * 这个类主要是用来生成背景图片
 */
public class Background {
    /**
     * 这个方法主要是用于获得背景图片，在这个方法里设置了一个随机数,分别代表了白天背景和晚上背景。
     * @return 背景图片
     */
    public static ImageView setBackground() {
        Random random = new Random();
        String filePath;
        int bg = random.nextInt(2);
        if(bg==0){
            filePath="/images/background.png";
        }
        else{
            filePath="/images/background_night.png";
        }
        ImageView imageView = new ImageView(new Image(Background.class.getResource(filePath).toExternalForm()));
        imageView.setFitWidth(FlappyBird_Game.APP_WIDTH);
        if(bg!=0){
            imageView.setFitHeight(727.5);
        }
        else {
            imageView.setFitHeight(FlappyBird_Game.APP_HEIGHT);
        }
        return imageView;
    }
}
