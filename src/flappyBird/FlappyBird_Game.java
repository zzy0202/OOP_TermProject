package flappyBird;
import main.Main;
import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * @author 朱章涌
 * 这个类主要作为游戏里的界面
 */
public class FlappyBird_Game extends Application {
    /**
     * 在双人模式里作为判断玩家1是否游戏失败;
     */
    public boolean bird1_lose=false;
    /**
     * 在双人模式里作为判断玩家2是否游戏失败.
     */
    public boolean bird2_lose=false;
    /**
     * 设定运行窗口的高度
     */
    public static int APP_HEIGHT = 700;
    /**
     * 设置运行窗口宽度
     */
    public static int APP_WIDTH = 800;
    /**
     * 记录分数
     */
    public int TOTAL_SCORE = 0;
    /**
     * 判断按键时间,spaceClickA负责1号玩家.
     */
    public long spaceClickA;
    /**
     * 判断按键时间,spaceClickB负责2号玩家.
     */
    public long spaceClickB;
    /**
     * 记录动画的动作时间,控制小鸟的动画
     */
    public double motionTime;
    /**
     * 记录时间,用来更新小鸟的动画效果.
     */
    public double elapsedTime;
    /**
     * CLICKED作为判断1号玩家是否按键了,CLICKED2则作为判断2号玩家的
     */
    public boolean CLICKED,CLICKED2;
    /**
     * GAME_START作为判断游戏是否开始,例如当游戏失败时按键将能再重新游戏
     */
    public boolean GAME_START;
    /**
     * GAME_OVER作为判断是否游戏结束,这时停止动画并输出相应的图标如"gameover"
     */
    public boolean GAME_OVER;
    /**
     * 判断1号玩家是否撞上了水管,true则1号玩家游戏失败.
     */
    public boolean HIT_PIPE1;
    /**
     * 判断2号玩家是否撞上了水管,true则2号玩家游戏失败.
     */
    public boolean HIT_PIPE2;
    /**
     * 判断1号玩家是否掉到了地上,true则1号玩家游戏失败.
     */
    public boolean HIT_FLOOR1;
    /**
     * 判断2号玩家是否掉到了地上,true则2号玩家游戏失败.
     */
    public boolean HIT_FLOOR2;
    /**
     * 用来获取当前的时间,在AnimationTimer里进行动画控制.
     */
    public flappyBird.FlappyBird_Game.LongValue startNanoTime;
    /**
     * 作为第1个地上的动画移动,以便让地上也会跟着小鸟移动.
     */
    public Sprite firstFloor;
    /**
     * 作为第2个地上的动画移动,和第1个进行交替动画显示.
     */
    public Sprite secondFloor;
    /**
     * 作为黄色小鸟的动画实例.
     */
    public Sprite birdSprite1 ;
    /**
     * 作为紫色小鸟的动画实例.
     */
    public Sprite birdSprite2;
    /**
     * 黄色小鸟的实例.
     */
    public Bird bird;
    /**
     * 紫色小鸟的实例.
     */
    public Bird2 bird2;
    /**
     * 作为展示分数的字体.
     */
    public static Text scoreLabel;
    /**
     * 作为游戏开始时的
     */
    AnimationTimer timer;
    /**
     *  作为用来进行绘图的缓冲区.
     */
    public GraphicsContext gc;
    /**
     * 用来进行黄色小鸟绘图的缓冲区.
     */
    public GraphicsContext birdGC1;
    /**
     * 用来进行紫色小鸟绘图的缓冲区.
     */
    public GraphicsContext birdGC2;
    /**
     * 用来保存pipes实例的Arraylist,在游戏时显示.
     */
    public ArrayList<Pipe> pipes;
    /**
     * gameover图标,在游戏结束时显示.
     */
    public static ImageView gameOver;
    /**
     * ready图标,在还没游戏还没开始前显示.
     */
    public static ImageView ready;
    /**
     * exit图标,让玩家点击后能进行游戏退出.
     */
    public static ImageView exit;
    /**
     * 作为一个文本图形输出,当1号玩家失败时,输出"Purple bird won."
     */
    public static Text player1;
    /**
     * 作为一个文本图形输出,当2号玩家失败时,输出"Yellow bird won,"
     */
    public static Text player2;
    /**
     * 作为一个文本输出,当1号玩家和2号玩家打成平手时输出.
     */
    public static Text draw;
    /**
     * 作为图形的挂载.
     */
    public Group root;
    /**
     * 用来保存上一个界面的Stage,当点击退出时就输出这个stage,实现回退功能.
     */
    public Stage give_stage;

