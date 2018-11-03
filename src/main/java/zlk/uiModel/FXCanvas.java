package zlk.uiModel;

import javafx.scene.canvas.Canvas;
import zlk.enums.CanvasState;

public class FXCanvas {
    private static FXCanvas instance = new FXCanvas();
    /**
     * 画布
     */
    private Canvas canvas;
    /**
     * 画布状态
     */
    private CanvasState canvasState;
    /**
     * 当前最后一笔是否为画笔，否则为标注框
     */
    private boolean isBrush = true;

    private FXCanvas(){}

    public static FXCanvas getInstance(){
        return instance;
    }


    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    public CanvasState getCanvasState() {
        return canvasState;
    }

    public void setCanvasState(CanvasState canvasState) {
        this.canvasState = canvasState;
    }

    public boolean isBrush() {
        return isBrush;
    }

    public void setBrush(boolean brush) {
        isBrush = brush;
    }
}
