package zlk.blservice.blserviceImpl;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import zlk.blservice.DistinguishBlService;
import zlk.enums.Graphics;
import zlk.uiModel.FXBrush;
import zlk.uiModel.FXImage;
import zlk.uiModel.FXPoint;
import zlk.uiModel.FXRecTag;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DistinguishBlServiceImpl implements DistinguishBlService {

    public Graphics distinguish(FXRecTag fxRecTag, FXImage fxImage){
        double startX = Math.min(fxRecTag.getStartPoint().getX(),fxRecTag.getEndPoint().getX());
        double startY = Math.min(fxRecTag.getStartPoint().getY(),fxRecTag.getEndPoint().getY());
        double endX = Math.max(fxRecTag.getEndPoint().getX(),fxRecTag.getEndPoint().getX());
        double endY = Math.max(fxRecTag.getEndPoint().getY(),fxRecTag.getEndPoint().getY());
        ArrayList<FXBrush> brushes = fxImage.getBrushes();
        ArrayList<FXBrush> validBrushes = new ArrayList<>();
        int brushNum = 0;
        for(FXBrush fxBrush : brushes){
            boolean isIn = true;
            for(FXPoint fxPoint : fxBrush.getFxPoints()){
                if(fxPoint.getX()<startX||fxPoint.getX()>endX||fxPoint.getY()<startY||fxPoint.getY()>endY){
                    isIn = false;
                }
            }
            if(isIn){
                brushNum = brushNum+1;
                validBrushes.add(fxBrush);
            }
        }

        switch (brushNum){
            case 1:
                return Graphics.CIRCLE;
            case 3:
                return Graphics.TRIANGLE;
            case 4:
                double distance_1 = getDistance(validBrushes.get(0));
                double distance_2 = getDistance(validBrushes.get(1));
                double distance_3 = getDistance(validBrushes.get(2));
                double distance_4 = getDistance(validBrushes.get(3));
                double ratio = 0.15;
                if(Math.abs(distance_1-distance_2)<=distance_1*ratio&&Math.abs(distance_2-distance_3)<=distance_2*ratio&&Math.abs(distance_3-distance_4)<=distance_3*ratio&&Math.abs(distance_4-distance_1)<=distance_4*ratio){
                    return Graphics.SQUARE;
                }else {
                    return Graphics.RECTANGLE;
                }
            default:
                return Graphics.NOTDISTINGUISH;
        }
    }

    private double getDistance(FXBrush fxBrush){
        ArrayList<FXPoint> points = fxBrush.getFxPoints();
        FXPoint startPoint = points.get(0);
        FXPoint endPoint = points.get(points.size()-1);
        return Math.pow(Math.pow(startPoint.getX()-endPoint.getX(),2)+Math.pow(startPoint.getY()-endPoint.getY(),2),0.5);
    }

    public Graphics opencvDistinguish(File file){
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        //读入图片
        Mat src = Imgcodecs.imread(file.getPath());
        Mat dst = src.clone();
        Imgproc.cvtColor(dst, dst, Imgproc.COLOR_BGRA2GRAY);
        Imgproc.adaptiveThreshold(dst, dst, 255, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY_INV, 3, 3);
        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(dst,contours,hierarchy,Imgproc.RETR_EXTERNAL,Imgproc.CHAIN_APPROX_SIMPLE,new org.opencv.core.Point(0,0));
        Graphics graphics = Graphics.NOTDISTINGUISH;

        for(MatOfPoint contour : contours){
            MatOfPoint2f mp2f = new MatOfPoint2f(contour.toArray());
            double peri = Imgproc.arcLength(mp2f,true);
            //对图像轮廓点进行多边形拟合
            MatOfPoint2f polyShape = new MatOfPoint2f();
            Imgproc.approxPolyDP(mp2f,polyShape,0.04*peri,true);
            int sharpPointNum = polyShape.toArray().length;
            switch (sharpPointNum){
                case 0:
                case 1:
                case 2:
                    break;
                case 3:
                    graphics = Graphics.TRIANGLE;
                    break;
                case 4:
                    Rect rect = Imgproc.boundingRect(contour);
                    float propotion = rect.width/rect.height;
                    //判断是长方形还是正方形
                    if(Math.abs(1-propotion)<=0.05){
                        graphics = Graphics.SQUARE;
                    }else{
                        graphics = Graphics.RECTANGLE;
                    }
                    break;
                default:
                    graphics = Graphics.CIRCLE;
                    break;
            }
            //识别出结果，跳出循环
            if(graphics!=Graphics.NOTDISTINGUISH){
                break;
            }
        }
        return graphics;
    }

}
