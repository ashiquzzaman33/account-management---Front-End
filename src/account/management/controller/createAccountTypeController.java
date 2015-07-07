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
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import org.json.JSONArray;

/**
 *
 * @author mohar
 */
public class createAccountTypeController implements Initializable{
    @FXML
    private Pane title_pane;
    @FXML
    private Label title_label;
    @FXML
    private AnchorPane main_container;
    @FXML
    private TextField input_account_type_name;
    @FXML
    private TextArea input_note;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

    @FXML
    private void onSubmitButtonClick(ActionEvent event) {

        
        new Thread(()->{
            try { 
                 String name = this.input_account_type_name.getText();
                 String note = this.input_note.getText();
                JSONArray response = Unirest.post(MetaData.baseUrl + "add/account_type")
                        .queryString("type_name", name)
                        .queryString("details", note)
                        .asJson().getBody().getArray();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Account type has been created successfully!");
                alert.setGraphic(new ImageView(new Image("resources/success.jpg")));
                alert.showAndWait();
                
                
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Sorry!! there is an error. Please try again.");
                alert.setGraphic(new ImageView(new Image("resources/error.jpg")));
                alert.showAndWait();
            }
        }).start();
        
    }
    
}
