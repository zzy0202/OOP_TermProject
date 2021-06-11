package flappyBird;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author 朱章涌 <br>
 * 这个类是用于生成黄色小鸟,作为单人模式里的玩家或双人模式里的1号玩家.
 */
public class Bird {
    /***
     * 作为黄色小鸟移动的动画类
     */
    public final Sprite bird1 = new Sprite();
    /**
     * 记录每次小鸟的移动动画
     */
    public ArrayList<Sprite> flight = new ArrayList<>();
    /**
     * 用于记录Sprite的Arraylist里的当前小鸟
     */
    public int currentBird = 0;
    /**
     * 设置x坐标
     */
    public double locationX = 100;
    /**
     * 设置y坐标
     */
    public double locationY = 200;
    /**
     * 设置黄色小鸟图片宽度
     */
    public int BIRD_WIDTH = 50;
    /**
     * 设置黄色小鸟图片高度
     */
    public int BIRD_HEIGHT = 45;

    /**
     * 这个方法是设置一开始黄色小鸟在游戏还未开始时的图片。
     */
    public Bird(){
        bird1.resizeImage("images/bird1.png",BIRD_WIDTH,BIRD_HEIGHT);
        bird1.setPositionXY(locationX, locationY);
        setFlightAnimation();
    }

    /**
     * 这个方法是设置黄色小鸟飞行动作,由于有三个动作,平飞,扑翅和下落,因此由三个图片组成。<br>
     * 最后加入flight的Arraylist里进行记录。
     */
    public void setFlightAnimation() {
        Sprite bird2 = new Sprite();
        bird2.resizeImage("/images/bird2.png", BIRD_WIDTH, BIRD_HEIGHT);
        bird2.setPositionXY(locationX, locationY);

        Sprite bird3 = new Sprite();
        bird3.resizeImage("/images/bird1.png", BIRD_WIDTH, BIRD_HEIGHT);
        bird3.setPositionXY(locationX, locationY);

        Sprite bird4 = new Sprite();
        bird4.resizeImage("/images/bird3.png", BIRD_WIDTH, BIRD_HEIGHT);
        bird4.setPositionXY(locationX, locationY);

        flight.addAll(Arrays.asList(bird1,bird2,bird3,bird4));
    }


    /**
     * 获取鸟的实例
     * @return 返回一只已经初始化好了的黄色小鸟.
     */
    public Sprite getBird() {
        return bird1;
    }

    /**
     * 负责计算flight arraylist的容量,并返回黄色小鸟.
     * @return 当前flight里最新的小鸟动画记录.
     */
    public Sprite animate() {
        if (currentBird == flight.size() - 1) {
            currentBird = 0;
        }

        return flight.get(currentBird++);
    }

}
