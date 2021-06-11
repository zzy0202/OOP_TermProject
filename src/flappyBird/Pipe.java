package flappyBird;

/**
 * @author 朱章涌
 * 这个类主要是负责水管的生成以及属性设置.
 */
public class Pipe {
    /**
     * 这个是一个水管的动画实现对象.
     */
    private Sprite pipe;
    /**
     * 这个是水管的x轴位置.
     */
    private double locationX;
    /**
     * 这个是水管的y轴位置.
     */
    private double locationY;
    /**
     * 设置水管的高度.
     */
    private double height;
    /**
     * 设置水管的宽度.
     */
    private double width;

    /**
     * 这个方法用于对水管进行属性赋值,并且生成相对应数值的图片.
     * @param isFaceUp 判断是上水管还是下水管.
     * @param height 水管高度.
     */
    public Pipe(boolean isFaceUp, int height) {
        this.pipe = new Sprite();
        this.pipe.resizeImage("images/bottom_pipe.png", 70, height);
        this.width = 70;
        this.height = height;
        this.locationX = 700;
        this.locationY = isFaceUp? 600 - height : 0;    //判断水管是否向上还是向下生成
        this.pipe.setPositionXY(locationX, locationY);
    }

    /**
     * 这个方法是用于获得一个水管实例.
     * @return 一个水管.
     */
    public Sprite getPipe() {
        return pipe;
    }
}
