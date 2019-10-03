
package pong;

import com.sun.javafx.geom.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class Ball extends DrawObject {

    private final Paddle paddleOne;
    private final Paddle paddleTwo;
    private final OnPointListener onPointListener;

    private Vec2d position;
    private Vec2d vel;
    private int rad;

    private boolean gameblouses;

    public Ball(GraphicsContext graphicsContext, Paddle paddleOne, Paddle paddleTwo, OnPointListener onPointListener) {
        super(graphicsContext);
        this.paddleOne = paddleOne;
        this.paddleTwo = paddleTwo;
        this.onPointListener = onPointListener;
        rad = 10;
    }

    public void draw(double width, double height) {
        if (gameblouses) return;
        if (position == null) position = new Vec2d(width / 2, height / 2);
        if (vel == null) vel = new Vec2d(-4, 0);

        position.x += vel.x;
        position.y += vel.y;
        if (vel.x < 0
                && position.x - rad < paddleOne.getWidth()
                && touchedPaddle(paddleOne)) {
            vel.x *= -1;
            vel.y += getVelocityY(paddleOne);
            position.x = paddleOne.getWidth() + rad * 2;
        }

        if (vel.x > 0
                && position.x + rad > width - paddleTwo.getWidth()
                && touchedPaddle(paddleTwo)) {
            vel.x *= -1;
            vel.y += getVelocityY(paddleTwo);
            position.x = width - paddleTwo.getWidth();
        }

        if (touchedWall(height)) vel.y *= -1;

        if (position.x >= 0 && position.x <= width) {
            getGraphicsContext().setFill(Color.BLUE);
            getGraphicsContext().fillOval(position.x - rad, position.y - rad, rad, rad);
        } else {
            gameblouses = true;
            if (vel.x < 0) onPointListener.playerTwo();
            else onPointListener.playerOne();
        }
    }

    public void reset() {
        position = null;
        vel.y = 0;
        gameblouses = false;
    }

    private boolean touchedWall(double wallHeight) {
        return position.y - rad <= 0 || position.y + rad >= wallHeight;
    }

    private boolean touchedPaddle(Paddle paddle) {
        return position.y > paddle.getHeight() && position.y < paddle.getHeight() + paddle.getLength();
    }

    private double getVelocityY(Paddle paddle) {
        Double[][] hitboxes = new Double[5][2];
        for (int i = 0; i < hitboxes.length; i++) {
            hitboxes[i][0] = (paddle.getLength() / hitboxes.length) * i + paddle.getHeight();
            hitboxes[i][1] = (paddle.getLength() / hitboxes.length) * (i + 1) + paddle.getHeight();
            if (i == 0) hitboxes[i][0] -= rad;
            else if (i == hitboxes.length - 1) hitboxes[i][1] += rad;
        }

        for (int i = 0; i < hitboxes.length; i++) {
            if (position.y > hitboxes[i][0] && position.y < hitboxes[i][1])
               
            	
            	switch (i) {
                    case 0:
                        return -4;
                    case 4:
                        return 4;
                    case 1:
                        return -2;
                    case 3:
                        return 2;
                }
        }
        return 0;
    }

    public interface OnPointListener {
        void playerOne();

        void playerTwo();
    }

}