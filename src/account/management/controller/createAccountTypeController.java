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
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
        String name = this.input_account_type_name.getText();
        String note = this.input_note.getText();
        
        new Thread(()->{
            try { 
                JSONArray response = Unirest.post(MetaData.baseUrl + "add/account_type")
                        .queryString("type_name", name)
                        .queryString("details", note)
                        .asJson().getBody().getArray();
                
            } catch (UnirestException ex) {
                System.out.println("error");
            }
        }).start();
        
    }
    
}
