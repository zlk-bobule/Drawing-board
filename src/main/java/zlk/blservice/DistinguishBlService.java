package zlk.blservice;

import zlk.enums.Graphics;
import zlk.uiModel.FXImage;
import zlk.uiModel.FXRecTag;

import java.io.File;

public interface DistinguishBlService {
    /**
     * 识别矩形框中的图形（基础笔画识别）
     * 通过识别矩形框中的笔画数来判断图形，一笔为圆，三笔为三角形，四笔为长方形，其他笔数显示未识别
     */
    Graphics distinguish(FXRecTag fxRecTag, FXImage fxImage);

    /**
     * opencv识别矩形框中的图形
     * @param file
     * @return
     */
    Graphics opencvDistinguish(File file);

}