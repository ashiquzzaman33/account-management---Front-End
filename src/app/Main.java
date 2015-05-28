package app;

import account.management.model.MetaData;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource(MetaData.viewPath + "AddLocation.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/style.css");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.setFullScreen(true);
        stage.show();
        stage.setTitle("Login- Account Management (UniTech4U)");
        stage.getIcons().setAll(new Image("/unitech4u.png"));
    }


    public static void main(String[] args) {
        launch(args);
    }
}
