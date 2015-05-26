/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package account.management.controller;

import account.management.model.Account;
import account.management.model.MetaData;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
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
    private ComboBox<Account> account_select;
    @FXML
    private ComboBox<Account> parent_select;
    @FXML
    private TextField name_input;
    @FXML
    private TextArea description_input;
    @FXML
    private Button update_button;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        new Thread(()->{
            try {
                JSONArray account = Unirest.get(MetaData.baseUrl + "get/accounts").asJson().getBody().getArray();
                Account acc = null;
                for(int i=0; i<account.length(); i++){
                    JSONObject obj =  account.getJSONObject(i);
                    int id = Integer.parseInt(obj.get("id").toString());
                    String name = obj.get("name").toString();
                    String desc = obj.get("description").toString();
                    int parent_id = Integer.parseInt(obj.get("parent").toString());
                    
                    acc = new Account(id,name,parent_id,desc,0f);
                    account_select.getItems().add(acc);
                    parent_select.getItems().add(acc);
                }
                
                
                
                //account_select.setPromptText(acc.toString());
            } catch (UnirestException ex) {
                System.err.println("exception in unirest");            }
        }).start();
        
        
        
    }    

    @FXML
    private void onUpdateButtonClick(ActionEvent event) {
    }

    @FXML
    private void onAccountSelect(ActionEvent event) {
        Account selected_acc = this.account_select.getSelectionModel().getSelectedItem();
        List<Account> acc_lists = account_select.getItems();
        for (Account acc_list : acc_lists) {
            if (acc_list.getId() == selected_acc.getParent()) {
                parent_select.getSelectionModel().select(acc_list);
                break;
            }
        }
        name_input.setText(selected_acc.getName());
        description_input.setText(selected_acc.getDescription());
        
    }
    
}
