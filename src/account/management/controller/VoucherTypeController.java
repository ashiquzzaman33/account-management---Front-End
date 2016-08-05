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
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author mohar
 */
public class VoucherTypeController implements Initializable {
    @FXML
    private TextField name;
    @FXML
    private TextField note;
    @FXML
    private Button save;
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
    private void onSaveButtonClick(ActionEvent event) {
        try {
            String name = this.name.getText();
            String note = this.note.getText();
            
            Unirest.post(MetaData.baseUrl + "add/voucher_type")
                    .field("type_name", name)
                    .field("details", note)
                    .asString();
            Msg.showInformation("Success");
        } catch (UnirestException ex) {
            Logger.getLogger(VoucherTypeController.class.getName()).log(Level.SEVERE, null, ex);
            Msg.showError("");
        }
        
    }

    @FXML
    private void onCancelButtonClick(ActionEvent event) {
        this.cancel.getScene().getWindow().hide();
    }
    
}
