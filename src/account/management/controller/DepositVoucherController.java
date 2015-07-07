/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package account.management.controller;

import account.management.model.EnglishNumberToWords;
import account.management.model.MetaData;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import report.Report;

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
        
        this.amount.setOnKeyReleased((e)->{
            if(!this.amount.getText().equals(""))
                this.word.setText(EnglishNumberToWords.convert(Long.parseLong(this.amount.getText())));
        });
        
    }    
    @FXML
    private void onSaveButtonClick(ActionEvent event) {
        String date = "",details = "",via = "",bank_ac = "",branch = "",address = "",amount = "",method = "",note = "",word = "";
        if(!this.date.getValue().toString().equals("")){
            try {
                date = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(this.date.getValue().toString()));
            } catch (ParseException ex) {
                
            }
        }
        if(!this.details.getText().equals(""))
            details = this.details.getText();
        if(!this.via.getText().equals(""))
            via = this.via.getText();
        if(!this.bank_ac.getText().equals(""))
            bank_ac = this.bank_ac.getText();
        if(!this.branch.getText().equals(""))
            branch = this.branch.getText();
        if(!this.address.getText().equals(""))
            address = this.address.getText();
        if(!this.amount.getText().equals(""))
            amount = this.amount.getText();
        if(!this.payment_type.getSelectionModel().getSelectedItem().equals(""))
            method = this.payment_type.getSelectionModel().getSelectedItem();
        if(!this.note.getText().equals(""))
            note = this.note.getText();
        if(!this.word.getText().equals(""))
            word = this.word.getText();
        
        try {
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
            
            Vector v = new Vector();
            v.add("aaa");
            HashMap params = new HashMap();
            params.put("voucher_no", id);
            params.put("date", date);
            params.put("description", details);
            params.put("received_from", via);
            params.put("bank_account", bank_ac);
            params.put("branch", branch);
            params.put("address", address);
            params.put("amount", amount);
            params.put("word", word);
            params.put("note", note);
            
            Report r = new Report();
            r.getReport("src\\report\\DepositVoucher.jrxml", new JRBeanCollectionDataSource(v), params);
            
        } catch (UnirestException ex) {
            Logger.getLogger(DepositVoucherController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @FXML
    private void onCancelButtonClick(ActionEvent event) {
        this.cancel.getScene().getWindow().hide();
    }
    
}
