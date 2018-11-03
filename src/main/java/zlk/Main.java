package zlk;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static Stage primaryStage;
    @Override
    public void start(Stage primaryStage) {
        try {
            Main.primaryStage = primaryStage;
            System.out.println(this.getClass().getResource("/view/Board.fxml").getPath());
            Parent root = FXMLLoader.load(this.getClass().getResource("/view/Board.fxml"));

            Scene scene = new Scene(root,720,531);
            scene.getStylesheets().add(getClass().getClassLoader().getResource("css/button.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);//设置不能窗口改变大小
            primaryStage.setTitle("Drawing board");//设置标题
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}
