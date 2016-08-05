/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package account.management.controller;

import account.management.model.Account;
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
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * FXML Controller class
 *
 * @author unitech
 */
public class ViewBankAccountController implements Initializable {
    @FXML
    private TableView<Account> table;
    @FXML
    private TableColumn<Account, Integer> id;
    @FXML
    private TableColumn<Account, String> name;
    @FXML
    private TableColumn<Account, String> description;
    
    private List<Account> bank_list;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bank_list = FXCollections.observableArrayList();
        id.setCellValueFactory(new PropertyValueFactory("id"));
        name.setCellValueFactory(new PropertyValueFactory("name"));
        description.setCellValueFactory(new PropertyValueFactory("description"));
        new Thread(()->{
            try {
                HttpResponse<JsonNode> res = Unirest.get(MetaData.baseUrl + "bank").asJson();
                JSONArray array = res.getBody().getArray();
                for(int i=0; i<array.length(); i++){
                    JSONObject obj = array.getJSONObject(i);
                    int id = obj.getInt("id");
                    String name = obj.getString("name");
                    String desc = obj.getString("description");
                    Account acc = new Account(id,name,desc);
                    bank_list.add(acc);
                }
            } catch (UnirestException ex) {
                Msg.showError("");
            }finally{
                this.table.getItems().addAll(bank_list);
            }
        }).start();
    }    
    
}
