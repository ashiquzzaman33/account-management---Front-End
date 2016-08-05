/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package account.management.controller;

import account.management.model.MetaData;
import account.management.model.Msg;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * FXML Controller class
 *
 * @author mohar
 */
public class DeleteUserController implements Initializable {
    @FXML
    private ComboBox<String> select_user;
    @FXML
    private TextField auth_password;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            JSONArray userArray = Unirest.get(MetaData.baseUrl + "api/get/user/all").asJson().getBody().getArray();
            int len = userArray.length();
            for(int i=0; i<len; i++){
                JSONObject obj = userArray.getJSONObject(i);
                select_user.getItems().add(obj.getString("username"));
            }
        } catch (UnirestException ex) {
            Logger.getLogger(DeleteUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    private void onDeleteButtonClick(ActionEvent event) {
        String username = this.select_user.getSelectionModel().getSelectedItem();
        String auth_password = this.auth_password.getText();
        try {
            String res = Unirest.post(MetaData.baseUrl + "api/user/delete/" + username + "?auth_password=" + auth_password).asString().getBody();
            if (res.equals("1")) {
                Msg.showInformation("Success");
                this.select_user.getScene().getWindow().hide();
            } else {
                Msg.showError("");
            }
        } catch (UnirestException ex) {
            Logger.getLogger(DeleteUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
