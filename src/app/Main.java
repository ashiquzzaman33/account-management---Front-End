package app;

import account.management.model.MetaData;
import account.management.model.Msg;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Date;
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
        /*
        *   Report ledger (initialize) and new voucher controller (submit) security check added
        */
//        if(new Date().getTime() > 1469117502290l){
//            Msg.showError("Software is expired. Please Contact with UniTech4U. Phone: +8801611200027");
//            System.exit(0);
//        }else{
//            //System.out.println("not expired");
//        }
        
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
        
        
        Parent root = FXMLLoader.load(getClass().getResource(MetaData.viewPath + "login.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/style.css");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.setFullScreen(false);
        stage.show();
        stage.setTitle("Uni Accounts - Login");
        stage.getIcons().setAll(new Image("/unitech4u.png"));
    }


    public static void main(String[] args) {
        launch(args);
    }
}
