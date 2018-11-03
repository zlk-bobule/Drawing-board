package zlk.enums;

/**
 * 按下按钮之后的画布状态
 */
public enum CanvasState {
    INITIAL("初始状态"),
    DRAW("绘图状态"),
    DISTINGUISH("识别框状态");

    String value;

    CanvasState(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }
}
