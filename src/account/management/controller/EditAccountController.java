/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package account.management.controller;

import account.management.model.Account;
import account.management.model.AccountType;
import account.management.model.AutoCompleteComboBoxListener;
import account.management.model.MetaData;
import account.management.model.Msg;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.net.URL;
import java.util.List;
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
 * @author Raju
 */
public class EditAccountController implements Initializable {
    @FXML
    private ComboBox<Account> account;
    @FXML
    private TextField name;
    @FXML
    private ComboBox<Account> parent;
    @FXML
    private TextArea note;
    @FXML
    private ComboBox<AccountType> account_type;
    @FXML
    private Button update;
    @FXML
    private Button cancel;
    
    private List<Account> accountList;
    private List<AccountType> accounTypetList;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        new AutoCompleteComboBoxListener<>(parent);
        parent.setOnHiding((e)->{
                Account a = parent.getSelectionModel().getSelectedItem();
                parent.setEditable(false);
                parent.getSelectionModel().select(a);
        });
        parent.setOnShowing((e)->{
                parent.setEditable(true);
        });
        
        new AutoCompleteComboBoxListener<>(account_type);
        account_type.setOnHiding((e)->{
                AccountType a = account_type.getSelectionModel().getSelectedItem();
                account_type.setEditable(false);
                account_type.getSelectionModel().select(a);
        });
        account_type.setOnShowing((e)->{
                account_type.setEditable(true);
        });
        
        
        accountList = FXCollections.observableArrayList();
        accounTypetList = FXCollections.observableArrayList();
        new Thread(()->{
            
            try {
                HttpResponse<JsonNode> res = Unirest.get(MetaData.baseUrl + "get/accounts").asJson();
                JSONArray array = res.getBody().getArray();
                for(int i=0; i<array.length(); i++){
                    JSONObject obj = array.getJSONObject(i);
                    
                    String id = obj.get("id").toString();
                    String name = obj.getString("name");
                    String parent = obj.get("parent").toString();
                    String desc = obj.getString("description");
                    String account_type = obj.get("account_type").toString();
                    accountList.add(new Account(id,name,parent,desc,account_type));
                }
            } catch (UnirestException ex) {
                
            }finally{
                this.account.getItems().addAll(accountList);
                this.parent.getItems().addAll(accountList);
                Platform.runLater(()->{
                    new AutoCompleteComboBoxListener<>(this.account);
                    this.account.setOnHiding((e)->{
                        Account a = this.account.getSelectionModel().getSelectedItem();
                        this.account.setEditable(false);
                        this.account.getSelectionModel().select(a);
                    });
                    this.account.setOnShowing((e)->{
                        this.account.setEditable(true);
                    });
                });
                
            }
            
        }).start();
        
        new Thread(()->{
            try {
                HttpResponse<JsonNode> res = Unirest.get(MetaData.baseUrl + "get/account/type").asJson();
                JSONArray array = res.getBody().getArray();
                accounTypetList.add(new AccountType("0", "None"));
                for(int i=0; i<array.length(); i++){
                    JSONObject obj = array.getJSONObject(i);
                    String id = obj.getString("id");
                    String name = obj.getString("name");
                    accounTypetList.add(new AccountType(id, name));
                }
                
            } catch (UnirestException ex) {
                Logger.getLogger(EditAccountController.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
                this.account_type.getItems().addAll(accounTypetList);
            }
        }).start();
        
    }

    @FXML
    private void onAccountSelect(ActionEvent event) {
        this.name.setText(this.account.getSelectionModel().getSelectedItem().getName());
        int parent_id = this.account.getSelectionModel().getSelectedItem().getParent();
        for(int i=0; i<accountList.size(); i++){
            if(accountList.get(i).getId() == parent_id){
                this.parent.getSelectionModel().select(accountList.get(i));
                break;
            }
        }
        
        this.note.setText(this.account.getSelectionModel().getSelectedItem().getDescription());
        
        
    }

    @FXML
    private void onUpdateButtonClick(ActionEvent event) {
        try {
            String account = String.valueOf(this.account.getSelectionModel().getSelectedItem().getId());
            String name = this.name.getText();
            String new_parent = String.valueOf(this.parent.getSelectionModel().getSelectedItem().getId());
            String old_parent = String.valueOf(this.account.getSelectionModel().getSelectedItem().getParent());
            String note = this.note.getText();
            String type = this.account_type.getSelectionModel().getSelectedItem().getId();

            Unirest.get(MetaData.baseUrl + "edit/account")
                    .queryString("id", account)
                    .queryString("name", name)
                    .queryString("parent", new_parent)
                    .queryString("description", note)
                    .queryString("account_type", type)
                    .queryString("old_parent", old_parent)
                    .queryString("new_parent", new_parent)
                    .asJson();
            Msg.showInformation("Account has been updated successfully");
        } catch (Exception e) {
            Msg.showError("");
        }
        
    }

    @FXML
    private void onCancelButtonClick(ActionEvent event) {
        this.cancel.getScene().getWindow().hide();
    }
}
