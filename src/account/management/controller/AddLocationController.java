/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package account.management.controller;

import account.management.model.MetaData;
import account.management.model.Msg;
import account.management.model.User;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * FXML Controller class
 *
 * @author mohar
 */
public class AddLocationController implements Initializable {
    @FXML
    private TextField input_name;
    @FXML
    private TextArea input_details;
    @FXML
    private Button button_submit;
    @FXML
    private Button cancel;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(!User.voucher){
            Msg.showError("You don't have permission to add location!!!");
            this.button_submit.getScene().getWindow().hide();
        }
    }    

    @FXML
    private void onSubmitButtonClick(ActionEvent event) {
        String name = "";
        if(this.input_name.getText().equals("")){
            Msg.showError("Please enter location name");
            return;
        }else{
            name = this.input_name.getText();
        }
        String details = "";
        if(!this.input_details.getText().equals("")){
            details = input_details.getText();
        }
        
        
        button_submit.setDisable(true);
        try {
            Unirest.get(MetaData.baseUrl + "add/location")
                    .queryString("name", name)
                    .queryString("details", details)
                    .asJson();
            button_submit.setDisable(false);
            Msg.showInformation("");
            this.button_submit.getScene().getWindow().hide();
        } catch (UnirestException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Sorry!! there is an error in the server. Please try again.");
            alert.setGraphic(new ImageView(new Image("resources/error.jpg")));
            alert.showAndWait();
        }
    }

    @FXML
    private void onCancelButtonClick(ActionEvent event) {
        this.cancel.getScene().getWindow().hide();
    }
    
}
