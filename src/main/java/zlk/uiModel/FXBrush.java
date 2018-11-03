package zlk.uiModel;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import lombok.Data;
import zlk.utils.FXColor;

import java.util.ArrayList;

/**
 * 笔画
 */
@Data
public class FXBrush{

    /**
     * 一笔中所有点的坐标集合
     */
    private ArrayList<FXPoint> fxPoints = new ArrayList<FXPoint>();
    /**
     * 笔画的颜色
     */
//    @JsonFormat(pattern = "")
    private FXColor fxColor;

    public FXBrush(){
    }

    public FXBrush(ArrayList<FXPoint> points , GraphicsContext graphicsContext){
        this.fxPoints.addAll(points);
        this.fxColor = new FXColor((Color)graphicsContext.getStroke());
//        this.fxColor = (Color)graphicsContext.getStroke();
    }

    /**
     * 增加笔画点
     * @param points
     */
    public void addFXPoints(FXPoint points){
        fxPoints.add(points);
    }

    /**
     * 清除笔画的所有点
     */
    public void clearAllPoints(){
        fxPoints.clear();
    }

    /**
     * 在图中清除笔画（初设定为涂成白色）
     */
    public void clearBrush(GraphicsContext graphicsContext){
        Color color = (Color)graphicsContext.getStroke();
        double lineWidth = graphicsContext.getLineWidth();
        //设置画笔
        graphicsContext.setStroke(Color.WHITE);
        graphicsContext.setLineWidth(lineWidth+2);
        //涂点
//        ArrayList<FXPoint> fxPoints = brush.getFxPoints();
        for(FXPoint fxPoint : fxPoints){
            graphicsContext.lineTo(fxPoint.getX(),fxPoint.getY());
        }
        graphicsContext.stroke();
        //恢复画笔
        graphicsContext.setStroke(color);
        graphicsContext.setLineWidth(lineWidth);
    }

    /**
     * 绘制笔画
     * @param graphicsContext
     */
    public void drawBrush(GraphicsContext graphicsContext){
        double lineWidth = graphicsContext.getLineWidth();
        Color color = (Color)graphicsContext.getStroke();
        graphicsContext.setStroke(fxColor.turnToColor());
        graphicsContext.setLineWidth(4.0);
        for(FXPoint fxPoint : fxPoints){
            graphicsContext.lineTo(fxPoint.getX(),fxPoint.getY());
        }
        graphicsContext.stroke();
        graphicsContext.beginPath();
        //恢复画笔
        graphicsContext.setStroke(color);
        graphicsContext.setLineWidth(lineWidth);
    }


}
