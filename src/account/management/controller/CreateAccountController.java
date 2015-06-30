/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package account.management.controller;

import account.management.model.Account;
import account.management.model.AccountType;
import account.management.model.Location;
import account.management.model.MetaData;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * FXML Controller class
 *
 * @author mohar
 */
public class CreateAccountController implements Initializable {
    @FXML
    private TextField input_account_name;
    @FXML
    private TextArea input_description;
    @FXML
    private ComboBox<Account> select_parent;
    @FXML
    private Button button_submit;
    @FXML
    private TextField input_opening_balance;
    @FXML
    private ComboBox<String> select_dr_cr;
    
    private Collection<Account> account;
    private Collection<Location> location;
    @FXML
    private ComboBox<Location> select_location;
    @FXML
    private ComboBox<AccountType> select_account_type;
    @FXML
    private Button cancel;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        

        location = FXCollections.observableArrayList();
        
        // account type
        new Thread(() -> {
            try {
                HttpResponse<JsonNode> response = Unirest.get(MetaData.baseUrl + "get/account_type").asJson();
                JSONArray account_type = response.getBody().getArray();
                for(int i = 0; i < account_type.length(); i++){
                    JSONObject obj = account_type.getJSONObject(i);
                    select_account_type.getItems().add(new AccountType(Integer.parseInt(obj.get("id").toString()), obj.get("type_name").toString(), obj.get("details").toString()));
                }
            } catch (UnirestException ex) {
                
            }
        }).start();
        
        
        
        /*
        *   add location to combo box
        */
        new Thread(() -> {
            try {
                HttpResponse<JsonNode> response = Unirest.get(MetaData.baseUrl + "get/locations").asJson();
                JSONArray locationArray = response.getBody().getArray();
                for(int i=0; i<locationArray.length(); i++){
                    JSONObject obj = locationArray.getJSONObject(i);
                    int id = Integer.parseInt(obj.get("id").toString());
                    String name = obj.get("name").toString();
                    String details = obj.get("details").toString();
                    // if the location is not "none" then add it
                    if(id != 1) location.add(new Location(id, name,details));
                }
                select_location.getItems().addAll(location);
            } catch (UnirestException ex) {
                
            }
        }).start();
        
        new Thread(() -> {
            try {
                account = FXCollections.observableArrayList();
                /*
                *   add dr/cr options in select_dr_cr combobox
                */
                select_dr_cr.setItems(FXCollections.observableArrayList("Dr","Cr"));
                HttpResponse<JsonNode> response = Unirest.get(MetaData.baseUrl +"get/accounts").asJson();

                JSONArray array = response.getBody().getArray();
                for(int i=6; i<array.length();i++){
                    
                    JSONObject obj = array.getJSONObject(i);
                    int id = Integer.parseInt(obj.get("id").toString());
                    String name = obj.get("name").toString();
                    int parent = Integer.parseInt(obj.get("parent").toString());
                    String desc = obj.get("description").toString();
                    
                    account.add(new Account(id, name, parent, desc,0f));
                }
                
                select_parent.getItems().addAll(account);
            } catch (UnirestException ex) {
                Logger.getLogger(CreateAccountController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();
    }
    
    @FXML
    private void onSubmitButtonClick(ActionEvent event) {
        try {
            String name, parent, type, desc, loc, dr_cr;
            float balance;
            name = this.input_account_name.getText();
            parent = String.valueOf(this.select_parent.getSelectionModel().getSelectedItem().getId());
            type = String.valueOf(this.select_account_type.getSelectionModel().getSelectedItem().getId());
            desc = this.input_description.getText();
            balance = Float.parseFloat(this.input_opening_balance.getText());
            dr_cr = this.select_dr_cr.getSelectionModel().getSelectedItem();
            if(dr_cr.equals("Cr")){
                balance *= -1.0f;
            }   loc = String.valueOf(this.select_location.getSelectionModel().getSelectedItem().getId());
            
            Unirest.post(MetaData.baseUrl + "add/account")
                    .field("name", name)
                    .field("parent", parent)
                    .field("account_type", type)
                    .field("description", desc)
                    .field("opening_balance", String.valueOf(balance))
                    .field("location", loc).asString();
            
        } catch (UnirestException ex) {
            Logger.getLogger(CreateAccountController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void onCancelButtonClick(ActionEvent event) {
        this.cancel.getScene().getWindow().hide();
    }

}
