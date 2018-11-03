package zlk.dataservice;

import javafx.scene.canvas.Canvas;
import zlk.uiModel.FXImage;

public interface ImageDataService {

    /**
     * 保存文件（格式可选择txt格式或png格式）
     */
    void saveFile(FXImage fxImage, Canvas canvas);

    /**
     * 打开文件，读取到画布上（规定只能读取txt格式）
     */
    FXImage openFile();

}
