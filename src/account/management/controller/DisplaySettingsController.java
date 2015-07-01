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
import javafx.scene.control.ColorPicker;

/**
 * FXML Controller class
 *
 * @author Amimul Ahsan
 */
public class DisplaySettingsController implements Initializable {
    @FXML
    private ColorPicker bg;
    @FXML
    private ColorPicker font;
    @FXML
    private Button apply;
    @FXML
    private Button default_color;
    @FXML
    private Button cancel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void onApplyClick(ActionEvent event) {
        String bg = this.bg.getValue().toString();
        String font = this.font.getValue().toString();
        
        new Thread(()->{
            try {
                Unirest.get(MetaData.baseUrl + "update/settings")
                        .queryString("key","background")
                        .queryString("value",bg)
                        .asString();
                Unirest.get(MetaData.baseUrl + "update/settings")
                        .queryString("key","font")
                        .queryString("value",font)
                        .asString();
                
            } catch (UnirestException ex) {
                Logger.getLogger(DisplaySettingsController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();
        
    }

    @FXML
    private void onDefaultClick(ActionEvent event) {
        
    }

    @FXML
    private void onCancelClick(ActionEvent event) {
        this.cancel.getScene().getWindow().hide();
    }
    
}
