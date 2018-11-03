package zlk.dataservice.dataserviceImpl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import zlk.dataservice.ImageDataService;
import zlk.uiModel.FXImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class ImageDataServiceImpl implements ImageDataService {
    private ObjectMapper objectMapper = new ObjectMapper();
//    private static String fileSeparator = System.getProperty("file.separator");

    public void saveFile(FXImage fxImage, Canvas canvas){
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter1 = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        FileChooser.ExtensionFilter extFilter2 = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
        fileChooser.getExtensionFilters().add(extFilter1);
        fileChooser.getExtensionFilters().add(extFilter2);
        File file = fileChooser.showSaveDialog(null);

        if(file != null){
            try{
//                if(file.exists()){
//                    file.delete();
//                    file.createNewFile();
//                }
                if(fileChooser.getSelectedExtensionFilter()==extFilter1){ //将文件存成txt格式
                    FileWriter fileWriter = new FileWriter(file, false);
                    BufferedWriter writer = new BufferedWriter(fileWriter);
                    writer.write(objectMapper.writeValueAsString(fxImage));
                    writer.close();
                }else{ //将文件存为png格式
                    WritableImage writableImage = new WritableImage(724,504);
                    canvas.snapshot(null,writableImage);
                    BufferedImage bufferedImage = SwingFXUtils.fromFXImage(writableImage,null);

                    try{
                        ImageIO.write(bufferedImage,"png",file);
                    }catch (IOException e){
                        throw new RuntimeException(e);
                    }
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public FXImage openFile(){
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("TXT files(*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extensionFilter);
        File file = fileChooser.showOpenDialog(null);

        if(file == null){
            return null;
        }
        try{
            FileReader fileReader = new FileReader(file);
            BufferedReader reader = new BufferedReader(fileReader);
            String line;
            String jsonString = "";
            while((line = reader.readLine()) != null){
                jsonString += line;
            }
            reader.close();

//            System.out.println(jsonString);
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            FXImage fxImage = objectMapper.readValue(jsonString, FXImage.class);
            return fxImage;

        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

}
