package zlk.viewcontroller;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import zlk.blservice.DistinguishBlService;
import zlk.blservice.blserviceImpl.DistinguishBlServiceImpl;
import zlk.dataservice.ImageDataService;
import zlk.dataservice.dataserviceImpl.ImageDataServiceImpl;
import zlk.enums.CanvasState;
import zlk.uiModel.*;

import java.io.File;
import java.util.ArrayList;

public class DrawPaneController {

    @FXML
    private Canvas canvas;

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private Button curveButton;

    @FXML
    private Button distinguishButton;

    private FXImage fxImage;
    private FXCanvas fxCanvas;
    private FXBrush fxBrush;
    private FXRecTag fxRecTag;

    private GraphicsContext graphicsContext;
    /**
     * 保存的笔画
     */
    private ArrayList<FXBrush> fxBrushes;
    /**
     * 保存的标注框
     */
    private ArrayList<FXRecTag> fxRecTags;

    /**
     * 画笔颜色
     */
    private Color brushColor;

    private DistinguishBlService distinguishBlService;
    private ImageDataService imageDataService;

    public DrawPaneController(){}

    @FXML
    public void initialize() {
        fxImage = new FXImage();
        fxCanvas = FXCanvas.getInstance();
        fxCanvas.setCanvas(canvas);
        fxBrush = new FXBrush();
        fxRecTag = new FXRecTag();
        fxBrushes = fxImage.getBrushes();
        fxRecTags = fxImage.getRecTags();

        brushColor = Color.BLACK;
        colorPicker.setValue(brushColor);

        /**
         * 初始化画笔
         */
        graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setStroke(brushColor);
        graphicsContext.setLineWidth(4.0);

        distinguishBlService = new DistinguishBlServiceImpl();
        imageDataService = new ImageDataServiceImpl();
    }

    /**
     * 曲线画画
     */
    @FXML
    void drawByCurve(){
        fxCanvas.setCanvasState(CanvasState.DRAW);
        fxCanvas.setBrush(true);
    }

    /**
     * 识别
     */
    @FXML
    void distinguish(){
        fxCanvas.setCanvasState(CanvasState.DISTINGUISH);
        fxCanvas.setBrush(false);
    }

    /**
     * 画布鼠标拖拽
     * @param event
     */
    @FXML
    void canvasMouseDragged(MouseEvent event){
        if(fxCanvas.getCanvasState()==CanvasState.DRAW){     //绘画状态
            graphicsContext.lineTo(event.getX(),event.getY());
            graphicsContext.stroke();
            fxBrush.addFXPoints(new FXPoint(event.getX(),event.getY()));
        }else if(fxCanvas.getCanvasState()==CanvasState.DISTINGUISH){  //识别框状态
            fxRecTag.clearLastRecTag(graphicsContext);
            //重绘所有笔画和矩形识别框，避免画矩形时被消除
            fxImage.redrawAllBrush(graphicsContext);
            fxImage.redrawAllRecTag(graphicsContext);

            fxRecTag.setEndPoint(new FXPoint(event.getX(),event.getY()));
            fxRecTag.drawRecTag(graphicsContext);
        }
    }

    /**
     * 画布释放鼠标
     * @param event
     */
    @FXML
    void canvasMouseReleased(MouseEvent event){
        if(fxCanvas.getCanvasState()==CanvasState.DRAW){  //画曲线状态
            FXBrush newFXBrush = new FXBrush(fxBrush.getFxPoints() , graphicsContext);
            fxBrushes.add(newFXBrush);
            fxBrush.clearAllPoints();
            graphicsContext.beginPath();
        }else if(fxCanvas.getCanvasState()==CanvasState.DISTINGUISH){  //识别框状态
            FXRecTag newFXRecTag = new FXRecTag(fxRecTag.getStartPoint(),fxRecTag.getEndPoint());
            File file = newFXRecTag.savePNGImage(canvas); //保存矩形框中的图像到img/test.png，返回文件
            newFXRecTag.addDescription(distinguishBlService.opencvDistinguish(file).getValue(),graphicsContext);
            fxRecTags.add(newFXRecTag);
            graphicsContext.beginPath();
        }
    }

    /**
     * 在画布上按下鼠标
     * @param event
     */
    @FXML
    void canvasMouseRPressed(MouseEvent event){
        if(fxCanvas.getCanvasState()==CanvasState.DISTINGUISH){ //识别框状态
            FXPoint fxPoint = new FXPoint(event.getX(),event.getY());
            fxRecTag.setStartPoint(fxPoint);
            FXPoint fxEndPoint = new FXPoint(event.getX(),event.getY());
            fxRecTag.setEndPoint(fxEndPoint);
            fxRecTag.setDescription("");
        }
    }

    /**
     * 鼠标按下绘画按钮
     */
    @FXML
    void curveButtonMouseReleased(){
        distinguishButton.setEffect(null);
        DropShadow shadow = new DropShadow();
        curveButton.setEffect(shadow);
    }

    /**
     * 鼠标按下扫描按钮
     */
    @FXML
    void distinguishButtonMouseReleased(){
        curveButton.setEffect(null);
        DropShadow shadow = new DropShadow();
        distinguishButton.setEffect(shadow);
    }

    /**
     * 撤销上一笔
     */
    @FXML
    void revoke(){
        if(fxCanvas.isBrush()){
            fxImage.revokeBrush(graphicsContext);
//            System.out.println(fxImage.getBrushes().size());
            graphicsContext.beginPath();
            fxImage.redrawAllBrush(graphicsContext);
            fxImage.redrawAllRecTag(graphicsContext);
        }else{
            fxImage.revokeRecTag(graphicsContext);
//            System.out.println(fxImage.getRecTags().size());
            graphicsContext.beginPath();
            fxImage.redrawAllRecTag(graphicsContext);
            fxImage.redrawAllBrush(graphicsContext);
        }
    }

    /**
     * 完全清空
     */
    @FXML
    void clearAll(){
        fxImage.clearImage(graphicsContext);
        fxBrushes.clear();
        fxRecTags.clear();
    }

    /**
     * 打开文件
     */
    @FXML
    void openFile(){
        FXImage newImage = imageDataService.openFile();
        if(newImage != null){
            fxImage.setBrushes(newImage.getBrushes());
            fxImage.setRecTags(newImage.getRecTags());
            fxBrushes = fxImage.getBrushes();
            fxRecTags = fxImage.getRecTags();
            fxImage.redrawAllBrush(graphicsContext);
            fxImage.redrawAllRecTag(graphicsContext);
        }
    }

    /**
     * 保存文件
     */
    @FXML
    void saveFile(){
        imageDataService.saveFile(fxImage,canvas);
    }

    @FXML
    void pickColor(){
        brushColor = colorPicker.getValue();
        graphicsContext.setStroke(brushColor);
    }

}