    /**
     * 这个方法里主要是对游戏的窗口进行初始化,例如设置窗口标题,监听器以及图形.
     * 里面设置了一个鼠标监听器来判断当鼠标点击的坐标处于exit的图标里,则这时进行窗口的回退.<br>
     * @param primaryStage 作为stage对象输出窗口.
     * @throws Exception 当方法调用失败时抛出异常.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Flappy Bird");
        primaryStage.setResizable(false);
        Parent root = getContent();
        Scene main = new Scene(root, APP_WIDTH, APP_HEIGHT);
        setKeyFunctions(main);
        primaryStage.setScene(main);
        primaryStage.show();
        main.setOnMouseClicked(mouseEvent -> {
            double posx = mouseEvent.getX();
            double posy = mouseEvent.getY();
            give_stage=primaryStage;
            if(posx>=600&&posx<=800&&posy>=0&&posy<=30){
                exitgame();
            }
        });
        startGame();
    }

    /**
     * 这个方法主要执行游戏的退出,根据give_stage保存的stage对象进行一个回退.
     */
    public void exitgame() {
        Main exit = new Main();
        Main.GAME_SET=false;
        exit.start(give_stage);
    }

    /**
     * 这个方法里主要调用里很多其他方法,用于设置图形.如果玩家在一开始选择了双人模式,则会在root里多添加一个紫色小鸟.
     * @return 一个已经把所有图形添加上去了的root.
     */
    public Parent getContent() {
        root = new Group();
        Canvas canvas = new Canvas(APP_WIDTH, APP_HEIGHT);
        gc = canvas.getGraphicsContext2D();
        Canvas birdCanvas1 = new Canvas(APP_WIDTH, APP_HEIGHT);
        birdGC1= birdCanvas1.getGraphicsContext2D();
        Canvas birdCanvas2 = new Canvas(APP_WIDTH, APP_HEIGHT);
        birdGC2= birdCanvas2.getGraphicsContext2D();
        ImageView bg = Background.setBackground();
        pipes = new ArrayList<>();
        setBird();
        if(FlappyBird_Main.birdamount == 2){
            setBird2();
        }
        Label label = new Label();
        label.setLabels();
        setPipes();
        setFloor();
        if(FlappyBird_Main.birdamount!=2){
            root.getChildren().addAll(bg, canvas, birdCanvas1, scoreLabel, ready,exit);
        }
        else {
            root.getChildren().addAll(bg, canvas, birdCanvas1,birdCanvas2, scoreLabel, ready,exit);
        }
        return root;
    }

    /**
     * 在这个方法里new出一个黄色小鸟的实例,并且添加到bird1的动画实现对象里去.
     */
    public void setBird() {
        bird = new Bird();
        birdSprite1 = bird.getBird();
        birdSprite1.render(gc);
    }

    /**
     * 在这个方法里new出一个紫色小鸟的实例,并且添加到bird2的动画实现对象里去.
     */
    public void setBird2() {
        bird2 = new Bird2();
        birdSprite2 = bird2.getBird();
        birdSprite2.render(gc);
    }

