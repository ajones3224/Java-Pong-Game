
package pong;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import pong.Main.PLAYER;


public class Paddle extends DrawObject {

    private final Main.PLAYER player;

    private double height;
    private double balance;
    private double length;
    private double density;

    private boolean moveUp;
    private boolean moveDown;

    public Paddle(GraphicsContext graphicsContext, Main.PLAYER player) {
        super(graphicsContext);
        this.player = player;
        balance = 0;
    }

    @Override
    public void draw(double x, double y) {

        length = y / 4;

        double position = balance;

        if (position < 0) position = -position;
        if (position + length / 2 <= y / 2) {
            if (moveDown) balance += y / 100;
            if (moveUp) balance -= y / 100;
        } else {
            if (balance <= 0) balance = -(y / 2 - length / 2);
            else balance = y / 2 - length / 2;
        }

        double w = 0;
        double h = y / 2 - length / 2 + balance;

        density = x / 80;
       if(player == PLAYER.ONE) {
    	   w = 10;
       }else {
    	   w = x-10- density;
       }
         
       
        getGraphicsContext().setFill(Color.BLACK);
        getGraphicsContext().fillRect(w, (height = h), density, length);
    }

    public void moveUp() {
        moveUp = true;
    }

    public void moveDown() {
        moveDown = true;
    }

    public void stopMoving() {
        moveUp = false;
        moveDown = false;
    }

    public void reset() {
        balance = 0;
    }

    public double getHeight() {
        return height;
    }

    public double getLength() {
        return length;
    }

    public double getWidth() {
        return density + 10;
    }

}
