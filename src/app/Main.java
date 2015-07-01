package app;

import account.management.model.MetaData;
import java.io.BufferedReader;
import java.io.FileReader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception{
        BufferedReader br = new BufferedReader(new FileReader("src\\url.txt"));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                //sb.append(System.lineSeparator());
                line = br.readLine();
            }
            String everything = sb.toString();
            MetaData.baseUrl = everything;
            //MetaData.baseUrl = everything;
        } finally {
            br.close();
        }
        Parent root = FXMLLoader.load(getClass().getResource(MetaData.viewPath + "newVoucher.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/style.css");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.setFullScreen(false);
        stage.show();
        stage.setTitle("Login- Account Management (UniTech4U)");
        stage.getIcons().setAll(new Image("/unitech4u.png"));
        
    }


    public static void main(String[] args) {
        launch(args);
    }
}
