

package pong;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;


public class Main extends Application {

    public enum PLAYER {
        ONE, TWO, 
    }
    public PLAYER player;
    private double width = 400;
    private double height = 200;
    private int count = 0;

    private Paddle paddleOne;
    private Paddle paddleTwo;
    private Ball ball;
    private Point pointOne;
    private Point pointTwo;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane root = new Pane();
        StackPane holder = new StackPane();
        holder.setStyle("-fx-background-color: pink");

        Canvas canvas = new Canvas(width, height);

        holder.getChildren().add(canvas);
        root.getChildren().add(holder);

        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        start(graphicsContext);

        Scene scene = new Scene(root);

        scene.widthProperty().addListener((observable, oldValue, newValue) -> {
            width = newValue.doubleValue();
        });
        scene.heightProperty().addListener((observable, oldValue, newValue) -> {
            height = newValue.doubleValue();
        });
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if(key.getCode()==KeyCode.UP) {
             paddleTwo.moveUp();
            }
           if(key.getCode()==KeyCode.DOWN) {
            paddleTwo.moveDown();
          }
           if(key.getCode()==KeyCode.W) {
            paddleOne.moveUp();
        }
           if(key.getCode()==KeyCode.S) {
      	     paddleOne.moveDown();
      }
      }); scene.addEventHandler(KeyEvent.KEY_RELEASED, (key) -> {
    	  if(key.getCode()==KeyCode.UP) {
              paddleTwo.stopMoving();
             }
            if(key.getCode()==KeyCode.DOWN) {
             paddleTwo.stopMoving();
           }
            if(key.getCode()==KeyCode.W) {
             paddleOne.stopMoving();
         }
            if(key.getCode()==KeyCode.S) {
       	     paddleOne.stopMoving();
       }
      });
        

                   
        primaryStage.setTitle("Austin Java Pong Game");
        primaryStage.setScene(scene);
        primaryStage.show();
        draw(canvas, graphicsContext);
    }

    private void start(GraphicsContext graphicsContext) {
        paddleOne = new Paddle(graphicsContext, PLAYER.ONE);
        paddleTwo = new Paddle(graphicsContext, PLAYER.TWO);
        ball = new Ball(graphicsContext, paddleOne, paddleTwo, new Ball.OnPointListener() {

            @Override
            public void playerOne() {
                pointOne.point++;
                reset();
            }

            @Override
            public void playerTwo() {
                pointTwo.point++;
                reset();
            }

            private void reset() {
                new Thread(() -> {
                    try {
                        Thread.sleep(1000);
                        ball.reset();
                        paddleOne.reset();
                        paddleTwo.reset();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        });
        pointOne = new Point(graphicsContext, PLAYER.ONE);
        pointTwo = new Point(graphicsContext, PLAYER.TWO);
    }

    private void draw(Canvas canvas, GraphicsContext graphicsContext) {
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                    count = 0;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }).start();

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(17), event -> {
            graphicsContext.clearRect(0, 0, width, height);

            pointOne.draw(width, height);
            pointTwo.draw(width, height);
            ball.draw(width, height);
            paddleOne.draw(width, height);
            paddleTwo.draw(width, height);

            canvas.setHeight(height);
            canvas.setWidth(width);
            count++;
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

}
