package app;

import account.management.model.MetaData;
import account.management.model.Settings;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;
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
        
//        new Thread(()->{
//            try {
//                HttpResponse<JsonNode> res = Unirest.get(MetaData.baseUrl + "get/settings").asJson();
//                JSONArray array = res.getBody().getArray();
//                for(int i=0; i<array.length(); i++){
//                    JSONObject obj = array.getJSONObject(i);
//                    if(obj.getString("key").equals("background")){
//                        Settings.bg = obj.getString("value");
//                    }
//                    if(obj.getString("key").equals("default_background")){
//                        Settings.default_bg = obj.getString("key");
//                    }
//                }
//            } catch (UnirestException ex) {
//                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }).start();
        
        
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
