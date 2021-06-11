package flappyBird;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * @author 朱章涌
 * 这个类主要作为动画类,对图片进行位置的更新
 */
public class Sprite {
    /**
     * 图片变量,用来保存图片.
     */
    public Image image;
    /**
     * 设置图片的x轴
     */
    public double positionX;
    /**
     * 设置图片的y轴.
     */
    public double positionY;
    /**
     * 设置水平速度.
     */
    public double velocityX;
    /**
     * 设置垂直速度.
     */
    public double velocityY;
    /**
     * 设置图片宽度.
     */
    public double width;
    /**
     * 设置图片高度.
     */
    public double height;

    /**
     * 构造方法,进行成员变量的初始化.
     */
    public Sprite() {
        this.positionX = 0;
        this.positionY = 0;
        this.velocityX = 0;
        this.velocityY = 0;
    }

    /**
     * 这个方法用于设置图片属性.
     * @param image 图片变量.
     */
    public void setImage(Image image) {
        this.image = image;
        this.width = image.getWidth();
        this.height = image.getHeight();
    }

    /**
     * 这个方法用于设置图片的修改.
     * @param filepath 文件路径
     * @param width 图片宽度
     * @param height 图片高度
     */
    public void resizeImage(String filepath, int width, int height) {
        Image toReturn = new Image(filepath, width, height, false, false);
        setImage(toReturn);
    }

    /**
     * 这个方法用于设置图片位置
     * @param positionX x轴
     * @param positionY y轴
     */
    public void setPositionXY(double positionX, double positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    /**
     * 这个方法用于获取x轴变量
     * @return x轴的数值
     */
    public double getPositionX() {
        return positionX;
    }

    /**
     * 这个方法用于获取y轴变量
     * @return y轴的数值
     */
    public double getPositionY() {
        return positionY;
    }

    /**
     * 这个方法用于设置速度.
     * @param velocityX 水平速度
     * @param velocityY 垂直速度
     */
    public void setVelocity(double velocityX, double velocityY) {
        this.velocityX = velocityX;
        this.velocityY = velocityY;
    }

    /**
     * 这个方法用于设置加速度.
     * @param x 水平加速度
     * @param y 垂直加速度
     */
    public void addVelocity(double x, double y) {
        this.velocityX += x;
        this.velocityY += y;
    }

    /**
     * 这个方法用于获取水平速度
     * @return 水平速度数值
     */
    public double getVelocityX() {
        return velocityX;
    }

    /**
     * 这个方法用于获取垂直速度
     * @return 垂直速度数值
     */
    public double getVelocityY() {
        return velocityY;
    }

    /**
     * 这个方法用于获取图片宽度.
     * @return 图片宽度.
     */
    public double getWidth() {
        return width;
    }

    /**
     * 这个方法用于通过重新画图更新图片.
     * @param gc GraphicContext变量.
     */
    public void render(GraphicsContext gc) {
        gc.drawImage(image, positionX, positionY);
    }

    /**
     * 这个方法用于获取图片边界位置和高宽度.
     * @return 一个带有图片属性的Rectangle2D对象.
     */
    public Rectangle2D getBoundary() {
        return new Rectangle2D(positionX, positionY, width, height);
    }

    /**
     * 这个方法进行判断两个图片是否相交,可用于判断小鸟是否撞上了水管或地上.
     * @return 若相交则返回true.
     */
    public boolean intersectsSprite(Sprite s) {
        return s.getBoundary().intersects(this.getBoundary());
    }

    /**
     * 这个方法根据提供的时间和速度来获得下一个帧率的所在位置.
     * @param time
     */
    public void update(double time) {
        positionX += velocityX * time;
        positionY += velocityY * time;
    }
}
