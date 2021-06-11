package flappyBird;

import java.util.ArrayList;
import java.util.Arrays;
/**
 * @author 朱章涌 <br>
 * 这个类是用于生成紫色小鸟,作为双人模式里的2号玩家.
 */
public class Bird2 {
    /***
     * 作为紫色小鸟移动的动画类
     */
    private Sprite bird2 = new Sprite();
    /**
     * 记录每次小鸟的移动动画
     */
    private ArrayList<Sprite> flight = new ArrayList<>();
    /**
     * 用于记录Sprite的Arraylist里的当前小鸟
     */
    private int currentBird = 0;
    /**
     * 设置x坐标
     */
    private double locationX = 100;
    /**
     * 设置y坐标
     */
    private double locationY = 100;
    /**
     * 设置紫色小鸟图片宽度
     */
    private int BIRD_WIDTH = 50;
    /**
     * 设置黄色小鸟图片高度
     */
    private int BIRD_HEIGHT = 45;

    /**
     * 这个方法是设置一开始紫色小鸟在游戏还未开始时的图片。
     */
    public Bird2(){
        bird2.resizeImage("images/bird2_1.png",BIRD_WIDTH,BIRD_HEIGHT);
        bird2.setPositionXY(locationX, locationY);
        setFlightAnimation();
    }

    /**
     * 这个方法是设置紫色小鸟3个动作,由于有三个动作,平飞,扑翅和下落,因此由三个图片组成。<br>
     * 最后加入flight的Arraylist里进行记录。
     */
    private void setFlightAnimation() {
        Sprite bird2 = new Sprite();
        bird2.resizeImage("/images/bird2_2.png", BIRD_WIDTH, BIRD_HEIGHT);
        bird2.setPositionXY(locationX, locationY);

        Sprite bird3 = new Sprite();
        bird3.resizeImage("/images/bird2_1.png", BIRD_WIDTH, BIRD_HEIGHT);
        bird3.setPositionXY(locationX, locationY);

        Sprite bird4 = new Sprite();
        bird4.resizeImage("/images/bird2_3.png", BIRD_WIDTH, BIRD_HEIGHT);
        bird4.setPositionXY(locationX, locationY);

        flight.addAll(Arrays.asList(this.bird2,bird2,bird3,bird4));
    }


    /**
     * 获取鸟的实例
     * @return 返回一只已经初始化好了的紫色小鸟.
     */
    public Sprite getBird() {
        return bird2;
    }

    /**
     * 负责计算flight arraylist的容量,并返回紫色小鸟.
     * @return 当前flight里最新的小鸟动画记录.
     */
    public Sprite animate() {
        if (currentBird == flight.size() - 1) {
            currentBird = 0;
        }

        return flight.get(currentBird++);
    }

}
