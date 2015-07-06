/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package account.management.controller;

import account.management.model.Location;
import account.management.model.MetaData;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * FXML Controller class
 *
 * @author mohar
 */
public class DepositVoucherController implements Initializable {
    @FXML
    private DatePicker date;
    @FXML
    private TextArea details;
    @FXML
    private TextField via;
    @FXML
    private TextField bank_ac;
    @FXML
    private TextField branch;
    @FXML
    private TextArea address;
    @FXML
    private TextField amount;
    @FXML
    private TextArea note;
    @FXML
    private TextField word;
    @FXML
    private Button save;
    @FXML
    private Button cancel;
    @FXML
    private ComboBox<String> payment_type;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //nogod, titi, online
        this.payment_type.getItems().addAll("নগদ","টিটি","অনলাইন");
    }    

    private void onSubmitButtonClick(ActionEvent event) {
        try {
            String date = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(this.date.getValue().toString()));
            String details = this.details.getText();
            String via = this.via.getText();
            String bank_ac = this.bank_ac.getText();
            String branch = this.branch.getText();
            String address = this.address.getText();
            String amount = this.amount.getText();
            String method = this.payment_type.getSelectionModel().getSelectedItem();
            String note = this.note.getText();
            String word = this.word.getText();
            
            HttpResponse<String> res = Unirest.get(MetaData.baseUrl + "add/deposit/voucher")
                    .queryString("date",date)
                    .queryString("details",details)
                    .queryString("via",via)
                    .queryString("bank_ac",bank_ac)
                    .queryString("branch",branch)
                    .queryString("address",address)
                    .queryString("amount",amount)
                    .queryString("method",method)
                    .queryString("note",note)
                    .queryString("word",word)
                    .asString();
            String id = res.getBody();
            
        } catch (ParseException ex) {
            Logger.getLogger(DepositVoucherController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnirestException ex) {
            Logger.getLogger(DepositVoucherController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void onSaveButtonClick(ActionEvent event) {
        
    }

    @FXML
    private void onCancelButtonClick(ActionEvent event) {
        this.cancel.getScene().getWindow().hide();
    }
    
}
