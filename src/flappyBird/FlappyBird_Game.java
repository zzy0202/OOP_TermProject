package flappyBird;
import main.Main;
import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.Arrays;



public class FlappyBird_Game extends Application {
    public Boolean bird1_lose=false,bird2_lose=false;
    public static int APP_HEIGHT = 700;
    public static int APP_WIDTH = 800;
    private int TOTAL_SCORE = 0;
    private long spaceClickA,spaceClickB;
    private double motionTime, elapsedTime;
    private boolean CLICKED,CLICKED2, GAME_START, HIT_PIPE1,HIT_PIPE2, GAME_OVER,HIT_FLOOR1,HIT_FLOOR2;
    private flappyBird.FlappyBird_Game.LongValue startNanoTime;
    private Sprite firstFloor, secondFloor, birdSprite1,birdSprite2;
    private Bird bird;
    private Bird2 bird2;
    private Text scoreLabel;
    private GraphicsContext gc, birdGC1,birdGC2,gc2;
    private AnimationTimer timer;
    private ArrayList<Pipe> pipes;
    private Sound coin, hit, wing, swoosh, die;
    private ImageView gameOver, startGame,exit;
    public static Text player1,player2;
    private Group root;
    private long gamePauseTime;
    public static AnimationTimer recordedAnimationTimer;
    public Stage give_stage;;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Flappy Bird");
        primaryStage.setResizable(false);
        Parent root = getContent();
        Scene main = new Scene(root, APP_WIDTH, APP_HEIGHT);
        setKeyFunctions(main);
        primaryStage.setScene(main);
        primaryStage.show();
        main.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                double posx = mouseEvent.getX();
                double posy = mouseEvent.getY();
                give_stage=primaryStage;
                if(posx>=600&&posx<=800&&posy>=0&&posy<=30){
                    exitgame();
                }
            }
        });
        startGame();
    }

    private void exitgame() {
        Main exit = new Main();
        Main.GAME_SET=false;
        exit.start(give_stage);
    }

    public Parent getContent() {
        root = new Group();
        Canvas canvas = new Canvas(APP_WIDTH, APP_HEIGHT);
        gc = canvas.getGraphicsContext2D();
        gc2 = canvas.getGraphicsContext2D();
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
        setLabels();
        setPipes();
        setFloor();
        if(FlappyBird_Main.birdamount!=2){
            root.getChildren().addAll(bg, canvas, birdCanvas1, scoreLabel, startGame,exit);
        }
        else if(FlappyBird_Main.birdamount==2){
            root.getChildren().addAll(bg, canvas, birdCanvas1,birdCanvas2, scoreLabel, startGame,exit);
        }
        return root;
    }

    private void setBird() {
        bird = new Bird();
        birdSprite1 = bird.getBird();
        birdSprite1.render(gc);
    }

    private void setBird2() {
        bird2 = new Bird2();
        birdSprite2 = bird2.getBird();
        birdSprite2.render(gc);
    }

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

    public void setOnUserInput1() {
        if (!HIT_PIPE1) {
            CLICKED = true;
            if (!GAME_START) {
                root.getChildren().remove(startGame);
                //swoosh.playClip();
                GAME_START = true;
            } else {
                //wing.playClip();
                spaceClickA = System.currentTimeMillis();
                birdSprite1.setVelocity(0, -250);
            }
        }
        if (GAME_OVER==true) {
            startNewGame();
        }
    }

    private void setOnUserInput2() {
        if (!HIT_PIPE2) {
            CLICKED2 = true;
            if (!GAME_START) {
                root.getChildren().remove(startGame);
                GAME_START = true;
            } else {
                spaceClickB = System.currentTimeMillis();
                birdSprite2.setVelocity(0, -250);
            }
        }
        if (GAME_OVER==true) {
            startNewGame();
        }
    }

    public void setLabels() {
        scoreLabel = new Text("0");
        scoreLabel.setFont(Font.font("Courier", FontWeight.EXTRA_BOLD, 50));
        scoreLabel.setStroke(Color.BLACK);
        scoreLabel.setFill(Color.WHITE);
        scoreLabel.setLayoutX(20);
        scoreLabel.setLayoutY(40);

        gameOver = new ImageView(new Image(getClass().getResource("/images/game_over.png").toExternalForm()));
        gameOver.setFitWidth(178);
        gameOver.setFitHeight(50);
        gameOver.setLayoutX(311);
        gameOver.setLayoutY(275);

        player1 = new Text("Purple Bird Won!");
        player1.setLayoutX(311);
        player1.setLayoutY(275);
        player1.setFont(Font.font("Courier", FontWeight.EXTRA_BOLD, 25));
        player1.setFill(Color.WHITE);
        player1.setStroke(Color.BLACK);

        player2 = new Text("Yellow Bird Won!");
        player2.setLayoutX(311);
        player2.setLayoutY(275);
        player2.setFont(Font.font("Courier", FontWeight.EXTRA_BOLD, 25));
        player2.setFill(Color.WHITE);
        player2.setStroke(Color.BLACK);

        startGame = new ImageView(new Image(getClass().getResource("/images/ready.png").toExternalForm()));
        startGame.setFitWidth(178);
        startGame.setFitHeight(50);
        startGame.setLayoutX(311);
        startGame.setLayoutY(275);

        exit = new ImageView(new Image(getClass().getResource("/images/exit.jpg").toExternalForm()));
        exit.setFitWidth(200);
        exit.setFitHeight(30);
        exit.setLayoutX(600);
        exit.setLayoutY(0);
    }


    public void setFloor() {
        firstFloor = new Sprite();
        firstFloor.resizeImage("/images/floor.png", 800, 140);
        firstFloor.setPositionXY(0, APP_HEIGHT - 100);
        firstFloor.setVelocity(-.4, 0);
        firstFloor.render(birdGC1);


        secondFloor = new Sprite();
        secondFloor.resizeImage("/images/floor.png", 800, 140);
        secondFloor.setPositionXY(firstFloor.getWidth(), APP_HEIGHT - 100);
        secondFloor.setVelocity(-.4, 0);
        secondFloor.render(gc);
    }

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
                    moveFloor();                    //让地上会跟随小鸟移动
                    checkTimeBetweenSpaceHits1();    //判断按键
                    checkTimeBetweenSpaceHits2();
                    if (GAME_START) {
                        renderPipes();  //显示出管子
                        checkPipeScroll();  //让管子随着移动的地板消失
                        updateTotalScore();

                        if (birdHitPipe1() || birdHitPipe2()) {
                            if (!root.getChildren().contains(player1)||!root.getChildren().contains(player2)) {
                                if(HIT_PIPE1){
                                    root.getChildren().add(player1);
                                    bird1_lose=true;
                                }
                                else if(HIT_PIPE2){
                                    root.getChildren().add(player2);
                                    bird2_lose=true;
                                }
                            }
                            stopScroll();
                            playHitSound();
                            motionTime += 0.18;
                            if (motionTime > 1) {
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
                            playHitSound();
                            GAME_OVER = true;
                            timer.stop();
                        }
                    }
                }
            };
            timer.start();
        }
        else{
            startNanoTime = new flappyBird.FlappyBird_Game.LongValue(System.nanoTime());
            timer = new AnimationTimer() {
                public void handle(long now) {
                    elapsedTime = (now - startNanoTime.value) / 1000000000.0;
                    startNanoTime.value = now;
                    gc.clearRect(0, 0, APP_WIDTH, APP_HEIGHT);
                    birdGC1.clearRect(0, 0, APP_WIDTH, APP_HEIGHT);
                    moveFloor();                    //让地上会跟随小鸟移动
                    checkTimeBetweenSpaceHits1();    //判断按键

                    if (GAME_START) {
                        renderPipes();
                        checkPipeScroll();
                        updateTotalScore();

                        if (birdHitPipe1()) {
                            root.getChildren().add(gameOver);
                            stopScroll();
                            playHitSound();
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
                                playHitSound();
                                showHitEffect();
                            }
                            timer.stop();
                            GAME_OVER = true;
                            //die.playClip();
                        }
                    }
                }
            };
            timer.start();
        }
    }

    public void startNewGame() {
        if(FlappyBird_Main.birdamount==1) {
            root.getChildren().remove(gameOver);
        }
        root.getChildren().add(startGame);
        if(FlappyBird_Main.birdamount==2){
            if(bird1_lose){
                root.getChildren().remove(player1);
                bird1_lose=false;
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

    private void resetVariables() {
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

    private void checkTimeBetweenSpaceHits1() {
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

    private void checkTimeBetweenSpaceHits2() {
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

    private void updateTotalScore() {
        if (!HIT_PIPE1) {
            for (Pipe pipe : pipes) {
                if (pipe.getPipe().getPositionX() == birdSprite1.getPositionX()) {
                    updateScoreLabel(++TOTAL_SCORE);
                    //coin.playClip();
                    break;
                }
            }
        }
    }

    private void updateScoreLabel(int score) {
        scoreLabel.setText(Integer.toString(score));
    }

    private void moveFloor() {
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

    private boolean birdHitPipe1() {
        for (Pipe pipe : pipes) {
            if (!HIT_PIPE1 && birdSprite1.intersectsSprite(pipe.getPipe())) {
                HIT_PIPE1 = true;
                showHitEffect();
                return true;
            }
        }
        return false;
    }

    private boolean birdHitPipe2() {
        for (Pipe pipe : pipes) {
            if (!HIT_PIPE2 && birdSprite2.intersectsSprite(pipe.getPipe())) {
                HIT_PIPE2 = true;
                showHitEffect();
                return true;
            }
        }
        return false;
    }

    private void showHitEffect() {
        ParallelTransition parallelTransition = new ParallelTransition();
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.10), root);
        fadeTransition.setToValue(0);
        fadeTransition.setCycleCount(2);
        fadeTransition.setAutoReverse(true);
        parallelTransition.getChildren().add(fadeTransition);
        parallelTransition.play();
    }

    private void playHitSound() {
//        hit.playClip();
    }

    private boolean birdHitFloor1() {
        if(birdSprite1.intersectsSprite(firstFloor) ||
                birdSprite1.intersectsSprite(secondFloor) ||
                birdSprite1.getPositionX() < 0){
            HIT_FLOOR1=true;
            return true;
        }
        return false;
    }

    private boolean birdHitFloor2() {
        if(birdSprite2.intersectsSprite(firstFloor) ||
                birdSprite2.intersectsSprite(secondFloor) ||
                birdSprite2.getPositionX() < 0){
            HIT_FLOOR2=true;
            return true;
        }
        return false;
    }

    private void stopScroll() {
        for (Pipe pipe : pipes) {
            pipe.getPipe().setVelocity(0, 0);
        }
        firstFloor.setVelocity(0, 0);
        secondFloor.setVelocity(0, 0);
    }

    private void checkPipeScroll() {
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

    private void setPipes() {
        int height = getRandomPipeHeight();

        Pipe pipe = new Pipe(true, height);
        Pipe downPipe = new Pipe(false, 425 - height);

        pipe.getPipe().setVelocity(-.4, 0);
        downPipe.getPipe().setVelocity(-.4, 0);

        pipe.getPipe().render(gc);
        downPipe.getPipe().render(gc);

        pipes.addAll(Arrays.asList(pipe, downPipe));
    }

    private int getRandomPipeHeight() {
        return (int) (Math.random() * (410 - 25)) + 25;
    }

    private void renderPipes() {
        for (Pipe pipe : pipes) {
            Sprite p = pipe.getPipe();
            p.render(gc);
            p.update(5);
        }
    }

    public static class LongValue {
        public long value;
        public LongValue(long i) {
            this.value = i;
        }
    }
}