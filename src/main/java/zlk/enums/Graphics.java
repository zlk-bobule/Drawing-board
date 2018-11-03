package zlk.enums;

public enum Graphics {
    TRIANGLE("三角形"),
    CIRCLE("圆"),
    RECTANGLE("长方形"),
    SQUARE("正方形"),
    NOTDISTINGUISH("未识别");

    String value;

    Graphics(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }
}
