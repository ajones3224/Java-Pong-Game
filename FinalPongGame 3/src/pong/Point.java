
package pong;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import pong.Main.PLAYER;


public class Point extends DrawObject {

    private final Main.PLAYER player;
    protected int point = 0;

    public Point(GraphicsContext graphicsContext, Main.PLAYER player) {
        super(graphicsContext);
        this.player = player;
    }

    @Override
    public void draw(double width, double height) {
        double w = 0;
        
        if(player == PLAYER.ONE) {
     	  w = width/4;
        }else {
          w = width - width / 4;
        }
          
          
        getGraphicsContext().setFill(Color.WHITE);
        getGraphicsContext().fillText(String.valueOf(point), w, 40);
    }

}
