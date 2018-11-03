package zlk.uiModel;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import zlk.Main;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * 矩形识别框
 */
public class FXRecTag {
    /**
     * 左上点
     */
    private FXPoint startPoint;
    /**
     * 右下点
     */
    private FXPoint endPoint;
    /**
     * 识别出来的结果
     */
    private String description;

    public FXRecTag(){this.description = "";}

    public FXRecTag(FXPoint fxPoint){
        this.startPoint=fxPoint;
    }

    public FXRecTag(FXPoint startPoint , FXPoint endPoint){
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    /**
     * 清除鼠标拖拉时的上一个矩形
     * @param graphicsContext
     */
    public void clearLastRecTag(GraphicsContext graphicsContext){
        Color color = (Color)graphicsContext.getStroke();
        double lineWidth = graphicsContext.getLineWidth();
        //设置画笔
        graphicsContext.setStroke(Color.WHITE);
        graphicsContext.setLineWidth(lineWidth+2);
        double min_X = Math.min(startPoint.getX(),endPoint.getX());
        double min_Y = Math.min(startPoint.getY(),endPoint.getY());
        double max_X = Math.max(startPoint.getX(),endPoint.getX());
        double max_Y = Math.max(startPoint.getY(),endPoint.getY());
        /**
         * 清除上一个矩形
         * 存在矩形右边和下边清除不完全的情况，选择用白笔描边
         */
        graphicsContext.moveTo(max_X,min_Y);
        graphicsContext.lineTo(max_X,max_Y);
        graphicsContext.lineTo(min_X,max_Y);
        graphicsContext.stroke();
        graphicsContext.beginPath();
        graphicsContext.clearRect(min_X,min_Y,max_X-min_X,max_Y-min_Y);

        //恢复画笔
        graphicsContext.setLineWidth(lineWidth);
        graphicsContext.setStroke(color);
    }

    /**
     * 画矩形识别框
     * @param graphicsContext
     */
    public void drawRecTag(GraphicsContext graphicsContext){
        Color color = (Color)graphicsContext.getStroke();
        double lineWidth = graphicsContext.getLineWidth();
        //定义画笔
        graphicsContext.setLineWidth(1);
        graphicsContext.setStroke(Color.RED);
        //绘制矩形
        graphicsContext.strokeRect(Math.min(startPoint.getX(),endPoint.getX()),Math.min(startPoint.getY(),endPoint.getY()),Math.abs(endPoint.getX()-startPoint.getX()),Math.abs(endPoint.getY()-startPoint.getY()));
        graphicsContext.beginPath();
        //恢复画笔
        graphicsContext.setLineWidth(lineWidth);
        graphicsContext.setStroke(color);
    }

    /**
     * 矩形框添加识别
     */
    public void addDescription(String description , GraphicsContext graphicsContext){
        this.description = description;
        double lineWidth = graphicsContext.getLineWidth();
        Color color = (Color) graphicsContext.getStroke();
        graphicsContext.setLineWidth(0.5);
        graphicsContext.setStroke(Color.BLUE);
        graphicsContext.setFont(new Font("SimSun",15));
        graphicsContext.strokeText(description,0.45*(startPoint.getX()+endPoint.getX()),Math.min(startPoint.getY(),endPoint.getY())+15);
        graphicsContext.setLineWidth(lineWidth);
        graphicsContext.setStroke(color);
    }

    /**
     * 将矩形框内的图保存在/resources/img中
     */
    public File savePNGImage(Canvas canvas){
        WritableImage writableImage = new WritableImage(724,504);
        canvas.snapshot(null,writableImage);

        try{
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(writableImage,null);
//            InputStream inputStream = this.getClass().getResourceAsStream("/img/test.png");
//            OutputStream outputStream = parse(inputStream);
            File file = new File(this.getClass().getResource("/img/test.png").getPath());

            bufferedImage = bufferedImage.getSubimage((int)Math.min(startPoint.getX(),endPoint.getX())+5,(int)Math.min(startPoint.getY()+5,endPoint.getY()),(int)Math.abs(endPoint.getX()-startPoint.getX())-5,(int)Math.abs(endPoint.getY()-startPoint.getY())-5);

            ImageIO.write(bufferedImage,"png",file);
            return new File(this.getClass().getResource("/img/test.png").getPath());
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("失败");
            return null;
        }
    }

    private ByteArrayOutputStream parse(InputStream in) throws Exception
    {
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        int ch;
        while ((ch = in.read()) != -1) {
            swapStream.write(ch);
        }
            return swapStream;
        }

    public FXPoint getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(FXPoint startPoint) {
        this.startPoint = startPoint;
    }

    public FXPoint getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(FXPoint endPoint) {
        this.endPoint = endPoint;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
