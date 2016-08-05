/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package account.management.controller;

import account.management.model.AutoCompleteComboBoxListener;
import account.management.model.Location;
import account.management.model.MetaData;
import account.management.model.Msg;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * FXML Controller class
 *
 * @author Amimul Ahsan
 */
public class BankAccountController implements Initializable {
    @FXML
    private TextField nalme;
    @FXML
    private TextArea note;
    @FXML
    private TextField balance;
    @FXML
    private ComboBox<String> dr_cr;
    @FXML
    private Button save;
    @FXML
    private Button cancel;
    @FXML
    private ComboBox<Location> location;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        new AutoCompleteComboBoxListener<>(location);
        location.setOnHiding((e)->{
                Location a = location.getSelectionModel().getSelectedItem();
                location.setEditable(false);
                location.getSelectionModel().select(a);
        });
        location.setOnShowing((e)->{
                location.setEditable(true);
        });
        
        
        // dr_ce combobox
        this.dr_cr.getItems().add("Dr");
        this.dr_cr.getItems().add("Cr");
        
        // location combobox
        new Thread(()->{
            try {
                HttpResponse<JsonNode> res = Unirest.get(MetaData.baseUrl + "get/locations").asJson();
                JSONArray array = res.getBody().getArray();
                for(int i=0; i<array.length(); i++){
                    JSONObject obj = array.getJSONObject(i);
                    this.location.getItems().add(new Location(obj.getInt("id"), obj.getString("name"), obj.getString("details")));
                }
            } catch (UnirestException ex) {
                Logger.getLogger(BankAccountController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();
        
    }    

    @FXML
    private void onSaveButtonClick(ActionEvent event) {
        String name = this.nalme.getText();
        String note = this.note.getText();
        String balance = this.balance.getText();

        try {
            float bal = 0;
            if(dr_cr.getSelectionModel().getSelectedItem().equals("Cr")){
                bal = Float.parseFloat(balance);
                bal *= -1f;
            }else{
                bal = Float.parseFloat(balance);
            }
            System.out.println("start");
            Unirest.get(MetaData.baseUrl + "add/account")
                    .queryString("name",name)
                    .queryString("parent","22")
                    .queryString("account_type","1")
                    .queryString("description",note)
                    .queryString("opening_balance", String.valueOf(bal))
                    .queryString("location",location.getSelectionModel().getSelectedItem().getId())
                    .asJson();
            Msg.showInformation("Success");
        } catch (Exception ex) {
            Msg.showError("");
        }
        
    }

    @FXML
    private void onCancelButtonClick(ActionEvent event) {
    }
    
}
