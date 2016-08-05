/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package account.management.controller;

import account.management.model.Account;
import account.management.model.AccountType;
import account.management.model.AutoCompleteComboBoxListener;
import account.management.model.Location;
import account.management.model.MetaData;
import account.management.model.Msg;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
    @FXML
    private TextField id;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            String res = Unirest.get(MetaData.baseUrl + "get/nextAccountId").asString().getBody();
            this.id.setText("LN" + String.format("%03d", Integer.parseInt(res)));
        } catch (UnirestException ex) {
            Logger.getLogger(CreateAccountController.class.getName()).log(Level.SEVERE, null, ex);
        }

        location = FXCollections.observableArrayList();
        
        new AutoCompleteComboBoxListener<>(select_location);
        select_location.setOnHiding((e)->{
                Location a = select_location.getSelectionModel().getSelectedItem();
                select_location.setEditable(false);
                select_location.getSelectionModel().select(a);
        });
        select_location.setOnShowing((e)->{
                select_location.setEditable(true);
        });
        
        // account type
        new Thread(() -> {
            this.select_account_type.getItems().add(new AccountType("0", "None"));
            try {
                HttpResponse<JsonNode> response = Unirest.get(MetaData.baseUrl + "get/account_type").asJson();
                JSONArray account_type = response.getBody().getArray();
                for(int i = 0; i < account_type.length(); i++){
                    JSONObject obj = account_type.getJSONObject(i);
                    select_account_type.getItems().add(new AccountType(obj.get("id").toString(), obj.getString("name")));
                }
            } catch (UnirestException ex) {
                
            }finally{
                Platform.runLater(()->{
                    new AutoCompleteComboBoxListener<>(this.select_account_type);
                    this.select_account_type.setOnHiding((e)->{
                        AccountType a = this.select_account_type.getSelectionModel().getSelectedItem();
                        this.select_account_type.setEditable(false);
                        this.select_account_type.getSelectionModel().select(a);
                    });
                    this.select_account_type.setOnShowing((e)->{
                        this.select_account_type.setEditable(true);
                    });
                    this.select_account_type.getSelectionModel().select(0);
                });
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
                    location.add(new Location(id, name,details));
                }
                select_location.getItems().addAll(location);
            } catch (UnirestException ex) {
                
            }finally{
                Platform.runLater(()->{
                    this.select_location.getSelectionModel().select(0);
                });
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
            }finally{
                Platform.runLater(()->{
                    new AutoCompleteComboBoxListener<>(this.select_parent);
                    this.select_parent.setOnHiding((e)->{
                        Account a = this.select_parent.getSelectionModel().getSelectedItem();
                        this.select_parent.setEditable(false);
                        this.select_parent.getSelectionModel().select(a);
                    });
                    this.select_parent.setOnShowing((e)->{
                        this.select_parent.setEditable(true);
                    });
                });
            }
        }).start();
        
        this.input_opening_balance.setText("0");
        this.select_dr_cr.getSelectionModel().select("Dr");
        
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
            
            HttpResponse<JsonNode> res = Unirest.get(MetaData.baseUrl + "add/account")
                    .queryString("name", name)
                    .queryString("parent", parent)
                    .queryString("account_type", type)
                    .queryString("description", desc)
                    .queryString("opening_balance", 0)
                    .queryString("location", loc).asJson();
            if(res.getBody().getArray().getJSONObject(0).getString("Status").equals("Success")){
                
                String res1 = Unirest.post(MetaData.baseUrl + "voucher/new/opening-balance")
                        .field("location", loc)
                        .field("balance", String.valueOf(balance))
                        .asString().getBody();
                if(res1.equals("1")){
                    Msg.showInformation("Account has been created successfully!!!");
                    this.button_submit.getScene().getWindow().hide();
                }else{
                    Msg.showError("Please try again!");
                }
                
            }else{
                Msg.showError("");
            }
            
        } catch (Exception ex) {
            Msg.showError("");
        }
        
    }

    @FXML
    private void onCancelButtonClick(ActionEvent event) {
        this.cancel.getScene().getWindow().hide();
    }

}
