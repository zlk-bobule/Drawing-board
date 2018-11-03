package zlk.utils;

import javafx.scene.paint.Color;
import lombok.Data;

@Data
public class FXColor {
    double red;
    double green;
    double blue;
    double opacit;
    boolean opaque;
    double hue;
    double saturation;
    double brightness;

    public FXColor(){
        this.red = 0.0;
        this.green = 0.0;
        this.blue = 0.0;
        this.opacit = 1.0;
        this.opaque = true;
        this.hue = 0.0;
        this.saturation = 0.0;
        this.brightness = 0.0;
    }

    public FXColor(Color color){
        this.red = color.getRed();
        this.green = color.getGreen();
        this.blue = color.getBlue();
        this.opacit = color.getOpacity();
        this.opaque = color.isOpaque();
        this.hue = color.getHue();
        this.saturation = color.getSaturation();
        this.brightness = color.getBrightness();
    }

    public Color turnToColor(){
        return Color.color(red,green,blue,opacit);
    }


}
