/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package account.management.controller;

import account.management.model.MetaData;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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

    }    

    @FXML
    private void onSubmitButtonClick(ActionEvent event) {
        String name = input_name.getText();
        String details = input_details.getText();
        
        button_submit.setDisable(true);
        try {
            Unirest.get(MetaData.baseUrl + "add/location")
                    .queryString("name", name)
                    .queryString("details", details)
                    .asJson();
            button_submit.setDisable(false);
        } catch (UnirestException ex) {
            Logger.getLogger(AddLocationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void onCancelButtonClick(ActionEvent event) {
        this.cancel.getScene().getWindow().hide();
    }
    
}
