package flappyBird;

public class Pipe {
    private Sprite pipe;
    private double locationX;
    private double locationY;
    private double height;
    private double width;

    public Pipe(boolean isFaceUp, int height) {
        this.pipe = new Sprite();
        this.pipe.resizeImage("images//bottom_pipe.png", 70, height);
        this.width = 70;
        this.height = height;
        this.locationX = 700;
        this.locationY = isFaceUp? 600 - height : 0;    //判断水管是否向上还是向下生成
        this.pipe.setPositionXY(locationX, locationY);
    }

    public Sprite getPipe() {
        return pipe;
    }
}