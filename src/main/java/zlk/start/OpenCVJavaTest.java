package zlk.start;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by better on 2014/10/4.
 */
public class OpenCVJavaTest{

    static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }

//    Mat src = Imgcodecs.imread(getClass().getClassLoader().getResource("img/19.png").getPath());
    public static void main(String[] args) {
//        System.out.println("Welcome to OpenCV " + Core.VERSION);
//        Mat m = new Mat(5, 10, CvType.CV_8UC1, new Scalar(0));
//        System.out.println("OpenCV Mat: " + m);
//        Mat mr1 = m.row(1);
//        mr1.setTo(new Scalar(1));
//        Mat mc5 = m.col(5);
//        mc5.setTo(new Scalar(5));
//        System.out.println("OpenCV Mat data:\n" + m.dump());


//        Mat src = Imgcodecs.imread(OpenCVJavaTest.class.getClassLoader().getResource("img/19.png").getPath());
//        if(src.empty()){
//            System.out.println("no file");
//        }
//        Mat dst = src.clone();
//        Imgproc.Canny(src, dst, 400, 500, 5, false);
//        Mat storage = new Mat();
//        Imgproc.HoughLines(dst, storage, 1, Math.PI / 180, 200, 0, 0, 0, 10);
//        for (int x = 0; x < storage.rows(); x++)
//        {
//            double[] vec = storage.get(x, 0);
//            double x1 = vec[0], y1 = vec[1], x2 = vec[2], y2 = vec[3];
//            Point start = new Point(x1, y1);
//            Point end = new Point(x2, y2);
//            Imgproc.line(src, start, end, new Scalar(255, 255, 255, 255), 1, Imgproc.LINE_4, 0);
//        }
//        Imgcodecs.imwrite(OpenCVJavaTest.class.getClassLoader().getResource("img/20.jpg").getPath(), src);
        Mat src = Imgcodecs.imread(OpenCVJavaTest.class.getClassLoader().getResource("img/test.png").getPath());
        Mat dst = src.clone();
//        Imgproc.blur(src, dst, new Size(9,9), new Point(-1, -1), Core.BORDER_DEFAULT);
//        Imgcodecs.imwrite(OpenCVJavaTest.class.getClassLoader().getResource("img/22.png").getPath(),dst);
        Imgproc.cvtColor(dst, dst, Imgproc.COLOR_BGRA2GRAY);
        Imgproc.adaptiveThreshold(dst, dst, 255, Imgproc.ADAPTIVE_THRESH_MEAN_C,
                Imgproc.THRESH_BINARY_INV, 3, 3);

        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(dst,contours,hierarchy,Imgproc.RETR_EXTERNAL,Imgproc.CHAIN_APPROX_SIMPLE,new Point(0,0));
        if(contours.size()==1) {
            MatOfPoint mp = contours.get(0);
            String shape = "unidentified";
            double peri;
            MatOfPoint2f mp2f = new MatOfPoint2f(contours.get(0).toArray());
            peri = Imgproc.arcLength(mp2f, true);
            //对图像轮廓点进行多边形拟合
            MatOfPoint2f polyShape = new MatOfPoint2f();
            Imgproc.approxPolyDP(mp2f, polyShape, 0.04 * peri, true);
            int shapeLen = polyShape.toArray().length;
            //根据轮廓凸点拟合结果，判断属于那个形状
            switch (shapeLen) {
                case 3:
                    shape = "triangle";
                    break;
                case 4:
                    Rect rect = Imgproc.boundingRect(mp);
                    float width = rect.width;
                    float height = rect.height;
                    float ar = width / height;
                    //计算宽高比，判断是矩形还是正方形
                    if (ar >= 0.95 && ar <= 1.05) {
                        shape = "square";
                    } else {
                        shape = "rectangle";
                    }
                    break;
                case 5:
                    shape = "pentagon";
                    break;
                default:
                    System.out.println(shapeLen);
                    shape = "circle";
                    break;
            }
            System.out.println(shape);
        }

      }
    }
