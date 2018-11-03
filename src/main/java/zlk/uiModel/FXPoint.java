package zlk.uiModel;

/**
 * 点
 */
public class FXPoint {
    /**
     * x坐标
     */
    private double x;
    /**
     * y坐标
     */
    private double y;

    public FXPoint(){}

    public FXPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

}
