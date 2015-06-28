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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author mohar
 */
public class CreateLCController implements Initializable {
    @FXML
    private TextField lc_no;
    @FXML
    private TextField party_name;
    @FXML
    private TextField party_bank;
    @FXML
    private TextField party_address;
    @FXML
    private TextField our_bank;
    @FXML
    private TextField amount;
    @FXML
    private DatePicker init_date;
    @FXML
    private DatePicker start_date;
    @FXML
    private DatePicker dimilish_date;
    @FXML
    private TextField type;
    @FXML
    private Button save;
    @FXML
    private Button cancel;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void onSaveButtonClick(ActionEvent event) {
        try {
            String lc_no, party_name, party_bank, party_address, our_bank, amount, init_date, start_date, dimilish_date, type;
            lc_no = this.lc_no.getText();
            party_name = this.party_name.getText();
            party_bank = this.party_bank.getText();
            party_address = this.party_address.getText();
            our_bank = this.our_bank.getText();
            amount = this.amount.getText();
            init_date = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(this.init_date.getValue().toString()));
            start_date = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(this.start_date.getValue().toString()));
            dimilish_date = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(this.dimilish_date.getValue().toString()));
            type = this.type.getText();
            
            Unirest.post(MetaData.baseUrl + "add/lc")
                    .field("lc_number", lc_no)
                    .field("party_name", party_name)
                    .field("party_bank_name", party_bank)
                    .field("party_address", party_address)
                    .field("our_bank_name", our_bank)
                    .field("lc_amount", amount)
                    .field("initialing_date", init_date)
                    .field("starting_date", start_date)
                    .field("dimilish_date", dimilish_date)
                    .field("type", type)
                    .asString();
            
        } catch (ParseException ex) {
            Logger.getLogger(CreateLCController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnirestException ex) {
            Logger.getLogger(CreateLCController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void onCancelButtonClick(ActionEvent event) {
        this.cancel.getScene().getWindow().hide();
    }
    
}
