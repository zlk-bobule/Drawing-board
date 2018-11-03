package zlk.uiModel;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class FXImage{

    /**
     * 该图保存的笔画
     */
    private ArrayList<FXBrush> brushes = new ArrayList<FXBrush>();
    /**
     * 该图保存的标注框
     */
    private ArrayList<FXRecTag> recTags = new ArrayList<FXRecTag>();

    public FXImage(){}

    /**
     * 撤销上一笔画
     */
    public void revokeBrush(GraphicsContext graphicsContext){
        if(brushes.size()==0){
            return;
        }
        FXBrush brush = brushes.get(brushes.size()-1);
        brush.clearBrush(graphicsContext);

        brushes.remove(brush);
    }

    /**
     * 撤销上一个标注框
     * @param graphicsContext
     */
    public void revokeRecTag(GraphicsContext graphicsContext){
        if(recTags.size()==0){
            return;
        }
        //清除矩形
        FXRecTag fxRecTag = recTags.get(recTags.size()-1);
        fxRecTag.clearLastRecTag(graphicsContext);

        recTags.remove(fxRecTag);
        redrawAllBrush(graphicsContext);
    }

    /**
     * 清空屏幕所有的笔画和矩形识别框
     * @param graphicsContext
     */
    public void clearImage(GraphicsContext graphicsContext){
        for(FXBrush brush : brushes){
            brush.clearBrush(graphicsContext);
        }
        for(FXRecTag fxRecTag : recTags){
            fxRecTag.clearLastRecTag(graphicsContext);
        }
        graphicsContext.beginPath();
    }

    /**
     * 重绘所有笔画
     */
    public void redrawAllBrush(GraphicsContext graphicsContext){
        for(FXBrush brush : brushes){
            brush.drawBrush(graphicsContext);
        }
    }

    /**
     * 重绘所有矩形识别框
     */
    public void redrawAllRecTag(GraphicsContext graphicsContext){
        for(FXRecTag fxRecTag : recTags){
            fxRecTag.drawRecTag(graphicsContext);
            fxRecTag.addDescription(fxRecTag.getDescription(),graphicsContext);
        }
    }

    public ArrayList<FXBrush> getBrushes() {
        return brushes;
    }

    public void setBrushes(ArrayList<FXBrush> brushes) {
        this.brushes = brushes;
    }

    public ArrayList<FXRecTag> getRecTags() {
        return recTags;
    }

    public void setRecTags(ArrayList<FXRecTag> recTags) {
        this.recTags = recTags;
    }
}
