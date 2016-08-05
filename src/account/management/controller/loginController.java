/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package account.management.controller;

import account.management.model.MetaData;
import account.management.model.Msg;
import account.management.model.User;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Raju
 */
public class loginController implements Initializable{
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private Button login_btn;
    @FXML
    private ImageView logo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logo.setImage(new Image("Utopia.png"));
    }

    @FXML
    private void onLoginButtonClick(ActionEvent event) {
        try {
                
            String username = this.username.getText();
            String password = this.password.getText();
            
            User.username = username;
            
            this.login_btn.setDisable(true);
            this.login_btn.setText("Loading...");
            Thread t = new Thread(() -> {
                try {
                    HttpResponse<JsonNode> res = Unirest.get(MetaData.baseUrl + "login")
                            .queryString("username", username)
                            .queryString("password", password)
                            .asJson();
                    JSONArray array = res.getBody().getArray();
                    JSONObject obj = array.getJSONObject(0);
                    if(obj.getInt("inventory") == 1) User.inventory = true;
                    if(obj.getInt("project") == 1) User.project = true;
                    if(obj.getInt("lc") == 1) User.lc = true;
                    if(obj.getInt("cnf") == 1) User.cnf = true;
                    if(obj.getInt("deposit_voucher") == 1) User.deposit_voucher = true;
                    if(obj.getInt("expense_voucher") == 1) User.expense_voucher = true;
                    if(obj.getInt("sell") == 1) User.sell = true;
                    if(obj.getInt("purchase") == 1) User.purchase = true;
                    if(obj.getInt("party_create") == 1) User.party_create = true;
                    if(obj.getInt("ledger_create") == 1) User.ledger = true;
                    if(obj.getInt("voucher") == 1) User.voucher = true;
                    if(obj.getInt("bank") == 1) User.bank = true;
                    if(obj.getInt("inventory_report") == 1) User.inventory_report = true;
                    if(obj.getInt("trial_balance") == 1) User.trial_balance = true;
                    if(obj.getInt("balance_sheet") == 1) User.balance_sheet = true;
                    if(obj.getInt("financial_statement") == 1) User.financial_statement = true;
                    if(obj.getInt("database_maintanance") == 1) User.database_maintanance = true;
                    
                    Platform.runLater(()->{
                        try {
                            this.login_btn.getScene().getWindow().hide();
                            Parent root;
                            root = FXMLLoader.load(getClass().getResource(MetaData.viewPath + "home1.fxml"));
                            Scene scene = new Scene(root);
                            scene.getStylesheets().add("/style.css");
                            Stage stage = new Stage();
                            scene.setRoot(root);
                            stage.setResizable(true);
                            stage.setTitle("Home");
                            stage.setScene(scene);
                            stage.showAndWait();
                        } catch (IOException ex) {
                            Logger.getLogger(loginController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                    
                } catch (Exception ex) {
                    Platform.runLater(() -> {
                        Msg.showError("Username or password is incorrect");
                    });
                }finally{
                    login_btn.setDisable(false);
                    Platform.runLater(() -> {
                        login_btn.setText("Login");
                        
                    });
                    
                }
            });
            t.start();
            
        } catch (Exception e) {
            Msg.showError("Sorry. There is an error. Please try again");
        }
    }
    
}