    /**
     * 设置监听器,这个方法主要监听在游戏时玩家的输入来进行小鸟的跳跃.输入SPACEBAR黄色小鸟进行跳跃,反之则紫色小鸟进行跳跃.
     * @param scene 作为该界面里的Scene对象.
     */
    public void setKeyFunctions(Scene scene) {
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.SPACE) {
                setOnUserInput1();
            }
            if(e.getCode()==KeyCode.W){
                setOnUserInput2();
            }
        });
    }

    /**
     * 这个方法设置了当玩家1输入SPACEBAR时进行的相应判断,若游戏尚未开始,则移除ready图标开始游戏.
     * 若开始了则记录当前的时间,并且设置黄色小鸟的速度.<br>
     * 当GAME_OVER为真则游戏结束.
     */
    public void setOnUserInput1() {
        if (!HIT_PIPE1) {
            CLICKED = true;
            if (!GAME_START) {
                root.getChildren().remove(ready);
                GAME_START = true;
            } else {
                spaceClickA = System.currentTimeMillis();
                birdSprite1.setVelocity(0, -250);
            }
        }
        if (GAME_OVER) {
            startNewGame();
        }
    }

    /**
     * 这个方法设置了当玩家2输入W时进行的相应判断,若游戏尚未开始,则移除ready图标开始游戏.
     * 若开始了则记录当前的时间,并且设置紫色小鸟的速度.<br>
     * 当GAME_OVER为真则游戏结束.
     */
    public void setOnUserInput2() {
        if (!HIT_PIPE2) {
            CLICKED2 = true;
            if (!GAME_START) {
                root.getChildren().remove(ready);
                GAME_START = true;
            } else {
                spaceClickB = System.currentTimeMillis();
                birdSprite2.setVelocity(0, -250);
            }
        }
        if (GAME_OVER) {
            startNewGame();
        }
    }

    /**
     * 该方法主要是设置地板的图片以及位置,地板1的初始x轴位置为0;而地板2的初始x轴则是地板1的宽度.
     */
    public void setFloor() {
        firstFloor = new Sprite();
        firstFloor.resizeImage("/images/floor.png", 800, 140);
        firstFloor.setPositionXY(0, APP_HEIGHT - 100);
        firstFloor.setVelocity(-.4, 0);
        firstFloor.render(birdGC1);
        firstFloor.render(birdGC2);

        secondFloor = new Sprite();
        secondFloor.resizeImage("/images/floor.png", 800, 140);
        secondFloor.setPositionXY(firstFloor.getWidth(), APP_HEIGHT - 100);
        secondFloor.setVelocity(-.4, 0);
        secondFloor.render(gc);
    }

    /**
     * 这个方法是游戏执行的主要方法.包含了游里里的开始,结束以及循环.<br>
     * 在这个方法里创建了一个AnimationTimer并覆盖重写了handle方法.AnimationTimer就是负责游戏的主题循环,然后没1/60就会调用一次handle方法
     * 达到60hz的刷新率.在handle方法里以开始就判断是单人或双人模式.若是单人模式则在游戏失败时显示gameover,双人则在某一玩家失败时显示另一玩家胜利.
     * 当游戏结束时,则调用AnimationTimer.stop()方法,至此循环结束.
     */
    public void startGame() {
        if(FlappyBird_Main.birdamount==2) {
            startNanoTime = new flappyBird.FlappyBird_Game.LongValue(System.nanoTime());
            timer = new AnimationTimer() {
                public void handle(long now) {
                    elapsedTime = (now - startNanoTime.value) / 1000000000.0;
                    startNanoTime.value = now;
                    gc.clearRect(0, 0, APP_WIDTH, APP_HEIGHT);
                    birdGC1.clearRect(0, 0, APP_WIDTH, APP_HEIGHT);
                    birdGC2.clearRect(0, 0, APP_WIDTH, APP_HEIGHT);
                    moveFloor();
                    checkTimeBetweenSpaceHits1();
                    checkTimeBetweenSpaceHits2();
                    if (GAME_START) {
                        renderPipes();
                        checkPipeScroll();
                        updateTotalScore();

                        if (birdHitPipe1() || birdHitPipe2()) {
                            if (!root.getChildren().contains(player1)||!root.getChildren().contains(player2)) {
                                if(HIT_PIPE1){
                                    if(HIT_PIPE2){
                                        root.getChildren().add(draw);
                                        bird2_lose=true;
                                    }
                                    else{
                                        root.getChildren().add(player1);
                                    }
                                    bird1_lose=true;
                                }
                                else if(HIT_PIPE2){
                                    root.getChildren().add(player2);
                                    bird2_lose=true;
                                }
                            }
                            stopScroll();
                            motionTime += 0.18;
                            if (motionTime > 0.5) {
                                birdSprite1.addVelocity(-200, 400);
                                birdSprite1.render(gc);
                                birdSprite1.update(elapsedTime);
                                birdSprite2.addVelocity(-200, 400);
                                birdSprite2.render(gc);
                                birdSprite2.update(elapsedTime);
                                motionTime = 0;
                            }
                            GAME_OVER = true;
                            timer.stop();
                        }

                        if (birdHitFloor1() || birdHitFloor2()) {
                            if (!root.getChildren().contains(player1)||!root.getChildren().contains(player2)) {
                                if(HIT_FLOOR1){
                                    root.getChildren().add(player1);
                                    bird1_lose=true;
                                }
                                else if(HIT_FLOOR2){
                                    root.getChildren().add(player2);
                                    bird2_lose=true;
                                }
                            }
                            stopScroll();
                            GAME_OVER = true;
                            timer.stop();
                        }
                    }
                }
            };
            timer.start();
        }
        else{
            System.out.println("SSS");
            startNanoTime = new flappyBird.FlappyBird_Game.LongValue(System.nanoTime());
            timer = new AnimationTimer() {
                public void handle(long now) {
                    elapsedTime = (now - startNanoTime.value) / 1000000000.0;
                    startNanoTime.value = now;
                    gc.clearRect(0, 0, APP_WIDTH, APP_HEIGHT);
                    birdGC1.clearRect(0, 0, APP_WIDTH, APP_HEIGHT);
                    moveFloor();
                    checkTimeBetweenSpaceHits1();

                    if (GAME_START) {
                        renderPipes();
                        checkPipeScroll();
                        updateTotalScore();

                        if (birdHitPipe1()) {
                            root.getChildren().add(gameOver);
                            stopScroll();
                            motionTime += 0.18;
                            if (motionTime > 0.5) {
                                birdSprite1.addVelocity(-200, 400);
                                birdSprite1.render(gc);
                                birdSprite1.update(elapsedTime);
                                motionTime = 0;
                            }
                        }

                        if (birdHitFloor1()) {
                            if (!root.getChildren().contains(gameOver)) {
                                root.getChildren().add(gameOver);

                            }
                            timer.stop();
                            GAME_OVER = true;
                        }
                    }
                }
            };
            timer.start();
        }
    }

    /**
     * 当游戏结束时,便再调用这个方法进行游戏窗口的初始化.主要是清除掉窗口上的一些不必要的图案,以及再进行一些初始化.
     * 当全部都进行好了初始化后,再调用startgame方法,进行新一轮的游戏.
     */
    public void startNewGame() {
        if(FlappyBird_Main.birdamount==1) {
            root.getChildren().remove(gameOver);
        }
        root.getChildren().add(ready);
        if(FlappyBird_Main.birdamount==2){
            if(bird1_lose){
                if(bird2_lose){
                    root.getChildren().remove(draw);
                }
                else {
                    root.getChildren().remove(player1);
                    bird1_lose = false;
                }
            }
            else if(bird2_lose){
                root.getChildren().remove(player2);
                bird2_lose=false;
            }
        }
        pipes.clear();
        setFloor();
        setPipes();
        setBird();
        setBird2();
        resetVariables();
        startGame();
    }

    /**
     * 这个方法主要是初始化一些基本变量如分数,玩家点击判断,以及玩家是否撞上了水管等等.
     */
    public void resetVariables() {
        updateScoreLabel(0);
        TOTAL_SCORE = 0;
        HIT_PIPE1 = false;
        HIT_PIPE2 = false;
        HIT_FLOOR1=false;
        HIT_FLOOR2=false;
        CLICKED = false;
        CLICKED2 = false;
        GAME_OVER = false;
        GAME_START = false;
        bird1_lose=false;
        bird2_lose=false;
    }

    /**
     * 这个方法主要是判断玩家1是否在某一时刻进行了按键,若是的话则让黄色小鸟进行跳跃,否则则正常让黄色小鸟普通进行动画刷新.
     */
    public void checkTimeBetweenSpaceHits1() {
        long difference = (System.currentTimeMillis() - spaceClickA) / 300;

        if (difference >= .001 && CLICKED) {
            CLICKED = false;
            birdSprite1.addVelocity(0, 800);
            birdSprite1.render(birdGC1);
            birdSprite1.update(elapsedTime);
        } else {
            animateBird1();
        }
    }

    /**
     * 这个方法主要是判断玩家2是否在某一时刻进行了按键,若是的话则让黄色小鸟进行跳跃,否则则正常让紫色小鸟普通进行动画刷新.
     */
    public void checkTimeBetweenSpaceHits2() {
        long difference = (System.currentTimeMillis() - spaceClickB) / 300;

        if (difference >= .001 && CLICKED2) {
            CLICKED2 = false;
            birdSprite2.addVelocity(0, 800);
            birdSprite2.render(birdGC2);
            birdSprite2.update(elapsedTime);
        } else {
            animateBird2();
        }
    }

    /**
     * 这个方法主要是用来进行分数的增加以及刷新,以x轴来进行判断.若小鸟垂直于水管的x轴,这时便判断小鸟分数增加.
     */
    public void updateTotalScore() {
        if (!HIT_PIPE1) {
            for (Pipe pipe : pipes) {
                if (pipe.getPipe().getPositionX() == birdSprite1.getPositionX()) {
                    updateScoreLabel(++TOTAL_SCORE);
                    break;
                }
            }
        }
    }

    /**
     * 这个方法是用来创建分数图标的,根据实时分数进行字标的改变.
     * @param score 当前分数.
     */
    public void updateScoreLabel(int score) {
        scoreLabel.setText(Integer.toString(score));
    }

    /**
     * 这个方法主要是不断一直对地板进行位置的转转换,利用两个地板交替输出的方法来达到让地板能绵绵不绝地显示.
     */
    public void moveFloor() {
        firstFloor.render(gc);
        secondFloor.render(gc);
        firstFloor.update(5);
        secondFloor.update(5);
        if (firstFloor.getPositionX() <= -APP_WIDTH) {
            firstFloor.setPositionXY(secondFloor.getPositionX() + secondFloor.getWidth(),
                    APP_HEIGHT - 100);
        } else if (secondFloor.getPositionX() <= -APP_WIDTH) {
            secondFloor.setPositionXY(firstFloor.getPositionX() + firstFloor.getWidth(),
                    APP_HEIGHT - 100);
        }
    }

    /**
     * 这个方法是对黄色小鸟的动画刷新,并以motionTime这个变量记录刷新的次数.
     */
    public void animateBird1() {
        birdSprite1.render(birdGC1);
        birdSprite1.update(elapsedTime);

        motionTime += 0.18;
        if (motionTime > 0.5 && CLICKED) {
            Sprite temp = birdSprite1;
            birdSprite1 = bird.animate();
            birdSprite1.setPositionXY(temp.getPositionX(), temp.getPositionY());
            birdSprite1.setVelocity(temp.getVelocityX(), temp.getVelocityY());
            motionTime = 0;
        }
    }

    /**
     * 这个方法是对紫色小鸟的动画刷新,并以motionTime这个变量记录刷新的次数.
     */
    public void animateBird2(){
        birdSprite2.render(birdGC2);
        birdSprite2.update(elapsedTime);
        motionTime += 0.18;
        if (motionTime > 0.5 && CLICKED2) {
            Sprite temp = birdSprite2;
            birdSprite2 = bird2.animate();
            birdSprite2.setPositionXY(temp.getPositionX(), temp.getPositionY());
            birdSprite2.setVelocity(temp.getVelocityX(), temp.getVelocityY());
            motionTime = 0;
        }
    }

    /**
     * 这个方法主要是拿来判断黄色小鸟是否撞上水管了,若是的话则黄色小鸟游戏失败.
     * @return 若黄色小鸟撞上了,则返回true.
     */
    public boolean birdHitPipe1() {
        for (Pipe pipe : pipes) {
            if (!HIT_PIPE1 && birdSprite1.intersectsSprite(pipe.getPipe())) {
                HIT_PIPE1 = true;
                if(FlappyBird_Main.birdamount==2){
                    birdHitPipe2();
                }
                showHitEffect();
                return true;
            }
        }
        return false;
    }

    /**
     * 这个方法主要是拿来判断紫色小鸟是否撞上水管了,若是的话则紫色小鸟游戏失败.
     * @return 若紫色小鸟撞上了,则返回true.
     */
    public boolean birdHitPipe2() {
        for (Pipe pipe : pipes) {
            if (!HIT_PIPE2 && birdSprite2.intersectsSprite(pipe.getPipe())) {
                HIT_PIPE2 = true;
                showHitEffect();
                return true;
            }
        }
        return false;
    }

    /**
     * 这个方法是在当小鸟撞上水管时,将调用该方法进行一个撞击动画.
     */
    public void showHitEffect() {
        ParallelTransition parallelTransition = new ParallelTransition();
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.10), root);
        fadeTransition.setToValue(0);
        fadeTransition.setCycleCount(2);
        fadeTransition.setAutoReverse(true);
        parallelTransition.getChildren().add(fadeTransition);
        parallelTransition.play();
    }

    /**
     * 这个方法主要是判断黄色小鸟是否掉到了地上,是的话则游戏结束,黄色小鸟失败.
     * @return 若黄色小鸟掉到了地上,返回true.
     */
    public boolean birdHitFloor1() {
        if(birdSprite1.intersectsSprite(firstFloor) ||
                birdSprite1.intersectsSprite(secondFloor) ||
                birdSprite1.getPositionX() < 0){
            HIT_FLOOR1=true;
            return true;
        }
        return false;
    }

    /**
     * 这个方法主要是判断紫色小鸟是否掉到了地上,是的话则游戏结束,紫色小鸟失败.
     * @return 若紫色小鸟掉到了地上,返回true.
     */
    public boolean birdHitFloor2() {
        if(birdSprite2.intersectsSprite(firstFloor) ||
                birdSprite2.intersectsSprite(secondFloor) ||
                birdSprite2.getPositionX() < 0){
            HIT_FLOOR2=true;
            return true;
        }
        return false;
    }

    /**
     * 这个方法在当游戏结束时,则停止让所有背景板移动,包括地板和水管.
     */
    public void stopScroll() {
        for (Pipe pipe : pipes) {
            pipe.getPipe().setVelocity(0, 0);
        }
        firstFloor.setVelocity(0, 0);
        secondFloor.setVelocity(0, 0);
    }

    /**
     * 这个方法是在刷新背景时负责判断水管的为位置,若水管的x轴位于处于窗口半径处的左边,则设置水管.
     */
    public void checkPipeScroll() {
        if (pipes.size() > 0) {
            Sprite p = pipes.get(pipes.size() - 1).getPipe();
            if (p.getPositionX() == APP_WIDTH  / 2 - 80) {
                setPipes();
            } else if (p.getPositionX() <= -p.getWidth()) {
                pipes.remove(0);
                pipes.remove(0);
            }
        }
    }

    /**
     * 这个方法就是负责设置水管.先通过随机数获得上方水管的高度,再用减出了中间空隙的高度减去随机数高度.
     * 这时就能获得一个随机生成的水管了.
     */
    public void setPipes() {
        int height = (int) (Math.random() * (410 - 25)) + 25;

        Pipe pipe = new Pipe(true, height);
        Pipe downPipe = new Pipe(false, 425 - height);

        pipe.getPipe().setVelocity(-.4, 0);
        downPipe.getPipe().setVelocity(-.4, 0);

        pipe.getPipe().render(gc);
        downPipe.getPipe().render(gc);

        pipes.addAll(Arrays.asList(pipe, downPipe));
    }

    /**
     * 这个方法负责更新水管的动画效果.
     */
    public void renderPipes() {
        for (Pipe pipe : pipes) {
            Sprite p = pipe.getPipe();
            p.render(gc);
            p.update(5);
        }
    }

    /**
     * 这个方法负责保存保存时间.
     */
    public static class LongValue {
        public long value;
        public LongValue(long i) {
            this.value = i;
        }
    }
}