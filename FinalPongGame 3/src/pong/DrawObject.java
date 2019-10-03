
package pong;

import javafx.scene.canvas.GraphicsContext;


public abstract class DrawObject {

    private final GraphicsContext graphicsContext;

    public DrawObject(GraphicsContext graphicsContext) {
        this.graphicsContext = graphicsContext;
    }

    public abstract void draw(double width, double height);

    protected final GraphicsContext getGraphicsContext() {
        return graphicsContext;
    }

}
