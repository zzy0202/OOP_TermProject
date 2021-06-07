package flappyBird;

import java.util.ArrayList;
import java.util.Arrays;
//用来生成第一只小鸟
public class Bird {
    private Sprite bird1 = new Sprite();
    private ArrayList<Sprite> flight = new ArrayList<>();
    private int currentBird = 0;
    private double locationX = 100;
    private double locationY = 200;
    private int BIRD_WIDTH = 50;
    private int BIRD_HEIGHT = 45;

    public Bird(){
        bird1.resizeImage("images//bird1.png",BIRD_WIDTH,BIRD_HEIGHT);
        bird1.setPositionXY(locationX, locationY);
        setFlightAnimation();
    }

    private void setFlightAnimation() {
        Sprite bird2 = new Sprite();
        bird2.resizeImage("/images/bird2.png", BIRD_WIDTH, BIRD_HEIGHT);//第2个挥翅图像
        bird2.setPositionXY(locationX, locationY);

        Sprite bird3 = new Sprite();
        bird3.resizeImage("/images/bird1.png", BIRD_WIDTH, BIRD_HEIGHT);//第1个挥翅图像
        bird3.setPositionXY(locationX, locationY);

        Sprite bird4 = new Sprite();
        bird4.resizeImage("/images/bird3.png", BIRD_WIDTH, BIRD_HEIGHT);//第3个挥翅图像
        bird4.setPositionXY(locationX, locationY);

        flight.addAll(Arrays.asList(bird1,bird2,bird3,bird4));
    }



    public Sprite getBird() {
        return bird1;
    }

    public Sprite animate() {
        if (currentBird == flight.size() - 1) {
            currentBird = 0;
        }

        return flight.get(currentBird++);
    }

}